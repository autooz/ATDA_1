package mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.StringTokenizer;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.utils.GetAnyStringFromTetSettingDate;

/**
 * Created by oleg on 05.10.15.
 */
public class TetZoneListActivityParcer {

    SharedPreferences settings;
    ArrayList<String> list;
    int i;

    public TetZoneListActivityParcer() {
    }

    public ArrayList<String> makeZoneArray(StringTokenizer messageForParser, Context context) {
        // Creating an empty array list
        list = new ArrayList<String>();
        i=0;
        while (messageForParser.hasMoreElements()) {
            i++;
            String tocken = messageForParser.nextToken();
            StringTokenizer st22 = new StringTokenizer(tocken, TetGlobalData.TOKEN_SEPARATOR_VARS);
            String stop = st22.nextToken();
            String allclasses = st22.nextToken();
            String thisclass = st22.nextToken();
            GetAnyStringFromTetSettingDate getStringFromTetSetingDate= new GetAnyStringFromTetSettingDate();
            String stopID = getStringFromTetSetingDate.GetValue(stop);
            String num = stop.replace("drvstopid_", "");
            String name ="zone_stop_"+num+"";
            String stopname = getStringFromTetSetingDate.GetValue(name);
            String toArray =""+Integer.toString(i)+" "+stopname+" "+context.getString(R.string.allCarAmmount)+" "+allclasses+" "+context.getString(R.string.allCarYouClassAmmount)+" "+thisclass+"";
            android.util.Log.d("makeZoneArray", " toArray = " + toArray+"");
            list.add(toArray);
        }
        android.util.Log.e("makeZoneArray", " list = " +list+"");

        return list;
    }


}
