package mobi.tet_a_tet.atda.tet_a_tet.dates;

import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSListnerZone;
import mobi.tet_a_tet.atda.mutual.mut_ulils.gps.GPSbyTimeListner;

/**
 * Created by oleg on 02.09.15.
 */
public class TetGpsData {

    public static final String latitude_key = "Latitude";
    public static double latitude_current;
    public static final String longitude_key = "Longitude";
    public static double longitude_current;
    public static final String altitiude_key = "Altitiude";
    public static double altitiude_current;
    public static final String accuracy_key = "Accuracy";
    public static float accuracy_current;
    public static final String bearing_key = "Bearing";
    public static float bearing_current;
    public static final String speed_key = "Speed";
    public static float speed_current;
    public static final String provider_key = "Provider";
    public static String provider_current;
    public static final String gpsTime_key = "GpsTime";
    public static long gpsTime_current;
    public static String GPSZoneListner_name;// = "LocationService";
    public static final String GPSListner_tag = "ChLocationlistner";
    public static final String gpsZone_tag = "gpsZone";
    public static long last_Update_time = 0;
    public static boolean canGetLocation = false;
    public static GPSListnerZone gpsZone;
    public static GPSbyTimeListner gpsListner;;
    public static String GPSListner_name;


    public void setTetGpsData(String name, String newVar) {
        name = newVar;
    }
}
