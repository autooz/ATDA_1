package mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus;
/*
 * Copyright (C) 2015 Mr.Simple <bboyfeiyu@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.handler.AsyncEventHandler;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.handler.DefaultEventHandler;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.handler.EventHandler;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.handler.UIThreadEventHandler;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.matchpolicy.DefaultMatchPolicy;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.matchpolicy.MatchPolicy;

/**
 * <p/>
 * * EventBus AndroidEventBus framework is the core class, but also the user's entry class. It stores the subscriber information and methods of user registration,
 * Event type and the event corresponding tag identifies a variety of events {see EventType}, each event corresponds with one or more subscribers {see Subscription}
 * Subscribers in the subscription function by {see Subcriber} annotation to identify tag and thread model, which makes a more user-friendly examination, the code neater.
 * <p/>
 * Users will need to subscribers before posting events registered by @ { see #register (Object)} method to EventBus in, EventBus will resolve the subscriber uses the
 * {see Subcriber} identification function, and they were to {see EventType} is key, with {see Subscription}
 * List the value stored in the map. When users post an event to find the corresponding subscribers through events to map and follow the thread model subscribe function will function at the corresponding thread.
 * <p/>
 * Finally, when not need to subscribe to the event, should call {see #unregister (Object)} function logout of the object to avoid memory leaks!
 * For example, cancellation of Activity or Fragment subscription in Activity or Fragment of onDestory function.
 * <p/>
 * Note: If you publish an event parameter type is a subclass of the event parameters to subscribe, subscribe default function will be executed. For example, you subscribe to the subscription function is List <String> type of event,
 * However, when was released is ArrayList <String> events
 * So List <String> is a generic abstract, but ArrayList <String> is the specific implementation
 * , So in this case the function will be executed subscription. If you need to subscribe to a function can receive an event type must match exactly, you can construct a EventBusConfig object
 * Then set MatchPolicy event and then use the bus before using the EventBusConfig to initialize event bus <code>
 * EventBusConfig config = new EventBusConfig();
 * config.setMatchPolicy(new StrictMatchPolicy());
 * EventBus.getDefault().initWithConfig(config);
 * </code>
 *
 * @author mrsimple
 */
public final class EventBus {

    /**
     * default descriptor
     */
    private static final String DESCRIPTOR = EventBus.class.getSimpleName();

    /**
     * Event Bus descriptor
     */
    private String mDesc = DESCRIPTOR;

    /**
     * EventType-Subcriptions map
     */
    private final Map<EventType, CopyOnWriteArrayList<Subscription>> mSubcriberMap = new ConcurrentHashMap<EventType, CopyOnWriteArrayList<Subscription>>();
    /**
     *
     */
    private List<EventType> mStickyEvents = Collections
            .synchronizedList(new LinkedList<EventType>());
    /**
     * the thread local event queue, every single thread has it's own queue.
     */
    ThreadLocal<Queue<EventType>> mLocalEvents = new ThreadLocal<Queue<EventType>>() {
        protected java.util.Queue<EventType> initialValue() {
            return new ConcurrentLinkedQueue<EventType>();
        }

        ;
    };

    /**
     * the event dispatcher
     */
    EventDispatcher mDispatcher = new EventDispatcher();

    /**
     * the subscriber method hunter, find all of the subscriber's methods
     * annotated with @Subcriber
     */
    SubsciberMethodHunter mMethodHunter = new SubsciberMethodHunter(mSubcriberMap);

    /**
     * The Default EventBus instance
     */
    private static EventBus sDefaultBus;

    /**
     * private Constructor
     */
    private EventBus() {
        this(DESCRIPTOR);
    }

    /**
     * constructor with desc
     *
     * @param desc the descriptor of eventbus
     */
    public EventBus(String desc) {
        mDesc = desc;
    }

    /**
     * @return
     */
    public static EventBus getDefault() {
        if (sDefaultBus == null) {
            synchronized (EventBus.class) {
                if (sDefaultBus == null) {
                    sDefaultBus = new EventBus();
                }
            }
        }
        return sDefaultBus;
    }

    /**
     * register a subscriber into the mSubcriberMap, the key is subscriber's
     * method's name and tag which annotated with {@see Subcriber}, the value is
     * a list of Subscription.
     *
     * @param subscriber the target subscriber
     */
    public void register(Object subscriber) {
        if (subscriber == null) {
            return;
        }

        synchronized (this) {
            mMethodHunter.findSubcribeMethods(subscriber);
        }
    }

    /**
     * In the form of sticky registered, after successful registration will iterate through all the sticky Event
     *
     * @param subscriber
     */
    public void registerSticky(Object subscriber) {
        this.register(subscriber);
        // Processing sticky Event
        mDispatcher.dispatchStickyEvents(subscriber);
    }

    /**
     * @param subscriber
     */
    public void unregister(Object subscriber) {
        if (subscriber == null) {
            return;
        }
        synchronized (this) {
            mMethodHunter.removeMethodsFromMap(subscriber);
        }
    }

    /**
     * post a event
     *
     * @param event
     */
    public void post(Object event) {
        post(event, EventType.DEFAULT_TAG);
    }

    /**
     * Post event
     *
     * @param event To publish an event
     * @param tag   Tag events, similar to the action BroadcastReceiver
     */
    public void post(Object event, String tag) {
        if (event == null) {
            Log.e(this.getClass().getSimpleName(), "The event object is null");
            return;
        }
        mLocalEvents.get().offer(new EventType(event.getClass(), tag));
        mDispatcher.dispatchEvents(event);
    }

    /**
     * Post Sticky event, tag is EventType.DEFAULT_TAG
     *
     * @param event
     */
    public void postSticky(Object event) {
        postSticky(event, EventType.DEFAULT_TAG);
    }

    /**
     * Post Sticky event contains the tag
     *
     * @param event Event
     * @param tag   Event tag
     */
    public void postSticky(Object event, String tag) {
        if (event == null) {
            Log.e(this.getClass().getSimpleName(), "The event object is null");
            return;
        }
        EventType eventType = new EventType(event.getClass(), tag);
        eventType.event = event;
        mStickyEvents.add(eventType);
    }

    public void removeStickyEvent(Class<?> eventClass) {
        removeStickyEvent(eventClass, EventType.DEFAULT_TAG);
    }

    /**
     * Remove Sticky event
     *
     * @param type
     */
    public void removeStickyEvent(Class<?> eventClass, String tag) {
        Iterator<EventType> iterator = mStickyEvents.iterator();
        while (iterator.hasNext()) {
            EventType eventType = iterator.next();
            if (eventType.paramClass.equals(eventClass)
                    && eventType.tag.equals(tag)) {
                iterator.remove();
            }
        }
    }

    public List<EventType> getStickyEvents() {
        return mStickyEvents;
    }

    /**
     * Set Subscription function matching strategy
     *
     * @param policy Match Policy
     */
    public void setMatchPolicy(MatchPolicy policy) {
        mDispatcher.mMatchPolicy = policy;
    }

    /**
     * Set the execution thread in the UI event handler
     *
     * @param handler
     */
    public void setUIThreadEventHandler(EventHandler handler) {
        mDispatcher.mUIThreadEventHandler = handler;
    }

    /**
     * Set the execution thread of events in the post processor
     *
     * @param handler
     */
    public void setPostThreadHandler(EventHandler handler) {
        mDispatcher.mPostThreadHandler = handler;
    }

    /**
     * Set the execution thread in asynchronous event handlers
     *
     * @param handler
     */
    public void setAsyncEventHandler(EventHandler handler) {
        mDispatcher.mAsyncEventHandler = handler;
    }

    /**
     * Returns subscribe map
     *
     * @return
     */
    public Map<EventType, CopyOnWriteArrayList<Subscription>> getSubscriberMap() {
        return mSubcriberMap;
    }

    /**
     * Get event queue waiting to be processed
     *
     * @return
     */
    public Queue<EventType> getEventQueue() {
        return mLocalEvents.get();
    }

    /**
     * clear the events and subcribers map
     */
    public synchronized void clear() {
        mLocalEvents.get().clear();
        mSubcriberMap.clear();
    }

    /**
     * get the descriptor of EventBus
     *
     * @return the descriptor of EventBus
     */
    public String getDescriptor() {
        return mDesc;
    }

    public EventDispatcher getDispatcher() {
        return mDispatcher;
    }

    /**
     * Event dispatcher
     *
     * @author mrsimple
     */
    private class EventDispatcher {

        /**
         * The reception method implemented in the UI thread
         */
        EventHandler mUIThreadEventHandler = new UIThreadEventHandler();

        /**
         * Which post thread of execution, receiving method in which thread is executed
         */
        EventHandler mPostThreadHandler = new DefaultEventHandler();

        /**
         * Subscribe to asynchronous thread execution method
         */
        EventHandler mAsyncEventHandler = new AsyncEventHandler();

        /**
         * Caching can be an event type corresponding EventType list
         */
        private Map<EventType, List<EventType>> mCacheEventTypes = new ConcurrentHashMap<EventType, List<EventType>>();
        /**
         * Event match strategy, based on the policy set corresponding to find EventType
         */
        MatchPolicy mMatchPolicy = new DefaultMatchPolicy();

        /**
         * @param event
         */
        void dispatchEvents(Object aEvent) {
            Queue<EventType> eventsQueue = mLocalEvents.get();
            while (eventsQueue.size() > 0) {
                deliveryEvent(eventsQueue.poll(), aEvent);
            }
        }

        /**
         * According aEvent to find all the matching set, then handle the event
         *
         * @param type
         * @param aEvent
         */
        private void deliveryEvent(EventType type, Object aEvent) {
            // If you have the cache is taken directly from the cache
            List<EventType> eventTypes = getMatchedEventTypes(type, aEvent);
            //Iterate through all matching event and distributed to subscribers
            for (EventType eventType : eventTypes) {
                handleEvent(eventType, aEvent);
            }
        }

        /**
         * Processing a single event
         *
         * @param eventType
         * @param aEvent
         */
        private void handleEvent(EventType eventType, Object aEvent) {
            List<Subscription> subscriptions = mSubcriberMap.get(eventType);
            if (subscriptions == null) {
                return;
            }

            for (Subscription subscription : subscriptions) {
                final ThreadMode mode = subscription.threadMode;
                EventHandler eventHandler = getEventHandler(mode);
                // Handling events
                eventHandler.handleEvent(subscription, aEvent);
            }
        }

        private List<EventType> getMatchedEventTypes(EventType type, Object aEvent) {
            List<EventType> eventTypes = null;
            // If you have the cache is taken directly from the cache
            if (mCacheEventTypes.containsKey(type)) {
                eventTypes = mCacheEventTypes.get(type);
            } else {
                eventTypes = mMatchPolicy.findMatchEventTypes(type, aEvent);
                mCacheEventTypes.put(type, eventTypes);
            }

            return eventTypes != null ? eventTypes : new ArrayList<EventType>();
        }

        void dispatchStickyEvents(Object subscriber) {
            for (EventType eventType : mStickyEvents) {
                handleStickyEvent(eventType, subscriber);
            }
        }

        /**
         * Sticky process a single event
         *
         * @param eventType
         * @param aEvent
         */
        private void handleStickyEvent(EventType eventType, Object subscriber) {
            List<EventType> eventTypes = getMatchedEventTypes(eventType, eventType.event);
            // Event
            Object event = eventType.event;
            for (EventType foundEventType : eventTypes) {
                Log.e("", "### Type Found : " + foundEventType.paramClass.getSimpleName()
                        + ", event class : " + event.getClass().getSimpleName());
                final List<Subscription> subscriptions = mSubcriberMap.get(foundEventType);
                if (subscriptions == null) {
                    continue;
                }
                for (Subscription subItem : subscriptions) {
                    final ThreadMode mode = subItem.threadMode;
                    EventHandler eventHandler = getEventHandler(mode);
                    // If the subscriber is empty, then the sticky event distributed to all subscribers. Otherwise, only distributed to the subscriber
                    if (isTarget(subItem, subscriber)
                            && (subItem.eventType.equals(foundEventType)
                            || subItem.eventType.paramClass
                            .isAssignableFrom(foundEventType.paramClass))) {
                        // Handling events
                        eventHandler.handleEvent(subItem, event);
                    }
                }
            }
        }

        /**
         * If passed in the subscriber is not empty, then the Sticky event is only passed to the subscribers (registration), otherwise, all subscribers are passed (publish).
         *
         * @param item
         * @param subscriber
         * @return
         */
        private boolean isTarget(Subscription item, Object subscriber) {
            Object cacheObject = item.subscriber != null ? item.subscriber.get() : null;
            return subscriber == null || (subscriber != null
                    && cacheObject != null && cacheObject.equals(subscriber));
        }

        private EventHandler getEventHandler(ThreadMode mode) {
            if (mode == ThreadMode.ASYNC) {
                return mAsyncEventHandler;
            }
            if (mode == ThreadMode.POST) {
                return mPostThreadHandler;
            }
            return mUIThreadEventHandler;
        }
    } // end of EventDispatcher

}
