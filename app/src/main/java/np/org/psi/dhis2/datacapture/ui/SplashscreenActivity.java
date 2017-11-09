package np.org.psi.dhis2.datacapture.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.database.Users;

public class SplashscreenActivity extends Activity {

    private static final int SPLASH_TIME_OUT = 2000;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Users usr = new Users(context);
                if(usr.userExist() == true) {
                    SharedPreferences sp = getSharedPreferences("user", Activity.MODE_PRIVATE);
                    int pin = sp.getInt("pin", -1);
                    if(sp.contains("pin") && pin != 0 ) {
                        Intent i = new Intent(SplashscreenActivity.this, Applock.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplashscreenActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                else {
                    Intent i = new Intent(SplashscreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
