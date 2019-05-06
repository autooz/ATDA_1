package mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import mobi.tet_a_tet.atda.MainActivity;
import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetTempoDate;

public class TetFirstJabRegActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private TableRow tableRow0;
    private TextView candidatInfoTV;
    private TableRow tableRow1;
    private TextView countryTextView;
    Spinner spinnerCountry;
    private TableRow tableRow2;
    private TextView citytextView;
    Spinner spinnerCity;
    private TableRow tableRow3;
    private TextView phonetextView2;
    private TableRow tableRow4;
    private TextView carModelTextView;
    private TableRow tableRow5;
    private String pseudo_tag;
    private TextView jabberUserLabel;
    private TextView jabberPasswordLabel;
    private int posCountryId;
    private String serverField;
    private String dispField;
    private int city;
    private String user;
    private String pass;
    private String server;
    private String disp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        android.util.Log.d(TetGlobalData.TAG_TET_A_TET, "=========== START  "+pseudo_tag+" ===============");


        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        setContentView(R.layout.activity_tet_first_jab_reg);


        countryTextView = (TextView) findViewById(R.id.countryTextView);
        tableRow2 = (TableRow) findViewById(R.id.tableRow2);
        citytextView = (TextView) findViewById(R.id.citytextView);
        tableRow3 = (TableRow) findViewById(R.id.tableRow3);
        jabberUserLabel = (TextView) findViewById(R.id.jabberUserLabel);
        tableRow4 = (TableRow) findViewById(R.id.tableRow4);
        jabberPasswordLabel = (TextView) findViewById(R.id.jabberPasswordLabel);
        tableRow5 = (TableRow) findViewById(R.id.tableRow5);
        spinnerCountry = (Spinner) findViewById(R.id.spinnerCountry);
        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerCountry.setOnItemSelectedListener(this);
        findViewById(R.id.jabberOkButton).setOnClickListener(this);
        findViewById(R.id.buttoniHaveNotLogin).setOnClickListener(this);
        findViewById(R.id.buttonExit).setOnClickListener(this);
        // smsManager = SmsManager.getDefault();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
                               long arg3) {
        Log.e(pseudo_tag, "runing  onItemSelected");
        posCountryId = pos;
        parent.getItemAtPosition(pos);
        if (pos == 1) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_ru_arrays,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
        } else if (pos == 2) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_ua_arrays,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
        } else if (pos == 3) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_by_arrays,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
        } else if (pos == 4) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_by_arrays,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
        }


        spinnerCity.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener() {
                    @Override

                    public void onItemSelected(AdapterView<?> parent, View itemSelected,

                                               int selectedltemPosition, long selectedId) {

                        parent.getItemAtPosition(selectedltemPosition);
                        String pos = (String) parent.getItemAtPosition(selectedltemPosition);
                        int posID = (int) parent.getSelectedItemPosition();

                        TetTempoDate.temp_str_1 = pos;

                        Log.e(pseudo_tag, "spinn pos=" + pos + "  posID=" + posID + "");

                        setServer(posCountryId, posID);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                    //… Other required overrides

                });
    }

    private void setServer(int posCountryId, int pos) {

        if (posCountryId == 1) { // Rusian federation
            city = pos;
            switch (pos) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    serverField = "brd.tet-a-tet.mobi";
                    dispField = "ustyug";
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                default:
                    break;
            }

        } else if (posCountryId == 2) { // Ukrain
            city = pos;

            switch (pos) {
                case 1:
                    break;
                case 2:
                    // Berdichev
                    serverField = "brd.tet-a-tet.mobi";
                    dispField = "tetatet";
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                default:
                    break;
            }
        } else if (posCountryId == 3) { // Belarus
            city = pos;
            switch (pos) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                default:
                    break;
            }
        }


    }


    private void setTemp() {
        TextView phoneField = (TextView) findViewById(R.id.phoneField);
        TextView carField = (TextView) findViewById(R.id.carModelField);
        TetTempoDate.temp_str_2 = phoneField.getText().toString();
        TetTempoDate.temp_str_3 = carField.getText().toString();

    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private EditText getEditText() {
        return (EditText) findViewById(R.id.phoneField);
    }

    private EditText getCarModelField() {
        return (EditText) findViewById(R.id.carModelField);
    }


    private Intent gogoclick() {

        Intent return_from_JabberdLoginActivity_Intent = new Intent();
        return_from_JabberdLoginActivity_Intent.putExtra(TetGlobalData.SJBP_KEY, pass);
        return_from_JabberdLoginActivity_Intent.putExtra(TetGlobalData.SJBS_KEY, server);
        return_from_JabberdLoginActivity_Intent.putExtra(TetGlobalData.SJBU_KEY, user);
        return_from_JabberdLoginActivity_Intent.putExtra(TetGlobalData.SJCALEE_KEY, disp);

        return return_from_JabberdLoginActivity_Intent;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jabberOkButton:
                //TODO implement
                TextView userField = (TextView) findViewById(R.id.jabberUserField);
                TextView passField = (TextView) findViewById(R.id.jabberPasswordField);
                server = serverField;
                user = userField.getText().toString();
                pass = passField.getText().toString();
                disp = dispField;
                if (city == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noTown, Toast.LENGTH_SHORT).show();
                    //onResume();
                } else if (user.length() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noJabUser, Toast.LENGTH_LONG).show();
                    //onResume();
                } else if (pass.length() == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noJabPasswd, Toast.LENGTH_LONG).show();
                    //onResume();
                } else {

                    TetGlobalData.CARRENT_ACTIVITY = TetGlobalData.JAB_LOGIN_ACTIVITY;
                    TetGlobalData.jabLoginAttempts = TetGlobalData.jabLoginAttempts + 1;
                    setResult(RESULT_FIRST_USER, gogoclick());
                    finish();
                }
                break;
            case R.id.buttoniHaveNotLogin:
                //TODO implement
                Intent nR = new Intent(getApplicationContext(), mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration.NotRegisteredYetActivity.class);
                // Oth.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nR);
                break;
            case R.id.buttonExit:
                //TODO implement
                Intent mA = new Intent(getApplicationContext(), MainActivity.class);
                mA.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mA);
                finish();
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




