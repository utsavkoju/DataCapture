package np.org.psi.dhis2.datacapture.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import np.org.psi.dhis2.datacapture.ui.LoginActivity;

/**
 * Created by utsav on 4/6/2016.
 */
public class AuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        LoginActivity authenticator = new LoginActivity();
        return null;
    }
}
