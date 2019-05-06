package mobi.tet_a_tet.atda.tet_a_tet.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;

/**
 * Created by oleg on 31.08.15.
 */
public class setTetATetSettingDateDS {

    SharedPreferences settings;
    private Activity mActivity;

    public setTetATetSettingDateDS(Activity context) {
        mActivity = context;

    }

    public void setTetATetSettingDateDS() {
        settings = PreferenceManager.getDefaultSharedPreferences(mActivity);

        TetATetSettingDate.settingOK = true;
        TetATetSettingDate.DS_SETING_VERS_VALUE = settings.getString(TetATetSettingDate.DS_SETING_VERS_KEY, "");
        TetATetSettingDate.GPS_UPDATE_DISTANCE = settings.getString(TetATetSettingDate.GPS_UPDATE_DISTANCE_КЕУ, "");
        TetATetSettingDate.GPS_UPDATE_SECONDS = settings.getString(TetATetSettingDate.GPS_UPDATE_SECONDS_KEY, "");
        TetATetSettingDate.zone_stop_1 = settings.getString(TetATetSettingDate.zone_stop_1_key, "");
        TetATetSettingDate.poligon_zone_1 = settings.getString(TetATetSettingDate.poligon_zone_1_key, "");
        TetATetSettingDate.drvstopid_1 = settings.getString(TetATetSettingDate.drvstopid_1_key, "");
        TetATetSettingDate.zone_stop_2 = settings.getString(TetATetSettingDate.zone_stop_2_key, "");
        TetATetSettingDate.poligon_zone_2 = settings.getString(TetATetSettingDate.poligon_zone_2_key, "");
        TetATetSettingDate.drvstopid_2 = settings.getString(TetATetSettingDate.drvstopid_2_key, "");
        TetATetSettingDate.zone_stop_3 = settings.getString(TetATetSettingDate.zone_stop_3_key, "");
        TetATetSettingDate.poligon_zone_3 = settings.getString(TetATetSettingDate.poligon_zone_3_key, "");
        TetATetSettingDate.drvstopid_3 = settings.getString(TetATetSettingDate.drvstopid_3_key, "");
        TetATetSettingDate.zone_stop_4 = settings.getString(TetATetSettingDate.zone_stop_4_key, "");
        TetATetSettingDate.poligon_zone_4 = settings.getString(TetATetSettingDate.poligon_zone_4_key, "");
        TetATetSettingDate.drvstopid_4 = settings.getString(TetATetSettingDate.drvstopid_4_key, "");
        TetATetSettingDate.zone_stop_5 = settings.getString(TetATetSettingDate.zone_stop_5_key, "");
        TetATetSettingDate.poligon_zone_5 = settings.getString(TetATetSettingDate.poligon_zone_5_key, "");
        TetATetSettingDate.drvstopid_5 = settings.getString(TetATetSettingDate.drvstopid_5_key, "");
        TetATetSettingDate.zone_stop_6 = settings.getString(TetATetSettingDate.zone_stop_6_key, "");
        TetATetSettingDate.poligon_zone_6 = settings.getString(TetATetSettingDate.poligon_zone_6_key, "");
        TetATetSettingDate.drvstopid_6 = settings.getString(TetATetSettingDate.drvstopid_6_key, "");
        TetATetSettingDate.zone_stop_7 = settings.getString(TetATetSettingDate.zone_stop_7_key, "");
        TetATetSettingDate.poligon_zone_7 = settings.getString(TetATetSettingDate.poligon_zone_7_key, "");
        TetATetSettingDate.drvstopid_7 = settings.getString(TetATetSettingDate.drvstopid_7_key, "");
        TetATetSettingDate.zone_stop_8 = settings.getString(TetATetSettingDate.zone_stop_8_key, "");
        TetATetSettingDate.poligon_zone_8 = settings.getString(TetATetSettingDate.poligon_zone_8_key, "");
        TetATetSettingDate.drvstopid_8 = settings.getString(TetATetSettingDate.drvstopid_8_key, "");
        TetATetSettingDate.zone_stop_9 = settings.getString(TetATetSettingDate.zone_stop_9_key, "");
        TetATetSettingDate.poligon_zone_9 = settings.getString(TetATetSettingDate.poligon_zone_9_key, "");
        TetATetSettingDate.drvstopid_9 = settings.getString(TetATetSettingDate.drvstopid_9_key, "");
        TetATetSettingDate.zone_stop_10 = settings.getString(TetATetSettingDate.zone_stop_10_key, "");
        TetATetSettingDate.poligon_zone_10 = settings.getString(TetATetSettingDate.poligon_zone_10_key, "");
        TetATetSettingDate.drvstopid_10 = settings.getString(TetATetSettingDate.drvstopid_10_key, "");
        TetATetSettingDate.zone_stop_11 = settings.getString(TetATetSettingDate.zone_stop_11_key, "");
        TetATetSettingDate.poligon_zone_11 = settings.getString(TetATetSettingDate.poligon_zone_11_key, "");
        TetATetSettingDate.drvstopid_11 = settings.getString(TetATetSettingDate.drvstopid_11_key, "");
        TetATetSettingDate.zone_stop_12 = settings.getString(TetATetSettingDate.zone_stop_12_key, "");
        TetATetSettingDate.poligon_zone_12 = settings.getString(TetATetSettingDate.poligon_zone_12_key, "");
        TetATetSettingDate.drvstopid_12 = settings.getString(TetATetSettingDate.drvstopid_12_key, "");
        TetATetSettingDate.zone_stop_13 = settings.getString(TetATetSettingDate.zone_stop_13_key, "");
        TetATetSettingDate.poligon_zone_13 = settings.getString(TetATetSettingDate.poligon_zone_13_key, "");
        TetATetSettingDate.drvstopid_13 = settings.getString(TetATetSettingDate.drvstopid_13_key, "");
        TetATetSettingDate.zone_stop_14 = settings.getString(TetATetSettingDate.zone_stop_14_key, "");
        TetATetSettingDate.poligon_zone_14 = settings.getString(TetATetSettingDate.poligon_zone_14_key, "");
        TetATetSettingDate.drvstopid_14 = settings.getString(TetATetSettingDate.drvstopid_14_key, "");
        TetATetSettingDate.zone_stop_15 = settings.getString(TetATetSettingDate.zone_stop_15_key, "");
        TetATetSettingDate.poligon_zone_15 = settings.getString(TetATetSettingDate.poligon_zone_15_key, "");
        TetATetSettingDate.drvstopid_15 = settings.getString(TetATetSettingDate.drvstopid_15_key, "");
        TetATetSettingDate.zone_stop_16 = settings.getString(TetATetSettingDate.zone_stop_16_key, "");
        TetATetSettingDate.poligon_zone_16 = settings.getString(TetATetSettingDate.poligon_zone_16_key, "");
        TetATetSettingDate.drvstopid_16 = settings.getString(TetATetSettingDate.drvstopid_16_key, "");
        TetATetSettingDate.zone_stop_17 = settings.getString(TetATetSettingDate.zone_stop_17_key, "");
        TetATetSettingDate.poligon_zone_17 = settings.getString(TetATetSettingDate.poligon_zone_17_key, "");
        TetATetSettingDate.drvstopid_17 = settings.getString(TetATetSettingDate.drvstopid_17_key, "");
        TetATetSettingDate.zone_stop_18 = settings.getString(TetATetSettingDate.zone_stop_18_key, "");
        TetATetSettingDate.poligon_zone_18 = settings.getString(TetATetSettingDate.poligon_zone_18_key, "");
        TetATetSettingDate.drvstopid_18 = settings.getString(TetATetSettingDate.drvstopid_18_key, "");
        TetATetSettingDate.zone_stop_19 = settings.getString(TetATetSettingDate.zone_stop_19_key, "");
        TetATetSettingDate.poligon_zone_19 = settings.getString(TetATetSettingDate.poligon_zone_19_key, "");
        TetATetSettingDate.drvstopid_19 = settings.getString(TetATetSettingDate.drvstopid_19_key, "");
        TetATetSettingDate.zone_stop_20 = settings.getString(TetATetSettingDate.zone_stop_20_key, "");
        TetATetSettingDate.poligon_zone_20 = settings.getString(TetATetSettingDate.poligon_zone_20_key, "");
        TetATetSettingDate.drvstopid_20 = settings.getString(TetATetSettingDate.drvstopid_20_key, "");
        TetATetSettingDate.zone_stop_21 = settings.getString(TetATetSettingDate.zone_stop_21_key, "");
        TetATetSettingDate.poligon_zone_21 = settings.getString(TetATetSettingDate.poligon_zone_21_key, "");
        TetATetSettingDate.drvstopid_21 = settings.getString(TetATetSettingDate.drvstopid_21_key, "");
        TetATetSettingDate.zone_stop_22 = settings.getString(TetATetSettingDate.zone_stop_22_key, "");
        TetATetSettingDate.poligon_zone_22 = settings.getString(TetATetSettingDate.poligon_zone_22_key, "");
        TetATetSettingDate.drvstopid_22 = settings.getString(TetATetSettingDate.drvstopid_22_key, "");
        TetATetSettingDate.zone_stop_23 = settings.getString(TetATetSettingDate.zone_stop_23_key, "");
        TetATetSettingDate.poligon_zone_23 = settings.getString(TetATetSettingDate.poligon_zone_23_key, "");
        TetATetSettingDate.drvstopid_23 = settings.getString(TetATetSettingDate.drvstopid_23_key, "");
        TetATetSettingDate.zone_stop_24 = settings.getString(TetATetSettingDate.zone_stop_24_key, "");
        TetATetSettingDate.poligon_zone_24 = settings.getString(TetATetSettingDate.poligon_zone_24_key, "");
        TetATetSettingDate.drvstopid_24 = settings.getString(TetATetSettingDate.drvstopid_24_key, "");
        TetATetSettingDate.zone_stop_25 = settings.getString(TetATetSettingDate.zone_stop_25_key, "");
        TetATetSettingDate.poligon_zone_25 = settings.getString(TetATetSettingDate.poligon_zone_25_key, "");
        TetATetSettingDate.drvstopid_25 = settings.getString(TetATetSettingDate.drvstopid_25_key, "");
        TetATetSettingDate.language = settings.getString(TetATetSettingDate.language_key, "");
        TetATetSettingDate.currency = settings.getString(TetATetSettingDate.currency_key, "");
        TetATetSettingDate.cityout_tariff = settings.getString(TetATetSettingDate.cityout_tariff_key, "");
        TetATetSettingDate.MinimalKm = settings.getString(TetATetSettingDate.MinimalKm_key, "");
        TetATetSettingDate.initialPayment = settings.getString(TetATetSettingDate.initialPayment_key, "");
        TetATetSettingDate.MinimalMinutes = settings.getString(TetATetSettingDate.MinimalMinutes_key, "");
        TetATetSettingDate.MinimalPrice = settings.getString(TetATetSettingDate.MinimalPrice_key, "");
        TetATetSettingDate.PriceKm = settings.getString(TetATetSettingDate.PriceKm_key, "");
        TetATetSettingDate.waitCustomerMinutes = settings.getString(TetATetSettingDate.waitCustomerMinutes_key, "");
        TetATetSettingDate.AutoToMinutesSpeed = settings.getString(TetATetSettingDate.AutoToMinutesSpeed_key, "");
        TetATetSettingDate.AutoToKMSpeed = settings.getString(TetATetSettingDate.AutoToKMSpeed_key, "");
        TetATetSettingDate.zoneChoiseByDriver = settings.getString(TetATetSettingDate.zoneChoiseByDriver_key, "");
        TetATetSettingDate.zoneOnlyByDriver = settings.getString(TetATetSettingDate.zoneOnlyByDriver_key, "");
        TetATetSettingDate.minGPSaccuracy = settings.getString(TetATetSettingDate.minGPSaccuracy_key, "");
        TetATetSettingDate.pay_by_order_day = settings.getString(TetATetSettingDate.pay_by_order_day_key, "");
        TetATetSettingDate.pay_by_order_night = settings.getString(TetATetSettingDate.pay_by_order_night_key, "");
        TetATetSettingDate.pay_by_staf = settings.getString(TetATetSettingDate.pay_by_staf_key, "");
        TetATetSettingDate.pay_by_hour = settings.getString(TetATetSettingDate.pay_by_hour_key, "");
        TetATetSettingDate.clear_without_GPS = settings.getString(TetATetSettingDate.clear_without_GPS__key,"");
        TetATetSettingDate.pay_by_hour = settings.getString(TetATetSettingDate.pay_by_hour_key, "");
        TetATetSettingDate.deliveryCarPrice = settings.getString(TetATetSettingDate.deliveryCarPrice_key,"");
        TetATetSettingDate.occupacyPrice = settings.getString(TetATetSettingDate.occupacyPrice_key,"");
        TetATetSettingDate.PriceMinute = settings.getString(TetATetSettingDate.PriceMinute_key,"");
        TetATetSettingDate.dsCity = settings.getString(TetATetSettingDate.dsCity_key,"");
        TetATetSettingDate.if_minimal_payment = settings.getString(TetATetSettingDate.if_minimal_payment_key,"");
        TetATetSettingDate.anroid_setting_hash = settings.getString(TetATetSettingDate.anroid_setting_hash_key,"");
        TetATetSettingDate.DS_SETING_VERS_VALUE = TetATetSettingDate.anroid_setting_hash;
        Log.e("setTetATetSettingDateDS","OK");
    }
}
