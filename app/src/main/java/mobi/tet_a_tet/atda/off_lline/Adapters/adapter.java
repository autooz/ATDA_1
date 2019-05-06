package mobi.tet_a_tet.atda.off_lline.Adapters;

/**
 * Created by oleg on 27.08.15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import mobi.tet_a_tet.atda.R;

public class adapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    varsForAdapter tempVarsForAdapter;
    private Activity activity;
    private ArrayList<varsForAdapter> array_list;
    private String typeAdapter;

    public adapter(Activity a, ArrayList<varsForAdapter> customListViewValuesArr, String type) {
        typeAdapter = type;
        activity = a;
        array_list = customListViewValuesArr;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return array_list.size();
    }

    @Override
    public Object getItem(int pos) {
        return array_list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        switch (typeAdapter) {
            case "driver":
                if (convertView == null) {
                    vi = inflater.inflate(R.layout.local_item_list_view, null);//выбираем layout для парсинга
                    holder = new ViewHolder();
                    holder.serialCar = (TextView) vi.findViewById(R.id.serialCar);//находим текстовое поле по идентификатору
                    holder.fio = (TextView) vi.findViewById(R.id.fio);//находим текстовое поле по идентификатору
                    holder.driverId = (TextView) vi.findViewById(R.id.textView2);//находим текстовое поле по идентификатору
                    vi.setTag(holder);
                } else holder = (ViewHolder) vi.getTag();

                //берет один элемент из набора данных бд, который вернул котроллер бд
                tempVarsForAdapter = (varsForAdapter) array_list.get(position);
                //устанавливаем текстовому полю serialCar значение элемента driver.get(1) (позывной)
                holder.serialCar.setText(tempVarsForAdapter.driver.get(1));
                //устанавливаем текстовому полю serialCar значение элемента (фио), формируется из трех элементов
                holder.fio.setText(tempVarsForAdapter.driver.get(2) + " " + tempVarsForAdapter.driver.get(3) + " " + tempVarsForAdapter.driver.get(4));
                //для скрытого элемента устанавливаем значение элемента (id)
                holder.driverId.setText(tempVarsForAdapter.driver.get(0));
                break;
            case "server":
                if (convertView == null) {
                    vi = inflater.inflate(R.layout.local_server_item, null);
                    holder = new ViewHolder();
                    holder.checkBox1 = (CheckBox) vi.findViewById(R.id.workServer);
                    holder.textView1 = (TextView) vi.findViewById(R.id.textView1);
                    holder.textView2 = (TextView) vi.findViewById(R.id.textView2);
                    vi.setTag(holder);
                } else holder = (ViewHolder) vi.getTag();

                tempVarsForAdapter = (varsForAdapter) array_list.get(position);
                holder.checkBox1.setChecked(Boolean.valueOf(tempVarsForAdapter.driver.get(2)));
                holder.textView1.setText(tempVarsForAdapter.driver.get(1));
                holder.textView2.setText(tempVarsForAdapter.driver.get(0));
                break;
            case "polygon":
                if (convertView == null) {
                    vi = inflater.inflate(R.layout.local_item_list_view, null);
                    holder = new ViewHolder();
                    holder.serialCar = (TextView) vi.findViewById(R.id.serialCar);
                    holder.fio = (TextView) vi.findViewById(R.id.fio);
                    holder.driverId = (TextView) vi.findViewById(R.id.textView2);
                    vi.setTag(holder);
                } else holder = (ViewHolder) vi.getTag();

                tempVarsForAdapter = (varsForAdapter) array_list.get(position);
                holder.serialCar.setText(tempVarsForAdapter.driver.get(0));
                holder.fio.setText(tempVarsForAdapter.driver.get(1));
                holder.driverId.setText(tempVarsForAdapter.driver.get(0));
                break;
            case "mode":
                if (convertView == null) {
                    vi = inflater.inflate(R.layout.local_server_item, null);
                    holder = new ViewHolder();
                    holder.checkBox1 = (CheckBox) vi.findViewById(R.id.workServer);
                    holder.textView1 = (TextView) vi.findViewById(R.id.textView1);
                    holder.textView2 = (TextView) vi.findViewById(R.id.textView2);
                    vi.setTag(holder);
                } else holder = (ViewHolder) vi.getTag();

                tempVarsForAdapter = (varsForAdapter) array_list.get(position);
                holder.checkBox1.setChecked(Boolean.valueOf(tempVarsForAdapter.driver.get(2)));
                holder.textView1.setText(tempVarsForAdapter.driver.get(1));
                holder.textView2.setText(tempVarsForAdapter.driver.get(0));
                break;
            case "tarif":
                if (convertView == null) {
                    vi = inflater.inflate(R.layout.local_item_list_view, null);
                    holder = new ViewHolder();
                    holder.serialCar = (TextView) vi.findViewById(R.id.serialCar);
                    holder.fio = (TextView) vi.findViewById(R.id.fio);
                    holder.driverId = (TextView) vi.findViewById(R.id.textView2);
                    vi.setTag(holder);
                } else holder = (ViewHolder) vi.getTag();

                tempVarsForAdapter = (varsForAdapter) array_list.get(position);
                holder.serialCar.setText("");
                holder.fio.setText("Тариф");
                holder.driverId.setText(tempVarsForAdapter.driver.get(0));
                break;

        }


        return vi;
    }

    private static class ViewHolder {
        public TextView fio, serialCar, driverId, textView1, textView2;
        public EditText editText1;
        public CheckBox checkBox1;
    }


}

