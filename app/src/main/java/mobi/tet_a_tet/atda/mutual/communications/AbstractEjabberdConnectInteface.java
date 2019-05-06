package mobi.tet_a_tet.atda.mutual.communications;

import org.jivesoftware.smack.AbstractXMPPConnection;

/**
 * Created by oleg on 20.07.15.
 */
public interface AbstractEjabberdConnectInteface<V> {

    void onBegin(); //Асинхронная операция началась

    void onSuccess(Boolean data); //Получили результат

    void onFailure(Throwable t); //Получили ошибку

    void onEnd(); //Операция закончилась

    void onRecievedMessage(String message);

    void reconnectionOK(boolean b);

    void jabberdIsConnected(boolean b);

    void jabberdConnectResumed(AbstractXMPPConnection jConnection);

    void connectionClosed();

    void transfer_jConnection(AbstractXMPPConnection jConnection);
}
