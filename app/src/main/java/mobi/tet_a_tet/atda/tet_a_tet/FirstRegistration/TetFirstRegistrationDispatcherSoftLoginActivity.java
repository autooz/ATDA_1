package mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;


public class TetFirstRegistrationDispatcherSoftLoginActivity extends Activity implements View.OnClickListener {

    private TableRow tableRow0;
    private TextView phoneLabel;
    private TableRow tableRow1;
    private TextView loginLabel;
    private TableRow tableRow2;
    private TextView carLabel;
    private TableRow tableRow3;
    private TextView passwordLabel;
    private TableRow tableRow5;
    private TextView loginMessageText;
    private TableRow tableRow4;
    private TextView loginMessageText2;
    private String pseudo_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        android.util.Log.d(TetGlobalData.TAG_TET_A_TET, "=========== ЗАПУСК   TetFirstRegistrationDispatcherSoftLoginActivity===============");

       // TetGlobalData.CARRENT_ACTIVITY = TetGlobalData.DS_LOGIN_ACTIVITY;
        if (TetGlobalData.DsLoginAttempts > 0) {
            //Toast.makeText(this, R.string.server_instruction, Toast.LENGTH_LONG).show();
            new mobi.tet_a_tet.atda.mutual.LoginUncorrect(this).show();
        }

        setContentView(R.layout.activity_dispatcher_soft_login);

        tableRow0 = (TableRow) findViewById(R.id.tableRow0);
        phoneLabel = (TextView) findViewById(R.id.phoneLabel);
        tableRow1 = (TableRow) findViewById(R.id.tableRow1);
        loginLabel = (TextView) findViewById(R.id.loginLabel);
        tableRow2 = (TableRow) findViewById(R.id.tableRow2);
        carLabel = (TextView) findViewById(R.id.carLabel);
        tableRow3 = (TableRow) findViewById(R.id.tableRow3);
        passwordLabel = (TextView) findViewById(R.id.passwordLabel);
        tableRow5 = (TableRow) findViewById(R.id.tableRow5);
        findViewById(R.id.exitButton).setOnClickListener(this);
        findViewById(R.id.firstDsLoginButton).setOnClickListener(this);
        loginMessageText = (TextView) findViewById(R.id.loginMessageText);
        tableRow4 = (TableRow) findViewById(R.id.tableRow4);
        findViewById(R.id.chServerButton).setOnClickListener(this);
        findViewById(R.id.iHaveNotLogin).setOnClickListener(this);
        loginMessageText2 = (TextView) findViewById(R.id.loginMessageText2);
    }

    private EditText getPhoneField() {
        return (EditText) findViewById(R.id.phoneField);
    }

    private EditText getLoginField() {
        return (EditText) findViewById(R.id.loginField);
    }

    private EditText getCarField() {
        return (EditText) findViewById(R.id.carField);
    }

    private EditText getPasswordField() {
        return (EditText) findViewById(R.id.passwordField);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exitButton:
                //TODO implement
                TetGlobalData.CARRENT_ACTIVITY = TetGlobalData.DS_LOGIN_ACTIVITY;
                TetGlobalData.DsLoginAttempts = TetGlobalData.DsLoginAttempts + 1;
                Intent return_from_DispatherSoftLoginActivity_Intent = new Intent();
                return_from_DispatherSoftLoginActivity_Intent.putExtra("do", "exit");
                setResult(RESULT_CANCELED, return_from_DispatherSoftLoginActivity_Intent);
                finish();
                break;
            case R.id.firstDsLoginButton:
                //TODO implement
                TetGlobalData.CARRENT_ACTIVITY = TetGlobalData.DS_LOGIN_ACTIVITY;
                TetGlobalData.DsLoginAttempts = TetGlobalData.DsLoginAttempts + 1;
                setResult(RESULT_OK, gogoclick());
                Log.e(pseudo_tag,"firstDsLoginButton befor finish activity "+pseudo_tag+"");
                finish();
                break;
            case R.id.chServerButton:
                //TODO implement
                clearSeverPreference();
                onBackPressed();
                break;
            case R.id.iHaveNotLogin:
                //TODO implement
                break;
        }
    }

    private Intent gogoclick() {
        TextView drvphone = (TextView) findViewById(R.id.phoneField);
        TextView drvsign = (TextView) findViewById(R.id.loginField);
        TextView cargosnum = (TextView) findViewById(R.id.carField);
        TextView drvpassword = (TextView) findViewById(R.id.passwordField);

        String phone = drvphone.getText().toString();
        String sign = drvsign.getText().toString();
        String car = cargosnum.getText().toString();
        String paswd = drvpassword.getText().toString();
        Intent return_from_DispatherSoftLoginActivity_Intent = new Intent();
        return_from_DispatherSoftLoginActivity_Intent.putExtra(TetGlobalData.SDRVPHONE_KEY, phone);
        return_from_DispatherSoftLoginActivity_Intent.putExtra(TetGlobalData.SDRVSIGN_KEY, sign);
        return_from_DispatherSoftLoginActivity_Intent.putExtra(TetGlobalData.SCARGOSNUM_KEY, car);
        return_from_DispatherSoftLoginActivity_Intent.putExtra(TetGlobalData.SDRVPASSWD_KEY, paswd);

        return return_from_DispatherSoftLoginActivity_Intent;
    }

    private Intent back() {
        Intent ret;
        ret = new Intent();
        ret.putExtra("a", "b");
        return ret;
    }

    private void clearSeverPreference() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(TetGlobalData.SJBS_KEY_BOOL);
        editor.remove(TetGlobalData.SJBS_KEY);
        editor.remove(TetGlobalData.SJBU_KEY);
        editor.remove(TetGlobalData.SJBP_KEY);
        editor.remove(TetGlobalData.SJPORT_KEY);
        editor.remove(TetGlobalData.SJCALEE_KEY);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        // here remove code for your last fragment
        setResult(TetGlobalData.BACKSTACK, back());

        Intent mA = new Intent(getApplicationContext(), TetFirstRegistrationMainActivity.class);
        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mA);
        finish();

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
