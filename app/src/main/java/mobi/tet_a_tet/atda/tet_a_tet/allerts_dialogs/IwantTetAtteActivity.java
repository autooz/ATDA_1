package mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.tet_a_tet.StartTetFirstRegistrationActivity;

public class IwantTetAtteActivity extends Activity implements View.OnClickListener {

    private TableRow tableRowTitle;
    private TextView titleIwantTW;
    private TableRow tableRowText;
    private TextView tetDillerTV;
    private TableRow tableRowContactInfo;
    private TableRow tableRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iwant_tet_atte);

        tableRowTitle = (TableRow) findViewById(R.id.TableRowTitle);
        titleIwantTW = (TextView) findViewById(R.id.titleIwantTW);
        tableRowText = (TableRow) findViewById(R.id.TableRowText);
        tetDillerTV = (TextView) findViewById(R.id.tetDillerTV);
        tableRowContactInfo = (TableRow) findViewById(R.id.TableRowContactInfo);
        findViewById(R.id.buttonContactInfo).setOnClickListener(this);
        tableRow = (TableRow) findViewById(R.id.tableRow);
        findViewById(R.id.buttonStartAsDrv).setOnClickListener(this);
        findViewById(R.id.buttonClose).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonContactInfo:
                //TODO implement
                Intent cI = new Intent(getApplicationContext(), ContactInfoActivity.class);
                cI.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cI);
                break;
            case R.id.buttonStartAsDrv:
                //TODO implement
                Intent tAt = new Intent(getApplicationContext(), StartTetFirstRegistrationActivity.class);
                tAt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tAt);
                finish();
                break;
            case R.id.buttonClose:
                //TODO implement
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_iwant_tet_atte, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
