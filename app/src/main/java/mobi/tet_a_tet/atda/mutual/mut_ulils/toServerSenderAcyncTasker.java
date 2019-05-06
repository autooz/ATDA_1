package mobi.tet_a_tet.atda.mutual.mut_ulils;

import android.content.Context;
import android.util.Log;

import mobi.tet_a_tet.atda.mutual.communications.eJabberdSenderInteface;
import mobi.tet_a_tet.atda.mutual.communications.jabberdSenderAcyncTasker;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventJabIncomMessage;
import mobi.tet_a_tet.atda.tet_a_tet.controllers.ActivityControllerClass;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

/**
 * Created by oleg on 07.03.16.
 */
public class toServerSenderAcyncTasker  implements eJabberdSenderInteface {


    private final String pseudo_tag;
    public Context mContext;

    public toServerSenderAcyncTasker(Context mContext, String REQUEST) {
        this.mContext = mContext;

        EventBus.getDefault().register(this);
        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;

        if (TetGlobalData.JBS.equals("172.14.2.101")) {
            //TetTempoDate.jabberd_OUT_COMMING_MESSAGE = REQUEST;
            new jabberdSenderAcyncTasker(this,mContext,REQUEST) {
                @Override
                protected boolean doAction() throws Exception {
                    Thread.currentThread().wait(1000);
                    return false;
                }
            }.execute();
        } else {
            new jabberdSenderAcyncTasker(this, mContext, REQUEST) {
                @Override
                protected boolean doAction() throws Exception {
                    Thread.currentThread().wait(1000);
                    return false;
                }
            }.execute();
        }
    }

    @Override
    public void onRecievedMessage(Context mContext, String msg) {
        Log.e(pseudo_tag, "onRecievedMessage = " + msg + "");
        EventBus.getDefault().post(new EventJabIncomMessage(msg), "CLOSE_PROGRESSBAR");
        Context contect = null;
        new ActivityControllerClass(mContext,msg);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLogin(boolean aTrue) {

    }

    @Override
    public void onPostExecute(boolean b) {

    }
}
