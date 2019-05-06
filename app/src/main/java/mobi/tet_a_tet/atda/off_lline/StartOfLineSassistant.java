package mobi.tet_a_tet.atda.off_lline;

/**
 * Created by oleg on 27.08.15.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;

import mobi.tet_a_tet.atda.MainActivity;
import mobi.tet_a_tet.atda.off_lline.Controllers.formController;

public class StartOfLineSassistant extends Activity {
    Bundle args;
    SharedPreferences sPref;
    SharedPreferences prefs;
    CheckBox local;
    formController form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        form = new formController(this);


        sPref = getSharedPreferences("settings", MODE_PRIVATE);// проверяем настройку (монопольно)

        if (form.db_controller.countDrivers() < 2 && sPref.getBoolean("monopoly", false)) {
            //если один пользователь и стоит "монопольно", то переходим на окно работы
            form.showForm(2, this, "");
        } else if (form.db_controller.countDrivers() < 1 && sPref.getBoolean("monopoly", false)) {
            //если один пользователей вообще нетто переходим на окно ввода водителей
            form.showForm(2, this, "");
        } else {
            // иначе переходим на окно авторизации
            form.showForm(4, this, "");
        }
    }


    @Override
    public void onBackPressed() {
        // here remove code for your last fragment
        Intent mA = new Intent(this, MainActivity.class);;
        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mA);
        finish();

    }


}
