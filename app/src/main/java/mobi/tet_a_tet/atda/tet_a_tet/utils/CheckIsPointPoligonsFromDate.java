package mobi.tet_a_tet.atda.tet_a_tet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;
import java.util.StringTokenizer;

import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventsGPS;
import mobi.tet_a_tet.atda.mutual.mut_ulils.poligons.Point;
import mobi.tet_a_tet.atda.mutual.mut_ulils.poligons.Polygon;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;

/**
 * Created by oleg on 11.09.15.
 */
public class CheckIsPointPoligonsFromDate {
    private Context mContext;
    SharedPreferences settings;
    private int i = 0;
    private int b = 0;
    private int c;
    private String polygon_from_SettingDate;
    private Field fieldr = null;
    private String G;
    private boolean contains;
    private String zone_stop_current;
    private String drvstopid;

    public CheckIsPointPoligonsFromDate() {
        //  mContext = context;
    }

    public void CheskIsPOintInPolygon(Double point_latitude, Double point_longitude) {
        EventBus.getDefault().register(this);
        do {
            i++;
            polygon_from_SettingDate = null;
            G = "poligon_zone_" + Integer.toString(i) + "";
            try {
                polygon_from_SettingDate = (String) TetATetSettingDate.class.getField(G).get(null);
            } catch (NoSuchFieldException x) {
                x.printStackTrace();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!polygon_from_SettingDate.equals("")) {

                prepare_peaks(polygon_from_SettingDate, i, point_latitude, point_longitude);

            }

        } while (!polygon_from_SettingDate.equals("") || i >= 30);
        if (c == 0) {

            TetDriverData.current_zone = TetGlobalData.out_of_city_key;
            EventBus.getDefault().post(new EventsGPS(TetDriverData.current_zone), "CheskIsPOintInPolygon");
        }
        EventBus.getDefault().unregister(this);
    }

    private void prepare_peaks(String polygon_settingDate, int i, Double car_latitude, Double car_longitude) {
        Polygon.Builder polygonBuilder = null;
        int a = 0;
        StringTokenizer st = new StringTokenizer(polygon_settingDate, TetGlobalData.POLYGONE_PEAK_SEPARATOR);
        float lati = Float.parseFloat(new Double(car_latitude).toString());
        float lond = Float.parseFloat(new Double(car_longitude).toString());
        Point point = new Point(lond, lati);

        while (st.hasMoreElements()) {
            String token = st.nextToken();
            String peak = null;
            peak = token.replace(TetGlobalData.POLYGONE_PEAK_first_char, "");
            peak = peak.replace(" ", "");
            String latitude = null;
            String longitude = null;
            StringTokenizer coo = new StringTokenizer(peak, TetGlobalData.COORDINATE_SEPARATOR);
            latitude = coo.nextToken();
            longitude = coo.nextToken();
            android.util.Log.d("WritePoligonsToDate", "!!!!!!!!!!!!!!!!! DEBUG  after Check G=" + i + " polygon_from_SharePrefs=" + peak + " latitude=" + latitude + " longitude=" + longitude + "");
            Float lat = Float.parseFloat(String.valueOf(latitude).replace("vv", ""));
            Float lon = Float.parseFloat(longitude);
            if (a == 0) {
                polygonBuilder = Polygon.Builder().addVertex(new Point(lat, lon));
                a++;
            } else {
                polygonBuilder.addVertex(new Point(lat, lon));
            }


        }
        Polygon polygon = polygonBuilder.build();
        contains = polygon.contains(point);

        if (contains) {
            c++;
            String zone = "zone_stop_" + Integer.toString(i) + "";
            String stopid = "drvstopid_" + Integer.toString(i) + "";
            try {
                zone_stop_current = (String) TetATetSettingDate.class.getField(zone).get(null);
                drvstopid = (String) TetATetSettingDate.class.getField(stopid).get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            android.util.Log.d("WindowFragmentCont", "!!!!!!!!!!!!!!!!! DEBUG BINGO BINGO BINGO BINGO zone_stop_current=" + zone_stop_current + " drvstopid=" + drvstopid + "");
            TetDriverData.current_zone = zone_stop_current;
            TetDriverData.drvstopid = drvstopid;
            EventBus.getDefault().post(new EventsGPS(drvstopid), "CheskIsPOintInPolygon");

        } else {
            b++;
            android.util.Log.d("WindowFragmentCont", "!!!!!!!!!!!!!!!!! DEBUG Check poligon_zone_" + i + " results=" + contains + " lat=" + lati + " lon=" + lond + " poligin=" + polygon.toString() + "");
        }

    }

}
