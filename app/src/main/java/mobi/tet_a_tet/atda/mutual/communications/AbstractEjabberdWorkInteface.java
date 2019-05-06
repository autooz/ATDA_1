package mobi.tet_a_tet.atda.mutual.communications;

import org.jivesoftware.smack.AbstractXMPPConnection;

/**
 * Created by oleg on 20.07.15.
 */
public interface AbstractEjabberdWorkInteface<V> {

    void onBegin(); //Асинхронная операция началась

    void onSuccess(AbstractXMPPConnection data); //Получили результат

    void onFailure(Throwable t); //Получили ошибку

    void onEnd(); //Операция закончилась

    void onRecievedMessage(String message);
}
