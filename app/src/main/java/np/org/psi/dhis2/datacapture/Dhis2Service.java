package np.org.psi.dhis2.datacapture;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import np.org.psi.dhis2.datacapture.processess.LoginProcess;

public class Dhis2Service extends Service {
    public static final String METHOD = "method";
    public static final String METHOD_LOGIN_USER = "loginUser";
    private ExecutorService exec;
    private ArrayList<Runnable> tasks;
    private String TAG = "services.Dhis2Service";

    public void onCreate() {
        super.onCreate();
        exec = Executors.newFixedThreadPool(0x3);
        tasks = new ArrayList();
        Log.i(TAG, "onCreate");
    }

    public int onStartCommand (Intent intent, int flags, int startId) {
        Log.i(TAG,"Service Started");
        Bundle extras = intent.getExtras();
        runMethod(extras,this);
        //Log.i(TAG,"Process passed");
        return 0x3;
    }

    public void onDestroy() {
        super.onDestroy();
        exec.shutdown();
        Log.i(TAG, "onDestroy()");
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private void runMethod(Bundle extras, Context context) {
        Log.i(TAG, "runMethod");
        if((extras == null) || (extras.getString("method") == null)) {
            return;
        }
        String method = extras.getString("method");
        Log.i(TAG, method);
        if(method.equals("loginUser")) {
            String username = extras.getString("username");
            String creds = extras.getString("creds");
            LoginProcess.loginUser(context, creds, username);
        }
    }

    private void onTaskFinished(Runnable obj) {
        tasks.remove(obj);
        if(tasks.size() == 0) {
            stopSelf();
        }
    }

}
