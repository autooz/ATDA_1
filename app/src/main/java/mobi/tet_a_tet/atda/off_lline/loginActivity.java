package mobi.tet_a_tet.atda.off_lline;

/**
 * Created by oleg on 27.08.15.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.off_lline.Controllers.formController;
import mobi.tet_a_tet.atda.off_lline.Controllers.messageController;

public class loginActivity extends Activity implements OnClickListener, messageController.OnDismissListener {
    SharedPreferences sPref;
    Editor ed;
    CheckBox local;
    Bundle args;
    messageController msg;
    EditText login, pass;
    formController form;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        msg = new messageController(this);
        form = new formController(this);

        sPref = getSharedPreferences("monopoly", MODE_PRIVATE);// check monopoly

        setContentView(R.layout.local_login_activity);

        local = (CheckBox) findViewById(R.id.monopoly);
        local.setChecked(sPref.getBoolean("monopoly", false));
        login = (EditText) findViewById(R.id.editCallsign);
        pass = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(this);

        super.onCreate(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        String userLogin, userPass;
        userLogin = login.getText().toString();
        userPass = pass.getText().toString();

        switch (v.getId()) {

            case R.id.buttonLogin:
                args = new Bundle();

                if (userLogin.isEmpty() | userPass.isEmpty()) { //проверяем пусты ли поля
                    args.putInt("type", 2); // put args (show dialog with one button "OK")
                    args.putInt("msg", 0); // put args (body message)
                    msg.setArguments(args);
                    msg.show(getFragmentManager(), "ema");
                    Log.w(getResources().getString(R.string.tag), "поля пусты");
                    break;
                }

                ed = sPref.edit(); //set mode monopoly
                ed.putBoolean("monopoly", local.isChecked());
                ed.commit();

                if (form.db_controller.checkLogin(userLogin, userPass) != 0)
                    form.showForm(2, this, ""); //rewrite to startWork.class
                else {
                    // not exist user or invalid username an password
                    args.putInt("type", 2); // put args (show dialog with one button "OK")
                    args.putInt("msg", 1); // put args (body message)
                    msg.setArguments(args);
                    msg.show(getFragmentManager(), "ema");
                }

                break;
        }
    }


    @Override
    public void OnDismissListener(int res) {
        // TODO Auto-generated method stub

    }


}

