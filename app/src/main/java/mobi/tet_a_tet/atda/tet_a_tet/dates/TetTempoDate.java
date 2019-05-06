package mobi.tet_a_tet.atda.tet_a_tet.dates;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Created by oleg on 24.08.15.
 */
public class TetTempoDate {

    public static int temp_int_1;
    public static int temp_int_2;
    public static int temp_int_3;
    public static int temp_int_4;
    public static int temp_int_5;

    public static String temp_str_1;
    public static String temp_str_2;
    public static String temp_str_3;
    public static String temp_str_4;
    public static String temp_str_5;
    public static String last_jab_incom_msg;
public static Thread runable_listner;
    public static XMPPTCPConnection jabberd_connect;

    public static void setRunning_listner_Thread_To_TetTempoDate(Thread running_listner) {
        TetTempoDate.runable_listner = running_listner;
    }


    public static void setJabberd_connect(XMPPTCPConnection jabberd_connect) {
        TetTempoDate.jabberd_connect = jabberd_connect;
    }

    public void setTetTempoDate(String name, String newVar) {
        name = newVar;
    }


}
