package mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus;

/**
 * Created by oleg on 31.08.15.
 */
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import android.util.Log;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * the subscriber method hunter, find all of the subscriber's methods which
 * annotated with @Subcriber.
 *
 * @author mrsimple
 */
public class SubsciberMethodHunter {

    /**
     * the event bus's subscriber's map
     */
    Map<EventType, CopyOnWriteArrayList<Subscription>> mSubcriberMap;

    /**
     * @param subscriberMap
     */
    public SubsciberMethodHunter(Map<EventType, CopyOnWriteArrayList<Subscription>> subscriberMap) {
        mSubcriberMap = subscriberMap;
    }

    /**
     * Find all subscriptions subscribe object functions, parameters can only have one subscription function.
     * Build Subscription stored in the Map After finding subscribe functions.
     *
     * @param subscriber Subscribe objects
     * @return
     */
    public void findSubcribeMethods(Object subscriber) {
        if (mSubcriberMap == null) {
            throw new NullPointerException("the mSubcriberMap is null. ");
        }
        Class<?> clazz = subscriber.getClass();
        // Registration method to find the class to meet the requirements until the Object class
        while (clazz != null && !isSystemCalss(clazz.getName())) {
            final Method[] allMethods = clazz.getDeclaredMethods();
            for (int i = 0; i < allMethods.length; i++) {
                Method method = allMethods[i];
                // According to the analytic function annotation
                Subscriber annotation = method.getAnnotation(Subscriber.class);
                if (annotation != null) {
                    // Get method parameters
                    Class<?>[] paramsTypeClass = method.getParameterTypes();
                    // Subscribe function supports only one parameter
                    if (paramsTypeClass != null && paramsTypeClass.length == 1) {
                        Class<?> paramType = convertType(paramsTypeClass[0]);
                        EventType eventType = new EventType(paramType, annotation.tag());
                        TargetMethod subscribeMethod = new TargetMethod(method, eventType,
                                annotation.mode());
                        subscibe(eventType, subscribeMethod, subscriber);
                    }
                }
            } // end for
            //Get the parent class to continue to find methods to meet the requirements of the parent class
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * According to a list of subscribers EventType storage, where the event type is EventType, an event corresponding to 0 to multiple subscribers.
     *
     * @param event      Event
     * @param method     Subscribe method of the object
     * @param subscriber Subscribers
     */
    private void subscibe(EventType event, TargetMethod method, Object subscriber) {
        CopyOnWriteArrayList<Subscription> subscriptionLists = mSubcriberMap.get(event);
        if (subscriptionLists == null) {
            subscriptionLists = new CopyOnWriteArrayList<Subscription>();
        }

        Subscription newSubscription = new Subscription(subscriber, method);
        if (subscriptionLists.contains(newSubscription)) {
            return;
        }

        subscriptionLists.add(newSubscription);
        // The key store event type and subscriber information to the map
        mSubcriberMap.put(event, subscriptionLists);
    }

    /**
     * remove subscriber methods from map
     *
     * @param subscriber
     */
    public void removeMethodsFromMap(Object subscriber) {
        Iterator<CopyOnWriteArrayList<Subscription>> iterator = mSubcriberMap
                .values().iterator();
        while (iterator.hasNext()) {
            CopyOnWriteArrayList<Subscription> subscriptions = iterator.next();
            if (subscriptions != null) {
                List<Subscription> foundSubscriptions = new
                        LinkedList<Subscription>();
                Iterator<Subscription> subIterator = subscriptions.iterator();
                while (subIterator.hasNext()) {
                    Subscription subscription = subIterator.next();
                    // Get references
                    Object cacheObject = subscription.subscriber.get();
                    if (isObjectsEqual(cacheObject, subscriber)
                            || cacheObject == null) {
                        Log.d("", "### Remove subscription " + subscriber.getClass().getName());
                        foundSubscriptions.add(subscription);
                    }
                }

                // Remove the subscriber of the relevant Subscription
                subscriptions.removeAll(foundSubscriptions);
            }

            // If an Event for the number of subscribers is empty, then the need to clear from the map
            if (subscriptions == null || subscriptions.size() == 0) {
                iterator.remove();
            }
        }
    }

    private boolean isObjectsEqual(Object cachedObj, Object subscriber) {
        return cachedObj != null
                && cachedObj.equals(subscriber);
    }

    /**
     * if the subscriber method's type is primitive, convert it to corresponding
     * Object type. for example, int to Integer.
     *
     * @param eventType origin Event Type
     * @return
     */
    private Class<?> convertType(Class<?> eventType) {
        Class<?> returnClass = eventType;
        if (eventType.equals(boolean.class)) {
            returnClass = Boolean.class;
        } else if (eventType.equals(int.class)) {
            returnClass = Integer.class;
        } else if (eventType.equals(float.class)) {
            returnClass = Float.class;
        } else if (eventType.equals(double.class)) {
            returnClass = Double.class;
        }

        return returnClass;
    }

    private boolean isSystemCalss(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }

}

