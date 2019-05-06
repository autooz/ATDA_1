package mobi.tet_a_tet.atda.tet_a_tet.dates;

import android.location.Location;

/**
 * Created by oleg on 17.08.15.
 */
public class TetDriverData {
    public static String drvbalance;
    public static String staffrules_event_class = "0";
    public static boolean choiseZoneByHand; // Choise by hand stop or zone or parking
    public static boolean without_GPS;
    public static String current_zone = TetGlobalData.out_of_city_key;
    public static String drvstopid = "0";
    public static final boolean falsebool= false;
    public static String carClassNameTaxi;
    public static String stringOrderBetweenTaxi;
    public static String carClassNameSpecialType;
    public static String stringOrderBetweenSpecialType;
    public static String allCarOnstop;
    public static String drvstate="0";
    public static Location driver_last_location = null;
    final public static int taxoSTOPPED = 0; // taximeter is switchOFF
    final public static int taxoRUN = 1; // taximeter is switchON
    public static int taxomode = taxoSTOPPED; // The current operating mode
    public static double last_tax_longitude = 0.0;
    public static double last_tax_latitude = 0.0;
    public static double adding_distance = 0;
    public static int cmAmmountOutCity = 0;
    public static int cmAmmountInCity = 0;
    public static float kmAmmount = 0;
    public static float totalKm = 0;
    public static double lastShownDistceOutOfCity;
    public static double lastShownDistceInCity;
    public static double lastShownPaymenteOutOfCity;
    public static double lastShownPaymentInCity;
    public static String currency = TetATetSettingDate.currency;

    public void setTetDriverData(String name, String newVar) {
        name = newVar;
    }


}
