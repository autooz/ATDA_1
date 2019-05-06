package mobi.tet_a_tet.atda.off_lline;

/**
 * Created by oleg on 27.08.15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import mobi.tet_a_tet.atda.MainActivity;
import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.off_lline.Controllers.formController;
import mobi.tet_a_tet.atda.off_lline.Controllers.messageController;

public class settingActivity extends Activity implements OnClickListener, messageController.OnDismissListener {
    formController form;
    Bundle args;
    messageController msg;
    Button btnDriver, btnServer, btnPolygon, btnMode, btnTariff, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_setting_activity);

        args = new Bundle();
        msg = new messageController(this);
        form = (formController) getIntent().getParcelableExtra(formController.class.getCanonicalName());

        btnDriver = (Button) findViewById(R.id.buttonDriver);
        btnServer = (Button) findViewById(R.id.btnServer);
        btnPolygon = (Button) findViewById(R.id.btnPolygon);
        btnMode = (Button) findViewById(R.id.btnMode);
        btnTariff = (Button) findViewById(R.id.btnTariff);
        btnExit = (Button) findViewById(R.id.btnExit);

        btnDriver.setOnClickListener(this);
        btnServer.setOnClickListener(this);
        btnPolygon.setOnClickListener(this);
        btnMode.setOnClickListener(this);
        btnTariff.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }


    @Override
    public void OnDismissListener(int res) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDriver:
                //form.db_controller.test();
                form.showForm(5, this, "driver");
                break;

            case R.id.btnServer:
                form.showForm(5, this, "server");
                break;

            case R.id.btnPolygon:
                form.showForm(5, this, "polygon");
                break;

            case R.id.btnMode:
                form.showForm(5, this, "mode");
                break;

            case R.id.btnTariff:
                form.showForm(5, this, "tarif");
                break;

            case R.id.btnExit:
                finish();
                break;
        }


    }

    public void onBackPressed() {
        // here remove code for your last fragment
        Intent mA = new Intent(this, MainActivity.class);;
        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mA);
        finish();

    }

}

