package mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import mobi.tet_a_tet.atda.MainActivity;
import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.communications.AbstractEjabberdConnectFirstSrverRegistration;
import mobi.tet_a_tet.atda.mutual.communications.AbstractEjabberdConnectFirstSrverRegistrationInteface;
import mobi.tet_a_tet.atda.mutual.communications.AbstractJabberd2ConnectAcyncTasker;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.tet_a_tet.CheckDSsettingsActivity;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.utils.setDsConfig;
import mobi.tet_a_tet.atda.tet_a_tet.utils.setJabberConfig;

/*!
\brief This is the first class to start the connection to the Tet-a-Tet server .
*/
/*!        In this class, a test is performed on the user registration Jabberd server and dispatcher's server .
*
*       Depending of the result an user be routed to different activity
*/

public class TetFirstRegistrationMainActivity extends Activity implements AbstractEjabberdConnectFirstSrverRegistrationInteface<Integer> {

    private int resultCode;
    public Vibrator vibrator;
    public static Intent processing;
    Intent dyn_activity;
    Context context;
    Object callback;
    SharedPreferences settings;
    private int whoOpen;
    private String pseudo_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        android.util.Log.d(TetGlobalData.TAG_TET_A_TET, "=========== ЗАПУСК "+pseudo_tag+" ===============");
        /// --- What's going on here ----
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");
        /// Check is last loging OK and prefs coped to cache on device Hdd act....
        /// Geting Setting cached
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        checkPrefs();
    }

    public void checkPrefs() {

        ///  boolean EulaOK = settings.getBoolean(TetGlobalData.TAG_EULA_KEY_AGREEMENT, false);
        String Revision = settings.getString(TetGlobalData.TAG_REV_VALUE, "");
        boolean JabServerOK = settings.getBoolean(TetGlobalData.SJBS_KEY_BOOL, false);
        boolean DsServerOK = settings.getBoolean(TetGlobalData.ADSS_KEY_BOOL, false);
        String CachServer = settings.getString(TetGlobalData.SJBS_KEY, "");
        android.util.Log.d("TetFirstRegistrMA", "!!!!!!!!!!!!!!!!! DEBUG START WHILE checkPrefs JabServerOK=" + JabServerOK + " DsServerOK=" + DsServerOK + " CachServer=" + CachServer + " TetGlobalData.CARRENT_ACTIVITY="+TetGlobalData.CARRENT_ACTIVITY+"");

        if (JabServerOK && !DsServerOK && TetGlobalData.CARRENT_ACTIVITY != TetGlobalData.DS_LOGIN_ACTIVITY ) {
            TetGlobalData.CARRENT_ACTIVITY = 0;
            /// This is the first connection, and so we need to enter a password and a server to Jabberd Server/
            Intent DispatcherSoftLoginActivity = new Intent(getApplicationContext(), TetFirstRegistrationDispatcherSoftLoginActivity.class);
            android.util.Log.d("TetFirstRegistrMA", "!!!!!!!!!!!!!!!!! DEBUG START WHILE !!!  DS_LOGIN_ACTIVITY");
            startActivityForResult(DispatcherSoftLoginActivity, TetGlobalData.DS_LOGIN_ACTIVITY);
        } else if (!JabServerOK && !DsServerOK && TetGlobalData.CARRENT_ACTIVITY != TetGlobalData.JAB_LOGIN_ACTIVITY || JabServerOK && CachServer.length() < 3) {
            TetGlobalData.CARRENT_ACTIVITY = 0;
            Intent jabberdLoginActivity = new Intent(getApplicationContext(), TetFirstJabRegActivity.class);
            android.util.Log.d("TetFirstRegistrMA", "!!!!!!!!!!!!!!!!! DEBUG START WHILE !!!  JAB_LOGIN_ACTIVITY");
            startActivityForResult(jabberdLoginActivity, TetGlobalData.JAB_LOGIN_ACTIVITY);
        } else if (JabServerOK && !DsServerOK && TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.DS_LOGIN_ACTIVITY) {
                if(whoOpen == TetGlobalData.DS_LOGIN_ACTIVITY){
                    whoOpen = 0;
                    Toast.makeText(getApplicationContext(), R.string.attemptToJabLogin, Toast.LENGTH_LONG).show();
                    doAttemptToConnetJabberd();


                }
        }else if(JabServerOK && DsServerOK && TetGlobalData.CARRENT_ACTIVITY == 0) {
            /// set date for DS connection from SharedPrefs
           // dsConnectRecordToSharePrefs();
            new setDsConfig(getApplicationContext());
            android.util.Log.d("TetFirstRegistrMA", "!!!!!!!!!!!!!!!!! DEBUG START WHILE !!! TO_START_WORK_ACTIVITY");
            Log.e("TAg", "in TetFirstRegistrationMainActivity befor CheckDSsettingsActivity" + TetGlobalData.DRVPHONE + " " + TetGlobalData.DRVSIGN + " " + TetGlobalData.CARGOSNUM + " " + TetGlobalData.DRVPASSWORD + "");
            Intent SW = new Intent(getApplicationContext(), CheckDSsettingsActivity.class);
            SW.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(SW);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), R.string.conSetting_error, Toast.LENGTH_LONG).show();
            Intent jabberdLoginActivity = new Intent(getApplicationContext(), TetFirstJabRegActivity.class);
            startActivityForResult(jabberdLoginActivity, TetGlobalData.JAB_LOGIN_ACTIVITY);
        }
    }

//    protected boolean isDataServiceRunning() {
//        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager
//                .getRunningServices(Integer.MAX_VALUE)) {
//            if ("mobi.tet_a_tet.atda.tet_a_tet.ProcessingService"
//                    .equals(service.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        whoOpen = requestCode;

        if (requestCode == TetGlobalData.JAB_LOGIN_ACTIVITY) {
            android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG START WHILE !!! onActivityResult JAB_LOGIN_ACTIVITY RESULT_FIRST_USER");
            if (resultCode == RESULT_FIRST_USER) {
                    settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    new setJabberConfig(resultCode, data, settings);
                    doAttemptToConnetJabberd();
//                if (TetGlobalData.JconnectedANDloged == true) {
//                    android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG TetGlobalData.JconnectedANDloged == true !!!  befor jabConnectRecordToSharePrefs()");
//                    jabConnectRecordToSharePrefs();
//                }
//                android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG befor Prefs After AsyncTask.Status.FINISHED !!!  AsyncTask.Status.FINISHED");
//                //checkPrefs();
            } else {
                android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!!ERROR !isDataServiceRunning() FINISHED !!!");
            }
        }
        if (requestCode == TetGlobalData.DS_LOGIN_ACTIVITY) {
            // First Loging to Dispatcher Server Processing
            android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG START WHILE !!! onActivityResult RESULT_OK");
            if (resultCode == RESULT_OK) {
                    settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    new setJabberConfig(resultCode, data, settings);
                TetGlobalData.CARRENT_ACTIVITY = TetGlobalData.DS_LOGIN_ACTIVITY;
                checkPrefs();
                //   doAttemptToConnetJabberd();
//                if (TetGlobalData.DSconnectedANDloged == true) {
//                    android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG TetGlobalData.DSconnectedANDloged == true !!!  befor dsConnectRecordToSharePrefs()");
//                    settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    new setJabberConfig(resultCode, data, settings);
//                   // dsConnectRecordToSharePrefs();
//                  //  checkPrefs();
//                }
//                else {
//                   // doAttemptToConnetJabberd();
//                    checkPrefs();
//                }
//                android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG befor Prefs After AsyncTask.Status.FINISHED !!!  AsyncTask.Status.FINISHED");

            } else if (resultCode == RESULT_CANCELED && TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.DS_LOGIN_ACTIVITY) {
                Log.d(pseudo_tag,"Kill because esultCode == RESULT_CANCELED && TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.DS_LOGIN_ACTIVITY ");
                finish();
            }

        }
        if (resultCode == TetGlobalData.BACKSTACK) {
            onResume();
            finish();
        }

    }


    private void dsConnectRecordToSharePrefs() {
        Log.e("TAg", "in TetFirstRegistrationMainActivity/dsConnectRecordToSharePrefs()" + TetGlobalData.DRVPHONE + " " + TetGlobalData.DRVSIGN + " " + TetGlobalData.CARGOSNUM + " " + TetGlobalData.DRVPASSWORD + "");
        SharedPreferences setingDS = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = setingDS.edit();
        editor.putBoolean(TetGlobalData.ADSS_KEY_BOOL, true);
        editor.putString(TetGlobalData.SDRVPHONE_KEY, TetGlobalData.DRVPHONE);
        editor.putString(TetGlobalData.SDRVSIGN_KEY, TetGlobalData.DRVSIGN);
        editor.putString(TetGlobalData.SCARGOSNUM_KEY, TetGlobalData.CARGOSNUM);
        editor.putString(TetGlobalData.SDRVPASSWD_KEY, TetGlobalData.DRVPASSWORD);
        editor.commit();
        android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG dsConnectRecordToSharePrefs() OK");
    }

    private void jabConnectRecordToSharePrefs() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(TetGlobalData.SJBS_KEY_BOOL, true);
        editor.putString(TetGlobalData.SJBS_KEY, TetGlobalData.JBS);
        editor.putString(TetGlobalData.SJBU_KEY, TetGlobalData.JBU);
        editor.putString(TetGlobalData.SJBP_KEY, TetGlobalData.JBP);
        editor.putInt(TetGlobalData.SJPORT_KEY, TetGlobalData.JPORT);
        editor.putString(TetGlobalData.SJCALEE_KEY, TetGlobalData.JBC);
        editor.commit();

        android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG jabConnectRecordToSharePrefs OK");
    }

    public void startVibrate(View v) {
        long pattern[] = {0, 100, 200, 300, 400};
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 0);
    }

    public void stopVibrate(View v) {
        vibrator.cancel();
    }

    public void onBackPressed() {
        // here remove code for your last fragment
        Intent mA = new Intent(getApplicationContext(), MainActivity.class);
        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mA);
        finish();

    }

    private void doAttemptToConnetJabberd() {
        if (TetGlobalData.JBS.equals("172.14.2.101")) {
            new AbstractJabberd2ConnectAcyncTasker<Integer>(this) {
                @Override
                protected boolean doAction() throws Exception {
                    Thread.currentThread().wait(1000);
                    return false;
                }
            }.execute();
        } else {
            new AbstractEjabberdConnectFirstSrverRegistration<Integer>(this) {
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

        Log.d("Async", "Begin");
    }

    @Override
    public void onSuccess(Boolean data) {
                Log.d(pseudo_tag,"makin  onSuccess");

        if (data == true && TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.JAB_LOGIN_ACTIVITY) {
            Log.d(pseudo_tag,"makin  onSuccess TetGlobalData.JAB_LOGIN_ACTIVITY");
            TetGlobalData.CARRENT_ACTIVITY =0;
            jabConnectRecordToSharePrefs();
            android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG TetGlobalData.JconnectedANDloged == true !!!  befor jabConnectRecordToSharePrefs()");
        }
        if (data == true && TetGlobalData.CARRENT_ACTIVITY == TetGlobalData.DS_LOGIN_ACTIVITY) {
            Log.d(pseudo_tag,"makin  onSuccessTetTet GlobalData.DS_LOGIN_ACTIVITY");
            TetGlobalData.CARRENT_ACTIVITY =0;
            dsConnectRecordToSharePrefs();
            android.util.Log.d("FirstRegistrationMain", "!!!!!!!!!!!!!!!!! DEBUG TetGlobalData.JconnectedANDloged == true and mesage trye !!!  after dispConnectRecordToSharePrefs()");
        }
        Log.d("Async", "Success:" + data);
        if (data != true) {

        }
        checkPrefs();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("FirstRegistrationMain", "Failure", t);
    }

    @Override
    public void onEnd() {
        Log.d("FirstRegistrationMain", "End");
    }

    @Override
    public void onRecievedMessage(String message) {
        Log.d("FirstRegistrationMain", "onRecievedMessage(String message)=" + message + "");
    }

    public static void onRestartActivity(Activity act){
        Intent intent=new Intent();
        intent.setClass(act, act.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        act.startActivity(intent);
        act.finish();

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





