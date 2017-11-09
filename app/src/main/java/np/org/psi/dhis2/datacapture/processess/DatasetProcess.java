package np.org.psi.dhis2.datacapture.processess;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import np.org.psi.dhis2.datacapture.database.Datasets;
import np.org.psi.dhis2.datacapture.database.Datasets_OrgUnits;
import np.org.psi.dhis2.datacapture.essentials.Constant;
import np.org.psi.dhis2.datacapture.services.HTTPClient;

/**
 * Created by utsav on 3/8/2016.
 */
public class DatasetProcess {
    private static final String DATASETS = "dataSets.json?paging=false&fields=[id,name,shortName,periodType,dataEntryForm],organisationUnits[id]&filter=name:eq:";
    private static final String TAG = "Processess.Dataset";
    private static final String[] dataSets = {"NP%20WHP%20BCC%20Program%20Summary", "NP%20WHP%20Didi%20Report", "NP%20WHP%20DPO%20Weekly"};

    public static void fetchDataSet(Context context, String creds) {
        Log.i(TAG, "FETCHING DATASET");
        String url = Constant.API_URL;
        for(int i = 0; i<dataSets.length; i++)
        {
            String Report_url = url + "/" + DATASETS + dataSets[i];
            String response = HTTPClient.get(Report_url, creds);
            storeDataset(context, response);
        }
    }

    public static void storeDataset(Context context, String response)
    {
        try {
            JSONObject dataSets = new JSONObject(response);
            JSONArray array = new JSONArray(dataSets.getString("dataSets"));
            JSONObject dsObj = array.getJSONObject(0);
            Datasets ds = new Datasets(context);
            Datasets_OrgUnits dou = new Datasets_OrgUnits(context);
            ds.deleteDataset();
            dou.deleteDataset_orgUnit();
            ds.saveDataset(dsObj.has("id")?dsObj.getString("id"):"",dsObj.has("name")?dsObj.getString("name"):"",dsObj.has("shortName")?dsObj.getString("shortName"):"",dsObj.has("periodType")?dsObj.getString("periodType"):"",dsObj.has("dataEntryForm")?dsObj.getString("dataEntryForm"):"");
            JSONArray douArray = new JSONArray(dsObj.getString("organisationUnits"));
            for(int j = 0; j<douArray.length();j++)
            {
                JSONObject obj = douArray.getJSONObject(j);
                dou.saveDataset_orgUnit(dsObj.getString("id"),obj.getString("id"));
            }
        } catch (Exception e) { e.printStackTrace();}
    }


}
