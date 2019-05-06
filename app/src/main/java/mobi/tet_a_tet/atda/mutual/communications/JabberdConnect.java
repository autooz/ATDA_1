package mobi.tet_a_tet.atda.mutual.communications;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.provided.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.TLSUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetTempoDate;

/**
 * Created by oleg on 10.08.15.
 */
public abstract class JabberdConnect<V> implements Runnable {
    private JabberdConnectInterface<V> callback;

    public AbstractXMPPConnection jConnection;
    private static String pseudo_tag;


    protected JabberdConnect(JabberdConnectInterface<V> callback) {
        this.callback = (JabberdConnectInterface<V>) callback;
    }

    public AbstractXMPPConnection JabberdGetConect() {
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
        return jConnection;

    }

    @Override
    public void run() {

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        System.out.println("" + pseudo_tag + " ==== >>>>> Making run()");
        if(jConnection == null){
            jConnection = JabberdGetConect();
            try {
                System.out.println("" + pseudo_tag + " Jabberd connecting");
                TetTempoDate.jabberd_connect.connect();
                System.out.println("" + pseudo_tag + " Jabberd connected");
                TetTempoDate.jabberd_connect.login();
                System.out.println(""+ pseudo_tag+" Jabberd logged in");
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

        }
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
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
                listningmessage(connection);
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

    }

    private void listningmessage(XMPPConnection connection) {


    }


}
