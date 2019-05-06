package mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus;

/**
 * Created by oleg on 31.08.15.
 */
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

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * Subscribers object that contains subscriber and target method
 *
 * @author mrsimple
 */
public class Subscription {
    /**
     * Subscriber object
     */
    public Reference<Object> subscriber;
    /**
     * Methods recipient
     */
    public Method targetMethod;
    /**
     * Threading model execution events
     */
    public ThreadMode threadMode;
    /**
     * Event Type
     */
    public EventType eventType;

    /**
     * @param subscriber
     * @param method
     */
    public Subscription(Object subscriber, TargetMethod targetMethod) {
        this.subscriber = new WeakReference<Object>(subscriber);
        this.targetMethod = targetMethod.method;
        this.threadMode = targetMethod.threadMode;
        this.eventType = targetMethod.eventType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
        result = prime * result + ((targetMethod == null) ? 0 : targetMethod.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Subscription other = (Subscription) obj;
        if (subscriber.get() == null) {
            if (other.subscriber.get() != null)
                return false;
        } else if (!subscriber.get().equals(other.subscriber.get()))
            return false;
        if (targetMethod == null) {
            if (other.targetMethod != null)
                return false;
        } else if (!targetMethod.equals(other.targetMethod))
            return false;
        return true;
    }

}

