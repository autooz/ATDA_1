package mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.handler;

/**
 * Created by oleg on 31.08.15.
 * <p/>
 * Copyright (C) 2015 Mr.Simple <bboyfeiyu@gmail.com>
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.lang.reflect.InvocationTargetException;

import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscription;

/**
 * Event post, receiving the event in which thread in which the thread
 *
 * @author mrsimple
 */
public class DefaultEventHandler implements EventHandler {
    /**
     * handle the event
     *
     * @param subscription
     * @param event
     */
    public void handleEvent(Subscription subscription, Object event) {
        if (subscription == null
                || subscription.subscriber.get() == null) {
            return;
        }
        try {
            // carried out
            subscription.targetMethod.invoke(subscription.subscriber.get(), event);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
