package mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.matchpolicy;

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

import java.util.LinkedList;
import java.util.List;

import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventType;

public class DefaultMatchPolicy implements MatchPolicy {

    @Override
    public List<EventType> findMatchEventTypes(EventType type, Object aEvent) {
        Class<?> eventClass = aEvent.getClass();
        List<EventType> result = new LinkedList<EventType>();
        while (eventClass != null) {
            result.add(new EventType(eventClass, type.tag));
            addInterfaces(result, eventClass, type.tag);
            eventClass = eventClass.getSuperclass();
        }

        return result;
    }

    /**
     * Get all the interface type of the event
     *
     * @param eventTypes Storage List
     * @param interfaces All interface events implemented
     */
    private void addInterfaces(List<EventType> eventTypes, Class<?> eventClass, String tag) {
        if (eventClass == null) {
            return;
        }
        Class<?>[] interfacesClasses = eventClass.getInterfaces();
        for (Class<?> interfaceClass : interfacesClasses) {
            if (!eventTypes.contains(interfaceClass)) {
                eventTypes.add(new EventType(interfaceClass, tag));
                addInterfaces(eventTypes, interfaceClass, tag);
            }
        }
    }

}
