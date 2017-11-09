package np.org.psi.dhis2.datacapture.processess;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import np.org.psi.dhis2.datacapture.database.DashboardItems;
import np.org.psi.dhis2.datacapture.database.Dashboards;
import np.org.psi.dhis2.datacapture.essentials.Constant;
import np.org.psi.dhis2.datacapture.services.HTTPClient;

/**
 * Created by utsav on 5/12/2016.
 */
public class DashboardProcess {
    private static final String TAG = "Processess.Dashboard";

    public static void fetchDashboard(Context context, String creds) {
        String url = Constant.API_DASHBOARD;
        String response = HTTPClient.get(url, creds);
        storeDash(context, response);
    }

    public static void storeDash(Context context, String response) {
        try {
            String chart_id = null;
            Dashboards dbs = new Dashboards(context);
            dbs.deleteDashboard();
            DashboardItems dbis = new DashboardItems(context);
            dbis.deleteDashboardItems();
            JSONObject resObj = new JSONObject(response);
            JSONArray array = new JSONArray(resObj.getString("dashboards"));
            for(int i = 0; i<array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                dbs.saveDash(obj.getString("id"), obj.getString("displayName"));
                if(obj.has("dashboardItems")) {
                    JSONArray items = obj.getJSONArray("dashboardItems");
                    for(int j = 0; j<items.length(); j++) {
                        JSONObject item = items.getJSONObject(j);
                        if(item.has("eventChart")){
                            JSONObject eChart = new JSONObject(item.getString("eventChart"));
                            chart_id = eChart.getString("id");
                        }
                        dbis.saveDashItems(item.getString("id"), obj.getString("id"), item.getString("type"), chart_id);
                    }
                }
            }
        }
        catch (Exception e ) { e.printStackTrace(); }
    }
}
