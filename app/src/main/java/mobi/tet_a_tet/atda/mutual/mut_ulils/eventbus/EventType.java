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

/**
 * <p/>
 * This class is a description of a function of the unique nature of the object, parameter types, tag two conditions to ensure the uniqueness of the object by object of this class to find all registered subscribers and the corresponding type of {see tag
 * Subscription}, and call all subscribers corresponding function when receiving the message.
 *
 * @author mrsimple
 */
public final class EventType {
    /**
     * The default tag
     */
    public static final String DEFAULT_TAG = "default_tag";

    /**
     * Parameter Type
     */
    Class<?> paramClass;
    /**
     * Tag function
     */
    public String tag = DEFAULT_TAG;

    public Object event;

    /**
     * @param aClass
     */
    public EventType(Class<?> aClass) {
        this(aClass, DEFAULT_TAG);
    }

    public EventType(Class<?> aClass, String aTag) {
        paramClass = aClass;
        tag = aTag;
    }

    @Override
    public String toString() {
        return "EventType [paramClass=" + paramClass.getName() + ", tag=" + tag + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((paramClass == null) ? 0 : paramClass.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
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
        EventType other = (EventType) obj;
        if (paramClass == null) {
            if (other.paramClass != null)
                return false;
        } else if (!paramClass.equals(other.paramClass))
            return false;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        return true;
    }

}

