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
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import mobi.tet_a_tet.atda.R;

public class adapterStr extends BaseAdapter {
    private Activity activity;
    private ArrayList<String> data;
    private static LayoutInflater inflater = null;
    String tempValues;
    private String typeAdapter;
    String[] res;

    public adapterStr(Activity a, ArrayList<String> customListViewValuesArr, String type) {
        typeAdapter = type;
        activity = a;
        data = customListViewValuesArr;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        public TextView textView1;
        public EditText editText1;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int pos) {
        return data.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position <= 0) return null;
        View vi = convertView;
        ViewHolder holder;
        //res = activity.getResources().getStringArray(R.array.strDriver);

        switch (typeAdapter) {
            case "driver":
                if (convertView == null) {
                    vi = inflater.inflate(R.layout.local_text_item_list_view, null);
                    holder = new ViewHolder();
                    holder.textView1 = (TextView) vi.findViewById(R.id.textView1);
                    holder.editText1 = (EditText) vi.findViewById(R.id.fName);
                    vi.setTag(holder);
                } else holder = (ViewHolder) vi.getTag();

                //holder.textView1.setText(res[position]);
                holder.editText1.setText(data.get(position));
                break;

        }

        return vi;
    }


}
