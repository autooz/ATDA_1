package mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventsGPS;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscriber;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGpsData;

public class GPSAccuracyNotGoodActivity extends Activity implements View.OnClickListener {

    Timer timer = new Timer();
    TimerTask tTask;
    long interval = 1000;// * Integer.parseInt(TetATetSettingDate.GPS_UPDATE_SECONDS); // in seconds;
    Float accuracy;

    private TableRow tableRowTitle;
    private TextView notgoodassuracyTitle;
    private TableRow tableRowMsg;
    private TextView notgoodassuracyMesageTV;
    private TextView textViewMinimalAccurasy;
    private TextView textView4;
    private TableRow tableRowMinAccurscy;
    private TextView notgoodassuracyMinAccuracyTV;
    private TextView textViewCurrentAccuracy;
    private TextView textView3;
    private TableRow tableRowProgrBar;
    private ProgressBar progressBar;
    private TableRow tableRowInstrution;
    private TextView notgoodassuracyInstrTv;
    private TableRow tableRowButtonWithoutGPS;
    private TableRow tableRowExit;

    private Float minAccuracy= Float.parseFloat(TetATetSettingDate.minGPSaccuracy);
    private Location location;
    private String pseudo_tag;

//    @Override
//    protected void onPostCreate(Bundle savedInstanceStat){
//
//        schedule();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsaccuracy_not_good);

        EventBus.getDefault().register(this);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        TetGlobalData.CARRENT_CLASS = pseudo_tag;

        tableRowTitle = (TableRow) findViewById(R.id.TableRowTitle);
        notgoodassuracyTitle = (TextView) findViewById(R.id.notgoodassuracyTitle);
        tableRowMsg = (TableRow) findViewById(R.id.TableRowMsg);
        notgoodassuracyMesageTV = (TextView) findViewById(R.id.notgoodassuracyMesageTV);
        textViewMinimalAccurasy = (TextView) findViewById(R.id.textViewMinimalAccurasy);
        textViewMinimalAccurasy.setText(TetATetSettingDate.minGPSaccuracy);
        Log.d(" GPSAccuracyNotGood", "HAs MinimalAccurasy = " + Integer.parseInt(TetATetSettingDate.minGPSaccuracy) + "");
        textView4 = (TextView) findViewById(R.id.textView4);
        tableRowMinAccurscy = (TableRow) findViewById(R.id.TableRowMinAccurscy);
        notgoodassuracyMinAccuracyTV = (TextView) findViewById(R.id.notgoodassuracyMinAccuracyTV);
        textViewCurrentAccuracy = (TextView) findViewById(R.id.textViewCurrentAccuracy);
        textView3 = (TextView) findViewById(R.id.textView3);
        tableRowProgrBar = (TableRow) findViewById(R.id.TableRowProgrBar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tableRowInstrution = (TableRow) findViewById(R.id.TableRowInstrution);
        notgoodassuracyInstrTv = (TextView) findViewById(R.id.notgoodassuracyInstrTv);
        tableRowButtonWithoutGPS = (TableRow) findViewById(R.id.TableRowButtonWithoutGPS);
        findViewById(R.id.buttonWithOutGps).setOnClickListener(this);
        tableRowExit = (TableRow) findViewById(R.id.TableRowExit);
        findViewById(R.id.exitButton).setOnClickListener(this);
        TetDriverData.without_GPS = false;

        if(TetATetSettingDate.clear_without_GPS.equals("false")){
            tableRowButtonWithoutGPS.setVisibility(View.INVISIBLE);
        }

    }




    @Subscriber(tag = "GPSbyTimeListner")
    private void updateEventsGPSWithTag(EventsGPS loc) {

        location = loc.ZoneLocationDates;
        accuracy =location.getAccuracy();
        Log.e("GPSAccuracyNotGood", "### update locatiom with accuracy, value = " + accuracy);

        textViewCurrentAccuracy.setText(String.valueOf(accuracy));

        if (accuracy >= minAccuracy) {

        }else{
            TetGpsData.accuracy_current = accuracy;
            EventBus.getDefault().unregister(this);
            setResult(RESULT_OK, gozero());
            this.finish();
        }

    }

    private Intent gozero() {
        Intent GPSAccuracyNotGoodActivity_Intent = new Intent();
        GPSAccuracyNotGoodActivity_Intent.putExtra("without_GPS", false);
        return GPSAccuracyNotGoodActivity_Intent;
    }


    private Intent gogoclick(){
        Intent GPSAccuracyNotGoodActivity_Intent = new Intent();
        GPSAccuracyNotGoodActivity_Intent.putExtra("without_GPS", TetDriverData.without_GPS);
        return GPSAccuracyNotGoodActivity_Intent;
    }

    @Override
    public void onClick(View view) {
      //  EventBus.getDefault().unregister(this);

        switch (view.getId()) {
            case R.id.buttonWithOutGps:
                //TODO implement
                TetDriverData.without_GPS = true;
                setResult(RESULT_OK, gogoclick());
               this.finish();
                break;
            case R.id.exitButton:
                //TODO implement
                setResult(RESULT_CANCELED, gogoclick());
                this.finish();
                break;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpsaccuracy_not_good, menu);
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
