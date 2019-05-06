package mobi.tet_a_tet.atda.mutual.communications;

import android.content.Context;
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

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;


/**
 * Created by oleg on 05.12.15.
 */
public abstract class jabberdSenderAcyncTasker extends AsyncTask<Void, Void, String> {
    public Context mContext;
    public eJabberdSenderInteface callback;
    public String oucoming_Message;
    private Throwable t;

    String JABBERSERVER = TetGlobalData.JBS;
    int JABBERPORT = TetGlobalData.JPORT;
    String JABBERDUSER = TetGlobalData.JBU;
    AbstractXMPPConnection jConnection;
    boolean result = true;
    boolean notReply = false;
    private String pseudo_tag="jabberdSendAcyncTasker";

//    public jabberdSenderAcyncTasker(String outcoming_mesage) {
//
//        super();
//
//        oucoming_Message = outcoming_mesage;
//    }
    protected jabberdSenderAcyncTasker(eJabberdSenderInteface callback, Context mContext, String REQUEST) {
        this.callback = (eJabberdSenderInteface) callback;
        this.mContext = mContext;
        oucoming_Message = REQUEST;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    protected abstract boolean doAction() throws Exception; // Do redefinition method

    @Override
    protected String doInBackground(Void... params) {
        Log.i(pseudo_tag, "" + pseudo_tag + " RUN doInBackground");
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
            System.out.println("connecting");
            jConnection.connect();
            System.out.println("connected");
            jConnection.login();
            System.out.println("logged in");
            callback.onLogin(true);
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(pseudo_tag, "###  jConnection.getStreamId() = " +jConnection.getStreamId()+"");
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
            /// Normal connect
            ChatManager chatmanager = ChatManager.getInstanceFor(jConnection);
            ///Create chat
            Chat newChat = chatmanager.createChat(TetGlobalData.JBC + "@" + TetGlobalData.JBS, new ChatMessageListener() {

                @Override
                public void processMessage(Chat chat, Message message) {
                    try {
                        chat.sendMessage(message);
                        String from = message.getFrom();
                        String body = message.getBody();
                        String msg = String.format("%1$s", body);
                        Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer msg =" + msg + " !!!");
                        StringTokenizer st = new StringTokenizer(msg, TetGlobalData.TOKEN_SEPARATOR);
                        String token = st.nextToken();
                        if (token.equals(TetGlobalData.RESPONCE)) {
                            String tokenres = st.nextToken();
                            if (!tokenres.equals(TetGlobalData.DS_SETUP_OK)) {
                                TetGlobalData.notReply = true;
                                Log.d(pseudo_tag, "++++!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer OK OK OK OK !!!!!!!!++++");
                                //EventBus.getDefault().post(new EventJabIncomMessage(msg), "INCOMING_MESSAGE");
                                 callback.onRecievedMessage(mContext,msg);
                                jConnection.disconnect();
                            } else {
                                TetGlobalData.notReply = true;
                                result = false;
                                jConnection.disconnect();
                            }

                        } else {
                            jConnection.disconnect();
                        }

                    } catch (SmackException.NotConnectedException e) {
                        Log.d(TetGlobalData.TAG_TET_A_TET, e.toString());
                    }
                }
            });

            Log.i(pseudo_tag, "" + pseudo_tag + " SENDING message ="+oucoming_Message+"! ");
            try {

                newChat.sendMessage(oucoming_Message);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }

        }
        return "";
    }

    @Override
    protected void onPostExecute(String v) {
        super.onPostExecute(v);
         callback.onPostExecute(true);
    }



}