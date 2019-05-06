package mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.toServerSenderAcyncTasker;
import mobi.tet_a_tet.atda.tet_a_tet.controllers.ActivityControllerService;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

public class TetOnCabStandOptions extends Activity implements View.OnClickListener {

    private TableRow choiseZoneTR;
    private TableRow tuningTR;
    private TableRow allTarifsTR;
    private Button butChoiseZone;
    private String pseudo_tag;
    private String REQUEST;
    private ServiceConnection serviceConnection;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent ACS = new Intent(getApplicationContext(), ActivityControllerService.class);
        bindService(ACS, serviceConnection, this.BIND_AUTO_CREATE);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        setContentView(R.layout.activity_tet_on_cab_stand_options);

        choiseZoneTR = (TableRow) findViewById(R.id.choiseZoneTR);
        findViewById(R.id.buttonChoiseZone).setOnClickListener(this);
        tuningTR = (TableRow) findViewById(R.id.tuningTR);
        findViewById(R.id.buttonTuningTR).setOnClickListener(this);
        allTarifsTR = (TableRow) findViewById(R.id.allTarifsTR);
        findViewById(R.id.buttonAllTarifs).setOnClickListener(this);
        updateViw();

    }

    private void updateViw() {

        if (TetATetSettingDate.zoneChoiseByDriver.equals("true")){
            choiseZoneTR.setVisibility(View.VISIBLE);
        } else {
            choiseZoneTR.setVisibility(View.INVISIBLE);
        }

        butChoiseZone = (Button) findViewById(R.id.buttonChoiseZone);

                 if(TetDriverData.choiseZoneByHand){

             butChoiseZone.setText(R.string.setZoneAutomatic);
             butChoiseZone.setBackgroundColor(Color.GREEN);
             butChoiseZone.setTextColor(Color.BLACK);
        }
        else {
             butChoiseZone.setText(R.string.setZoneByHand);
             butChoiseZone.setBackgroundColor(Color.RED);
             butChoiseZone.setTextColor(Color.WHITE);
        }

    }

    @Override
    public void onClick(View view) {
        Log.d(pseudo_tag,"Click working");
        switch (view.getId()) {
            case R.id.buttonChoiseZone:
                //TODO implement
                Log.d(pseudo_tag,"buttonChoiseZone working TetDriverData.choiseZoneByHand="+TetDriverData.choiseZoneByHand+"");
                if(!TetDriverData.choiseZoneByHand){
                    REQUEST = "" +
                            TetGlobalData.REQUEST + TetGlobalData.TOKEN_SEPARATOR +
                            TetGlobalData.REQUEST_STOP_LIST + TetGlobalData.TOKEN_SEPARATOR +
                            TetGlobalData.DRVPHONE + TetGlobalData.TOKEN_SEPARATOR +
                            TetGlobalData.DRVSIGN + TetGlobalData.TOKEN_SEPARATOR +
                            TetGlobalData.CARGOSNUM + TetGlobalData.TOKEN_SEPARATOR +
                            TetGlobalData.DRVPASSWORD + TetGlobalData.TOKEN_SEPARATOR +
                            "";
                    Intent ProgresBarWaitResponceActivity = new Intent(getApplicationContext(), mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs.ProgresBarWaitResponceActivity.class);
                    startActivityForResult(ProgresBarWaitResponceActivity, TetGlobalData.WAIT_JB_RESPONCE);
                    //EventBus.getDefault().post(new EventJabOutcomMessage(REQUEST), "OUTCOMING_MESSAGE");
                    new toServerSenderAcyncTasker(mContext, REQUEST);
                    Log.d(pseudo_tag, "Do go with own passenger from skirting and send OUTCOMING_MESSAGE REQUEST =" + REQUEST + "");
                }
                else {
                    butChoiseZone.setText(R.string.setZoneByHand);
                    butChoiseZone.setBackgroundColor(Color.RED);
                    butChoiseZone.setTextColor(Color.WHITE);
                    TetDriverData.choiseZoneByHand = false;
                }
                break;
            case R.id.buttonTuningTR:
                //TODO implement
                break;
            case R.id.buttonAllTarifs:
                //TODO implement
                break;
        }
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
