package mobi.tet_a_tet.atda.mutual.communications;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.provided.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.TLSUtils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetTempoDate;

/**
 * Created by oleg on 20.07.15.
 */
public abstract class AbstractEjabberdConnect<V> extends AsyncTask<Void, Void, Boolean> {
    private AbstractEjabberdConnectInteface<V> callback;
    private Throwable t;

    String JABBERSERVER = TetGlobalData.JBS;
    int JABBERPORT = TetGlobalData.JPORT;
    String JABBERDUSER = TetGlobalData.JBU;
    AbstractXMPPConnection jConnection;
    boolean result;
    boolean notReply = false;
    private String pseudo_tag;
    Context mBase;
    //В конструктор передаём интерфейс
    protected AbstractEjabberdConnect(AbstractEjabberdConnectInteface callback) {
        this.callback = (AbstractEjabberdConnectInteface<V>) callback;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        String action = getClass().getCanonicalName();
//        int pos = action.lastIndexOf('.') + 1;
//        String onlyClass = action.substring(pos);
        pseudo_tag = "AbstractEjabberdConnect";
        Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! WE ARE in "+pseudo_tag+" onPreExecute !!!");
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
    // config.setReconnectionAllowed(true);
    // config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
    //config.setRosterLoadedAtLogin(true);
    // config.setSocketFactory(ManagerFactory.getDefault());
    //
    // config.setSASLAuthenticationEnabled(false);
    //config.setSocketFactory(new DummySSLSocketFactory());
    //config.setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible);
        try {
    config.build();
} catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), R.string.gpsIsON, Toast.LENGTH_LONG).show();
}

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

        callback.transfer_jConnection(jConnection);
        try {
            doAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            System.out.println("connecting");
//            jConnection.connect();
//            System.out.println("connected");
//            jConnection.login();
//            System.out.println("logged in");
//        } catch (XMPPException e) {
//            e.printStackTrace();
//        } catch (SmackException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        if (jConnection.isAuthenticated()) {            // Normal connect
            callback.onSuccess(true);
//            ChatManager chatmanager = ChatManager.getInstanceFor(jConnection);
//            //Create chat
//            Chat newChat = chatmanager.createChat(TetGlobalData.JBC + "@" + TetGlobalData.JBS, new ChatMessageListener() {
//
//                @Override
//                public void processMessage(Chat chat, Message message) {
//                    try {
//                        chat.sendMessage(message);
//                        String from = message.getFrom();
//                        String body = message.getBody();
//                        String msg = String.format("%1$s", body);
//                        Log.d("pseudo_tag", "!!!!!!!!!!!!!!!!! DEBUG pseudo_tag StringTokenizer msg =" + msg + " !!!");
//                        StringTokenizer st = new StringTokenizer(msg, TetGlobalData.TOKEN_SEPARAROR);
//                        String token = st.nextToken();
//                        if (token.equals("RES")) {
//                            String tokenres = st.nextToken();
//                            if (tokenres.equals("FR_OK")) {
//                                TetGlobalData.notReply = true;
//                                Log.d("AbstractAsyncTasker", "++++!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer OK OK OK OK !!!!!!!!++++");
//                                callback.onRecievedMessage(msg);
//                                //callback.onSuccess(true);
//                                // jConnection.disconnect();
//                            } else {
//                                result = false;
//                               // jConnection.disconnect();
//                            }
//                            //
//                        } else {
//                            // Du somsing
//                        }
//                       // EventsJabberdIncomingMessage.INCOMMING_MESSAGE = body;
//                    } catch (SmackException.NotConnectedException e) {
//                        Log.d(TetGlobalData.TAG_TET_A_TET, e.toString());
//                    }
//                }
//            });

//            try {
//                newChat.sendMessage("REQ*|*DSET*|*" + TetATetSettingDate.DS_SETING_VERS_VALUE + "*|*" + TetGlobalData.DRVPHONE + "*|*" + TetGlobalData.DRVSIGN + "*|*" + TetGlobalData.CARGOSNUM + "*|*" + TetGlobalData.DRVPASSWORD + "");
//            } catch (SmackException.NotConnectedException e) {
//                e.printStackTrace();
//            }

//        }
      //  android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        jConnection.addConnectionListener(new ConnectionListener() {
            @Override
            public void reconnectionSuccessful() {
                // TODO Auto-generated method stub
                System.out.println("" + pseudo_tag + " ==== >>>>> reconnectionSuccessful");
                callback.reconnectionOK(true);
            }

            @Override
            public void reconnectionFailed(Exception arg0) {
                // TODO Auto-generated method stub
                System.out.println("" + pseudo_tag + " ==== >>>>> reconnectionFailed >>> " + arg0.toString());
                callback.reconnectionOK(false);
            }

            @Override
            public void reconnectingIn(int arg0) {
                // TODO Auto-generated method stub
                System.out.println("" + pseudo_tag + " ==== >>>>> reconnectingIn >>> " + arg0);
                // Progress bar
            }

            @Override
            public void connectionClosedOnError(Exception arg0) {
                // TODO Auto-generated method stub
                System.out.println("" + pseudo_tag + " ==== >>>>> connectionClosedOnError >>>" + arg0.toString());
                jConnection = null;

            }

            @Override
            public void connected(XMPPConnection connection) {
                callback.jabberdIsConnected(true);
            }

            @Override
            public void authenticated(XMPPConnection connection, boolean resumed) {
                if (!resumed) {
                    System.out.println("" + pseudo_tag + " ==== >>>>> connection authenticated");
                    callback.jabberdConnectResumed(jConnection);
                }
              //  listningmessage(connection);
            }

            @Override
            public void connectionClosed() {
                // TODO Auto-generated method stub
                System.out.println("" + pseudo_tag + " ==== >>>>> connection Closed");
                callback.connectionClosed();
                int i = 0;
//                while (jConnection != null || i >= 10) {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    JabberdGetConect();
//                    i++;
//                }
            }
        });
        TetTempoDate.setRunning_listner_Thread_To_TetTempoDate(Thread.currentThread());

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

    public Context getApplicationContext() {
        return mBase.getApplicationContext();
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