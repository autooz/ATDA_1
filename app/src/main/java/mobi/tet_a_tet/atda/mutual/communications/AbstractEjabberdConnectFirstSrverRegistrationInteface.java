package mobi.tet_a_tet.atda.mutual.communications;

/**
 * Created by oleg on 20.07.15.
 */
public interface AbstractEjabberdConnectFirstSrverRegistrationInteface<V> {

    void onBegin(); //Асинхронная операция началась

    void onSuccess(Boolean data); //Получили результат

    void onFailure(Throwable t); //Получили ошибку

    void onEnd(); //Операция закончилась

    void onRecievedMessage(String message);
}
