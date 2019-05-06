package mobi.tet_a_tet.atda.tet_a_tet;

/**
 * Created by oleg on 02.07.15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration.TetFirstRegistrationMainActivity;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
/*!
\brief  The parent class is not carrying any meaning

This class has only one simple goal: Start APP
 */
/*!
    AP when running, the user can switch to the other activities of their choice.
*/
public class StartTetFirstRegistrationActivity extends Activity {

    private LinearLayout fistPageLayayt;
    private TextView firstStartPageTextWiev;
    private String pseudo_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.util.Log.d(TetGlobalData.TAG_TET_A_TET, "=========== ЗАПУСК  StartTetFirstRegistrationActivity===============");

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        ///Check EULA Tet-A-Tet is agree.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean wasShown = prefs.getBoolean(TetGlobalData.TAG_EULA_KEY_AGREEMENT, false);
        android.util.Log.d("StartFirstRegistration", "!!!!!!!!!!!!!!!!! DEBUG  befor Check EULA !!!");
        if (wasShown == false) {
            android.util.Log.d("StartFirstRegistration", "!!!!!!!!!!!!!!!!! DEBUG  after Check EULA !!!");
            new mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs.TetatetEula(this).show();
            android.util.Log.d("StartFirstRegistration", "!!!!!!!!!!!!!!!!! DEBUG  after show EULA !!!");
        } else {
                Intent JlA = new Intent(getApplicationContext(), TetFirstRegistrationMainActivity.class);
                startActivity(JlA);
                finish();
        }
    }

    public boolean isOnline() { /// // TODO: 16.11.15 Make check switch ON/OF Internet in  StartTetFirstRegistrationActivity
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            Log.v("status", "ONLINE");

        } else {
            Log.v("status", "OFFLINE");
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        onRestart();
        Log.e(pseudo_tag, "onResume()");
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        // Activity being restarted from stopped state
    }

}