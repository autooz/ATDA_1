package mobi.tet_a_tet.atda.mutual.mut_ulils.gps;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import mobi.tet_a_tet.atda.R;
import mobi.tet_a_tet.atda.mutual.TaxiCounterDistanAndTime;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventBus;
import mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus.EventsGPS;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGlobalData;
import mobi.tet_a_tet.atda.tet_a_tet.dates.TetGpsData;

public class GPSbyTimeListner extends Service implements LocationListener {


    Context mContext = this;
    Intent intent;
    Timer timer = new Timer();
    TimerTask tTask;
    long interval = 1000;

    boolean isGPSEnabled = false;
    private String BROADCAST_ACTION;
    // flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    double altitiude; // altitiude
    float accuracy; // accuracy
    float bearing; // bearing
    float speed; // speeed
    // The minimum distance to change Updates in meters
    public Location previousBestLocation = null;
    private IBinder mBinder;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = Integer.parseInt(TetATetSettingDate.GPS_UPDATE_DISTANCE); // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * Integer.parseInt(TetATetSettingDate.GPS_UPDATE_SECONDS); // seconds

    // Declaring a Location Manager
    protected LocationManager locationManager;
    private static final String pseudo_tag = "GPSbyTimeListner";
    public TaxiCounterDistanAndTime taximetreCounter;
    private double latitude_old;
    private double longitude_old;
    private double altitiude_old;
    private float accuracy_old;
    private float bearing_old;
    private float speed_old;
    private int ret;
    private int std;
    private int flg;
    private boolean runing = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        EventBus.getDefault().registerSticky(this);
        std = startId;
        flg =flags;
        if (startId > 1 && runing == true){
     //   if ((flags & START_FLAG_RETRY) == 0) {
            // TODO Если это повторный запуск, выполнить какие-то действия.
            android.util.Log.e(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG This is secondary start Doing ostopself() flag ="+flags+" startId ="+startId+" =");
          //  ret = 0;
           // stopSelf(startId);
        } else {
            android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG onStartCommand OK");
//            EventBus.getDefault().register(this);
//            EventBus.getDefault().registerSticky(this);
             getLocation();
            ret = Service.START_STICKY;
        }
        Log.e(pseudo_tag, "onStartCommand STARTED flag ="+flags+" startId ="+startId+"  ret="+ret+"");
        return ret;

        // return super.onStartCommand(intent, flags, startId);
    }


    public GPSbyTimeListner(Context context) {
        this.mContext = context;
        getLocation();
    }

    public GPSbyTimeListner() {
        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG onStartCommand OK");
//        EventBus.getDefault().register(this);
//        EventBus.getDefault().registerSticky(this);
        getLocation();
    }

    @Override
    public void onCreate() {
        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG onCreate nethod OK");
        super.onCreate();
        String action = getClass().getCanonicalName();
        int pos = action.lastIndexOf('.') + 1;
        String onlyClass = action.substring(pos);
        TetGpsData.GPSZoneListner_name = pseudo_tag;
        BROADCAST_ACTION = TetGpsData.GPSZoneListner_name;
        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG onCreate BROADCAST_ACTION =" + BROADCAST_ACTION + "");
        intent = new Intent(BROADCAST_ACTION);
        getLocation();

    }

//    @Subscriber(tag = "CLOSEPROG")
//    private void closeProgWithTag(EventFromControllerActivityMessage msg){
//        if(msg.FROM_CONTROLLER_ACTIVITY_MESSAGE.equals("CLOSEPROG")){
//            Log.e(pseudo_tag, "KILL service program via EventFromControllerActivityMessage flag ="+flg+" startId ="+std+"  ret="+ret+"");
//
//            stopSelf();
//        }
//    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.e(pseudo_tag, "FINISHED flag ="+flg+" startId ="+std+"  ret="+ret+"");
        stopUsingGPS();
        Timer timer;
        TimerTask tTask;
        // schedule("stop");
        tTask = null;
        long interval = 1000;
        super.onDestroy();
    }

////    public GPSbyTimeListner(Context context) {
////        this.mContext = context;
////        Log.e(pseudo_tag, "STARTED");
////        getLocation();
////    }
//
//    public GPSbyTimeListner() {
//        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG onStartCommand OK");
////        EventBus.getDefault().register(this);
////        EventBus.getDefault().registerSticky(this);
//        // getLocation();
//    }

    @Override
    public IBinder onBind(Intent arg0) {
        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG onBind nethod OK");
        getLocation();
        return null;
    }


    public Location getLocation() {
        runing = true;
        taximetreCounter = new TaxiCounterDistanAndTime();
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.e(pseudo_tag, "STARTED First get location from Provider");
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG GPS  isGPSEnabled=" + isGPSEnabled + " isNetworkEnabled=" + isNetworkEnabled + "");
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                this.canGetLocation = false;
            } else {
                // First get location from Network Provider

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, 0, this);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, 0, this);
                    Log.d(pseudo_tag, "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            Log.d(pseudo_tag, "Посылаем апдате из этого места");
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, 0, this);
                        Log.d(pseudo_tag, "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                canGetLocation = true;
                                Log.d(pseudo_tag, "Посылаем апдате из этого места GPSbyTimeListner");
//                                latitude = location.getLatitude();
//                                longitude = location.getLongitude();
//                                altitiude = location.getAltitude();
//                                accuracy = location.getAccuracy();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;

    }


    ////////////////////////////////////////////////////////////////////////
    @Override
    public void onLocationChanged(Location loc) {

        latitude_old = TetDriverData.last_tax_latitude;
        longitude_old = TetDriverData.last_tax_longitude;

        android.util.Log.d(pseudo_tag, "!!!!!!!!!!!!!!!!! DEBUG onLocationChanged nethod OK BROADCAST_ACTION = " + BROADCAST_ACTION + " Accuracy=" + loc.getAccuracy() + "");
        Log.i(getClass().getCanonicalName(), "Location changed Latitude=" + loc.getLatitude() + " Longitude=" + loc.getLongitude() + " ");
        if (isBetterLocation(loc, previousBestLocation)) {
            TetGpsData.latitude_current = loc.getLatitude();
            TetGpsData.longitude_current = loc.getLongitude();
            TetGpsData.altitiude_current = loc.getAltitude();
            TetGpsData.accuracy_current = loc.getAccuracy();
            TetGpsData.bearing_current = loc.getBearing();
            TetGpsData.speed_current = loc.getSpeed();
            TetGpsData.provider_current = loc.getProvider();
            TetGpsData.gpsTime_current = loc.getTime();


            // GetIng old driver location
            if (TetDriverData.driver_last_location == null) {
                TetDriverData.driver_last_location = loc;
            }

            if(TetGlobalData.CARRENT_CLASS.equals("GPSAccuracyNotGoodActivity")){
                EventBus.getDefault().post(new EventsGPS(loc), "GPSbyTimeListner");
                Log.e(pseudo_tag, "GPSbyTimeListner send loc");
            }


            if (TetATetSettingDate.clear_without_GPS.equals("true") && TetDriverData.without_GPS == true){

            } else if(TetDriverData.taxomode == TetDriverData.taxoRUN) {


                taximetreCounter.main(latitude_old,longitude_old,loc.getLatitude(),loc.getLongitude(),loc.getSpeed());
                // taxometre work
                TetGpsData.latitude_current = loc.getLatitude();
                TetGpsData.longitude_current = loc.getLongitude();
                TetGpsData.altitiude_current = loc.getAltitude();
                TetGpsData.accuracy_current = loc.getAccuracy();
                TetGpsData.bearing_current = loc.getBearing();
                TetGpsData.speed_current = loc.getSpeed();
                TetGpsData.provider_current = loc.getProvider();
                TetGpsData.gpsTime_current = loc.getTime();

                EventBus.getDefault().post(new EventsGPS(loc), "GPSbyTimeListner");
                Log.e(pseudo_tag, "GPSbyTimeListner send loc");


            }
            EventBus.getDefault().post(new EventsGPS(true), "TO_TAXIMETRE_UPDATE");
            // Send Message with TAG about position changing

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(), R.string.gpsIsOFF, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getApplicationContext(), R.string.gpsIsON, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }


    public double getAltitude() {
        if (location != null) {
            altitiude = location.getAltitude();
        }
        return altitiude;
    }

    public float getAccuracy() {
        if (location != null) {
            accuracy = location.getAccuracy();
        }

        // return longitude
        return accuracy;
    }


    public float getBearing() {
        if (location != null) {
            bearing = location.getBearing();

        }

        // return longitude
        return bearing;
    }


    public float getSpeed() {
        if (location != null) {
            speed = location.getSpeed();
        }

        // return longitude
        return speed;
    }


    /**
     * Function to check if best network provider
     *
     * @return boolean
     */
    public boolean canGetLocation() {

        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSbyTimeListner.this);

        }
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > MIN_TIME_BW_UPDATES;
        boolean isSignificantlyOlder = timeDelta < -MIN_TIME_BW_UPDATES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;


        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }


}
