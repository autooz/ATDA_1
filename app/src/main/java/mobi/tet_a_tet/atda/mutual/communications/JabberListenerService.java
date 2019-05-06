package mobi.tet_a_tet.atda.mutual.communications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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
import java.util.StringTokenizer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventJabIncomMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventJabOutcomMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscriber;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetTempoDate;

import static java.lang.System.exit;

public class JabberListenerService extends Service implements AbstractEjabberdConnectInteface {

    ChatManager chatmanager;
    private Chat newChat = null;
    private String pseudo_tag;
    public StringTokenizer st;
    private int std;
    private int flg;
    private IBinder mBinder;
    public AbstractXMPPConnection jConnection;
    private AbstractXMPPConnection thisjConnection;

    public JabberListenerService() {
     //   this.mContext = context;
        //getLocation();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
      //  throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().register(this);
        std = startId;
        flg =flags;
        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Toast.makeText(this, R.string.jabListnerStarted, Toast.LENGTH_LONG).show();
        Log.e(pseudo_tag, "onStartCommand STARTED flag =" + flags + " startId =" + startId + "");

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
     //   EventBus.getDefault().register(this);
        new AbstractEjabberdConnect<Integer>(this) {
            @Override
            protected boolean doAction() throws Exception {
                Thread.currentThread().wait(1000);
                Log.e(pseudo_tag, "ERROR : NO COONNECT  boolean doAction() ");
                return false;
            }
        }.execute();
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED flag =" + flg + " startId =" + std + " ");
        super.onDestroy();
    }


    @Subscriber(tag = "OUTCOMING_MESSAGE")
    private void updateEventsJabberdOutcomingMessageWithTag(EventJabOutcomMessage message) {
        Log.e(pseudo_tag, "### EventsJabberdOutcomingMessageWithTag Message to be send = " + message.OUTCOMING_MESSAGE);
        String message_out = message.OUTCOMING_MESSAGE;
        int i = 0;
                while (newChat == null && i <= 10) {
                    try {
                        i++;
                        //transfer_jConnection(thisjConnection);
                        Toast.makeText(this, "Connection CLOSED - RECONNECT", Toast.LENGTH_LONG).show();
                        doReconnect();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG WAIT CHAT in "+pseudo_tag+" i="+i+" !!!");
                    if (i >= 10){
                        Toast.makeText(this, "ATTEMPT RECONNECT MORE 10 TIMES", Toast.LENGTH_LONG).show();
                        exit(0);
                    }

                }
        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG ПРОРВАЛИСЬ к ЧАТУ in " + pseudo_tag + " !!!");
        try {
            newChat.sendMessage(message_out);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    private void doReconnect() {
        Log.e(pseudo_tag,"MAKE RECONNECT TO JABBERD");
        System.out.println(""+ pseudo_tag+" ==== >>>>> run()");
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
//        try {
//            System.out.println("" + pseudo_tag + " Jabberd connecting");
//            jConnection.connect();
//            System.out.println("" + pseudo_tag + " Jabberd connected");
//            jConnection.login();
//            System.out.println(""+ pseudo_tag+" Jabberd logged in");
//        } catch (XMPPException e) {
//            e.printStackTrace();
//        } catch (SmackException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        TetTempoDate.jabberd_connect = (XMPPTCPConnection) jConnection;
        transfer_jConnection(jConnection);
    }


    @Override
    public void onBegin() {
        Log.d(pseudo_tag, "### Message to be callback onBegin() OK");
    }

    @Override
    public void onSuccess(Boolean data) {
        Log.d(pseudo_tag, "### Message to be callback onSuccess OK");
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(pseudo_tag, "### Message to be callback onFailure OK");
    }

    @Override
    public void onEnd() {
        Log.d(pseudo_tag, "### Message to be callback onEnd OK");
    }

    @Override
    public void onRecievedMessage(String message) {
        Log.d(pseudo_tag, "### Message to be callback onRecievedMessage OK");

    }

    @Override
    public void reconnectionOK(boolean b) {
        Log.d(pseudo_tag, "### Message to be callback reconnectionOK OK");
    }

    @Override
    public void jabberdIsConnected(boolean b) {
        Log.d(pseudo_tag, "### Message to be callback jabberdIsConnected OK");
    }

    @Override
    public void jabberdConnectResumed(AbstractXMPPConnection jConnection) {
        Log.d(pseudo_tag, "### Message to be callback jabberdConnectResumed() OK");
    }

    @Override
    public void connectionClosed() {
        Log.d(pseudo_tag, "### Message to be callback connectionClosed() OK");
    }

    @Override
    public void transfer_jConnection(AbstractXMPPConnection jConnection) {
        Log.d(pseudo_tag, "### jConnection is callback transfer_jConnection(AbstractXMPPConnection jConnection) OK");
        thisjConnection = jConnection;
        try {
            System.out.println("" + pseudo_tag + " connecting");
            jConnection.connect();
            System.out.println("" + pseudo_tag + " connected");
            jConnection.login();
            System.out.println("" + pseudo_tag + " logged in");
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        ChatManager chatmanager = ChatManager.getInstanceFor(jConnection);
        //Create chat
        newChat = chatmanager.createChat(TetGlobalData.JBC + "@" + TetGlobalData.JBS, new ChatMessageListener() {

            @Override
            public void processMessage(Chat chat, Message message) {
                try {
                    chat.sendMessage(message);
                    String from = message.getFrom();
                    String body = message.getBody();
                    String msg = String.format("%1$s", body);
                    android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer msg =" + msg + " !!!");
                    st = new StringTokenizer(msg, TetGlobalData.TOKEN_SEPARATOR);
                    String token = st.nextToken();
                    if (token.equals(TetGlobalData.RESPONCE)) {
                        String tokenres = st.nextToken();
                        if (tokenres.equals(TetGlobalData.FIRST_REG_OK)) {
                            TetGlobalData.notReply = true;
                            android.util.Log.d(pseudo_tag, "++++!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer OK OK OK OK !!!!!!!!++++");
                        } else{
                       //     if (!msg.equals(TetTempoDate.last_jab_incom_msg)) {
                                TetTempoDate.last_jab_incom_msg = msg;
                                sendIncommingMessageViaIB(msg);
                       //     }
                        }
                        //
                    }
                } catch (SmackException.NotConnectedException e) {
                    android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG  NO chatmanager.createChat in "+pseudo_tag+" !!!");
                    Log.d(TetGlobalData.TAG_TET_A_TET, e.toString());
                }
            }
        });

        TetTempoDate.setRunning_listner_Thread_To_TetTempoDate(Thread.currentThread());
    }

    public void sendIncommingMessageViaIB(String msg) {
        android.util.Log.d(pseudo_tag, "++++!!!!!!!!!!!!!!!!! DEBUG sendIncommingMessageViaIB via  EventsJabberdIncomingMessag "+msg+" !!!!!!!!++++");
        EventBus.getDefault().post(new EventJabIncomMessage(msg), "INCOMING_MESSAGE");
        sendBroadcast(new Intent("killProgresBar"));
        //EventBus.getDefault().postSticky(new EventJabIncomMessage(msg), "INCOMING_MESSAGE");
    }


}

