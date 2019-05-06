package mobi.tet_a_tet.atda.tet_a_tet.FirstRegistration;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetTempoDate;


public class NotRegisteredYetActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private TableRow tableRow0;
    private TextView candidatSMSInfoTV;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        setContentView(R.layout.activity_not_registered_yet);

        tableRow0 = (TableRow) findViewById(R.id.TableRow0);
        candidatSMSInfoTV = (TextView) findViewById(R.id.candidatSMSInfoTV);
        tableRow1 = (TableRow) findViewById(R.id.tableRow1);
        countryTextView = (TextView) findViewById(R.id.countryTextView);
        tableRow2 = (TableRow) findViewById(R.id.tableRow2);
        citytextView = (TextView) findViewById(R.id.citytextView);
        tableRow3 = (TableRow) findViewById(R.id.tableRow3);
        phonetextView2 = (TextView) findViewById(R.id.phonetextView2);
        tableRow4 = (TableRow) findViewById(R.id.tableRow4);
        carModelTextView = (TextView) findViewById(R.id.carModelTextView);
        tableRow5 = (TableRow) findViewById(R.id.tableRow5);
        findViewById(R.id.sendSmsButton).setOnClickListener(this);
        findViewById(R.id.buttonNoCity).setOnClickListener(this);
        spinnerCountry = (Spinner) findViewById(R.id.spinnerCountry);
        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerCountry.setOnItemSelectedListener(this);

        // smsManager = SmsManager.getDefault();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
                               long arg3) {
        Log.e(pseudo_tag, "runing  onItemSelected");
        parent.getItemAtPosition(pos);
        if (pos == 1) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_ru_arrays,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
            TetTempoDate.temp_str_5 = "+380977052826";
        } else if (pos == 2) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_ua_arrays,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
            TetTempoDate.temp_str_5 = "+380674119875";
        } else if (pos == 3) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_by_arrays,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
            TetTempoDate.temp_str_5 = "+380674119875";
        } else if (pos == 4) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_by_arrays,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
            TetTempoDate.temp_str_5 = "+380674119875";
        }
        spinnerCity.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener() {
                    @Override

                    public void onItemSelected(AdapterView<?> parent, View itemSelected,

                                               int selectedltemPosition, long selectedId) {

                        parent.getItemAtPosition(selectedltemPosition);
                        String pos = (String) parent.getItemAtPosition(selectedltemPosition);

                        TetTempoDate.temp_str_1 = pos;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                    //… Other required overrides

                });
    }


    private void setTemp() {
        TextView phoneField = (TextView) findViewById(R.id.phoneField);
        TextView carField = (TextView) findViewById(R.id.carModelField);
        TetTempoDate.temp_str_2 = phoneField.getText().toString();
        TetTempoDate.temp_str_3 = carField.getText().toString();
        //Эксперементы////////////////////
        android.util.Log.d("NotRegisteredYet", "!!!!!!!!!!!!!!!!! DEBUG DELATE AFTER ALL!!!  itemSelected " + TetTempoDate.temp_str_1 + " phone " + TetTempoDate.temp_str_2 + " car " + TetTempoDate.temp_str_3 + "");
        //////////////////////////////////////////////////
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendSmsButton:
                //TODO implement
                setTemp();

                String smsMessage = "" + getString(R.string.cityWork) + " " + TetTempoDate.temp_str_1 + "  " + getString(R.string.phoneLabelText) + " " + TetTempoDate.temp_str_2 + "  " + getString(R.string.carModel) + " " + TetTempoDate.temp_str_3 + "";
                //Эксперементы////////////////////
                android.util.Log.d("NotRegisteredYet", "!!!!!!!!!!!!!!!!! DEBUG DELATE AFTER ALL!!!  smsMessage " + smsMessage + "");
                //////////////////////////////////////////////////
                String phone = TetTempoDate.temp_str_5;
                sendSMS(phone, smsMessage);
                break;
            case R.id.buttonNoCity:
                //TODO implement
                Intent tAt = new Intent(getApplicationContext(), mobi.tet_a_tet.atda.tet_a_tet.allerts_dialogs.IwantTetAtteActivity.class);
                tAt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tAt);
                finish();
                break;
        }


    }


    private void sendSMS(String phoneNumber, String message) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        intent.putExtra("sms_body", message);
        startActivity(intent);
//
//        Intent sendIntent=new Intent(Intent.ACTION_VIEW);
//        sendIntent.putExtra("sms_body","Content of the SMS goes here...");
//        sendIntent.setType("vnd.android-dir/mms-sms");
//        startActivity(sendIntent);
//
//
//        String SENT="SMS_SENT";
//        String DELIVERED="SMS_DELIVERED";
//
//        PendingIntent sentPI= PendingIntent.getBroadcast(this, 0,
//                new Intent(SENT), 0);
//
//        PendingIntent deliveredPI= PendingIntent.getBroadcast(this, 0,
//                new Intent(DELIVERED), 0);
//
//
////---когда SMS отправлено---
//        registerReceiver(new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context arg0, Intent arg1){
//                switch(getResultCode())
//                {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS sent",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(getBaseContext(),"Generic failure",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(getBaseContext(),"No service",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(getBaseContext(),"Null PDU",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(getBaseContext(),"Radio off",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        },new IntentFilter(SENT));
//
////---когда SMS доставлено---
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(getBaseContext(), "SMS not delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(DELIVERED));
//
//        SmsManager sms= SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);

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



