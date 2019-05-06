package mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus;

import java.util.StringTokenizer;

/**
 * Created by oleg on 05.10.15.
 */
public class EventFromControllerActivityMessage {

    public Boolean AUTO_UPDATE_POZITION;
    public String STRINGFROM_CONTROLLER_ACTIVITY_MESSAGE;
    public StringTokenizer FROM_CONTROLLER_ACTIVITY_MESSAGE;
    public int UPDATE_VIEW = 1;
    public EventFromControllerActivityMessage(StringTokenizer value) {
        FROM_CONTROLLER_ACTIVITY_MESSAGE = value;
    }

    public EventFromControllerActivityMessage(String string) {
        STRINGFROM_CONTROLLER_ACTIVITY_MESSAGE = string;
    }

    public EventFromControllerActivityMessage(Boolean false_or_true) {
        AUTO_UPDATE_POZITION = false_or_true;
    }
    public EventFromControllerActivityMessage(int false_or_true) {
        UPDATE_VIEW = false_or_true;
    }
}

