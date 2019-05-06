package mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSbyTimeListner;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;

public class TetTaximetreActivity extends Activity implements View.OnClickListener {


    private TableRow tableRow1;
    private TextView firmNameLabel;
    private TableRow tableRow2;
    private TextView tariffNameLabel;
    private TableRow tableRow3;
    private TextView callLabel;
    private TextView callPriceLabel;
    private TableRow tableRow4;
    private TextView minimalLabel;
    private TextView minimalPriceLabel;
    private TableRow tableRow5;
    private TextView kmLabel;
    private TextView kmPriceLabel;
    private TableRow tableRow6;
    private TextView minuteLabel;
    private TextView minutePriceLabel;
    private TableRow tableRow7;
    private TableRow tableRow8;
    private TextView totalKmLabelStatic;
    private TextView totalKmLabel;
    private TableRow tableRow9;
    private TextView totalMinutesLabelStatic;
    private TextView totalMinutesLabel;
    private TableRow tableRow10;
    private TableRow tableRow11;
    private TextView totalCostLabel;
    private TextView totalCostLabelStatic;
    private String pseudo_tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tet_taximetre);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        Intent TLS = new Intent(getApplicationContext(), GPSbyTimeListner.class);
        stopService(TLS);

        tableRow1 = (TableRow) findViewById(R.id.tableRow1);
        firmNameLabel = (TextView) findViewById(R.id.firmNameLabel);
        tableRow2 = (TableRow) findViewById(R.id.tableRow2);
        tariffNameLabel = (TextView) findViewById(R.id.tariffNameLabel);
        tableRow3 = (TableRow) findViewById(R.id.tableRow3);
        callLabel = (TextView) findViewById(R.id.callLabel);
        callPriceLabel = (TextView) findViewById(R.id.callPriceLabel);
        tableRow4 = (TableRow) findViewById(R.id.tableRow4);
        minimalLabel = (TextView) findViewById(R.id.minimalLabel);
        minimalPriceLabel = (TextView) findViewById(R.id.minimalPriceLabel);
        tableRow5 = (TableRow) findViewById(R.id.tableRow5);
        kmLabel = (TextView) findViewById(R.id.kmLabel);
        kmPriceLabel = (TextView) findViewById(R.id.kmPriceLabel);
        tableRow6 = (TableRow) findViewById(R.id.tableRow6);
        minuteLabel = (TextView) findViewById(R.id.minuteLabel);
        minutePriceLabel = (TextView) findViewById(R.id.minutePriceLabel);
        tableRow7 = (TableRow) findViewById(R.id.tableRow7);
        tableRow8 = (TableRow) findViewById(R.id.tableRow8);
        totalKmLabelStatic = (TextView) findViewById(R.id.totalKmLabelStatic);
        totalKmLabel = (TextView) findViewById(R.id.totalKmLabel);
        tableRow9 = (TableRow) findViewById(R.id.tableRow9);
        totalMinutesLabelStatic = (TextView) findViewById(R.id.totalMinutesLabelStatic);
        totalMinutesLabel = (TextView) findViewById(R.id.totalMinutesLabel);
        tableRow10 = (TableRow) findViewById(R.id.tableRow10);
        tableRow11 = (TableRow) findViewById(R.id.tableRow11);
        totalCostLabel.setText(TetATetSettingDate.currency);
        totalCostLabelStatic = (TextView) findViewById(R.id.totalCostLabelStatic);
        findViewById(R.id.taxometerMenuButton).setOnClickListener(this);
        findViewById(R.id.showOrdersButton).setOnClickListener(this);

        Intent TS = new Intent(getApplicationContext(), GPSbyTimeListner.class);
        startService(TS);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.taxometerMenuButton:
                //TODO implement
                break;
            case R.id.showOrdersButton:
                //TODO implement
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tet_taximetre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /// Handle action bar item clicks here. The action bar will
        /// automatically handle clicks on the Home/Up button, so long
        /// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ///noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
