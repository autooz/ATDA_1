package mobi.tet_a_tet.atda.mutual.communications;

import android.os.AsyncTask;
import android.util.Log;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.provided.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.TLSUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

/**
 * Created by oleg on 20.07.15.
 */
public abstract class AbstractEjabberdWork<V> extends AsyncTask<Void, Void, String> {
    private AbstractEjabberdWorkInteface<V> callback;
    private Throwable t;

    String JABBERSERVER = TetGlobalData.JBS;
    int JABBERPORT = TetGlobalData.JPORT;
    String JABBERDUSER = TetGlobalData.JBU;
    AbstractXMPPConnection jConnection;
    boolean result;
    boolean notReply = false;

    //В конструктор передаём интерфейс
    protected AbstractEjabberdWork(AbstractEjabberdWorkInteface<V> callback) {
        this.callback = (AbstractEjabberdWorkInteface<V>) callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("AbstractEjabberdWork", "!!!!!!!!!!!!!!!!! WE ARE in AbstractAsyncTasker onPreExecute !!!");
        if (callback != null) {
            callback.onBegin(); //Сообщаем через интерфейс о начале
        }
    }

    protected abstract boolean doAction() throws Exception; //Этот метод будем переопределять

    @Override
    protected String doInBackground(Void... params) {


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
            System.out.println("AbstractEjabberdWork connecting");
            jConnection.connect();
            System.out.println("AbstractEjabberdWork connected");
            jConnection.login();
            System.out.println("AbstractEjabberdWork logged in");
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        callback.onSuccess(jConnection);
        Log.e("AbstractEjabberdWork", "###  jConnection.getStreamId() = " +jConnection.getStreamId()+"");
        if (jConnection.isAuthenticated()) {
            Roster roster = Roster.getInstanceFor(jConnection);

            if (!roster.isLoaded()) {
                try {
                    roster.reloadAndWait();
                } catch (SmackException.NotLoggedInException e) {
                    e.printStackTrace();
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Collection<RosterEntry> entries = roster.getEntries();

            for (RosterEntry entry : entries)
                System.out.println("Here: " + entry);
//            // Normal connect
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
//                        Log.d("AbstractAsyncTasker", "!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer msg =" + msg + " !!!");
//                        StringTokenizer st = new StringTokenizer(msg, TetGlobalData.TOKEN_SEPARAROR);
//                        String token = st.nextToken();
//                        if (token.equals("RES")) {
//                            String tokenres = st.nextToken();
//                            if (!tokenres.equals("DSET_OK")) {
//                                TetGlobalData.notReply = true;
//                                Log.d("AbstractAsyncTasker", "++++!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer OK OK OK OK !!!!!!!!++++");
//                                callback.onRecievedMessage(msg);
//                                callback.onSuccess("true");
//                                // jConnection.disconnect();
//                            } else {
//                                TetGlobalData.notReply = true;
//                                callback.onRecievedMessage(msg);
//                                callback.onSuccess("true");
//                                result = false;
//                                jConnection.disconnect();
//                            }
//                            //
//                        } else {
//                            jConnection.disconnect();
//                        }
//                        //TetGlobalData.RECIEVED_MASSAGE = body;
//                    } catch (SmackException.NotConnectedException e) {
//                        Log.d(TetGlobalData.TAG_TET_A_TET, e.toString());
//                    }
//                }
//            });
//
//            try {
//                newChat.sendMessage("REQ*|*DSET*|*" + TetGlobalData.DRVPHONE + "*|*" + TetGlobalData.DRVSIGN + "*|*" + TetGlobalData.CARGOSNUM + "*|*" + TetGlobalData.DRVPASSWORD + "*|*" + TetATetSettingDate.DS_SETING_VERS_VALUE + "*|*");
//            } catch (SmackException.NotConnectedException e) {
//                e.printStackTrace();
//            }
//
        }
        return "";
    }

    @Override
    protected void onPostExecute(String v) {

        super.onPostExecute(v);
       // callback.onSuccess(v);
        if (callback != null) {
            callback.onEnd(); //Сообщаем об окончании
        }
        //  generateCallback(v);
    }

    protected void onRecievedMessages(String msg) {
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