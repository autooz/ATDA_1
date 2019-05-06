package mobi.tet_a_tet.atda.mutual;

import android.content.Context;
import android.util.Log;

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGpsData;

/**
 * Created by oleg on 22.10.15.
 */
public class TaxiCounterDistanAndTime {
    private static double dd;
    //The current rate of speed from gps receiver - to write always

    Context taxiCounterDistanceAndTime;
    static double currspeed = 0;
    //тThe current geo coordinates from gps receiver - to write always
    static double currx =0;
    static double curry =0;
    // The following parameters must be taken from config
    final public static int maxmetersinsecond = 50; // how much can drive the second period of 1 sec. (100 = 360 km.h 50 =180km.h)
    final public static int maxsppedrun = Integer.parseInt(TetATetSettingDate.AutoToKMSpeed) ; //  the rate of speed above which start counting the mileage or stoping with counting by time
    final public static int maxsppedruntime = Integer.parseInt(TetATetSettingDate.AutoToMinutesSpeed); // how many seconds the speed should be higher or lower, to switch counting by time or distance
    final public static int freestopseconds = 0; // How many seconds are standing free, after a stop (traffic lights, etc. and etc.)
    // The rest variables must be global
    static public double[] testcoords= {56.01647078990936,54.758153808639136,56.016132831573486,54.75827762543359,56.015902161598206,54.75841072806528,56.01559638977051,54.758519067093545,56.01520478725433,54.75871407661374,56.014684438705444,54.75890537071632,56.014448404312134,54.75899204090304,56.014201641082764,54.75885584481202,56.013890504837036,54.75870107597051,56.013654470443726,54.758509161785206,56.013450622558594,54.75838534569895,56.01325750350952,54.7582120025419,56.01305365562439,54.758106758120135,56.012946367263794,54.75802627690712,56.01274251937866,54.75787150489385,56.012935638427734,54.757735305032675,56.013171672821045,54.7576733958534,56.01348280906677,54.75752481343682,56.013675928115845,54.757469094889984,56.013911962509155,54.75735765756628,56.01422309875488,54.75723383795792,56.014437675476074,54.75714716400672,56.014673709869385,54.75703572579681,56.014888286590576,54.75695524245442,56.01523160934448,54.7567942752896,56.01549983024597,54.75670140932643,56.015682220458984,54.7566209253194,56.015939712524414,54.75651567676103,56.016154289245605,54.75637328121715,56.01656198501587,54.75624945859793,56.016680002212524,54.75617516484463,56.01677656173706,54.756107062117636,56.017388105392456,54.75589656205539,56.017634868621826,54.75603895927609,56.01781725883484,54.75623088517239,56.01797819137573,54.75634232559785,56.01823568344116,54.756534250055935,56.018418073654175,54.756707600397284,56.01862192153931,54.756849994765226,56.01875066757202,54.7570109617085,56.01885795593262,54.75707906291489,56.018922328948975,54.75717192801172,56.018697023391724,54.757277174863916,56.01836442947388,54.75737003950629,56.018171310424805,54.75747528584341,56.01770997047424,54.7576238684418,56.01754903793335,54.75774768685717,56.017173528671265,54.757883886676694,56.016905307769775,54.75799532255179,56.0167121887207,54.75802008603793,56.01659417152405,54.75813152153814};

    private static final int timeMODE = 33;
    private static final int lenMODE = 77;

//static public int speedmode = 0;

    static int runtimer = 0; // Timer to calculate time after stop
    static int stoptimer =0; // Timer to to switch mod Calculate By Time after stop
    static int speedtimer =0; // Timer to to switch mod Calculate By Distance after start

    static int calcMODE = timeMODE;


    static double prevx =0;
    static double prevy =0;

    static int totalmeters = 0;
    static int totalseconds = 0;

    public TaxiCounterDistanAndTime(){

    }

    public static double latlng2distance (double lat1 , double long1,double lat2,double long2) { // calculating the distance between the coordinates in meters gps
        // radius of the Earth
        double R = 6372795;

        // transfer the coordinates in radians
        lat1 *= Math.PI / 180;
        lat2 *= Math.PI / 180;
        long1 *= Math.PI / 180;
        long2 *= Math.PI / 180;

        // calculate sines and cosines of the latitudes and difference of the longitudes
        double cl1 = Math.cos(lat1);
        double cl2 = Math.cos(lat2);
        double sl1 = Math.sin(lat1);
        double sl2 = Math.sin(lat2);
        double delta = long2 - long1;
        double cdelta = Math.cos(delta);
        double sdelta = Math.sin(delta);

        // calculate the length of the great circle
        double y = Math.sqrt(Math.pow(cl2 * sdelta, 2) + Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
        double x = sl1 * sl2 + cl1 * cl2 * cdelta;
        double ad = Math.atan2(y, x);
        double dist = ad * R; // distance between two coordinates by meters

        return dist;
    }

    public static void main(double latitude_old, double longitude_old, double latitude_current, double longitude_current, float speed_current) {
        // TODO Auto-generated method stub
        Log.d("mainInTAxidist", "main Get params");
        currspeed = 100.00;// speed_current;


        if(Double.toString(TetDriverData.last_tax_longitude).equals("0.0") || Double.toString(TetDriverData.last_tax_latitude).equals("0.0")){
            prevx=TetGpsData.longitude_current; // Always update the coordinates to avoid a sharp rise and jamping apon switchOFF mode Calculate Time
            prevy=TetGpsData.latitude_current;
        } else {
            prevx=longitude_old; // Always update the coordinates to avoid a sharp rise and jamping apon switchOFF mode Calculate Time
            prevy=latitude_old;
        }
        currx = TetGpsData.longitude_current;
        curry = TetGpsData.latitude_current;

            taxomain();
    }


    private static void taxomain() {  // Called once per second
        // TODO Auto-generated method stub
        if (currspeed>maxsppedrun)
        {
            runtimer = maxsppedruntime; // if you're moving, always willing to stop the counter
            if (speedtimer!=0) {speedtimer=speedtimer-1;}
            else {calcMODE=lenMODE;}
        }
        else
        {
            if (runtimer!=0)
            {runtimer=runtimer-1;// If the rate has fallen, subtract one second.
                stoptimer=freestopseconds;
            };
            if (runtimer==0)
            {   speedtimer=maxsppedruntime; // If the stand - always prepare to counter for movement
                if (stoptimer!=0) {stoptimer=stoptimer-1;
                }
                else
                {
                    totalseconds=totalseconds+1;
                    calcMODE=timeMODE;
                }
            }
        }
        // Always update the coordinates to avoid a sharp rise and jamping apon switchOFF mode Calculate Time
        switch (TetDriverData.taxomode) {
            case TetDriverData.taxoSTOPPED:
            { // Taximeter stopped, but newe the less doing position update
                prevx=currx;
                prevy=curry;
                dd = 0;
            };
            break;

            case TetDriverData.taxoRUN: //
            {
                if (calcMODE == lenMODE) {
                    if (currx != prevx || curry != prevy) { // If there is no match to the previous coordinates, we calculate the distance
                        dd = latlng2distance(prevx, prevy, currx, curry);
                        if (dd < maxmetersinsecond) { // If between the coordinates of more than $maxmetersinsecond, do not do anything, we suspect that the gps jumps of errors
                            totalmeters = (int) (totalmeters + dd);
                            // Here believe the price of the distance using total meters
                            Log.e("TATATAT", "len=" + dd + " total=" + totalmeters);
                            double mmAddDistanceFloat = dd*1000; //get int distanse double in mm 12087.870870
                            int mmAdddistanceInt = (int) mmAddDistanceFloat; //get int distanse int in mm 12087
                            double metreDist3DigsPastDot = (double) mmAdddistanceInt/1000; // get in meters looks like 12.087
                            TetDriverData.adding_distance = metreDist3DigsPastDot; // send float distanse looks like 12.087
                            Log.d("TATATAT","mmAddDistanceFloat="+mmAddDistanceFloat+" mmAdddistanceInt="+mmAdddistanceInt+" metreDist3DigsPastDot="+metreDist3DigsPastDot+" TetDriverData.adding_distance="+TetDriverData.adding_distance+"");
                        }
                        ;
                        if (calcMODE == timeMODE) {
                            dd = 0;
                            // Here we believe the price in time using totalseconds
                            //System.out.println("len="++" total="+totalseconds);
                        }

                    }
                }
                TetDriverData.last_tax_longitude = currx; // Always update the coordinates to avoid a sharp rise and jamping apon switchOFF mode Calculate Time
                TetDriverData.last_tax_latitude = curry;
                TetGpsData.longitude_current = currx;
                TetGpsData.latitude_current = curry;
            };
            break;
            default:
                dd = 0;
                break;
        }
    }

}
