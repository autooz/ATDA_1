package mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventFromControllerActivityMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventsGPS;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscriber;
import mobi.tet_a_tet.atda.mutual.mut_ulils.toServerSenderAcyncTasker;
import mobi.tet_a_tet.atda.off_lline.DriverTaximetreActivity;
import mobi.tet_a_tet.atda.tet_a_tet.controllers.ActivityControllerService;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverSettings;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.utils.CheckIsPointPoligonsFromDate;

public class TetOnCabStandPriceActivity extends Activity implements View.OnClickListener {
    private static String pseudo_tag;

    private TableLayout tableLayaut;
    private TextView tetTarifInfo;
    private TextView carClassNameTaxi;
    private TextView stopName;
    private TextView zoneStop;
    private TextView orderTaxi;
    private TextView zeroLine;
    private TextView zeroLineClass;
    private TableRow deliveryCarTR;
    private TextView deliveryCarTV;
    private TextView deliveryCarPriceTV;
    private TableRow occupacyTR;
    private TextView occupacyTxt;
    private TextView occupacyPrice;
    private TableRow priceByDistanceTR;
    private TextView priceByDistance;
    private TextView priceByDistanceTV;
    private TableRow distOutOfCityTR;
    private TextView distOutOfCityTXT;
    private TextView distOutOfCityTV;
    private TableRow downtimeTR;
    private TextView priceDowntimeText;
    private TextView priceDowntimeTV;
    private TableRow minPaymentTR;
    private TextView minPaymentTXT;
    private TextView minimalPriceTV;
    private TableRow kmMinPaymentTR;
    private TextView kmInMinimalTXT;
    private TextView kmInMinimalTV;
    private TableRow minsMinPaymentTR;
    private TextView minsInMinimalTXT;
    private TextView minsInMinimalTV;
    private LinearLayout currencyLL;
    private TextView currencyTV;
    private ScrollView scrollView;
    private ListView listView;
    Button buttonState;

    private AudioManager audioManager;
    public SoundPool soundPool;
    public int soundID = -1;
    private boolean isGpsPosListnerOn = true;
    private boolean uptateZone = true;
    private String zonename;
    private String stopId;
    private CheckIsPointPoligonsFromDate wPTD;
    private String REQUEST;
    private String stopZoneId;
    private ServiceConnection sConn;
    private Intent intent;
    private PowerManager.WakeLock wakeLock;
    private ServiceConnection serviceConnection;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        EventBus.getDefault().register(this);
        EventBus.getDefault().registerSticky(this);

        Intent ACS = new Intent(getApplicationContext(), ActivityControllerService.class);
//        bindService(ACS, serviceConnection, this.BIND_AUTO_CREATE);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, pseudo_tag);
        wakeLock.acquire();

        setContentView(R.layout.activity_tet_on_cab_stand_price);


        tableLayaut = (TableLayout) findViewById(R.id.tableLayaut);
        tetTarifInfo = (TextView) findViewById(R.id.tetTarifInfo);
        stopName = (TextView) findViewById(R.id.stopName);
        zoneStop = (TextView) findViewById(R.id.zone_stop);
        carClassNameTaxi =(TextView)findViewById(R.id.carClassNameTaxi);
        orderTaxi = (TextView) findViewById(R.id.orderBetweenTaxi);
        zeroLine = (TextView) findViewById(R.id.zeroLine);
        zeroLineClass = (TextView) findViewById(R.id.zeroLIneClass);
        deliveryCarTR = (TableRow) findViewById(R.id.deliveryCarTR);
        deliveryCarTV = (TextView) findViewById(R.id.deliveryCarTV);
        deliveryCarPriceTV = (TextView) findViewById(R.id.deliveryCarPriceTV);
        occupacyTR = (TableRow) findViewById(R.id.occupacyTR);
        occupacyTxt = (TextView) findViewById(R.id.occupacyTxt);
        occupacyPrice = (TextView) findViewById(R.id.occupacyPrice);
        priceByDistanceTR = (TableRow) findViewById(R.id.priceByDistanceTR);
        priceByDistance = (TextView) findViewById(R.id.priceByDistance);
        priceByDistanceTV = (TextView) findViewById(R.id.priceByDistanceTV);
        distOutOfCityTR = (TableRow) findViewById(R.id.distOutOfCityTR);
        distOutOfCityTXT = (TextView) findViewById(R.id.distOutOfCityTXT);
        distOutOfCityTV = (TextView) findViewById(R.id.distOutOfCityTV);
        downtimeTR = (TableRow) findViewById(R.id.downtimeTR);
        priceDowntimeText = (TextView) findViewById(R.id.priceDowntimeText);
        priceDowntimeTV = (TextView) findViewById(R.id.priceDowntimeTV);
        minPaymentTR = (TableRow) findViewById(R.id.minPaymentTR);
        minPaymentTXT = (TextView) findViewById(R.id.minPaymentTXT);
        minimalPriceTV = (TextView) findViewById(R.id.minimalPriceTV);
        kmMinPaymentTR = (TableRow) findViewById(R.id.kmMinPaymentTR);
        kmInMinimalTXT = (TextView) findViewById(R.id.kmInMinimalTXT);
        kmInMinimalTV = (TextView) findViewById(R.id.kmInMinimalTV);
        minsMinPaymentTR = (TableRow) findViewById(R.id.MinsMinPaymentTR);
        minsInMinimalTXT = (TextView) findViewById(R.id.minsInMinimalTXT);
        minsInMinimalTV = (TextView) findViewById(R.id.minsInMinimalTV);
        currencyLL = (LinearLayout) findViewById(R.id.currencyLL);
        currencyTV = (TextView) findViewById(R.id.currencyTV);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        listView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.buttonStart).setOnClickListener(this);
        findViewById(R.id.buttonOption).setOnClickListener(this);
        findViewById(R.id.buttonAditionService).setOnClickListener(this);
        findViewById(R.id.buttonState).setOnClickListener(this);

        updateViw();
//        //Voice
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            createSoundPoolWithBuilder();
//        } else {
//            createSoundPoolWithConstructor();
//        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tet_on_cab_stand_price, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED");
        super.onDestroy();
        wakeLock.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.acquire();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(pseudo_tag, "onResume()");
        wakeLock.acquire();
    }

    private void updateViw() {

        // update display state
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        tableLayaut = (TableLayout) findViewById(R.id.tableLayaut);
        tetTarifInfo = (TextView) findViewById(R.id.tetTarifInfo);
        if (uptateZone) {
            stopName.setText(TetDriverData.current_zone);
        }
        carClassNameTaxi.setText(TetDriverData.carClassNameTaxi);
        zoneStop = (TextView) findViewById(R.id.zone_stop);
        orderTaxi.setText(TetDriverData.stringOrderBetweenTaxi);
        zeroLine.setText(TetDriverData.allCarOnstop);
        zeroLineClass.setText(TetDriverData.stringOrderBetweenSpecialType);
        deliveryCarTR = (TableRow) findViewById(R.id.deliveryCarTR);
        deliveryCarTV = (TextView) findViewById(R.id.deliveryCarTV);
        deliveryCarPriceTV.setText(TetATetSettingDate.deliveryCarPrice);
        if(TetATetSettingDate.deliveryCarPrice.equals("0")){
            deliveryCarTR.setVisibility(View.INVISIBLE);
            deliveryCarTV.setVisibility(View.INVISIBLE);
            deliveryCarPriceTV.setVisibility(View.INVISIBLE);
        }
        occupacyTR = (TableRow) findViewById(R.id.occupacyTR);
        occupacyTxt = (TextView) findViewById(R.id.occupacyTxt);
        occupacyPrice.setText(TetATetSettingDate.occupacyPrice);
        if(TetATetSettingDate.occupacyPrice.equals("0")){
            occupacyTR.setVisibility(View.INVISIBLE);
            occupacyTxt.setVisibility(View.INVISIBLE);
            occupacyPrice.setVisibility(View.INVISIBLE);
        }
        priceByDistanceTR = (TableRow) findViewById(R.id.priceByDistanceTR);
        priceByDistance = (TextView) findViewById(R.id.priceByDistance);
        priceByDistanceTV.setText(TetATetSettingDate.PriceKm);
        ;
        distOutOfCityTR = (TableRow) findViewById(R.id.distOutOfCityTR);
        distOutOfCityTXT = (TextView) findViewById(R.id.distOutOfCityTXT);
        distOutOfCityTV.setText(TetATetSettingDate.cityout_tariff);
        downtimeTR = (TableRow) findViewById(R.id.downtimeTR);
        priceDowntimeText = (TextView) findViewById(R.id.priceDowntimeText);
        priceDowntimeTV.setText(TetATetSettingDate.PriceMinute);
        if(!TetATetSettingDate.if_minimal_payment.equals("yes")) {
            minPaymentTR.setVisibility(View.INVISIBLE);
            minPaymentTR.setVisibility(View.INVISIBLE);
            minPaymentTXT.setVisibility(View.INVISIBLE);;
            minimalPriceTV.setVisibility(View.INVISIBLE);
            kmMinPaymentTR.setVisibility(View.INVISIBLE);
            kmInMinimalTXT.setVisibility(View.INVISIBLE);
            kmInMinimalTV.setVisibility(View.INVISIBLE);
            minsMinPaymentTR.setVisibility(View.INVISIBLE);
            minsInMinimalTXT.setVisibility(View.INVISIBLE);
            minsInMinimalTV.setVisibility(View.INVISIBLE);
        } else {
            minimalPriceTV.setText(TetATetSettingDate.MinimalPrice);
            kmInMinimalTV.setText(TetATetSettingDate.MinimalKm);
            minsInMinimalTV.setText(TetATetSettingDate.MinimalMinutes);
        }
        currencyLL = (LinearLayout) findViewById(R.id.currencyLL);
        currencyTV.setText(TetATetSettingDate.currency);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        listView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.buttonStart).setOnClickListener(this);
        findViewById(R.id.buttonOption).setOnClickListener(this);
        findViewById(R.id.buttonAditionService).setOnClickListener(this);
        zonename = TetDriverData.current_zone;
        findViewById(R.id.buttonStart).setOnClickListener(this);
        findViewById(R.id.buttonOption).setOnClickListener(this);
        findViewById(R.id.buttonAditionService).setOnClickListener(this);
        stopId = TetDriverData.drvstopid;
        buttonState = (Button) findViewById(R.id.buttonState);

        if(TetDriverData.drvstate.equals("1")){


            buttonState.setText(R.string.drvStateFree);
            buttonState.setBackgroundColor(Color.GREEN);
            buttonState.setTextColor(Color.BLACK);
        }
        else {
            buttonState.setText(R.string.drvStateBusy);
            buttonState.setBackgroundColor(Color.RED);
            buttonState.setTextColor(Color.WHITE);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonState:
                //TODO implement
                String RequestNewState;
                if(TetDriverData.drvstate.equals("1")){

                    RequestNewState = "2";
                } else {
                    RequestNewState = "1";
                }

                REQUEST = "" +
                        TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.REQUEST_CHANGE_DRVSTATE + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                        RequestNewState + TetGlobalData.TOKEN_SEPARATOR +
                        "";
                Intent ProgresBarWaitResponceActivity = new Intent(getApplicationContext(), mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs.ProgresBarWaitResponceActivity.class);
                startActivityForResult(ProgresBarWaitResponceActivity, TetGlobalData.WAIT_JB_RESPONCE);
                //EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
                new toServerSenderAcyncTasker(mContext, REQUEST);
                Log.d(pseudo_tag, "Do go with own passenger from skirting and send OUTCOMING_MESSAGE REQUEST =" + REQUEST + "");
                break;
            case R.id.buttonStart:
                //TODO implement
                if(TetDriverData.current_zone.equals(TetGlobalData.OUT_OF_CITY)){
                    stopZoneId = TetGlobalData.OUT_OF_CITY;
                } else {
                    stopZoneId = TetDriverData.drvstopid;
                }
                /// Seting taximetre mode taxoRUN  or switchON;
                TetDriverData.taxomode = TetDriverData.taxoRUN;
                TetDriverData.choiseZoneByHand = false;

                REQUEST = "" +
                        TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.REQUEST_ORDER_FROM_BORDER + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                        TetDriverData.drvstopid + TetGlobalData.TOKEN_SEPARATOR +
                        "";
                //EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
                new toServerSenderAcyncTasker(mContext, REQUEST);
                Log.d(pseudo_tag, "Do go with own passenger from skirting and send OUTCOMING_MESSAGE REQUEST =" + REQUEST + "");
                Intent i = new Intent();
                if (TetDriverSettings.own_price_in_taximetre_from_border) {
                    i.setClass(this, DriverTaximetreActivity.class);
                }
                else
                {
                    i.setClass(this, TetTaximetreActivity.class);
                }
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

                break;
            case R.id.buttonOption:
                //TODO implement
                Intent Options = new Intent(getApplicationContext(), TetOnCabStandOptions.class);
                startActivity(Options);
                break;
            case R.id.buttonAditionService:
                //TODO implement
                REQUEST = "" +
                        TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.REQUEST_FINISH_WORK + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                        "";
                //EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
                new toServerSenderAcyncTasker(mContext, REQUEST);
                Intent ProgresWaitResponceActivity = new Intent(getApplicationContext(), mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs.ProgresBarWaitResponceActivity.class);
                startActivity(ProgresWaitResponceActivity);

                Log.d(pseudo_tag, "### Du out_of_city Exit from all previos windows startet wia windowsFragment controller ");
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /// Handle action bar item clicks here. The action bar will
        /// automatically handle clicks on the Home/Up button, so long
        /// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /// noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        onResume();
    }

    @Subscriber(tag = "UPDATE_VIEW")
    private void updateview(EventFromControllerActivityMessage msg) {
        Log.e(pseudo_tag, "Recirve message UPDATE_VIEW " + msg + "");
        updateViw();

    }

    @Subscriber(tag = "CLOSEPROG")
    private void updateEventFromControllerActivityMessageWithTag(EventFromControllerActivityMessage msg) {
        Log.e(pseudo_tag, "Recirve message CLOSEPROG in TetOnCabStandPriceActivity" + msg + "");
//
//        try {
//            // Create objects of the 2 required classes
//            AssetManager assetManager = this.getAssets();
//            AssetFileDescriptor descriptor;
//            descriptor = assetManager.openFd(String.valueOf(R.raw.ding));
//            // Load our fx in memory ready for use
//            descriptor = assetManager.openFd("ding.wav");
//            soundID = soundPool.load(descriptor, 0);
//
//        } catch (IOException e) {
//            // Print an error message to the console
//            Log.e("error", "failed to load sound files");
//        }
//
//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setMode(AudioManager.STREAM_MUSIC);
//        if (DriverTuning.TurnSpeakerOn) {
//            audioManager.setSpeakerphoneOn(true);
//        }
//        int actualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//        //int volume = actualVolume / maxVolume;
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
//
//
//        soundPool.play(soundID, 1, 1, 0, 0, 1);

//        final Handler mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                audioManager.setMode(AudioManager.STREAM_MUSIC);
//                if (DriverTuning.TurnSpeakerOn) {
//                    audioManager.setSpeakerphoneOn(true);
//                }
//                int actualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                //int volume = actualVolume / maxVolume;
//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
//                audioManager,getDrawable(R.raw.ding);
//                audioManager.prepare()
//
//                Log.d("incoming_call","speaker_on");
//            }
//        }, 200);
        Intent wHt = new Intent(getApplicationContext(), GoodByActivity.class);
        wHt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        wHt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(wHt);

       // Toast.makeText(this, R.string.jabListnerStarted, Toast.LENGTH_LONG).show();


        finish();

    }

    @Subscriber(tag = "CTRL_ACTIVITY_TO_ONSTOP")
    private void updateViewEventFromControllerActivityMessageWithTag(EventFromControllerActivityMessage msg) {
        Log.e(pseudo_tag, "Recirve message CTRL_ACTIVITY_TO_ONSTOP " + msg + "");

        updateViw();

    }

    @Subscriber(tag = "UPDATE_POSITIOON")
    private void updateStickyViewEventFromControllerActivityMessageWithTag(EventFromControllerActivityMessage falsetrue) {
        Log.e(pseudo_tag, "UPDATE_POSITIOON=" + falsetrue + "");
        isGpsPosListnerOn = falsetrue.AUTO_UPDATE_POZITION;

        if (isGpsPosListnerOn && !TetDriverData.choiseZoneByHand) {
            updateViw();
        }

    }

    @Subscriber(tag = "gpsZone")
    private void updateStickyViewEventGpsZoneeWithTag(EventsGPS msg) {
        Log.e(pseudo_tag, "gpsZone  current_zone=" + TetDriverData.current_zone + " TetDriverData.drvstopid=" + TetDriverData.drvstopid + " Recirve message gpsZone " + msg.ZoneLocationDates.getLatitude() + "");
        wPTD = new CheckIsPointPoligonsFromDate();
        wPTD.CheskIsPOintInPolygon(msg.ZoneLocationDates.getLatitude(), msg.ZoneLocationDates.getLongitude());
    }

    @Subscriber(tag = "CheskIsPOintInPolygon")
    private void updateZoneeWithTag(EventsGPS drvstopid) {
        Log.e(pseudo_tag, "current_zone=" + TetDriverData.current_zone + " memory Stopid=" + stopId + "TetDriverData.drvstopid=" + TetDriverData.drvstopid + " Recirve message drvstopid " + drvstopid + "");
        Log.d(pseudo_tag, "### update user with my_tag, drvstopid = " + drvstopid.eventString);

        if (stopId.equals(drvstopid) && zonename.equals(TetDriverData.current_zone) || !isGpsPosListnerOn) {

        } else {

            if (drvstopid.eventString.equals(TetGlobalData.out_of_city_key)) {
                sendJabRequesrOutOfCity();
            } else {
                sendJabRequesrWithPOsition();
            }
        }
    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    protected void createSoundPoolWithBuilder() {
//
//        AudioAttributes attributes = new AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_GAME)
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .build();
//
//        soundPool = new SoundPool.Builder()
//                .setAudioAttributes(attributes)
//                .setMaxStreams(10)
//                .build();
//
//    }

    @SuppressWarnings("deprecation")
    protected void createSoundPoolWithConstructor() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }

    private void sendJabRequesrOutOfCity() {
        REQUEST = "" +
                TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.REQUEST_NEW_STOP_OR_ZONE + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.OUT_OF_CITY + TetGlobalData.TOKEN_SEPARATOR +
                "in" + TetGlobalData.TOKEN_SEPARATOR +
                "TetZoneListActivity setOnItemClickListener case 0 " + TetGlobalData.DRVPHONE + "";
        //EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
        new toServerSenderAcyncTasker(mContext, REQUEST);
    }


    private void sendJabRequesrWithPOsition() {
        REQUEST = "" +
                TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.REQUEST_NEW_STOP_OR_ZONE + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                TetDriverData.drvstopid + TetGlobalData.TOKEN_SEPARATOR +
                TetDriverData.choiseZoneByHand + TetGlobalData.TOKEN_SEPARATOR +
                TetATetSettingDate.clear_without_GPS + TetGlobalData.TOKEN_SEPARATOR +
                "";
        //EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
        new toServerSenderAcyncTasker(mContext, REQUEST);
        Boolean isGpsPosListnerOn = false;
        EventBus.getDefault().postSticky(new EventsGPS(isGpsPosListnerOn), "UPDATE_POSITIOON");
    }


}
