package np.org.psi.dhis2.datacapture.essentials;

import java.util.Calendar;

/**
 * Created by utsav on 2/17/2016.
 */
public class Constant {
    public static final int CATEGORY_GRID = 0;
    //public static final String BASIC = "c2FndW5QU0kyMDE1bnA=";

    /** URLs **/
    public static final String SERVER_URL = "https://dev.psi-mis.org";
    public static final String API_URL = SERVER_URL+"/api";
    public static final String API_ME = API_URL+"/me";
    public static final String USER_ACCOUNT = API_ME+"/user-account";
    public static final String USER_ORGUNIT = API_ME+"/organisationUnits";
    public static final String ORGUNIT_URL = API_URL+"/organisationUnits?paging=false&";
    private static Calendar calendar = Calendar.getInstance();
    public static final String YEAR = String.valueOf(calendar.get(Calendar.YEAR));
    public static final String API_DASHBOARD = API_URL+"/dashboards.json?paging=false&fields=id,displayName,dashboardItems[id,type,eventReport[id],eventChart[id]]";
    public static final String API_DASHBOARDITEMS = API_URL+"/dashboardItems";
}
