package mobi.tet_a_tet.atda.off_lline;

/**
 * Created by oleg on 27.08.15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.off_lline.Adapters.adapter;
import mobi.tet_a_tet.atda.off_lline.Adapters.varsForAdapter;
import mobi.tet_a_tet.atda.off_lline.Controllers.formController;
import mobi.tet_a_tet.atda.off_lline.Controllers.messageController;

public class listViewActivity extends Activity implements OnClickListener, messageController.OnDismissListener {
    formController form;
    Bundle args;
    messageController msg;
    adapter adapter;
    ArrayList<varsForAdapter> data;
    ListView list;
    Button btnAdd, btnDelete, btnChange;
    String typeListView;
    TextView tempTextView;
    TextView text;
    private String pseudo_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        pseudo_tag = onlyClass;

        setContentView(R.layout.local_list_view);
        Log.e(pseudo_tag, "Activity " + pseudo_tag + " STARTED");



        args = new Bundle();
        msg = new messageController(this);
        form = (formController) getIntent().getParcelableExtra(formController.class.getCanonicalName());
        typeListView = getIntent().getStringExtra("typeListView");
        Log.e(pseudo_tag,"Type ="+typeListView+"");
        text = (TextView) findViewById(R.id.serialCar);
//
//        if (typeListView.equals("driver")) {
//            text.setText(R.string.listDrivers);
////        } else if (typeListView.equals("server")){
////            text.setText(R.string.infoServer);
//
//        } else if (typeListView.equals("mode")){
//            text.setText(R.string.infoServer);
//
//        } else if (typeListView.equals("polygon")){
//            text.setText(R.string.infoServer);
//
//        } else if (typeListView.equals("tarif")){
//            text.setText(R.string.infoServer);
//
//        }

        list = (ListView) findViewById(R.id.listView1);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        btnAdd = (Button) findViewById(R.id.btnAddDriver);
        btnDelete = (Button) findViewById(R.id.btnDeleteDriver);
        btnChange = (Button) findViewById(R.id.btnChangeDriver);

        selectType();

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        Log.e(pseudo_tag,"Activity "+pseudo_tag+" RESUMED");
        super.onResume();
        selectType();
    }

    @Override
    public void onClick(View v) {
        Log.e(pseudo_tag,"Activity "+pseudo_tag+" onClicked");
        switch (v.getId()) {
            case R.id.btnAddDriver:
                form.db_controller.tempId = -1;
                form.showForm(6, this, typeListView);
                break;

            case R.id.btnDeleteDriver:
                switch (typeListView) {
                    case "driver":
                        if (typeListView.equals("driver")) {
                            if (form.db_controller.getUserId() != 1) {//если не главный пользователь, показываем сообщение
                                args = new Bundle();
                                args.putInt("type", 2);
                                args.putInt("msg", 5);
                                msg.setArguments(args);
                                msg.show(getFragmentManager(), "ema");
                            } else {
                                if (form.db_controller.tempId == 1) {// показываем что нельзя удалить главного пользователя
                                    args = new Bundle();
                                    args.putInt("type", 2);
                                    args.putInt("msg", 6);
                                    msg.setArguments(args);
                                    msg.show(getFragmentManager(), "ema");
                                } else {
                                    form.db_controller.delete(typeListView);//

                                    args = new Bundle();//показываем сообщение об успешном удалении
                                    args.putInt("type", 2);
                                    args.putInt("msg", 4);
                                    msg.setArguments(args);
                                    msg.show(getFragmentManager(), "ema");
                                }
                            }
                        }
                        break;

                    default:
                        if (form.db_controller.tempId != -1) {
                            form.db_controller.delete(typeListView);
                            args = new Bundle();//показываем сообщение об успешном удалении
                            args.putInt("type", 2);
                            args.putInt("msg", 4);
                            msg.setArguments(args);
                            msg.show(getFragmentManager(), "ema");
                        } else {
                            args = new Bundle();
                            args.putInt("type", 2);
                            args.putInt("msg", 3);
                            msg.setArguments(args);
                            msg.show(getFragmentManager(), "ema");
                        }
                        break;
                }
                break;

            case R.id.btnChangeDriver:
                if (form.db_controller.tempId != -1) form.showForm(6, this, typeListView);
                else {
                    args = new Bundle();
                    args.putInt("type", 2); // put args (show dialog with one button "OK")
                    args.putInt("msg", 3); // put args (body message)
                    msg.setArguments(args);
                    msg.show(getFragmentManager(), "ema");
                }
                break;
        }
    }


    @Override
    public void OnDismissListener(int res) {
        selectType();
    }

    private void selectType() {
        Log.e(pseudo_tag,"Activity "+pseudo_tag+" OnDismissListener");
        if (typeListView.equals("driver")) {
            text.setText(R.string.listDrivers);
            adapter = new adapter(this, form.db_controller.select("selectAllDrivers"), typeListView);

        } else if (typeListView.equals("server")) {
            text.setText(R.string.infoServer);
            adapter = new adapter(this, form.db_controller.select("selectAllServers"), typeListView);

        } else if (typeListView.equals("mode")) {
            text.setText(R.string.mode);
            adapter = new adapter(this, form.db_controller.select("selectAllModes"), typeListView);
            btnAdd.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);

        } else if (typeListView.equals("polygon")) {
            text.setText(R.string.polugons);
            adapter = new adapter(this, form.db_controller.select("selectAllPolygon"), typeListView);

        } else if (typeListView.equals("tarif")) {
            text.setText(R.string.tariff);
            adapter = new adapter(this, form.db_controller.select("selectTarif"), typeListView);
            btnAdd.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);

        }

        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(form.tag, "itemClick: position = " + position + ", id = " + id);

                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(0);
                }
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                tempTextView = (TextView) view.findViewById(R.id.textView2);
                form.db_controller.tempId = Integer.parseInt(tempTextView.getText().toString());

            }
        });
    }
}

