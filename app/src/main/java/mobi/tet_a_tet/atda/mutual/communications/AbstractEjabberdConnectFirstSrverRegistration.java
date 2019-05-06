package mobi.tet_a_tet.atda.mutual.communications;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.provided.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.TLSUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetTempoDate;

/**
 * Created by oleg on 20.07.15.
 */
public abstract class AbstractEjabberdConnectFirstSrverRegistration<V> extends AsyncTask<Void, Void, Boolean> {
    private AbstractEjabberdConnectFirstSrverRegistrationInteface<V> callback;
    private Throwable t;

    String JABBERSERVER = TetGlobalData.JBS;
    int JABBERPORT = TetGlobalData.JPORT;
    String JABBERDUSER = TetGlobalData.JBU;
    AbstractXMPPConnection jConnection;
    boolean result;
    boolean notReply = false;
    private AbstractEjabberdConnect mContext;

    //В конструктор передаём интерфейс
    protected AbstractEjabberdConnectFirstSrverRegistration(AbstractEjabberdConnectFirstSrverRegistrationInteface<V> callback) {
        this.callback = (AbstractEjabberdConnectFirstSrverRegistrationInteface<V>) callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        android.util.Log.d("AbstractAsyncTasker", "!!!!!!!!!!!!!!!!! WE ARE in AbstractAsyncTasker onPreExecute !!!");
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
       // config.setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible);
       // config.setSendPresence(true);
        config.setCompressionEnabled(false);
        // config.setReconnectionAllowed(true);
        // config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        //config.setRosterLoadedAtLogin(true);
        // config.setSocketFactory(ManagerFactory.getDefault());
        //
        // config.setSASLAuthenticationEnabled(false);
        //config.setSocketFactory(new DummySSLSocketFactory());
        //config.setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible);
        config.build();
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        SASLMechanism mechanism = new SASLDigestMD5Mechanism();
        SASLAuthentication.registerSASLMechanism(mechanism);
        SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
        SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
        SASLAuthentication.unBlacklistSASLMechanism("PLAN");
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
        XMPPTCPConnection.setUseStreamManagementDefault(true);
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
            Log.e("jabFcon"," send in AbstractEjabberdConnectFirstSrverRegistration reg onJabServer if TetGlobalData.JAB_LOGIN_ACTIVITY");
            jConnection.disconnect();
            return true;
        } else if (TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.DS_LOGIN_ACTIVITY && jConnection.isAuthenticated()) {
            Log.e("jabFcon"," send in AbstractEjabberdConnectFirstSrverRegistration reg onDsServer if TetGlobalData.DS_LOGIN_ACTIVITY");
            //// send registration message
            ChatManager chatmanager = ChatManager.getInstanceFor(jConnection);
            ////Create chat
            Chat newChat = chatmanager.createChat(TetGlobalData.JBC + "@" + TetGlobalData.JBS, new ChatMessageListener() {

                @Override
                public void processMessage(Chat chat, Message message) {
                    try {
                        chat.sendMessage(message);
                        String from = message.getFrom();
                        String body = message.getBody();
                        String msg = String.format("%1$s", body);
                        android.util.Log.d("AbstractAsyncTasker", "!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer msg =" + msg + " !!!");
                        if (msg.equals("null")){
                            result = false;
                            jConnection.disconnect();
                            Toast.makeText(mContext.getApplicationContext(), R.string.errorWithRequest, Toast.LENGTH_LONG).show();
                        }
                        StringTokenizer st = new StringTokenizer(msg, TetGlobalData.TOKEN_SEPARATOR);
                        String token = st.nextToken();
                        if (token.equals(TetGlobalData.RESPONCE)) {
                            String tokenres = st.nextToken();
                            if (tokenres.equals(TetGlobalData.FIRST_REG_OK)) {
                                TetGlobalData.notReply = true;
                                android.util.Log.d("AbstractAsyncTasker", "++++!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer OK OK OK OK !!!!!!!!++++");
                                callback.onSuccess(true);
                                TetTempoDate.temp_int_1 = TetGlobalData.DS_LOGIN_ACTIVITY;
                                jConnection.disconnect();
                            } else {
                                result = false;
                                jConnection.disconnect();
                            }
                            //
                        } else {
                            jConnection.disconnect();
                        }

                    } catch (SmackException.NotConnectedException e) {
                        Log.d(TetGlobalData.TAG_TET_A_TET, e.toString());
                    }
                }
            });

            try {
                newChat.sendMessage("REQ*|*FR*|*" + TetGlobalData.DRVPHONE + "*|*" + TetGlobalData.DRVSIGN + "*|*" + TetGlobalData.CARGOSNUM + "*|*" + TetGlobalData.DRVPASSWORD + "");
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }


            // jConnection.disconnect();
            return false;

        } else if (jConnection.isAuthenticated()) {
            // Normal connect
            Log.e("jabFcon","in AbstractEjabberdConnectFirstSrverRegistration send DS_SETUP");
            ChatManager chatmanager = ChatManager.getInstanceFor(jConnection);
            //Create chat
            Chat newChat = chatmanager.createChat(TetGlobalData.JBC + "@" + TetGlobalData.JBS, new ChatMessageListener() {

                @Override
                public void processMessage(Chat chat, Message message) {
                    try {
                        chat.sendMessage(message);
                        String from = message.getFrom();
                        String body = message.getBody();
                        String msg = String.format("%1$s", body);
                        android.util.Log.d("AbstractAsyncTasker", "!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer msg =" + msg + " !!!");
                        StringTokenizer st = new StringTokenizer(msg, TetGlobalData.TOKEN_SEPARATOR);
                        String token = st.nextToken();
                        if (token.equals(TetGlobalData.RESPONCE)) {
                            String tokenres = st.nextToken();
                            if (tokenres.equals(TetGlobalData.FIRST_REG_OK)) {
                                TetGlobalData.notReply = true;
                                android.util.Log.d("AbstractAsyncTasker", "++++!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer OK OK OK OK !!!!!!!!++++");
                                callback.onRecievedMessage(msg);
                                callback.onSuccess(true);
                                // jConnection.disconnect();
                            } else {
                                result = false;
                                jConnection.disconnect();
                            }
                            //
                        } else {
                            jConnection.disconnect();
                        }
                        //TetGlobalData.RECIEVED_MASSAGE = body;
                    } catch (SmackException.NotConnectedException e) {
                        Log.d(TetGlobalData.TAG_TET_A_TET, e.toString());
                    }
                }
            });

            try {
                newChat.sendMessage(""+TetGlobalData.REQUEST+"*|*"+TetGlobalData.DS_SETUP+"*|*" + TetATetSettingDate.DS_SETING_VERS_VALUE + "*|*" + TetGlobalData.DRVPHONE + "*|*" + TetGlobalData.DRVSIGN + "*|*" + TetGlobalData.CARGOSNUM + "*|*" + TetGlobalData.DRVPASSWORD + "");
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }

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

    protected void onRecievedMessage(String msg) {
        callback.onRecievedMessage(msg);


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