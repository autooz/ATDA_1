package mobi.tet_a_tet.atda.off_lline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.StringTokenizer;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventFromControllerActivityMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventJabIncomMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventsGPS;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscriber;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSbyTimeListner;
import mobi.tet_a_tet.atda.mutual.mut_ulils.toServerSenderAcyncTasker;
import mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs.ProgresBarWaitResponceActivity;
import mobi.tet_a_tet.atda.tet_a_tet.controllers.ActivityControllerService;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.utils.CheckIsPointPoligonsFromDate;

public class DriverTaximetreActivity extends Activity implements View.OnClickListener {


    private TableRow tableRow1;
    private TextView firmNameLabel;
    private TableRow tableRow2;
    private TextView tariffNameLabel;
    private TableRow tableRow3;
    private TextView podacha;
    private TextView podachalPriceLabel;
    private TableRow tableRow4;
    private TextView minimalLabel;
    private TextView minimalPriceLabel;
    private TableRow tableRow5;
    private TextView kmLabel;
    private TextView priceKM;
    private TextView multiplyKM;
    private TextView kmAmmount;
    private TextView textView8;
    private TextView totalPriceKMCity;
    private TableRow tableRow6;
    private TextView minuteLabel;
    private TextView outCityPriceLabel;
    private TextView multiplyKMoutCity;
    private TextView kmAmmountOutCity;
    private TextView textView9;
    private TextView totalPriceOutCity;
    private TableRow tableRow12;
    private TextView textView5;
    private TextView priceProstoy;
    private TextView textView7;
    private TextView prostoyAmmount;
    private TextView textView10;
    private TextView totalPriceProstoy;
    private TableRow tableRow7;
    private TableRow tableRow8;
    private TextView totalKmLabelStatic;
    private TextView totalKmLabel;
    private TableRow tableRow9;
    private TextView totalMinutesLabelStatic;
    private TextView totalMinutesLabel;
    private TableRow tableRow10;
    private TextView totalCostLabel;
    private TextView currency;
    private TextView textView12;
    private LinearLayout ifTaximetreStoped;
    private LinearLayout ifTaxometreStarted;
    private String pseudo_tag;

    SharedPreferences taxiCouter;
    SharedPreferences.Editor editor;
    private double payForOutOfCity;
    private double payForInCity;
    double payForkmAmmount;
    double cmOutCity;
    double cmInCity;
    float byCity;
    private CheckIsPointPoligonsFromDate wPTD;
    private double by10metreOutCityTotal;
    private int by10metreInCityTotal;
    private float kmByCity;
    private float kmtotalKm;
    private float metersoutCity;
    private int intOutCity;
    private int intPriceInCity;
    private int intPriseOutCity;
    private String showPaymentOutOfCity;
    private String distceOutOfCityInMetre;
    private float showDistanseInCity;
    private String showPaymenInCity;
    private String distceInCityInMetre;
    private int totaBy10metersInCity;
    private int totaBy10metersOutCity;
    private double doublePriseOutCitu;
    private double doublePriseInCitu;
    private double initialPayment;
    private double deliveryCarPrice;
    private int intInitialPayment;
    private int intDeliveryCarPrice;
    private int IntpayForInCity;
    private int IntpayForOutOfCity;
    private double showTotalKmDouble;
    private double showTotalPriceDouble;
    private String showTotalKm;
    private String showTotalPrice;
    private int paymentInCity;
    private int paymentOutOfCity;
    private String REQUEST;
    private PowerManager.WakeLock wakeLock;
    private PowerManager pm;
    private int totalPrice;
    private int consumedMinPrice;
    private ServiceConnection serviceConnection;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent TLS = new Intent(getApplicationContext(), GPSbyTimeListner.class);
        stopService(TLS);
        EventBus.getDefault().register(this);

        Intent ACS = new Intent(getApplicationContext(), ActivityControllerService.class);
        startService(ACS);
//        bindService(ACS, serviceConnection, this.BIND_AUTO_CREATE);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        taxiCouter = PreferenceManager.getDefaultSharedPreferences(this);

        Intent TLdS = new Intent(this, GPSbyTimeListner.class);
        startService(TLdS);

        doublePriseOutCitu = Double.parseDouble(TetATetSettingDate.cityout_tariff);
        doublePriseInCitu = Double.parseDouble(TetATetSettingDate.PriceKm);
        initialPayment = Double.parseDouble(TetATetSettingDate.occupacyPrice) * 100;
        deliveryCarPrice = Double.parseDouble(TetATetSettingDate.deliveryCarPrice) * 100;
        intInitialPayment = (int) initialPayment;
        intDeliveryCarPrice = (int) deliveryCarPrice;

        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, pseudo_tag);
        wakeLock.acquire();

        editor = taxiCouter.edit();
        TetDriverData.lastShownDistceOutOfCity = taxiCouter.getInt("intOutCity", 0) / 100;
        TetDriverData.lastShownPaymenteOutOfCity = taxiCouter.getInt("payForOutOfCity", 0) / 100;
        TetDriverData.lastShownDistceInCity = taxiCouter.getInt("intInCity", 0) / 100;
        TetDriverData.lastShownPaymentInCity = taxiCouter.getInt("payForCity", 0) / 100;

        Log.e(pseudo_tag, "In  onCreate TetDriverData.lastShownPaymenteOutOfCity=" + TetDriverData.lastShownPaymenteOutOfCity + " TetDriverData.lastShownDistceOutOfCity=" + TetDriverData.lastShownDistceOutOfCity + "");
        Log.e(pseudo_tag, "In  onCreate TetDriverData.lastShownDistceInCity=" + TetDriverData.lastShownDistceInCity + "  TetDriverData.lastShownPaymentInCity==" + TetDriverData.lastShownPaymentInCity + "");

        setContentView(R.layout.activity_driver_taximetre_activity);

        tableRow1 = (TableRow) findViewById(R.id.tableRow1);
        firmNameLabel = (TextView) findViewById(R.id.firmNameLabel);
        tableRow2 = (TableRow) findViewById(R.id.tableRow2);
        tariffNameLabel = (TextView) findViewById(R.id.tariffNameLabel);
        tableRow3 = (TableRow) findViewById(R.id.tableRow3);
        podacha = (TextView) findViewById(R.id.podacha);
        podachalPriceLabel = (TextView) findViewById(R.id.podachalPriceLabel);
        tableRow4 = (TableRow) findViewById(R.id.tableRow4);
        minimalLabel = (TextView) findViewById(R.id.minimalLabel);
        minimalPriceLabel = (TextView) findViewById(R.id.minimalPriceLabel);
        tableRow5 = (TableRow) findViewById(R.id.tableRow5);
        kmLabel = (TextView) findViewById(R.id.kmLabel);
        priceKM = (TextView) findViewById(R.id.priceKM);
        multiplyKM = (TextView) findViewById(R.id.multiplyKM);
        kmAmmount = (TextView) findViewById(R.id.kmAmmount);
        textView8 = (TextView) findViewById(R.id.textView8);
        totalPriceKMCity = (TextView) findViewById(R.id.totalPriceKMCity);
        tableRow6 = (TableRow) findViewById(R.id.tableRow6);
        minuteLabel = (TextView) findViewById(R.id.minuteLabel);
        outCityPriceLabel = (TextView) findViewById(R.id.outCityPriceLabel);
        multiplyKMoutCity = (TextView) findViewById(R.id.multiplyKMoutCity);
        kmAmmountOutCity = (TextView) findViewById(R.id.kmAmmountOutCity);
        textView9 = (TextView) findViewById(R.id.textView9);
        totalPriceOutCity = (TextView) findViewById(R.id.totalPriceOutCity);
        tableRow12 = (TableRow) findViewById(R.id.tableRow12);
        textView5 = (TextView) findViewById(R.id.textView5);
        priceProstoy = (TextView) findViewById(R.id.priceProstoy);
        textView7 = (TextView) findViewById(R.id.textView7);
        prostoyAmmount = (TextView) findViewById(R.id.prostoyAmmount);
        textView10 = (TextView) findViewById(R.id.textView10);
        totalPriceProstoy = (TextView) findViewById(R.id.totalPriceProstoy);
        tableRow7 = (TableRow) findViewById(R.id.tableRow7);
        tableRow8 = (TableRow) findViewById(R.id.tableRow8);
        totalKmLabelStatic = (TextView) findViewById(R.id.totalKmLabelStatic);
        totalKmLabel = (TextView) findViewById(R.id.totalKmLabel);
        tableRow9 = (TableRow) findViewById(R.id.tableRow9);
        totalMinutesLabelStatic = (TextView) findViewById(R.id.totalMinutesLabelStatic);
        totalMinutesLabel = (TextView) findViewById(R.id.totalMinutesLabel);
        tableRow10 = (TableRow) findViewById(R.id.tableRow10);
        totalCostLabel = (TextView) findViewById(R.id.totalCostLabel);
        currency = (TextView) findViewById(R.id.currency);
        textView12 = (TextView) findViewById(R.id.textView12);
        ifTaximetreStoped = (LinearLayout) findViewById(R.id.ifTaximetreStoped);
        findViewById(R.id.taxometerStartButton).setOnClickListener(this);
        findViewById(R.id.goOrdersButton).setOnClickListener(this);
        ifTaxometreStarted = (LinearLayout) findViewById(R.id.ifTaxometreStarted);
        findViewById(R.id.stopTaximButton).setOnClickListener(this);
        Intent TS = new Intent(getApplicationContext(), GPSbyTimeListner.class);
        startService(TS);
        updateViev();

    }

    private void updateViev() {
        // update display state
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        distceOutOfCityInMetre = Double.toString(TetDriverData.lastShownDistceOutOfCity);
        showPaymentOutOfCity = Double.toString(TetDriverData.lastShownPaymenteOutOfCity);
        distceInCityInMetre = Double.toString(TetDriverData.lastShownDistceInCity);
        showPaymenInCity = Double.toString(TetDriverData.lastShownPaymentInCity);

        Log.e(pseudo_tag, "In updateViev() distceOutOfCityInMetre=" + distceOutOfCityInMetre + "  distceInCityInMetre=" + distceInCityInMetre + "\n" +
                " distceInCityInMetre=" + distceInCityInMetre + "");

        tableRow1 = (TableRow) findViewById(R.id.tableRow1);
        firmNameLabel.setText(TetATetSettingDate.dsCity);
        tableRow2 = (TableRow) findViewById(R.id.tableRow2);
        tariffNameLabel = (TextView) findViewById(R.id.tariffNameLabel);
        tableRow3 = (TableRow) findViewById(R.id.tableRow3);
        podacha = (TextView) findViewById(R.id.podacha);
        podachalPriceLabel.setText(TetATetSettingDate.deliveryCarPrice);
        tableRow4 = (TableRow) findViewById(R.id.tableRow4);
        minimalLabel = (TextView) findViewById(R.id.minimalLabel);
        minimalPriceLabel.setText(TetATetSettingDate.occupacyPrice);
        tableRow5 = (TableRow) findViewById(R.id.tableRow5);
        kmLabel = (TextView) findViewById(R.id.kmLabel);
        priceKM.setText(TetATetSettingDate.PriceKm);
        multiplyKM = (TextView) findViewById(R.id.multiplyKM);
        kmAmmount.setText(distceInCityInMetre);
        textView8 = (TextView) findViewById(R.id.textView8);
        totalPriceKMCity.setText(showPaymenInCity);
        tableRow6 = (TableRow) findViewById(R.id.tableRow6);
        minuteLabel = (TextView) findViewById(R.id.minuteLabel);
        outCityPriceLabel.setText(TetATetSettingDate.cityout_tariff);
        multiplyKMoutCity = (TextView) findViewById(R.id.multiplyKMoutCity);
        kmAmmountOutCity.setText(distceOutOfCityInMetre);
        textView9 = (TextView) findViewById(R.id.textView9);
        totalPriceOutCity.setText(showPaymentOutOfCity);
        tableRow12 = (TableRow) findViewById(R.id.tableRow12);
        textView5 = (TextView) findViewById(R.id.textView5);
        priceProstoy.setText(TetATetSettingDate.PriceMinute);
        textView7 = (TextView) findViewById(R.id.textView7);
        prostoyAmmount = (TextView) findViewById(R.id.prostoyAmmount);
        textView10 = (TextView) findViewById(R.id.textView10);
        totalPriceProstoy = (TextView) findViewById(R.id.totalPriceProstoy);
        tableRow7 = (TableRow) findViewById(R.id.tableRow7);
        tableRow8 = (TableRow) findViewById(R.id.tableRow8);
        totalKmLabelStatic = (TextView) findViewById(R.id.totalKmLabelStatic);
        totalKmLabel.setText(showTotalKm);
        tableRow9 = (TableRow) findViewById(R.id.tableRow9);
        totalMinutesLabelStatic = (TextView) findViewById(R.id.totalMinutesLabelStatic);
        totalMinutesLabel = (TextView) findViewById(R.id.totalMinutesLabel);
        tableRow10 = (TableRow) findViewById(R.id.tableRow10);
        totalCostLabel.setText(showTotalPrice);
        currency.setText(TetDriverData.currency);
        textView12.setText(TetDriverData.currency);
        ifTaximetreStoped = (LinearLayout) findViewById(R.id.ifTaximetreStoped);
        findViewById(R.id.taxometerStartButton).setOnClickListener(this);
        findViewById(R.id.goOrdersButton).setOnClickListener(this);
        ifTaxometreStarted = (LinearLayout) findViewById(R.id.ifTaxometreStarted);
        findViewById(R.id.stopTaximButton).setOnClickListener(this);

        if (TetDriverData.taxomode == TetDriverData.taxoRUN) {
            ifTaximetreStoped.setVisibility(View.INVISIBLE);
            ifTaxometreStarted.setVisibility(View.VISIBLE);
        } else {
            ifTaximetreStoped.setVisibility(View.VISIBLE);
            ifTaxometreStarted.setVisibility(View.INVISIBLE);
        }
    }

    @Subscriber(tag = "gpsZone")
    private void updateStickyViewEventGpsZoneeWithTag(EventsGPS msg) {
        Log.e(pseudo_tag, "gpsZone  current_zone=" + TetDriverData.current_zone + " TetDriverData.drvstopid=" + TetDriverData.drvstopid + " Recirve message gpsZone " + msg.ZoneLocationDates.getLatitude() + "");
        wPTD = new CheckIsPointPoligonsFromDate();
        wPTD.CheskIsPOintInPolygon(msg.ZoneLocationDates.getLatitude(), msg.ZoneLocationDates.getLongitude());
        updateViev();
    }

    @Subscriber(tag = "TO_TAXIMETRE_UPDATE")
    private void updateview(EventsGPS msg) {
        Log.e(pseudo_tag, "Recirve message TO_TAXIMETRE_UPDATE");

        Log.e(pseudo_tag, "TetDriverData.current_zone =" + TetDriverData.current_zone + " TetGlobalData.OUT_OF_CITY=" + TetGlobalData.OUT_OF_CITY + "");

        if (TetDriverData.current_zone == null || TetDriverData.current_zone.equals(TetGlobalData.OUT_OF_CITY)) {
            Log.d(pseudo_tag, "Counting cmOutCity");
            cmOutCity = TetDriverData.adding_distance * 100; //catch 12.082 convert to 1208.2 centimetre distance from TaxiCounterDistanAndTime looks like 12.087 and convert it to santimetr
            TetDriverData.cmAmmountOutCity = TetDriverData.cmAmmountOutCity + (int) cmOutCity;//safe total cantimetre (1208) in TetDriverData.cmAmmountOutCity
            by10metreOutCityTotal = TetDriverData.cmAmmountOutCity / 1000; // total by 10 meters s float float looks like
            totaBy10metersOutCity = (int) by10metreOutCityTotal;
            TetDriverData.lastShownDistceOutOfCity = (double) totaBy10metersOutCity / 100;
            intPriseOutCity = (int) doublePriseOutCitu * 100; // if price 4.75 be looks like 475
            payForOutOfCity = TetDriverData.lastShownDistceOutOfCity * intPriseOutCity;// if price 4.75 be looks like 475000
            IntpayForOutOfCity = (int) payForOutOfCity * 100;
            double doubIntpayForOutOfCity = IntpayForOutOfCity / 100;
            paymentOutOfCity = (int) doubIntpayForOutOfCity;
            TetDriverData.lastShownPaymenteOutOfCity = (double) paymentOutOfCity / 100;
        } else {
            Log.d(pseudo_tag, "Counting cmOutCity");
            cmInCity = TetDriverData.adding_distance * 100; //catch 12.082 convert to 1208.2 centimetre distance from TaxiCounterDistanAndTime looks like 12.087 and convert it to santimetr
            TetDriverData.cmAmmountInCity = TetDriverData.cmAmmountInCity + (int) cmInCity;//safe total cantimetre (1208) in TetDriverData.cmAmmountOutCity
            by10metreInCityTotal = TetDriverData.cmAmmountInCity / 1000; // total by 10 meters s float float looks like
            totaBy10metersInCity = (int) by10metreInCityTotal;
            TetDriverData.lastShownDistceInCity = (double) totaBy10metersInCity / 100;
            intPriceInCity = (int) doublePriseInCitu * 100; // if price 4.75 be looks like 475
            payForInCity = TetDriverData.lastShownDistceInCity * intPriceInCity;// if price 4.75 be looks like 475000
            IntpayForInCity = (int) payForInCity * 100;
            double doubIntpayForInCity = IntpayForInCity / 100;
            paymentInCity = (int) doubIntpayForInCity;
            TetDriverData.lastShownPaymentInCity = (double) paymentInCity / 100;
        }
        //            // Add to SharedPreferences
        editor.putInt("payForOutOfCity", paymentOutOfCity);
        editor.putInt("intOutCity", totaBy10metersOutCity);
        editor.putInt("payForCity", paymentInCity);
        editor.putInt("intInCity", totaBy10metersInCity);
        //  TetDriverData.totalKm = cmOutCity + byCity;

        kmtotalKm = totaBy10metersInCity + totaBy10metersOutCity;

        showTotalKmDouble = (double) kmtotalKm / 100;
        showTotalKm = Double.toString(showTotalKmDouble);

        //counting mnimal payment
        if (TetATetSettingDate.if_minimal_payment.equals("yes")) {
            double minPrice = Double.parseDouble(TetATetSettingDate.MinimalPrice);

            int intminPriceCent = (int) minPrice * 100;
            if (consumedMinPrice <= intminPriceCent) {
                if (TetDriverData.current_zone == null || TetDriverData.current_zone.equals(TetGlobalData.OUT_OF_CITY)) {  // out of city
                    consumedMinPrice = intDeliveryCarPrice + intInitialPayment + paymentInCity + paymentOutOfCity;
                    totalPrice = intminPriceCent;
                } else {

                    consumedMinPrice = intDeliveryCarPrice + intInitialPayment + paymentInCity + paymentOutOfCity;
                    totalPrice = intminPriceCent;
                }

            } else {
                totalPrice = intDeliveryCarPrice + intInitialPayment + paymentInCity + paymentOutOfCity;
            }
        } else {
            totalPrice = intDeliveryCarPrice + intInitialPayment + paymentInCity + paymentOutOfCity;
        }
        showTotalPriceDouble = (double) totalPrice / 100;
        showTotalPrice = Double.toString(showTotalPriceDouble);


        Log.e(pseudo_tag, "intDeliveryCarPrice=" + intDeliveryCarPrice + " intInitialPayment=" + intInitialPayment + " paymentInCity=" + paymentInCity + " paymentOutOfCity =" + paymentOutOfCity + " totalPrice=" + totalPrice + "");

//        editor.remove(TetGlobalData.ADSS_KEY_BOOL);
//        editor.putBoolean()
//        editor.putString(var, value);
        editor.commit();
        TetDriverData.adding_distance = 0;
        updateViev();

    }

    private static float round(float number, int scale) {
        int pow = (int) Math.pow(2, scale);
        float tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.taxometerStartButton:
                //TODO implement
                //set taxometre mode taxoRUN or switchOFF
                TetDriverData.taxomode = TetDriverData.taxoRUN;
                ifTaximetreStoped.setVisibility(View.INVISIBLE);
                ifTaxometreStarted.setVisibility(View.VISIBLE);
                break;
            case R.id.goOrdersButton:
                //TODO implement
                Log.e(pseudo_tag, "CARR FREE");
                editor.putInt("payForOutOfCity", 0);
                editor.putInt("intOutCity", 0);
                editor.putInt("payForCity", 0);
                editor.putInt("intInCity", 0);
                editor.commit();
                TetDriverData.lastShownDistceOutOfCity = 0;// taxiCouter.getInt("intOutCity", 0)/100;
                TetDriverData.lastShownPaymenteOutOfCity = 0; //taxiCouter.getInt("payForOutOfCity", 0)/100;
                TetDriverData.lastShownDistceInCity = 0;// taxiCouter.getInt("intInCity", 0)/100;
                TetDriverData.lastShownPaymentInCity = 0; //taxiCouter.getInt("payForCity", 0)/100;
                TetDriverData.adding_distance = 0;
                REQUEST = "" +
                        TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.REQUEST_CLOSE_ORDER + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                        TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                        TetDriverData.drvstopid + TetGlobalData.TOKEN_SEPARATOR +
                        TetDriverData.choiseZoneByHand + TetGlobalData.TOKEN_SEPARATOR +
                        TetATetSettingDate.clear_without_GPS + TetGlobalData.TOKEN_SEPARATOR +
                        "";

                Intent ProgresBarWaitResponceActivity = new Intent(getApplicationContext(), ProgresBarWaitResponceActivity.class);
                startActivity(ProgresBarWaitResponceActivity);
                //EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
                new toServerSenderAcyncTasker(mContext, REQUEST);
                finish();
                break;
            case R.id.stopTaximButton:
                //TODO implement
                //set taxometre mode taxoSTOPPED or switchOFF
                Intent TLS = new Intent(getApplicationContext(), GPSbyTimeListner.class);
                stopService(TLS);
                TetDriverData.taxomode = TetDriverData.taxoSTOPPED;

                ifTaxometreStarted.setVisibility(View.INVISIBLE);
                ifTaximetreStoped.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        onResume();
        //        Intent mA = new Intent(this, TetFirstRegistrationMainActivity.class);
//        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(mA);
//        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.acquire();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(pseudo_tag, "onResume()");
        TetDriverData.lastShownDistceOutOfCity = taxiCouter.getInt("intOutCity", 0) / 100;
        TetDriverData.lastShownPaymenteOutOfCity = taxiCouter.getInt("payForOutOfCity", 0) / 100;
        TetDriverData.lastShownDistceInCity = taxiCouter.getInt("intInCity", 0) / 100;
        TetDriverData.lastShownPaymentInCity = taxiCouter.getInt("payForCity", 0) / 100;
        updateViev();
        wakeLock.acquire();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tet_taximetre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED");
        super.onDestroy();
        wakeLock.release();
    }

    @Subscriber(tag = "INCOMING_MESSAGE")
    private void updateEventsJabberdMessagesWithTag(EventJabIncomMessage msg) {
        Log.e(pseudo_tag, "### EventsJabberdIncomingMessageWithTag Message to be send = " + msg.INCOMMING_MESSAGE);
        StringTokenizer st = new StringTokenizer(msg.INCOMMING_MESSAGE, TetGlobalData.TOKEN_SEPARATOR);
        String token = st.nextToken();
        Log.e(pseudo_tag, "### update user with my_tag, name = " + msg.INCOMMING_MESSAGE);
        if (token.equals(TetGlobalData.RESPONCE)) {
            String tokenres = st.nextToken();
            if (tokenres.equals(TetGlobalData.OW_ONSTOP) || tokenres.equals(TetGlobalData.OW_STOPLIST)) {
                updateViev();
            }
        }
    }

    @Subscriber(tag ="CLW_TAXIMETR")
    private void closeTaximetrByActivityControllerService(EventFromControllerActivityMessage msg){
        finish();

    }

}