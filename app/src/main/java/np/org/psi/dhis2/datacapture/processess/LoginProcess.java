package np.org.psi.dhis2.datacapture.processess;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import np.org.psi.dhis2.datacapture.database.Users;
import np.org.psi.dhis2.datacapture.essentials.Constant;
import np.org.psi.dhis2.datacapture.services.HTTPClient;


public class LoginProcess {

    private static String TAG = "processess.Login";
    public static void loginUser(Context context, String creds, String username) {
        Log.i(TAG, "login User");
        String url = Constant.USER_ACCOUNT;
        String response = HTTPClient.get(url, creds);
        storeUser(context, creds, response);
    }

    public static void storeUser(Context context, String creds, String user)
    {
        try {
            JSONObject objUser = new JSONObject(user);
            Users usr = new Users(context);
            usr.deleteUser();
            usr.saveUser((objUser.has("username"))?objUser.getString("username"):"",creds, (objUser.has("firstName"))?objUser.getString("firstName"):"", (objUser.has("surname"))?objUser.getString("surname"):"", (objUser.has("email"))?objUser.getString("email"):"", (objUser.has("phoneNumber"))?objUser.getString("phoneNumber"):"", (objUser.has("employer"))?objUser.getString("employer"):"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
