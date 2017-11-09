package np.org.psi.dhis2.datacapture.processess;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import np.org.psi.dhis2.datacapture.database.OrgUnits;
import np.org.psi.dhis2.datacapture.essentials.Constant;
import np.org.psi.dhis2.datacapture.services.HTTPClient;

/**
 * Created by utsav on 2/23/2016.
 */
public class OrgUnitProcess {
    private static final String ORG_UNITS = "organisationUnits";
    private static final String TAG = "Processess.OrgUnit";
    private static final int maxLevel = 8;
    private OrgUnits ounits;

    public static void fetchOrgUnit(Context context, String creds) {
        Log.i(TAG, "Fetching OrgUnits");
        OrgUnits ounits = new OrgUnits(context);
        ounits.deleteOrgUnit();
        String url = Constant.API_ME;
        String response = HTTPClient.get(url, creds);
        Log.i(TAG, response);
        ArrayList<String> base = storeBaseOrgUnit(context, response);
        for(int i = 0; i< base.size(); i++) {
            String parent_Id = base.get(i).toString();
            int level = getLevel(parent_Id, creds);
            int baseLevel = level;
            String child = "children[";
            String bracket = "]";
            while(level < maxLevel) {
                String children = "id,name,shortName,code,level,coordinates,parent[id]";
                for(int j = baseLevel; j<=level; j++) { children = child + children + bracket; }
                String orgUrl = Constant.ORGUNIT_URL+"&fields="+children+"&filter=id:eq:"+parent_Id;
                Log.i(TAG, orgUrl);
                String orgResponse = HTTPClient.get(orgUrl, creds);
                storeOrgUnit(context, orgResponse);
                level = level + 1;
            }
        }
    }

    public static ArrayList<String> storeBaseOrgUnit(Context context, String orgUnit)
    {
        OrgUnits ounits = new OrgUnits(context);
        ounits.deleteOrgUnit();
        ArrayList<String> orgUnitId = new ArrayList<String>();
        try {
            JSONObject obj = new JSONObject(orgUnit);
            JSONArray array = obj.getJSONArray("organisationUnits");
            for(int n = 0; n < array.length(); n++) {
                JSONObject ou = array.getJSONObject(n);
                orgUnitId.add(n, ou.getString("id"));
                ounits.saveOrgUnit(ou.has("id") ? ou.getString("id") : "", ou.has("name") ? ou.getString("name") : "", ou.has("shortName") ? ou.getString("shortName") : "", ou.has("code") ? ou.getString("code") : "", ou.has("level") ? ou.getString("level") : "", ou.has("coordinates") ? ou.getString("coordinates") : "","");
                //ounits.saveOrgUnit(ou.getString("id"), ou.getString("name"), ou.getString("shortName"), ou.getString("code"), ou.getString("level"), "","");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return orgUnitId;
    }

    public static void storeOrgUnit(Context context, String orgUnit)
    {
        try {
            JSONObject ou = new JSONObject();
            JSONObject orgUnits = new JSONObject(orgUnit);
            JSONArray array = new JSONArray(orgUnits.getString("organisationUnits"));
            JSONObject arrayObj = array.getJSONObject(0);
            JSONArray child = arrayObj.getJSONArray("children");
            JSONObject o = null;
            JSONArray arr = null;
            OrgUnits ounits = new OrgUnits(context);

            for(int i = 0; i<child.length(); i++) {
                o = child.getJSONObject(i);
                if(o.has("children")) {
                    arr = o.getJSONArray("children");
                    JSONObject obj1 = null;
                    JSONArray arr1 = null;
                    for(int j = 0; j<arr.length(); j++)
                    {
                        obj1 = arr.getJSONObject(j);
                        if(obj1.has("children")) {
                            arr1 = obj1.getJSONArray("children");
                            JSONObject obj2 = null;
                            JSONArray arr2 = null;
                            for (int k = 0; k < arr1.length(); k++) {
                                obj2 = arr1.getJSONObject(k);
                                if(obj2.has("children")) {
                                    arr2 = obj2.getJSONArray("children");
                                    JSONObject obj3 = null;
                                    JSONArray arr3 = null;
                                    for(int l = 0; l<arr2.length(); l++) {
                                        obj3 = arr2.getJSONObject(l);
                                        if(obj3.has("children")) {
                                            arr3 = obj3.getJSONArray("children");
                                            JSONObject objfinal = null;
                                            for(int m = 0; m < arr3.length(); m++){
                                                objfinal = arr3.getJSONObject(m);
                                                if (objfinal.has("name") && objfinal.has("id")) {
                                                    save_OrgUnit(context, objfinal);
                                                }
                                            }
                                        }
                                        else {
                                            save_OrgUnit(context, obj3);
                                            continue;
                                        }
                                    }
                                }
                                else {
                                    save_OrgUnit(context, obj2);
                                    continue;
                                }
                            }
                        }
                        else {
                            save_OrgUnit(context, obj1);
                            continue;
                        }
                    }
                }
                else {
                    save_OrgUnit(context, o);
                    continue;
                }
            }

        }  catch (Exception e) { e.printStackTrace();}
    }

    public static void save_OrgUnit(Context context, JSONObject obj) {
        OrgUnits ounits = new OrgUnits(context);
        if (obj.has("name") && obj.has("id")) {
            try {
                Log.i(TAG, obj.getString("id")+"-"+obj.getString("name"));
                String parent = obj.getString("parent");
                JSONObject par = new JSONObject(parent);
                Log.e(TAG, obj.getString("id") + "-" + obj.getString("name"));
                ounits.saveOrgUnit(obj.has("id") ? obj.getString("id") : "", obj.has("name") ? obj.getString("name") : "", obj.has("shortName") ? obj.getString("shortName") : "", obj.has("code") ? obj.getString("code") : "", obj.has("level") ? obj.getString("level") : "", obj.has("coordinates") ? obj.getString("coordinates") : "", par.has("id") ? par.getString("id") : "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void storeOrgUnit_bak(Context context, String orgUnit, int base, int level)
    {
        try{
            JSONObject ou = new JSONObject();
            JSONObject orgUnits = new JSONObject(orgUnit);
            JSONArray array = new JSONArray(orgUnits.getString("organisationUnits"));
            for (int i = base; i <= level; i++) {
                for (int j = 0; j < array.length(); j++) {
                    ou = array.getJSONObject(j);
                    if (i == level) {
                        JSONArray result = new JSONArray(ou.getString("children"));
                        for (int x = 0; x < result.length(); x++) {
                            JSONObject jobject = result.getJSONObject(x);
                            String parent = jobject.getString("parent");
                            JSONObject par = new JSONObject(parent);
                            OrgUnits ounits = new OrgUnits(context);
                            if (jobject.has("name") && jobject.has("id"))
                                ounits.saveOrgUnit(jobject.has("id") ? jobject.getString("id") : "", jobject.has("name") ? jobject.getString("name") : "", jobject.has("shortName") ? jobject.getString("shortName") : "", jobject.has("code") ? jobject.getString("code") : "", jobject.has("level") ? jobject.getString("level") : "", jobject.has("coordinates") ? jobject.getString("coordinates") : "", par.has("id") ? par.getString("id") : "");
                        }
                    } else {
                        array = new JSONArray(ou.getString("children"));
                    }
                }
            }
            //Log.e(TAG, ou.getString("name"));
        } catch (Exception e) { e.printStackTrace();}
    }

    public static int getLevel(String id, String creds) {
        int level = 8;
        String url = Constant.ORGUNIT_URL+"&fields=[level]&filter=id:eq:"+id;
        String response = HTTPClient.get(url, creds);
        try{
            JSONObject obj = new JSONObject(response);
            JSONArray arr = obj.getJSONArray("organisationUnits");
            JSONObject lvl = arr.getJSONObject(0);
            level = Integer.parseInt(lvl.getString("level"));
        } catch (JSONException e) { e.printStackTrace(); }

        return level;
    }
}
