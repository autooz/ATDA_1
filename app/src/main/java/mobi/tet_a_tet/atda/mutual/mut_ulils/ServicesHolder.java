package mobi.tet_a_tet.atda.mutual.mut_ulils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by oleg on 15.10.15.
 */
public class ServicesHolder {

    public static int GPSListnerZoneStartId = 1;
    public static int GPSbyTimeListnerStartId = 1;
    public static int ActivityControllerServiceStartId = 1;
    public static int JabberListenerServiceStartId = 1;


    private static final Set<Class<? extends Service>> services = new HashSet();

    public static void addServices(Class<? extends Service>... sc) {
        Collections.addAll(services, sc);
    }

    public void stopAll(Context context) {
        if (context == null) return;
        for (Class<? extends Service> service : services) {
            context.stopService(new Intent(context, service));
        }
    }
}
