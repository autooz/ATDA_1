package mobi.tet_a_tet.atda.tet_a_tet.controllers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.StringTokenizer;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventFromControllerActivityMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventJabIncomMessage;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.Subscriber;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSListnerZone;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSbyTimeListner;
import mobi.tet_a_tet.atda.off_lline.DriverTaximetreActivity;
import mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis.TetOnCabStandPriceActivity;
import mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis.TetOnCabStandPriceActivtyParcer;
import mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis.TetTaximetreActivity;
import mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis.TetZoneListActivity;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverSettings;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.utils.UpdateATDAActivity;

public class ActivityControllerService extends Service {
    Context mContext;
    private String pseudo_tag;
    private IBinder mBind;

    // http://stackoverflow.com/questions/3456034/how-to-start-an-activity-from-a-service
    // http://stackoverflow.com/questions/6925968/how-to-close-an-activity-finish-from-service


    public ActivityControllerService(Context context) {
        this.mContext = context;

    }
    public ActivityControllerService() {

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().register(this);
        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = "ActivityControllerService";
        Log.e(pseudo_tag, "onStartCommand STARTED flag ="+flags+" startId ="+startId+"");

        return START_STICKY;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        Log.e(pseudo_tag,"Am DESTROED LOOK REAZON");
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return mBind;
    }

    @Subscriber(tag = "INCOMING_MESSAGE")
    private void updateEventsJabberdMessagesWithTag(EventJabIncomMessage msg) {

        Log.e(pseudo_tag, "### update user with my_tag, name = " + msg.INCOMMING_MESSAGE);
        StringTokenizer st = new StringTokenizer(msg.INCOMMING_MESSAGE, TetGlobalData.TOKEN_SEPARATOR);
        String token = st.nextToken();
        Log.e(pseudo_tag, "### token = " + token);
        if (token.equals(TetGlobalData.RESPONCE)) {
            String tokenres = st.nextToken();
            Log.e(pseudo_tag, "### tokenres = " + tokenres);
            switch (tokenres){
                case TetGlobalData.OW_APP_ACTUAL:
                    Toast.makeText(getApplicationContext(), R.string.versionOK, Toast.LENGTH_LONG).show();
                    Intent ACS = new Intent(getApplicationContext(), ActivityControllerService.class);
                    stopService(ACS);
                    break;
                case TetGlobalData.OW_UPDATE_APP:
                    do_UPDATE();
                    break;
                case TetGlobalData.OW_STOPLIST:
                    Log.e(pseudo_tag, "### WI are It intent start OW_STOPLIST" );
                    do_OW_STOPLIST(st);
                    break;
                case TetGlobalData.OW_TAXIMETRE:
                    do_OW_TAXIMETRE(st);
                    break;
                case TetGlobalData.CLW_TAXIMETR:
                    do_CLOSETAXIM(st);
                    break;
                case TetGlobalData.OW_ONSTOP:
                    do_OW_ONSTOP(st);
                    break;
                case TetGlobalData.UP_ONSTOP:
                    do_UP_ONSTOP(st);
                    break;
                case TetGlobalData.UP_DRVSTATE:
                    do_UP_DRVSTATE(st);
                    break;
                case TetGlobalData.CLOSEPROG:
                    do_CLOSEPROG(tokenres);
                    break;
                case TetGlobalData.ERROR:
                    do_ERROR();
                    break;
                default:
                    do_ERROR();
                    break;
            }
        }
        else if (token.equals(TetGlobalData.REQUEST)){

        }
        else{
            android.util.Log.e(pseudo_tag, "---- ERROR jn first tocken ="+token+" with Message=" + msg.INCOMMING_MESSAGE + "---");
        }
    }

    private void do_CLOSETAXIM(StringTokenizer st) {
        String close = TetGlobalData.CLW_TAXIMETR;
        EventBus.getDefault().post(new EventFromControllerActivityMessage(close), "UPDATE_VIEW");
    }

    private void do_UPDATE() {
        Intent uA = new Intent(getApplicationContext(), UpdateATDAActivity.class);
        startActivity(uA);
    }

    private void do_ERROR() {
        Toast.makeText(getApplicationContext(), R.string.errorWithRequest, Toast.LENGTH_LONG).show();

    }

    private void do_UP_DRVSTATE(StringTokenizer st) {
        String state = st.nextToken();
        TetDriverData.drvstate = state;
        int update_view = 0;
        EventBus.getDefault().post(new EventFromControllerActivityMessage(update_view), "UPDATE_VIEW");
        android.util.Log.e(pseudo_tag, "Send Message UPDATE_POSITIOON_ACTIVITY_TO_ONSTOP");
    }

    private void do_CLOSEPROG(String string) {
        EventBus.getDefault().post(new EventFromControllerActivityMessage(string), "CLOSEPROG");
        Intent TLS = new Intent(getApplicationContext(), GPSbyTimeListner.class);
        stopService(TLS);
        Intent PLS = new Intent(getApplicationContext(), GPSListnerZone.class);
        stopService(PLS);
//        Intent JS = new Intent(this, JabberListenerService.class);
//        stopService(JS);
//        Intent TLS1 = new Intent(this, GPSbyTimeListner.class);
//        stopService(TLS1);
//        Intent PLS1 = new Intent(this, GPSListnerZone.class);
//        stopService(PLS1);
        EventBus.getDefault().post(new EventFromControllerActivityMessage(""), "CLOSEPROG");
//        Intent wHt = new Intent(getApplicationContext(), GoodByActivity.class);
//        wHt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(wHt);


    }

    private void do_UP_ONSTOP(StringTokenizer st) {
        Boolean auto_update_false_or_true = true;
        TetOnCabStandPriceActivtyParcer parcer = new TetOnCabStandPriceActivtyParcer();
        parcer.setParams(st);
        EventBus.getDefault().post(new EventFromControllerActivityMessage(auto_update_false_or_true), "UPDATE_POSITIOON");
        android.util.Log.e(pseudo_tag, "Send Message UPDATE_POSITIOON_ACTIVITY_TO_ONSTOP");

    }

    private void do_OW_ONSTOP(StringTokenizer s) {

        TetOnCabStandPriceActivtyParcer parcer = new TetOnCabStandPriceActivtyParcer();
        parcer.setParams(s);
        Intent i = new Intent();
        i.setClass(getApplicationContext(), TetOnCabStandPriceActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void do_OW_TAXIMETRE(StringTokenizer s) {
        EventBus.getDefault().postSticky(new EventFromControllerActivityMessage(s), "CTRL_ACTIVITY_TO_TAXIMETRE");
        android.util.Log.e(pseudo_tag, "Send Message CTRL_ACTIVITY_TO_TAXIMETRE");
        Intent i = new Intent();
        //@// TODO: 24.10.15 make difference tetData fnd Driver dsta

        if (TetDriverSettings.own_price_in_taximetre_from_border) {
            TetDriverData.taxomode =TetDriverData.taxoRUN;
            i.setClass(this, DriverTaximetreActivity.class);
        }
        else
        {
            TetDriverData.taxomode =TetDriverData.taxoRUN;
            i.setClass(this, TetTaximetreActivity.class);
        }
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void do_OW_STOPLIST(StringTokenizer s) {

        EventBus.getDefault().postSticky(new EventFromControllerActivityMessage(s), "CTRL_ACTIVITY_TO_ZL");
        android.util.Log.e(pseudo_tag, "Send Message CTRL_ACTIVITY_TO_ZL");
        TetDriverData.choiseZoneByHand = true;
        Intent i = new Intent();
        i.setClass(getApplicationContext(), TetZoneListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


//    set Kill frtivity in onCreate
//    if(this.getIntent().getExtras().getInt("kill")==1)
//    finish();
}

