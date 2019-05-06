package mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.handler;

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


import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscription;

/**
 * Event processing interface
 *
 * @author mrsimple
 */
public interface EventHandler {
    /**
     * Handling events
     *
     * @param subscription Subscribe objects
     * @param event        Event to be processed
     */
    void handleEvent(Subscription subscription, Object event);
}

