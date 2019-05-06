package mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus;

/**
 * Created by oleg on 15.09.15.
 */
public class EventJabOutcomMessage {
    public String OUTCOMING_MESSAGE;

    public EventJabOutcomMessage(String value) {
        OUTCOMING_MESSAGE = value;
    }
    public String getMessage(){
        return OUTCOMING_MESSAGE;
    }
}
