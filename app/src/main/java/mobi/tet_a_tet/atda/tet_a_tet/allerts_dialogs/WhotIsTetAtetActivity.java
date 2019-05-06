package mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import mobi.tet_a_tet.atda.MainActivity;
import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.tet_a_tet.StartTetFirstRegistrationActivity;


public class WhotIsTetAtetActivity extends Activity implements View.OnClickListener {

    private TableRow tableRowTitle;
    private TextView textViewTitle;
    private TableRow tableRowMessage;
    private TextView textViewMessage;
    private TableRow tableRowButttonDiller;
    private TableRow tableRowButttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whot_is_tet_atet);

        tableRowTitle = (TableRow) findViewById(R.id.TableRowTitle);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        tableRowMessage = (TableRow) findViewById(R.id.TableRowMessage);
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);
        tableRowButttonDiller = (TableRow) findViewById(R.id.TableRowButttonDiller);
        findViewById(R.id.buttonDiller).setOnClickListener(this);
        tableRowButttonClose = (TableRow) findViewById(R.id.TableRowButttonClose);
        findViewById(R.id.buttonClose).setOnClickListener(this);
        findViewById(R.id.buttonStartAsDrv).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStartAsDrv:
                //TODO implement
                Intent tAt = new Intent(getApplicationContext(), StartTetFirstRegistrationActivity.class);
                tAt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tAt);
                finish();
                break;
            case R.id.buttonDiller:
                //TODO implement
                Intent wt = new Intent(getApplicationContext(), IwantTetAtteActivity.class);
                //  wHt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(wt);
                break;
            case R.id.buttonClose:
                //TODO implement
                onBackPressed();
                // finish();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_whot_is_tet_a_tet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent mA = new Intent(getApplicationContext(), MainActivity.class);
        mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mA);
        finish();
    }
}
