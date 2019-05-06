package mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventFromControllerActivityMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventsGPS;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscriber;
import mobi.tet_a_tet.atda.mutual.mut_ulils.toServerSenderAcyncTasker;
import mobi.tet_a_tet.atda.tet_a_tet.controllers.ActivityControllerService;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.utils.GetAnyStringFromTetSettingDate;

public class TetZoneListActivity extends Activity {

    private String pseudo_tag;
    private ServiceConnection serviceConnection;
    private Context mContext = this;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);

        Intent ACS = new Intent(getApplicationContext(), ActivityControllerService.class);
//        bindService(ACS, serviceConnection, this.BIND_AUTO_CREATE);
        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        Log.e(pseudo_tag, "Started");
        setContentView(R.layout.activity_tet_zone_list);
    }

    @Subscriber(tag = "CTRL_ACTIVITY_TO_ZL")
    private void updateEventsJabberdMessagesWithTag(EventFromControllerActivityMessage msg) {
        android.util.Log.e(pseudo_tag, "Recieve Message CTRL_ACTIVITY_TO_ZL");
        StringTokenizer st = msg.FROM_CONTROLLER_ACTIVITY_MESSAGE;

        TetZoneListActivityParcer parcer = new TetZoneListActivityParcer();
        ArrayList<String> stops = parcer.makeZoneArray(st, this);

        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, stops);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);


        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                // int pos = (Integer) arg1.getTag();
                android.util.Log.e(pseudo_tag, "setOnItemClickListener position=" + position + "");
                String REQUEST;
                switch (position) {

                    case 0:
                        REQUEST = "" +
                                TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.REQUEST_NEW_STOP_OR_ZONE + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.OUT_OF_CITY + TetGlobalData.TOKEN_SEPARATOR +
                                TetDriverData.choiseZoneByHand + TetGlobalData.TOKEN_SEPARATOR +
                                TetATetSettingDate.clear_without_GPS + TetGlobalData.TOKEN_SEPARATOR +
                                "";
                        //EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
                        new toServerSenderAcyncTasker(mContext, REQUEST);
                        break;
                    default:
                        String stopIdInAndroid = "drvstopid_" + Integer.toString(position + 1);
                        GetAnyStringFromTetSettingDate getStringFromTetSetingDate = new GetAnyStringFromTetSettingDate();
                        String stopId = getStringFromTetSetingDate.GetValue(stopIdInAndroid);
                        android.util.Log.e(pseudo_tag, "setOnItemClickListener position=" + position + " stopId = " + stopId + "");
                        REQUEST = "" +
                                TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.REQUEST_NEW_STOP_OR_ZONE_BY_HAND + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                                TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                                stopId + TetGlobalData.TOKEN_SEPARATOR +
                                TetDriverData.choiseZoneByHand + TetGlobalData.TOKEN_SEPARATOR +
                                TetATetSettingDate.clear_without_GPS + TetGlobalData.TOKEN_SEPARATOR +
                                "";
                        //EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
                        new toServerSenderAcyncTasker(mContext, REQUEST);
                        Boolean isGpsPosListnerOn = false;
                        EventBus.getDefault().postSticky(new EventsGPS(isGpsPosListnerOn), "UPDATE_POSITIOON");

                        if (TetATetSettingDate.zoneChoiseByDriver.equals("true") || TetATetSettingDate.zoneOnlyByDriver.equals("true")) {
                            TetDriverData.choiseZoneByHand = true;
                        }
                        finish();
                        break;
                }

            }
        });
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