package mobi.tet_a_tet.atda.mutual.mut_ulils.eventbus;

import android.content.Intent;
import android.location.Location;

/**
 * Created by oleg on 03.09.15.
 */
public class EventsGPS {
    public Location ZoneLocationDates;
    public Float eventFloat;
    public String eventString;
    public Double eventDouble;
    public Long eventLong;
    public Object eventObject;
    public Intent eventIntent;
    public Integer eventInteger;
    public int eventInt;
    public Boolean isGpsPosListnerOn;


    public EventsGPS(Float value) {
        eventFloat = value;
    }
    public EventsGPS(String value) {
        eventString = value;
    }

    public EventsGPS(Object value) {
        eventObject = value;
    }

    public EventsGPS(Intent value) {
        eventIntent = value;
    }

    public EventsGPS(Double value) {
        eventDouble = value;
    }
    public EventsGPS(Integer value) {
        eventInteger = value;
    }

    public EventsGPS(Long value) {
        eventLong = value;
    }

    public EventsGPS(int value) {
        eventInt = value;
    }

    public EventsGPS(Boolean value) {
        isGpsPosListnerOn = value;
    }

    public EventsGPS(Location mLastLocation) {
        ZoneLocationDates = mLastLocation;
    }
}
