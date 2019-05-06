package mobi.tet_a_tet.atda.tet_a_tet.utils;

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetATetSettingDate;

/**
 * Created by oleg on 05.10.15.
 */
public class GetAnyStringFromTetSettingDate {

    public String value;

    public GetAnyStringFromTetSettingDate(){

    }
    public String GetValue(String val){

        try {
            value = (String) TetATetSettingDate.class.getField(val).get(null);
        } catch (NoSuchFieldException x) {
            x.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return value;
    }
}
