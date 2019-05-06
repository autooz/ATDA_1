package mobi.tet_a_tet.atda.tet_a_tet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.StringTokenizer;
import java.util.TimerTask;
import java.util.jar.JarEntry;

import mobi.tet_a_tet.atda.MainActivity;
import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.communications.AbstractEjabberdGetDsSettings;
import mobi.tet_a_tet.atda.mutual.communications.AbstractEjabberdGetDsSettingsInteface;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSListnerZone;
import mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.StartWorkActivity;
import mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration.ThenksForRegActivity;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGpsData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetTempoDate;
import mobi.tet_a_tet.atda.tet_a_tet.utils.setJabberConfig;
import mobi.tet_a_tet.atda.tet_a_tet.utils.setTetATetSettingDateDS;

public class CheckDSsettingsActivity extends Activity implements View.OnClickListener, AbstractEjabberdGetDsSettingsInteface<Integer> {

    SharedPreferences settings;
    private RelativeLayout checkDSsetting;
    private TextView cheskDsSettingstV;
    private ProgressBar progressBarChDs;
    setTetATetSettingDateDS setDsSettings;
    BroadcastReceiver br;
    private String pseudo_tag;
    Context cont = this;

    public boolean getSetingOK() {
        return TetATetSettingDate.settingOK;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_ds_settings);
        android.util.Log.d(TetGlobalData.TAG_TET_A_TET, "=========== ЗАПУСК  CheckDSsettingsActivity===============");
        Log.e(pseudo_tag, "in CheckDSsettingsActivity" + TetGlobalData.DRVPHONE + " " + TetGlobalData.DRVSIGN + " " + TetGlobalData.CARGOSNUM + " " + TetGlobalData.DRVPASSWORD + "");

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");


        checkDSsetting = (RelativeLayout) findViewById(R.id.checkDSsetting);
        cheskDsSettingstV = (TextView) findViewById(R.id.cheskDsSettingstV);
        progressBarChDs = (ProgressBar) findViewById(R.id.progressBarChDs);
        findViewById(R.id.buttonExit).setOnClickListener(this);

        progressBarChDs.setVisibility(ProgressBar.VISIBLE);

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        EventBus.getDefault().register(this);
        ///Check GPS is ON/OFF


        String clear_wihtoutGPS = settings.getString(TetATetSettingDate.clear_without_GPS__key, "");

        /// set TetATetSettingDateDS
//        setDsSettings = new setTetATetSettingDateDS(this);
//        setDsSettings.setTetATetSettingDateDS();
        ///Check GPS is ON/OFF
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        TetGpsData.canGetLocation = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Intent PLS = new Intent(getApplicationContext(), GPSListnerZone.class);
        startService(PLS);

        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG GPS clear_wihtoutGPS=" + clear_wihtoutGPS + " gps.canGetLocation()=" + TetGpsData.canGetLocation + " gps.getAccuracy()=" + TetGpsData.accuracy_current + "");
        if (TetGpsData.canGetLocation == false && clear_wihtoutGPS.equals("false")) {
            ///    gps enabled
            android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG GPS NOT OK");
            showSettingsAlert(); /// Would you like to enable GPS?
        } else {

            android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG GPS OK Recieved gps.getAccuracy()=" + TetGpsData.accuracy_current + "");

            android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG GPS OK gps.getLatitude()=" + TetGpsData.latitude_current + " gps.getAccuracy()=" + TetGpsData.accuracy_current + "");

            settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            /// set jaberrd and Ds configs
            Intent data = new Intent();
            new setJabberConfig(RESULT_CANCELED, data, settings);

            /// Set All TetGlobalDate
            setDsSettings = new setTetATetSettingDateDS(this);
            setDsSettings.setTetATetSettingDateDS();
            Log.e(pseudo_tag, "doAttemptToConnetJabberd() 1");
            doAttemptToConnetJabberd();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonExit:
                Intent PLS1 = new Intent(getApplicationContext(), GPSListnerZone.class);
                stopService(PLS1);

                EventBus.getDefault().unregister(this);
                Intent Oth = new Intent(getApplicationContext(), mobi.tet_a_tet.atda.MainActivity.class);
                Oth.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Oth);
                finish();
                break;
            case R.id.btnChangeServerAndDs:
                ///delate Shared Prefs about DS loging
                delateSharedPrefs();
                Intent PLS = new Intent(getApplicationContext(), GPSListnerZone.class);
                stopService(PLS);
                EventBus.getDefault().unregister(this);
                Intent tAt = new Intent(getApplicationContext(), StartTetFirstRegistrationActivity.class);
                tAt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tAt);
                finish();
                break;
            case R.id.btnStartWork:
                //TODO implement
                settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                String request = "rerrdfsgfggdge";
                Intent data = new Intent();
                new setJabberConfig(RESULT_CANCELED, data, settings);
                Log.e(pseudo_tag, "doAttemptToConnetJabberd() 2");
                doAttemptToConnetJabberd();
                break;
        }
    }

    public void delateSharedPrefs() {
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(TetGlobalData.ADSS_KEY_BOOL);
        editor.remove(TetGlobalData.SDRVPHONE_KEY);
        editor.remove(TetGlobalData.SDRVSIGN_KEY);
        editor.remove(TetGlobalData.SCARGOSNUM_KEY);
        editor.remove(TetGlobalData.SDRVPASSWD_KEY);
        editor.commit();
        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG  delateSharedPrefs OK");
    }

    public void setTetATetSettingDate() {

        setDsSettings = new setTetATetSettingDateDS(this);
        setDsSettings.setTetATetSettingDateDS();
        Log.e(pseudo_tag, "Ettempt open StartWorkActivity from " + pseudo_tag + "");
//
//        if (TetTempoDate.temp_int_1 == TetGlobalData.DS_LOGIN_ACTIVITY) {
//            Intent SW = new Intent(getApplicationContext(), ThenksForRegActivity.class);
//            SW.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(SW);
//            onDestroy();
//            finish();
//
//        } else {
            Intent sT = new Intent(getApplicationContext(), StartWorkActivity.class);
            sT.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            sT.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(sT);
            finish();
       // }
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        /// Setting Dialog Title
        alertDialog.setTitle(R.string.enable_GPS);

        /// Setting Dialog Message
        alertDialog.setMessage(R.string.gpsIsOFF);

        /// Setting Icon to Dialog
        ///alertDialog.setIcon(R.drawable.delete);

        /// On pressing Settings button
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent Oth = new Intent(getApplicationContext(), mobi.tet_a_tet.atda.MainActivity.class);
                Oth.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                EventBus.getDefault().unregister(this);
                startActivity(Oth);

                Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(viewIntent);
                finish();
                dialog.cancel();


            }
        });

        /// on pressing cancel button
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent PLS1 = new Intent(cont, GPSListnerZone.class);
                stopService(PLS1);
                EventBus.getDefault().unregister(this);
                dialog.cancel();
            }
        });

        /// Showing Alert Message
        alertDialog.show();
    }

    private void doAttemptToConnetJabberd() {
        if (TetGlobalData.JBS.equals("172.14.2.101")) {
            new AbstractEjabberdGetDsSettings<Integer>(this) {
                @Override
                protected boolean doAction() throws Exception {
                    Thread.currentThread().wait(1000);
                    return false;
                }
            }.execute();
        } else {
            new AbstractEjabberdGetDsSettings<Integer>(this) {
                @Override
                protected boolean doAction() throws Exception {
                    Thread.currentThread().wait(1000);
                    return false;
                }
            }.execute();
        }
    }


    @Override
    public void onBegin() {

    }

    @Override
    public void onSuccess(String data) {
        if (data.equals("true")) {

        }
    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onRecievedMessage(String message) {

        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG jabMessage " + message + "");
        /// Get last setting from the server
        String dsSetingVer = settings.getString(TetATetSettingDate.DS_SETING_VERS_KEY, "");
        StringTokenizer st = new StringTokenizer(message, TetGlobalData.TOKEN_SEPARATOR);
        String token = st.nextToken();
        if (token.equals(TetGlobalData.RESPONCE)) {
            String tokenres = st.nextToken();
            if (tokenres.equals(TetGlobalData.DS_SETUP_OK)) {
                String sTok = st.nextToken();
                android.util.Log.d(pseudo_tag, "---- sTok " + sTok + "---");
                StringTokenizer st22 = new StringTokenizer(sTok, TetGlobalData.TOKEN_SEPARATOR_VARS);
                String varb = st22.nextToken();
                int i = 0;
                while (st22.hasMoreElements()) {
                    if (varb.equals("drvbalance") || i > 3) {
                        String bal = st22.nextToken();
                        TetDriverData.drvbalance = bal;
                        android.util.Log.d(pseudo_tag, "---- bal " + bal + "---");
                    } else {
                        i++;
                    }
                }
                setTetATetSettingDate();
            } else if (tokenres.equals(TetGlobalData.DS_SETUP_FALSE)) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                while (st.hasMoreElements()) {
                    String fTok = st.nextToken();
                    android.util.Log.d(pseudo_tag, "---- Split by '" + TetGlobalData.TOKEN_SEPARATOR + "' ---'" + fTok + "'---");
                    StringTokenizer st2 = new StringTokenizer(fTok, TetGlobalData.TOKEN_SEPARATOR_VARS);

                    String var = st2.nextToken();
                    while (st2.hasMoreElements()) {
                        if (var.equals("drvbalance")) {
                            String bal = st2.nextToken();
                            TetDriverData.drvbalance = bal;
                            android.util.Log.d(pseudo_tag, "---- bal " + bal + "---");
                        } else {
                            st2.countTokens();
                            String value = st2.nextToken();
                            android.util.Log.d(pseudo_tag, "---- Split var'" + var + "' ---value --'" + value + "'---");
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(var, value);
                            editor.commit();
                        }
                    }
                }
                android.util.Log.d(pseudo_tag, "---- TetDriverData.drvbalanse " + TetDriverData.drvbalance + "---");
//                setDsSettings = new setTetATetSettingDateDS(this);
//                setDsSettings.setTetATetSettingDateDS();
                setTetATetSettingDate();

            } else if (token.equals(dsSetingVer) && !token.equals("")) {
                android.util.Log.d(pseudo_tag, "++++!!!!!!!!!!!!!!!!! DEBUG  StringTokenizer OK OK OK OK !!!!!!!!++++");
                setTetATetSettingDate();
            } else {
                Log.e(pseudo_tag, "ERROR WITH tokenres.equals(TetGlobalData.DS_SETUP_OK))");
            }

        } else if (token.equals(TetGlobalData.REQUEST)) {

        } else {
            android.util.Log.e(pseudo_tag, "---- ERROR jn first tocken =" + token + " with Message=" + message + "---");
        }

    }

    @Override
    public void onBackPressed() {
        /// here reload this code
        Intent mA = new Intent(getApplicationContext(), MainActivity.class);
        TetGpsData.gpsZone.stopUsingGPS();
        EventBus.getDefault().unregister(this);
        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mA);
        finish();

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(pseudo_tag, "onResume()");
    }
}

