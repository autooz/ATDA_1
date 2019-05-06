package mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import mobi.tet_a_tet.atda.MainActivity;
import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventJabOutcomMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventsGPS;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscriber;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSListnerZone;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSbyTimeListner;
import mobi.tet_a_tet.atda.mutual.mut_ulils.toServerSenderAcyncTasker;
import mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration.TetFirstRegistrationMainActivity;
import mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs.ProgresBarWaitResponceActivity;
import mobi.tet_a_tet.atda.tet_a_tet.controllers.ActivityControllerService;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGpsData;
import mobi.tet_a_tet.atda.tet_a_tet.utils.CheckIsPointPoligonsFromDate;

public class StartWorkActivity extends Activity implements View.OnClickListener {


    private TextView startWorkTitleMessage;
    private TableRow tableRow1;
    private TextView loginLabel;
    private TableRow tableRow2;
    private TextView carLabel;
    private TableRow tableRow3;
    private TextView passwordLabel;
    private TableRow tableRowBalance;
    private TextView textViewYouBallance;
    private TextView textViewDrvBalance;
    private TextView currency;
    private TextView loginMessageText2;
    private RadioGroup radioGroup;
    private TableRow tableRow4;
    private RadioButton radioButtonByOrder;
    private RadioButton radioButtonByOrderNight;
    private TextView priceByOrder;
    private TextView priceByOrderNight;
    private TextView currency0;
    private TextView currencyNight;
    private TextView byOrderTextField;
    private TextView byOrderTextFieldNight;
    private TableRow tableRow5;
    private RadioButton radioButtonByShift;
    private TextView priceByShift;
    private TextView currensy1;
    private TextView byShiftTextField;
    private TableRow tableRow6;
    private RadioButton radioButtonByHourly;
    private TextView tammountHourTextFild;
    private TextView priceByHour;
    private TextView currency3;
    private TextView byHourTextField;
    private TableRow tableRow7;
    private TableRow tableRow8;
    private TextView doOrder;
    private CheckBox checkboxByHand;

    private CheckIsPointPoligonsFromDate wPTD;
  //  Context mContext = this;
    private static final int ACCURASY = Integer.parseInt(TetATetSettingDate.minGPSaccuracy);
    BroadcastReceiver br;
    private String pseudo_tag;
    private ServiceConnection serviceConnection;
    private  int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.util.Log.d(TetGlobalData.TAG_TET_A_TET, "=========== ЗАПУСК  StartWorkActivity===============");
        Log.e(pseudo_tag, "in CheckDSsettingsActivity" + TetGlobalData.DRVPHONE + " " + TetGlobalData.DRVSIGN + " " + TetGlobalData.CARGOSNUM + " " + TetGlobalData.DRVPASSWORD + "");

        setContentView(R.layout.activity_start_work);
        EventBus.getDefault().registerSticky(this);


        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");



        startWorkTitleMessage = (TextView) findViewById(R.id.startWorkTitleMessage);
        tableRow1 = (TableRow) findViewById(R.id.tableRow1);
        loginLabel = (TextView) findViewById(R.id.loginLabel);
        tableRow2 = (TableRow) findViewById(R.id.tableRow2);
        carLabel = (TextView) findViewById(R.id.carLabel);
        tableRow3 = (TableRow) findViewById(R.id.tableRow3);
        passwordLabel = (TextView) findViewById(R.id.passwordLabel);
        tableRowBalance = (TableRow) findViewById(R.id.TableRowBalance);
        textViewYouBallance = (TextView) findViewById(R.id.textViewYouBallance);
        textViewDrvBalance = (TextView) findViewById(R.id.textViewDrvBalance);
        textViewDrvBalance.setText(TetDriverData.drvbalance);
        currency = (TextView) findViewById(R.id.currency);
        currency.setText(TetATetSettingDate.currency);
        loginMessageText2 = (TextView) findViewById(R.id.loginMessageText2);
        radioGroup = (RadioGroup) findViewById(R.id.radioButtonGroupPayment);
        tableRow4 = (TableRow) findViewById(R.id.tableRow4);
        radioButtonByOrder = (RadioButton) findViewById(R.id.radioButtonByOrder);
       // radioButtonByOrderNight = (RadioButton) findViewById(R.id.radioButtonByOrderNight);
        priceByOrder = (TextView) findViewById(R.id.priceByOrder);
        priceByOrder.setText(TetATetSettingDate.pay_by_order_day);
        priceByOrderNight = (TextView) findViewById(R.id.priceByOrderNight);
        priceByOrderNight.setText(TetATetSettingDate.pay_by_order_night);
        currency0 = (TextView) findViewById(R.id.currency0);
        currency0.setText(TetATetSettingDate.currency);
        currencyNight = (TextView) findViewById(R.id.currencyNight);
        currencyNight.setText(TetATetSettingDate.currency);
        byOrderTextField = (TextView) findViewById(R.id.byOrderTextField);
        byOrderTextFieldNight = (TextView) findViewById(R.id.byOrderTextFieldNight);
        tableRow5 = (TableRow) findViewById(R.id.tableRow5);
        radioButtonByShift = (RadioButton) findViewById(R.id.radioButtonByShift);
        priceByShift = (TextView) findViewById(R.id.priceByShift);
        priceByShift.setText(TetATetSettingDate.pay_by_staf);
        currensy1 = (TextView) findViewById(R.id.currensy1);
        currensy1.setText(TetATetSettingDate.currency);
        byShiftTextField = (TextView) findViewById(R.id.byShiftTextField);
        tableRow6 = (TableRow) findViewById(R.id.tableRow6);
        radioButtonByHourly = (RadioButton) findViewById(R.id.radioButtonByHourly);
        tammountHourTextFild = (TextView) findViewById(R.id.tammountHourTextFild);
        priceByHour = (TextView) findViewById(R.id.priceByHour);
        priceByHour.setText(TetATetSettingDate.pay_by_hour);
        currency3 = (TextView) findViewById(R.id.currency3);
        currency3.setText(TetATetSettingDate.currency);
        byHourTextField = (TextView) findViewById(R.id.byHourTextField);
        tableRow7 = (TableRow) findViewById(R.id.tableRow7);
        findViewById(R.id.btnExit).setOnClickListener(this);
        findViewById(R.id.btnStartWork).setOnClickListener(this);
        tableRow8 = (TableRow) findViewById(R.id.tableRow8);
        findViewById(R.id.btnChangeServerAndDs).setOnClickListener(this);
        TextView login = (TextView) findViewById(R.id.loginField);

        TextView carnum = (TextView) findViewById(R.id.carField);

        TextView dspasswd = (TextView) findViewById(R.id.passwordField);

        doOrder = (TextView) findViewById(R.id.textViewOrdHour);
        checkboxByHand = (CheckBox) findViewById(R.id.checkBoxZoneByHand);
        findViewById(R.id.buttonCheckUpdate).setOnClickListener(this);

        Log.e(pseudo_tag, "in onCreate" + TetGlobalData.DRVPHONE + " " + TetGlobalData.DRVSIGN + " " + TetGlobalData.CARGOSNUM + " " + TetGlobalData.DRVPASSWORD + "");

        login.setText(TetGlobalData.DRVSIGN);
        carnum.setText(TetGlobalData.CARGOSNUM);
        dspasswd.setText(TetGlobalData.DRVPASSWORD);

        if (TetATetSettingDate.zoneChoiseByDriver.equals("true")) {
            checkboxByHand.setVisibility(View.VISIBLE);
        } else {
            checkboxByHand.setVisibility(View.INVISIBLE);
        }

        radioButtonByOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TetDriverData.staffrules_event_class = "0";
                    doOrder.setVisibility(View.INVISIBLE);
                    getAmmountHours().setVisibility(View.INVISIBLE);
                    tammountHourTextFild.setVisibility(View.INVISIBLE);
                    radioButtonByShift.setChecked(false);
                    radioButtonByHourly.setChecked(false);
                }
            }

        });
        radioButtonByShift.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TetDriverData.staffrules_event_class = "1";
                    doOrder.setVisibility(View.INVISIBLE);
                    getAmmountHours().setVisibility(View.INVISIBLE);
                    tammountHourTextFild.setVisibility(View.INVISIBLE);
                    radioButtonByOrder.setChecked(false);
                    radioButtonByHourly.setChecked(false);
                }
            }

        });
        radioButtonByHourly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TetDriverData.staffrules_event_class = "2";
                    doOrder.setVisibility(View.VISIBLE);
                    getAmmountHours().setVisibility(View.VISIBLE);
                    tammountHourTextFild.setVisibility(View.VISIBLE);
                    radioButtonByOrder.setChecked(false);
                    radioButtonByShift.setChecked(false);
                }
            }

        });


        ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);

        for (int i = 0; i < rs.size(); i++) {
            ActivityManager.RunningServiceInfo rsi = rs.get(i);
            Log.i(pseudo_tag, "Process " + rsi.process + " with component "
                    + rsi.service.getClassName());
        }

    }


    private EditText getLoginField() {
        return (EditText) findViewById(R.id.loginField);
    }

    private EditText getCarField() {
        return (EditText) findViewById(R.id.carField);
    }

    private EditText getPasswordField() {
        return (EditText) findViewById(R.id.passwordField);
    }

    private EditText getAmmountHours() {
        return (EditText) findViewById(R.id.ammountHours);
    }


    @Override
    public void onClick(View view) {
        //Эксперементы////////////////////
        android.util.Log.d("StartWorkActivity", "!!!!!!!!!!!!!!!!! DEBUG DELATE AFTER ALL!!! OnClick started");
        //////////////////////////////////////////////////
        switch (view.getId()) {
            case R.id.btnExit:
                //TODO implement
                Intent PLS1 = new Intent(getApplicationContext(), GPSListnerZone.class);
                stopService(PLS1);
                //stopService(new Intent(StartWorkActivity.this, GPSListnerTracker.class));
                EventBus.getDefault().unregister(this);
                Intent Oth = new Intent(getApplicationContext(), mobi.tet_a_tet.atda.MainActivity.class);
                Oth.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Oth);
                finish();
                break;
            case R.id.btnStartWork:
                //TODO implement
                if (checkboxByHand.isChecked()) {
                    TetDriverData.choiseZoneByHand = true;
                } else {
                    TetDriverData.choiseZoneByHand = false;
                }
                doingPrepeadToWork();
                break;
            case R.id.btnChangeServerAndDs:
                //TODO implement

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(TetGlobalData.SJBS_KEY_BOOL);
                editor.remove(TetGlobalData.ADSS_KEY_BOOL);
                editor.remove(TetGlobalData.SJBS_KEY);
                editor.remove(TetGlobalData.SJBU_KEY);
                editor.remove(TetGlobalData.SJBP_KEY);
                editor.remove(TetGlobalData.SJPORT_KEY);
                editor.remove(TetGlobalData.SJCALEE_KEY);
                editor.remove(TetGlobalData.SDRVPASSWD_KEY);
                editor.remove(TetGlobalData.SCARGOSNUM_KEY);
                editor.remove(TetGlobalData.SDRVSIGN_KEY);
                editor.remove(TetGlobalData.SDRVPASSWD_KEY);
                editor.remove(TetGlobalData.SDRVPHONE_KEY);
                // Clear DS settings
                editor.remove(TetATetSettingDate.DS_SETING_VERS_KEY);
                editor.commit();
                Intent mA = new Intent(getApplicationContext(), MainActivity.class);
                mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mA);
                finish();
                break;
            case R.id.buttonCheckUpdate:
                checkUpdate();
                break;

        }
    }

    private void checkUpdate() {
        Intent ACS = new Intent(getApplicationContext(), ActivityControllerService.class);
        startService(ACS);
        String REQUEST = "" +
                TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.CHESK_UPDATE + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.VERSION+ TetGlobalData.TOKEN_SEPARATOR +
                "";
        Log.d(pseudo_tag, "### Du Exit from all previos windows startet wia windowsFragment controller ");
        EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
        new toServerSenderAcyncTasker(this, REQUEST);
        //finish();
//        Intent uA = new Intent(this, UpdateATDAActivity.class);
//        //uA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(uA);
    }

    private void doingPrepeadToWork() {

        Float control_null = null;
        // Compare accuracy min accuracy and current accuracy
        EventBus.getDefault().register(this);
        Intent TLS = new Intent(getApplicationContext(), GPSbyTimeListner.class);
        startService(TLS);
//        Intent JBL = new Intent(getApplicationContext(), JabberListenerService.class);
//        startService(JBL);
//        Intent JCS = new Intent(getApplicationContext(), JabberListenerService.class);
//        bindService(JCS, serviceConnection, this.BIND_AUTO_CREATE);

        Float minAccuracy = Float.parseFloat(TetATetSettingDate.minGPSaccuracy);
        //bindService(new Intent(this, GPSListnerZone.class), sConn, Context.BIND_AUTO_CREATE);
        android.util.Log.d(pseudo_tag, "Compare accuracy min accuracy and current accuracy --minAccuracy = " + minAccuracy + " TetGpsData.accuracy_current=" + TetGpsData.accuracy_current + "");
        android.util.Log.d(pseudo_tag, "TetATetSettingDate.clear_without_GPS = " + TetATetSettingDate.clear_without_GPS + " TetDriverData.without_GPS=" + TetDriverData.without_GPS + "");
        if (TetGpsData.accuracy_current == 0.00 || TetGpsData.accuracy_current > minAccuracy) {
            if (TetATetSettingDate.clear_without_GPS.equals("true") && TetDriverData.without_GPS == true) {
                stopService(TLS);
                sendJabRequesrOutOfCity();

            }
            else if (TetATetSettingDate.clear_without_GPS.equals("false") && TetDriverData.without_GPS == false){

                Intent gPSAccuracyNotGoodActivity = new Intent(getApplicationContext(), GPSAccuracyNotGoodActivity.class);
                android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG Opening gPSAccuracyNotGoodActivity");
                startActivityForResult(gPSAccuracyNotGoodActivity, TetGlobalData.GPS_IS_NOT_GOOD);
            }

        } else {
            // send Starter information and Go to taximetre
            openWindowControllerMain();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        android.util.Log.e(pseudo_tag, "!!!!!!!!!!!!!!!!! requestCode " + requestCode + " !!!");
        if (requestCode == TetGlobalData.GPS_IS_NOT_GOOD) {
            android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG START WHILE !!! onActivityResult JAB_LOGIN_ACTIVITY RESULT_FIRST_USER");
            if (resultCode == RESULT_OK) {

                if (data.getBooleanExtra("without_GPS", true)) {
                    TetDriverData.without_GPS = true;
                    doingPrepeadToWork();
                    //   sendJabRequesrOutOfCity();
                } else {
                    TetDriverData.without_GPS = false;
                    // sendJabRequesrWithPOsition();
                    doingPrepeadToWork();
                }
                finish();
            } else {
                android.util.Log.e(pseudo_tag, "!!!!!!!!!!!!!!!!!ERROR GPS_IS_NOT_GOOD !!!");
            }
        } else if (requestCode == TetGlobalData.WAIT_JB_RESPONCE) {
            android.util.Log.e(pseudo_tag, "!!!!!!!!!!!!!!!!! WAIT_JB_RESPONCE OK WAIT_JB_RESPONCE !!!");
            if (resultCode == RESULT_OK) {
                finish();
            } else {
                android.util.Log.e(pseudo_tag, "!!!!!!!!!!!!!!!!!ERROR WAIT_JB_RESPONCE !!!");
//                Intent wcm = new Intent(getApplicationContext(), WindowFragmentControllerMainActivity.class);
//                wcm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(wcm);
                finish();
            }
        }

    }

    private void sendJabRequesrWithPOsition() {
        String REQUEST = "" +
                TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.REQUEST_START_WORK + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                TetDriverData.drvstopid + TetGlobalData.TOKEN_SEPARATOR +
                TetDriverData.staffrules_event_class + TetGlobalData.TOKEN_SEPARATOR +
                "";
        Intent ACS = new Intent(getApplicationContext(), ActivityControllerService.class);
        startService(ACS);
        bindService(ACS, serviceConnection, this.BIND_AUTO_CREATE);
        Intent ProgresBarWaitResponceActivity = new Intent(getApplicationContext(), ProgresBarWaitResponceActivity.class);
        startActivityForResult(ProgresBarWaitResponceActivity, TetGlobalData.WAIT_JB_RESPONCE);
//        try {
//            i++;
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Log.d(pseudo_tag, "### Du Exit from all previos windows startet wia windowsFragment controller ");
       // EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
        new toServerSenderAcyncTasker(this, REQUEST);
        finish();
    }


    private void sendJabRequesrOutOfCity() {
        String REQUEST = "" +
                TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.REQUEST_START_WORK + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.out_of_city_key + TetGlobalData.TOKEN_SEPARATOR +
                TetDriverData.staffrules_event_class + TetGlobalData.TOKEN_SEPARATOR +
                "";
        Intent ACS = new Intent(getApplicationContext(), ActivityControllerService.class);
        startService(ACS);
        Intent ProgresBarWaitResponceActivity = new Intent(getApplicationContext(), ProgresBarWaitResponceActivity.class);
        startActivityForResult(ProgresBarWaitResponceActivity, TetGlobalData.WAIT_JB_RESPONCE);
        new toServerSenderAcyncTasker(this,REQUEST);
        finish();
    }


    @Override
    public void onBackPressed() {
        // here remove code for your last fragment
        Intent mA = new Intent(getApplicationContext(), TetFirstRegistrationMainActivity.class);
        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mA);
        finish();

    }

    public static void onRestartActivity(Activity act) {
        Intent intent = new Intent();
        intent.setClass(act, act.getClass());
        act.startActivity(intent);
        act.finish();

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED");
        super.onDestroy();
    }

    @Subscriber(tag = "GPSListnerTracker")
    private void updateEventsGPS(EventsGPS message) {
        Log.e(pseudo_tag, "### update user name = " + message.eventFloat);
    }

    private void openWindowControllerMain() {
        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        TetGlobalData.CARRENT_CLASS = onlyClass;

        float lat = Float.parseFloat(new Double(TetGpsData.latitude_current).toString());
        float lon = Float.parseFloat(new Double(TetGpsData.longitude_current).toString());
        wPTD = new CheckIsPointPoligonsFromDate();
        wPTD.CheskIsPOintInPolygon(TetGpsData.latitude_current, TetGpsData.longitude_current);

//        if(TetDriverData.drvstopid.equals("0")){
//            sendJabRequesrOutOfCity();
//        }else{
//            sendJabRequesrOutOfCity();
//           // sendJabRequesrWithPOsition();
//        }

//
//        Intent wcm = new Intent(getApplicationContext(), WindowFragmentControllerMainActivity.class);
//        wcm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(wcm);
//        finish();
    }

    @Subscriber(tag = "CheskIsPOintInPolygon")
    protected void updateEventsGPSWithMode(EventsGPS drvstopid) {
        Log.d(pseudo_tag, "### update user with my_tag, drvstopid = " + drvstopid.eventString);
        if (drvstopid.eventString.equals(TetGlobalData.out_of_city_key)) {
            sendJabRequesrOutOfCity();
        } else {
            sendJabRequesrOutOfCity();
           // sendJabRequesrWithPOsition();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
       // doingPrepeadToWork();
        Log.e(pseudo_tag, "onResume()");
    }
}
