package mobi.tet_a_tet.atda.mutual.mut_ulils.gps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by oleg on 04.09.15.
 */
public class GPSclass {

    private GPSclassInterface main;

    // Helper for GPS-Position
    private LocationListener mlocListener;
    private LocationManager mlocManager;

    private boolean isRunning;

    public GPSclass(GPSclassInterface main) {
        this.main = main;

        // GPS Position
        mlocManager = (LocationManager) ((Activity) this.main).getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        // GPS Position END
        this.isRunning = true;
    }

    public void stopGPS() {
        if(isRunning) {
            mlocManager.removeUpdates(mlocListener);
            this.isRunning = false;
        }
    }

    public void resumeGPS() {
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        this.isRunning = true;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public class MyLocationListener implements LocationListener {

        private final String TAG = MyLocationListener.class.getSimpleName();

        @Override
        public void onLocationChanged(Location loc) {
            GPSclass.this.main.locationChanged(loc.getLongitude(), loc.getLatitude());
        }

        @Override
        public void onProviderDisabled(String provider) {
            GPSclass.this.main.displayGPSSettingsDialog();
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }
}
