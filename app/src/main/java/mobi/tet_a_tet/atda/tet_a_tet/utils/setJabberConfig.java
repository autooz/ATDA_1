package mobi.tet_a_tet.atda.tet_a_tet.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

/**
 * Created by oleg on 08.07.15.
 */
public class setJabberConfig {
    private Activity setActivity;

    public setJabberConfig(Activity context) {
        setActivity = context;
    }


    private String outcoming_message;
    private int RequestCode;

    private String jserver;
    private String juser;
    private String jpassw;
    private String jdisp;
    private String drvphone;
    private String drvsign;
    private String cargosnum;
    private String drvpassword;

    public setJabberConfig() {
        super();
    }


    public setJabberConfig(int resultCode, Intent data, SharedPreferences preferences) {

        synchronized (this) {
            RequestCode = resultCode;
            outcoming_message = null;
        }
        android.util.Log.d("setJabberConfig", "!!!!!!!!!!!!!!!!! DEBUG DELATE AFTER ALL!!! TOP  jabserver " + TetGlobalData.JBS + " jabuser " + TetGlobalData.JBU +
                " jabpassword " + TetGlobalData.JBP + " jabdisp " + TetGlobalData.JBC +
                " TetGlobalData.JAB_LOGIN_ACTIVITY " + TetGlobalData.JAB_LOGIN_ACTIVITY +
                " \n" +
                " resultCode " +resultCode+
                " CARRENT_ACTIVITY "+TetGlobalData.CARRENT_ACTIVITY+
                "");

        if (RequestCode == Activity.RESULT_FIRST_USER && TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.JAB_LOGIN_ACTIVITY) {
            jserver = data.getStringExtra(TetGlobalData.SJBS_KEY);
            juser = data.getStringExtra(TetGlobalData.SJBU_KEY);
            jpassw = data.getStringExtra(TetGlobalData.SJBP_KEY);
            jdisp = data.getStringExtra(TetGlobalData.SJCALEE_KEY);

            TetGlobalData.JBS = jserver;
            TetGlobalData.JBU = juser;
            TetGlobalData.JBP = jpassw;
            TetGlobalData.JBC = jdisp;

            android.util.Log.d("setJabberConfig", "!!!!!!!!!!!!!!!!! DEBUG DELATE AFTER ALL!!! TOP  jabserver " + TetGlobalData.JBS + " jabuser " + TetGlobalData.JBU + " jabpassword " + TetGlobalData.JBP + " jabdisp " + TetGlobalData.JBC + " TetGlobalData.JAB_LOGIN_ACTIVITY " + TetGlobalData.JAB_LOGIN_ACTIVITY + "");

        } else if (RequestCode == Activity.RESULT_OK && TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.DS_LOGIN_ACTIVITY) {
            android.util.Log.d("setJabberConfig", "!!!!!!!!!!!!!!!!! DEBUG DELATE AFTER ALL!!! TOP DS_LOGIN_ACTIVITY");
            drvphone = data.getStringExtra(TetGlobalData.SDRVPHONE_KEY);
            drvsign = data.getStringExtra(TetGlobalData.SDRVSIGN_KEY);
            cargosnum = data.getStringExtra(TetGlobalData.SCARGOSNUM_KEY);
            drvpassword = data.getStringExtra(TetGlobalData.SDRVPASSWD_KEY);
            final SharedPreferences prefs = preferences;
            jserver = prefs.getString(TetGlobalData.SJBS_KEY, "");
            juser = prefs.getString(TetGlobalData.SJBU_KEY, "");
            jpassw = prefs.getString(TetGlobalData.SJBP_KEY, "");
            jdisp = prefs.getString(TetGlobalData.SJCALEE_KEY, "");

            TetGlobalData.JBS = jserver;
            TetGlobalData.JBU = juser;
            TetGlobalData.JBP = jpassw;
            TetGlobalData.JBC = jdisp;
            TetGlobalData.DRVPHONE = drvphone;
            TetGlobalData.DRVSIGN = drvsign;
            TetGlobalData.CARGOSNUM = cargosnum;
            TetGlobalData.DRVPASSWORD = drvpassword;
            android.util.Log.d("setJabberConfig", "!!!!!!!!!!!!!!!!! DEBUG DELATE AFTER ALL!!!  TetGlobalData.DRVPHONE " + TetGlobalData.DRVPHONE + " TetGlobalData.DRVSIGN " + TetGlobalData.DRVSIGN + " jabpassword " + TetGlobalData.CARGOSNUM+ " jabdisp " + TetGlobalData.CARGOSNUM+ "");

        } else {
            final SharedPreferences prefs = preferences;
            jserver = prefs.getString(TetGlobalData.SJBS_KEY, "");
            juser = prefs.getString(TetGlobalData.SJBU_KEY, "");
            jpassw = prefs.getString(TetGlobalData.SJBP_KEY, "");
            jdisp = prefs.getString(TetGlobalData.SJCALEE_KEY, "");
            drvphone = prefs.getString(TetGlobalData.SDRVPHONE_KEY, "");
            drvsign = prefs.getString(TetGlobalData.SDRVSIGN_KEY, "");
            cargosnum = prefs.getString(TetGlobalData.SCARGOSNUM_KEY, "");
            drvpassword = prefs.getString(TetGlobalData.SDRVPASSWD_KEY, "");

            TetGlobalData.JBS = jserver;
            TetGlobalData.JBU = juser;
            TetGlobalData.JBP = jpassw;
            TetGlobalData.JBC = jdisp;
            TetGlobalData.DRVPHONE = drvphone;
            TetGlobalData.DRVSIGN = drvsign;
            TetGlobalData.CARGOSNUM = cargosnum;
            TetGlobalData.DRVPASSWORD = drvpassword;

        }

        android.util.Log.d("setJabberConfig", "!!!!!!!!!!!!!!!!! DEBUG DELATE AFTER ALL!!!  jabserver " + TetGlobalData.JBS + " jabuser " + TetGlobalData.JBU + " jabpassword " + TetGlobalData.JBP + " jabdisp " + TetGlobalData.JBC + "");

    }


}

