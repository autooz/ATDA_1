package mobi.tet_a_tet.atda.tet_a_tet.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

/**
 * Created by oleg on 15.11.15.
 */
public class setDsConfig {

    private String outcoming_message;
    private int RequestCode;

    private String drvphone;
    private String drvsign;
    private String cargosnum;
    private String drvpassword;
    Context context;
    public setDsConfig(){
        super();
    }


    public setDsConfig(Context applicationContext) {
        context = applicationContext;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        drvphone = prefs.getString(TetGlobalData.SDRVPHONE_KEY, "");
        drvsign = prefs.getString(TetGlobalData.SDRVSIGN_KEY, "");
        cargosnum = prefs.getString(TetGlobalData.SCARGOSNUM_KEY, "");
        drvpassword = prefs.getString(TetGlobalData.SDRVPASSWD_KEY, "");
        TetGlobalData.DRVPHONE = drvphone;
        TetGlobalData.DRVSIGN = drvsign;
        TetGlobalData.CARGOSNUM = cargosnum;
        TetGlobalData.DRVPASSWORD = drvpassword;
        Log.e("setDsConfig","OK");
    }
}
