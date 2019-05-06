package mobi.tet_a_tet.atda.mutual.communications;

import org.jivesoftware.smack.AbstractXMPPConnection;

/**
 * Created by oleg on 17.09.15.
 */
public interface JabberdConnectInterface<V> {

    void reconnectionOK(boolean b);

    void jabberdIsConnected(boolean b);

    void connectionClosed();

    void jabberdConnectResumed(AbstractXMPPConnection jConnection);
}
