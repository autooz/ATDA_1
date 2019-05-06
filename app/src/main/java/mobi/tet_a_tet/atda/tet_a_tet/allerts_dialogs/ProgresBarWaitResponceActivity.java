package mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.StringTokenizer;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventJabIncomMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventJabOutcomMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscriber;
import mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis.GoodByActivity;
import mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration.TetFirstRegistrationMainActivity;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

public class ProgresBarWaitResponceActivity extends Activity implements View.OnClickListener {

    private RelativeLayout progressBarWeitResponce;
    private TextView cheskDsSettingstV;
    private ProgressBar progressBarChDs;
    private String pseudo_tag;
    private boolean openStartPage;

    //BroadcastReceiver abcd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;

        EventBus.getDefault().register(this);
        Log.e(pseudo_tag, "### START");

        setContentView(R.layout.activity_progres_bar_wait_responce);

      Log.e(pseudo_tag, "### Started Activity = " + action);

        progressBarWeitResponce = (RelativeLayout) findViewById(R.id.progressBarWeitResponce);
        cheskDsSettingstV = (TextView) findViewById(R.id.cheskDsSettingstV);
        progressBarChDs = (ProgressBar) findViewById(R.id.progressBarChDs);
        findViewById(R.id.buttonExit).setOnClickListener(this);

    }

    @Subscriber(tag = "OUTCOMING_MESSAGE")
    private void updateEventsJabberdOutcomingMessageWithTag(EventJabOutcomMessage message) {
        Log.e(pseudo_tag, "### EventsJabberdOutcomingMessageWithTag Message to be send = " + message.OUTCOMING_MESSAGE);
        StringTokenizer st = new StringTokenizer(message.OUTCOMING_MESSAGE, TetGlobalData.TOKEN_SEPARATOR);
        String token = st.nextToken();
        if (token.equals(TetGlobalData.REQUEST)) {
            String tokenres = st.nextToken();
            if (tokenres.equals(TetGlobalData.REQUEST_START_WORK)) {
                TetGlobalData.notReply = true;
                openStartPage = true;
            }
            else if (tokenres.equals(TetGlobalData.CLOSEPROG)){

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonExit:
                //TODO implement

                if(openStartPage) {
                    onBackPressed();
                }
                else{
                    finish();
                }
                break;
        }
    }

    @Subscriber(tag = "CLOSE_PROGRESSBAR")
    private void updateEventsJabberdMessagesWithTag(EventJabIncomMessage msg) {
        Log.e(pseudo_tag, "### EventsJabberdOutcomingMessageWithTag Message to be send = " + msg.INCOMMING_MESSAGE);
        StringTokenizer st = new StringTokenizer(msg.INCOMMING_MESSAGE, TetGlobalData.TOKEN_SEPARATOR);
        String token = st.nextToken();
        Log.e(pseudo_tag, "### update user with my_tag, name = " + msg.INCOMMING_MESSAGE);
        if (token.equals(TetGlobalData.RESPONCE)) {
            String tokenres = st.nextToken();
            if (tokenres.equals(TetGlobalData.CLOSEPROG)) {
                Intent wHt = new Intent(getApplicationContext(), GoodByActivity.class);
                wHt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                wHt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(wHt);
                finish();
            }

        }
        finish();
    }

    private Intent gozero() {
        Intent GPSAccuracyNotGoodActivity_Intent = new Intent();
        GPSAccuracyNotGoodActivity_Intent.putExtra("without_GPS_", false);
        return GPSAccuracyNotGoodActivity_Intent;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // Don’t forget to unregister !!
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // here remove code for your last fragment

        Intent mA = new Intent(getApplicationContext(), TetFirstRegistrationMainActivity.class);
        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mA);
        finish();

    }

}

