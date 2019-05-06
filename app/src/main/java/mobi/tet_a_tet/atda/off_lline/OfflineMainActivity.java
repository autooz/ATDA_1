package mobi.tet_a_tet.atda.off_lline;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.off_lline.Controllers.formController;
import mobi.tet_a_tet.atda.off_lline.Controllers.messageController;

/**
 * Created by oleg on 02.07.15.
 */

public class OfflineMainActivity extends Activity implements OnClickListener, messageController.OnDismissListener {
    Button btnStartWork, btnSettings, btnExitProgram;
    formController form;
    Bundle args;
    messageController msg;
    int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_main_activity);

        args = new Bundle();//для передачи данных для контроллера сообщений
        msg = new messageController(this);
        form = (formController) getIntent().getParcelableExtra(formController.class.getCanonicalName());//принимаем класс контроллера форм

        btnStartWork = (Button) findViewById(R.id.buttonStartWork);
        btnSettings = (Button) findViewById(R.id.buttonSettings);
        btnExitProgram = (Button) findViewById(R.id.buttonExitProgram);

        btnStartWork.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnExitProgram.setOnClickListener(this);


        //Log.w(form.db_controller.tag, "i from form " + form.db_controller.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonStartWork:
                form.showForm(3, this, "");
                break;


            case R.id.buttonSettings:
                form.showForm(4, this, "");
                break;


            case R.id.buttonExitProgram:
                args.putInt("type", 1); //dialog with 3 buttons (yes, no, cancel)
                args.putInt("msg", 2);
                msg.setArguments(args);
                msg.show(getFragmentManager(), "ema");
                break;

        }

    }

    @Override
    public void OnDismissListener(int res) {
        if (res == 1) System.exit(0);
    }


    @Override
    public void onBackPressed() {
        args.putInt("type", 1); //dialog with 3 buttons (yes, no, cancel)
        args.putInt("msg", 2);
        msg.setArguments(args);
        msg.show(getFragmentManager(), "ema");
    }

}

