package mobi.tet_a_tet.atda.off_lline;

/**
 * Created by oleg on 27.08.15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.off_lline.Adapters.adapterStr;
import mobi.tet_a_tet.atda.off_lline.Controllers.formController;
import mobi.tet_a_tet.atda.off_lline.Controllers.messageController;

public class addOrChangeListViewActivity extends Activity implements OnClickListener, messageController.OnDismissListener {
    formController form;
    String typeListView;
    Bundle args;
    messageController msg;
    ListView list;
    adapterStr adapter;
    ArrayList<String> strArr;
    Button btnAction;
    EditText fName, sName, tName, password, licence, spd, serialCar, cityWork, nameIDE,
            cityIDE, streetIDE, houseIDE, roomIDE, okpo;
    EditText nameServer, ipMain, portMain, ipSafe, portSafe, ipGPS, portGPS;
    CheckBox workServer, valueMode;
    EditText namePolygon, valuePolygon;
    TextView TextView2;
    TextView sitdown, minCost, kmCost, minuteCostStay, minKmCost, minuteMinCost, countryCost, countryBackCost, minSpeed;
    private String pseudo_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;
        Log.e(pseudo_tag, "###  this.getTaskId() = " + this.getTaskId() + "");

        args = new Bundle();
        msg = new messageController(this);
        form = (formController) getIntent().getParcelableExtra(formController.class.getCanonicalName());

        typeListView = getIntent().getStringExtra("typeListView");


        switch (typeListView) {
            case "driver":
                setContentView(R.layout.local_add_or_change_driver);


                fName = (EditText) findViewById(R.id.fName);
                sName = (EditText) findViewById(R.id.sName);
                tName = (EditText) findViewById(R.id.tName);
                password = (EditText) findViewById(R.id.password);
                licence = (EditText) findViewById(R.id.license);
                spd = (EditText) findViewById(R.id.spd);
                serialCar = (EditText) findViewById(R.id.serialCarDrv);
                cityWork = (EditText) findViewById(R.id.cityWork);
                nameIDE = (EditText) findViewById(R.id.nameIDE);
                cityIDE = (EditText) findViewById(R.id.cityIDE);
                streetIDE = (EditText) findViewById(R.id.streetIDE);
                houseIDE = (EditText) findViewById(R.id.houseIDE);
                roomIDE = (EditText) findViewById(R.id.roomIDE);
                okpo = (EditText) findViewById(R.id.okpo);


                if (form.db_controller.getUserId() != 1)
                    if (form.db_controller.getUserId() != form.db_controller.tempId & form.db_controller.tempId != -1) {
                        fName.setEnabled(false);
                        sName.setEnabled(false);
                        tName.setEnabled(false);
                        licence.setEnabled(false);
                        spd.setEnabled(false);
                        cityWork.setEnabled(false);
                        nameIDE.setEnabled(false);
                        cityIDE.setEnabled(false);
                        streetIDE.setEnabled(false);
                        houseIDE.setEnabled(false);
                        roomIDE.setEnabled(false);
                        okpo.setEnabled(false);
                    }

                if (form.db_controller.tempId != -1) {
                    strArr = form.db_controller.select("selectAllAboutDriver").get(0).driver;
                    fName.setText(strArr.get(1));
                    sName.setText(strArr.get(2));
                    tName.setText(strArr.get(3));
                    password.setText(strArr.get(4));
                    licence.setText(strArr.get(5));
                    spd.setText(strArr.get(6));
                    serialCar.setText(strArr.get(7));
                    cityWork.setText(strArr.get(8));
                    nameIDE.setText(strArr.get(9));
                    cityIDE.setText(strArr.get(10));
                    streetIDE.setText(strArr.get(11));
                    houseIDE.setText(strArr.get(12));
                    roomIDE.setText(strArr.get(13));
                    okpo.setText(strArr.get(14));
                }

							/*list = (ListView) findViewById(R.id.listView1);
                            list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

					        adapter = new adapterStr(this, form.db_controller.select("selectAllAboutDriver").get(0).driver, typeListView);*/
                break;
            case "server":
                setContentView(R.layout.local_add_or_change_server);

                nameServer = (EditText) findViewById(R.id.nameServer);
                ipMain = (EditText) findViewById(R.id.ipMain);
                portMain = (EditText) findViewById(R.id.portMain);
                ipSafe = (EditText) findViewById(R.id.ipSafe);
                portSafe = (EditText) findViewById(R.id.portSafe);
                ipGPS = (EditText) findViewById(R.id.ipGPS);
                portGPS = (EditText) findViewById(R.id.portGPS);
                workServer = (CheckBox) findViewById(R.id.workServer);

                if (form.db_controller.tempId != -1) {
                    strArr = form.db_controller.select("selectAllAboutServer").get(0).driver;

                    nameServer.setText(strArr.get(2));
                    ipMain.setText(strArr.get(3));
                    portMain.setText(strArr.get(4));
                    ipSafe.setText(strArr.get(5));
                    portSafe.setText(strArr.get(6));
                    ipGPS.setText(strArr.get(7));
                    portGPS.setText(strArr.get(8));
                    workServer.setChecked((strArr.get(9).equals("true")) ? true : false);
                }

                break;
            case "mode":
                setContentView(R.layout.local_item_mode);

                strArr = form.db_controller.select("selectAllAboutMode").get(0).driver;

                TextView2 = (TextView) findViewById(R.id.textView2);
                TextView2.setText(strArr.get(3));
                valueMode = (CheckBox) findViewById(R.id.valueMode);
                valueMode.setChecked((strArr.get(4).equals("true")) ? true : false);
                valueMode.setText(strArr.get(2));


                break;
            case "polygon":
                setContentView(R.layout.local_polygon_edit);

                namePolygon = (EditText) findViewById(R.id.namePolygon);
                valuePolygon = (EditText) findViewById(R.id.valuePolygon);

                if (form.db_controller.tempId != -1) {
                    strArr = form.db_controller.select("selectAllAboutPolygon").get(0).driver;

                    namePolygon.setText(strArr.get(2));
                    valuePolygon.setText(strArr.get(3).replace(",", "\n"));
                }
                break;

            case "tarif":
                setContentView(R.layout.local_tarif_edit);

                sitdown = (EditText) findViewById(R.id.sitdown);
                minCost = (EditText) findViewById(R.id.minCost);
                kmCost = (EditText) findViewById(R.id.kmCost);
                minuteCostStay = (EditText) findViewById(R.id.minuteCostStay);
                minKmCost = (EditText) findViewById(R.id.minKmCost);
                minuteMinCost = (EditText) findViewById(R.id.minuteMinCost);
                countryCost = (EditText) findViewById(R.id.countryCost);
                countryBackCost = (EditText) findViewById(R.id.countryBackCost);
                minSpeed = (EditText) findViewById(R.id.minSpeed);

                strArr = form.db_controller.select("selectInfoTarif").get(0).driver;

                sitdown.setText(strArr.get(2));
                minCost.setText(strArr.get(3));
                kmCost.setText(strArr.get(4));
                minuteCostStay.setText(strArr.get(5));
                minKmCost.setText(strArr.get(6));
                minuteMinCost.setText(strArr.get(7));
                countryCost.setText(strArr.get(8));
                countryBackCost.setText(strArr.get(9));
                minSpeed.setText(strArr.get(10));
                break;
        }
        //list.setAdapter(adapter);

        btnAction = (Button) findViewById(R.id.btnAction);
        btnAction.setOnClickListener(this);

    }

    @Override
    public void OnDismissListener(int res) {
        if (res == 1) finish();//после показа сообщения, закрываем окно
    }

    @Override
    public void onClick(View v) {
        strArr = new ArrayList<String>();

        switch (v.getId()) {
            case R.id.btnAction:
                switch (typeListView) {
                    case "driver":
                        //получаем значения из полей
                        strArr.add(fName.getText().toString());
                        strArr.add(sName.getText().toString());
                        strArr.add(tName.getText().toString());
                        strArr.add(password.getText().toString());
                        strArr.add(licence.getText().toString());
                        strArr.add(spd.getText().toString());
                        strArr.add(serialCar.getText().toString());
                        strArr.add(cityWork.getText().toString());
                        strArr.add(nameIDE.getText().toString());
                        strArr.add(cityIDE.getText().toString());
                        strArr.add(streetIDE.getText().toString());
                        strArr.add(houseIDE.getText().toString());
                        strArr.add(roomIDE.getText().toString());
                        strArr.add(okpo.getText().toString());

                        break;
                    case "server":
                        strArr.add(nameServer.getText().toString());
                        strArr.add(ipMain.getText().toString());
                        strArr.add(portMain.getText().toString());
                        strArr.add(ipSafe.getText().toString());
                        strArr.add(portSafe.getText().toString());
                        strArr.add(ipGPS.getText().toString());
                        strArr.add(portGPS.getText().toString());
                        strArr.add(String.valueOf(workServer.isChecked()));


                        break;
                    case "polygon":
                        strArr.add(namePolygon.getText().toString());
                        strArr.add(valuePolygon.getText().toString().replace("\n", ","));
                        break;

                    case "mode":
                        strArr.add(valueMode.getText().toString());
                        strArr.add(TextView2.getText().toString());
                        strArr.add(String.valueOf(valueMode.isChecked()));
                        break;
                    case "tarif":
                        strArr.add(sitdown.getText().toString());
                        strArr.add(minCost.getText().toString());
                        strArr.add(kmCost.getText().toString());
                        strArr.add(minuteCostStay.getText().toString());
                        strArr.add(minKmCost.getText().toString());
                        strArr.add(minuteMinCost.getText().toString());
                        strArr.add(countryCost.getText().toString());
                        strArr.add(countryBackCost.getText().toString());
                        strArr.add(minSpeed.getText().toString());
                        break;
                }

        }

        if (form.db_controller.tempId != -1) //делаем апдейт существующей записи
            form.db_controller.update(typeListView, strArr);
        else //создаем новую запись
            form.db_controller.insert(typeListView, strArr);

        //выводим сообщение об успешном выполнении
        args.putInt("type", 2); // put args (show dialog with one button "OK")
        args.putInt("msg", 4); // put args (body message)
        msg.setArguments(args);
        msg.show(getFragmentManager(), "ema");

    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED");
        super.onDestroy();
    }
}

