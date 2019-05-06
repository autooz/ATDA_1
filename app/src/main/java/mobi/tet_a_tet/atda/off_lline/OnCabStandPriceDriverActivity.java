package mobi.tet_a_tet.atda.off_lline;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;

public class OnCabStandPriceDriverActivity extends Activity implements View.OnClickListener {
    private static String pseudo_tag;


    private TableLayout tableLayaut;
    private TextView dsCityOrName;
    private TextView tetTarifInfo;
    private TextView stopName;
    private TextView zoneStop;
    private TextView orderBetweenTaxi;
    private TextView zeroLine;
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
    private LinearLayout currencyLL;
    private TextView currencyTV;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;

        setContentView(R.layout.activity_on_cab_stand_price_driver);

        tableLayaut = (TableLayout) findViewById(R.id.tableLayaut);
        dsCityOrName = (TextView) findViewById(R.id.dsCityOrName);
        tetTarifInfo = (TextView) findViewById(R.id.tetTarifInfo);
        stopName = (TextView) findViewById(R.id.stopName);
        zoneStop = (TextView) findViewById(R.id.zone_stop);
        orderBetweenTaxi = (TextView) findViewById(R.id.orderBetweenTaxi);
        zeroLine = (TextView) findViewById(R.id.zeroLine);
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
        currencyLL = (LinearLayout) findViewById(R.id.currencyLL);
        currencyTV = (TextView) findViewById(R.id.currencyTV);
        listView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.buttonStart).setOnClickListener(this);
        findViewById(R.id.buttonOption).setOnClickListener(this);
        findViewById(R.id.buttonAditionService).setOnClickListener(this);
        findViewById(R.id.buttonState).setOnClickListener(this);

        updateViw();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(pseudo_tag, "onResume()");
        updateViw();
    }

    private void updateViw() {
        tableLayaut = (TableLayout) findViewById(R.id.tableLayaut);
        dsCityOrName.setText(TetATetSettingDate.dsCity);
        tetTarifInfo = (TextView) findViewById(R.id.tetTarifInfo);
        stopName.setText(TetDriverData.current_zone);
        zoneStop = (TextView) findViewById(R.id.zone_stop);
        orderBetweenTaxi.setText(TetDriverData.stringOrderBetweenTaxi);
        zeroLine = (TextView) findViewById(R.id.zeroLine);
        deliveryCarTR = (TableRow) findViewById(R.id.deliveryCarTR);
        deliveryCarTV = (TextView) findViewById(R.id.deliveryCarTV);
        deliveryCarPriceTV.setText(TetATetSettingDate.deliveryCarPrice);
        occupacyTR = (TableRow) findViewById(R.id.occupacyTR);
        occupacyTxt = (TextView) findViewById(R.id.occupacyTxt);
        occupacyPrice.setText(TetATetSettingDate.occupacyPrice);
        priceByDistanceTR = (TableRow) findViewById(R.id.priceByDistanceTR);
        priceByDistance = (TextView) findViewById(R.id.priceByDistance);
        priceByDistanceTV.setText(TetATetSettingDate.PriceKm);;
        distOutOfCityTR = (TableRow) findViewById(R.id.distOutOfCityTR);
        distOutOfCityTXT = (TextView) findViewById(R.id.distOutOfCityTXT);
        distOutOfCityTV.setText(TetATetSettingDate.cityout_tariff);
        downtimeTR = (TableRow) findViewById(R.id.downtimeTR);
        priceDowntimeText = (TextView) findViewById(R.id.priceDowntimeText);
        priceDowntimeTV.setText(TetATetSettingDate.PriceMinute);
        currencyLL = (LinearLayout) findViewById(R.id.currencyLL);
        currencyTV.setText(TetATetSettingDate.currency);
        listView = (ListView) findViewById(R.id.listView);
        findViewById(R.id.buttonStart).setOnClickListener(this);
        findViewById(R.id.buttonOption).setOnClickListener(this);
        findViewById(R.id.buttonAditionService).setOnClickListener(this);
        findViewById(R.id.buttonState).setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonState:
                //TODO implement
                break;
            case R.id.buttonStart:
                //TODO implement
                break;
            case R.id.buttonOption:
                //TODO implement
                break;
            case R.id.buttonAditionService:
                //TODO implement
                break;
        }
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

    @Override
    public void onBackPressed() {
        onResume();
//        Intent mA = new Intent(this, TetFirstRegistrationMainActivity.class);
//        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(mA);
//        finish();

    }


}
