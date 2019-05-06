package mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import mobi.tet_a_tet.atda.MainActivity;
import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;

import static java.lang.System.exit;

public class ThenksForRegActivity extends Activity implements View.OnClickListener {

    private TextView textView11;
    private String pseudo_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        setContentView(R.layout.activity_thenks_for_reg);

        textView11 = (TextView) findViewById(R.id.textView11);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Intent tAt = new Intent(getApplicationContext(), MainActivity.class);
                tAt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                tAt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                tAt.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(tAt);
                exit(2);
                finish();
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
