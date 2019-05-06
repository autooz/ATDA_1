package mobi.tet_a_tet.atda.mutual.communications;

import android.os.AsyncTask;
import android.util.Log;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.SASLAnonymous;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.TLSUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

/**
 * Created by oleg on 29.07.15.
 */
public abstract class AbstractJabberd2ConnectAcyncTasker<V> extends AsyncTask<Void, Void, Boolean> {
    private AbstractEjabberdConnectFirstSrverRegistrationInteface<V> callback;
    private Throwable t;

    String JABBERSERVER = TetGlobalData.JBS;
    int JABBERPORT = TetGlobalData.JPORT;
    String JABBERDUSER = TetGlobalData.JBU;
    AbstractXMPPConnection jConnection;

    //В конструктор передаём интерфейс
    protected AbstractJabberd2ConnectAcyncTasker(AbstractEjabberdConnectFirstSrverRegistrationInteface<V> callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        android.util.Log.d("AbstractJabberd2Tasker", "!!!!!!!!!!!!!!!!! WE ARE in AbstractAsyncTasker onPreExecute !!!");
        if (callback != null) {
            callback.onBegin(); //Сообщаем через интерфейс о начале
        }
    }

    protected abstract boolean doAction() throws Exception; //Этот метод будем переопределять

    @Override
    protected Boolean doInBackground(Void... params) {


        XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
        config.setUsernameAndPassword(TetGlobalData.JBU + "@" + TetGlobalData.JBS, TetGlobalData.JBP);
        config.setServiceName(TetGlobalData.JBS);
        config.setHost(TetGlobalData.JBS);
        config.setPort(TetGlobalData.JPORT);
        config.setDebuggerEnabled(true);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible);
        config.setSendPresence(true);
        config.setCompressionEnabled(false);
        // config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        //config.setRosterLoadedAtLogin(true);
        // config.setSocketFactory(ManagerFactory.getDefault());
        //
        //config.setSocketFactory(new DummySSLSocketFactory());
        config.build();
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        SASLMechanism mechanism = new SASLAnonymous();
        SASLAuthentication.registerSASLMechanism(mechanism);
        SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
        SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
        //SASLAuthentication.unBlacklistSASLMechanism("PLAN");
//////////////////////////////////////////////////////////////////////////////////////////////////
        // accept all certificate - just for testing
        try {
            TLSUtils.acceptAllCertificates(config);
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyManagementException e) {
        }
//////////////////////////////////////////////////////////////////////////////////////////////////
// verify all hostname - just for testing
        config.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////
 /*       try {
            SSLContext sc = SSLContext.getInstance("TLS");
            config.setCustomSSLContext(sc);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
*/
        //     XMPPTCPConnection.setUseStreamManagementDefault(true);
        jConnection = new XMPPTCPConnection(config.build());
        jConnection.setPacketReplyTimeout(10000);
        try {
            System.out.println("connecting");
            jConnection.connect();
            System.out.println("connected");
            jConnection.login();
            System.out.println("logged in");
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.JAB_LOGIN_ACTIVITY && jConnection.isAuthenticated()) {
            jConnection.disconnect();
            return true;
        } else if (TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.DS_LOGIN_ACTIVITY && jConnection.isAuthenticated()) {
            // send registration message
            ChatManager chatmanager = ChatManager.getInstanceFor(jConnection);
            //Create chat
            Chat newChat = chatmanager.createChat(TetGlobalData.JBC + "@" + TetGlobalData.JBS, new ChatMessageListener() {

                String message = "devID\n" +
                        "7\n" +
                        "R_FREERUN";

                @Override
                public void processMessage(Chat chat, Message message) {
                    try {
                        chat.sendMessage(message);
                        String from = message.getFrom();
                        String body = message.getBody();
                        System.out.println(String.format("Received message принимаем'%1$s' from %2$s", body, from));

                    } catch (SmackException.NotConnectedException e) {
                        Log.d(TetGlobalData.TAG_TET_A_TET, e.toString());
                    }
                }


            });
            try {

                newChat.sendMessage("devID\n" +
                        "7\n" +
                        "R_DRVSTOPSID");

            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }


            //  jConnection.disconnect();
            return true;
        } else if (jConnection.isAuthenticated()) {
            // Normal connect

        }

        try {
            return doAction(); //В параллельном потоке вызываем абстрактный метод.
        } catch (Exception e) {
            t = e;
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean v) {
        super.onPostExecute(v);
        callback.onSuccess(v);
        if (callback != null) {
            callback.onEnd(); //Сообщаем об окончании
        }
        //  generateCallback(v);
    }

//    private void generateCallback(boolean data) { //Генерируем ответ
//        if (callback == null) return;
//        if (!data) { //Есть данные - всё хорошо
//            callback.onSuccess(data);
//        }
//        else if (t != null) {
//            callback.onFailure(t); //Есть ошибка - вызываем onFailure
//        } else { //А такая ситуация вообще не должна появляться)
//            callback.onFailure(new NullPointerException("Result is empty but error empty too"));
//        }
//    }
}