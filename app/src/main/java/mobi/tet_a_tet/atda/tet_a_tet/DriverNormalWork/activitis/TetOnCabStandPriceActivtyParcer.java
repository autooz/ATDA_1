package mobi.tet_a_tet.atda.tet_a_tet.DriverNormalWork.activitis;

import java.util.StringTokenizer;

import mobi.tet_a_tet.atda.tet_a_tet.dates.TetDriverData;
import mobi.tet_a_tet.atda.tet_a_tet.utils.GetAnyStringFromTetSettingDate;

/**
 * Created by oleg on 12.10.15.
 */
public class TetOnCabStandPriceActivtyParcer {

    public TetOnCabStandPriceActivtyParcer() {
    }

    public void setParams(StringTokenizer st) {
        TetDriverData.drvstate= st.nextToken();
        TetDriverData.drvstopid = st.nextToken();
        String getStopName = "zone_stop_"+ TetDriverData.drvstopid;
        GetAnyStringFromTetSettingDate getStringFromTetSetingDate = new GetAnyStringFromTetSettingDate();
        TetDriverData.current_zone = getStringFromTetSetingDate.GetValue(getStopName);
        TetDriverData.carClassNameTaxi = st.nextToken();
        TetDriverData.stringOrderBetweenTaxi = st.nextToken();
        TetDriverData.allCarOnstop = st.nextToken();
        if (st.hasMoreElements()){
            TetDriverData.carClassNameSpecialType = st.nextToken();
            TetDriverData.stringOrderBetweenSpecialType =st.nextToken();
        }
    }


}
