package np.org.psi.dhis2.datacapture.ui;

import android.accounts.AccountAuthenticatorActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.essentials.Constant;
import np.org.psi.dhis2.datacapture.processess.DashboardProcess;
import np.org.psi.dhis2.datacapture.processess.DatasetProcess;
import np.org.psi.dhis2.datacapture.processess.LoginProcess;
import np.org.psi.dhis2.datacapture.processess.OptionsetProcess;
import np.org.psi.dhis2.datacapture.processess.OrgUnitProcess;
import np.org.psi.dhis2.datacapture.services.HTTPClient;
import np.org.psi.dhis2.datacapture.services.NetworkUtils;
import np.org.psi.dhis2.datacapture.ui.fragments.SpinnerFragment;

public class LoginActivity extends AccountAuthenticatorActivity {

    public static final String PARAM_TOKEN_TYPE = "basic.token";
    public static final String CREDENTIALS = "creds";
    public static final String SERVER = "server";
    public static final String TAG = "activity.LoginActivity";
    public static final String USERNAME = "username";
    BroadcastReceiver receiver;
    private ProgressDialog progress;
    EditText input_username, input_password;
    Button Login;
    private ProgressBar spinner;
    private Context context;
    String url = Constant.USER_ACCOUNT, creds, pair;
    LinearLayout llayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        llayout = (LinearLayout) findViewById(R.id.LoginBox);
        input_username = (EditText)findViewById(R.id.input_username);
        input_password = (EditText)findViewById(R.id.input_password);
        Login = (Button)findViewById(R.id.submit);
        spinner.setVisibility(View.GONE);
        progress = new ProgressDialog(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean proceed = loginValidate();
                if (proceed == true) {
                    String username = input_username.getText().toString();
                    String password = input_password.getText().toString();
                    pair = String.format("%s:%s",username,password);
                    creds = Base64.encodeToString(pair.getBytes(), 0x2);
                    boolean validUser = isUser(creds);
                    Log.i(TAG, String.valueOf(validUser));
                    if(validUser) {
                        new fetchData().execute();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Username & Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean loginValidate() {
        String username = input_username.getText().toString();
        String password = input_password.getText().toString();
        if(username.equals("") || password.equals(""))
        {
            input_username.setBackgroundColor(Color.RED);
            input_password.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isUser(String creds){
        SpinnerFragment mSpinnerFragment;
        //String pair = String.format("%s:%s",input_username.getText().toString(),input_password.getText().toString());
        if(NetworkUtils.checkConnection(this) == true) {
            String url = Constant.USER_ACCOUNT;
            Log.i(TAG, creds);
            String response = HTTPClient.get(url, creds);
            try{
                JSONObject jobject = new JSONObject(response);
                Log.i("name",String.valueOf(jobject.getString("firstName")));
                if(jobject.has("firstName")) {
                    return true;
                }
                else return false;
            } catch (JSONException e) { e.printStackTrace(); }
            return false;
        }
        Toast.makeText(this, "No Internet Connectivity",Toast.LENGTH_SHORT).show();
        return false;
    }

    private class fetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            llayout.setVisibility(View.GONE);
            Login.setVisibility(View.GONE);
            progress.setTitle("Logging In");
            progress.setMessage("Please Wait!! We are fetching Data");
            progress.show();
            //spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String response = HTTPClient.get(url, creds);
            LoginProcess logProcess = new LoginProcess();
            logProcess.storeUser(LoginActivity.this, creds, response);
            OrgUnitProcess ou = new OrgUnitProcess();
            DatasetProcess ds = new DatasetProcess();
            OptionsetProcess os = new OptionsetProcess();
            DashboardProcess dp = new DashboardProcess();
            ou.fetchOrgUnit(LoginActivity.this, creds);
            ds.fetchDataSet(LoginActivity.this, creds);
            os.fetchDataSet(LoginActivity.this, creds);
            dp.fetchDashboard(LoginActivity.this, creds);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //spinner.setVisibility(View.GONE);
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
