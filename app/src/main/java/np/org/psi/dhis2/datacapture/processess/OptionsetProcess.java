package np.org.psi.dhis2.datacapture.processess;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import np.org.psi.dhis2.datacapture.database.OptionSets;
import np.org.psi.dhis2.datacapture.database.Options;
import np.org.psi.dhis2.datacapture.essentials.Constant;
import np.org.psi.dhis2.datacapture.services.HTTPClient;

/**
 * Created by utsav on 3/9/2016.
 */
public class OptionsetProcess {
    private static final String OPTIONSET_URL = "optionSets.json?paging=false&fields=:all&filter=name:eq:";
    private static final String TAG = "Process.OptionSet";
    private static final String[] OPTIONSETS = {"NP%20-%20Activity%20Type"};

    public static void fetchDataSet(Context context, String creds) {
        Log.i(TAG, "FETCHING DATASET");
        String url = Constant.API_URL;
        for(int i = 0; i<OPTIONSETS.length; i++)
        {
            String Report_url = url + "/" + OPTIONSET_URL + OPTIONSETS[i];
            String response = HTTPClient.get(Report_url, creds);
            storeDataset(context, response);
        }
    }

    public static void storeDataset(Context context, String response)
    {
        try {
            JSONObject dataSets = new JSONObject(response);
            JSONArray array = new JSONArray(dataSets.getString("optionSets"));
            JSONObject osObj = array.getJSONObject(0);
            OptionSets Os = new OptionSets(context);
            Options optns = new Options(context);
            Os.deleteOptionset();
            optns.deleteOption();
            Os.saveOptionSet(osObj.has("id") ? osObj.getString("id") : "", osObj.has("name") ? osObj.getString("name") : "", osObj.has("displayName") ? osObj.getString("displayName") : "", osObj.has("code") ? osObj.getString("code") : "");
            JSONArray optnArray = new JSONArray(osObj.getString("options"));
            for(int j = 0; j<optnArray.length();j++)
            {
                JSONObject obj = optnArray.getJSONObject(j);
                optns.saveOptions(obj.getString("id"), obj.getString("name"), obj.getString("code"), osObj.getString("id"));
            }
        } catch (Exception e) { e.printStackTrace();}
    }
}
