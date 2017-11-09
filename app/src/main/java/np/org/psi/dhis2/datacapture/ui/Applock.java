package np.org.psi.dhis2.datacapture.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import np.org.psi.dhis2.datacapture.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Applock extends Activity {

    EditText input_pin;
    Button submit;
    String pinVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_applock);

        input_pin = (EditText) findViewById(R.id.input_pin);
        submit = (Button) findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                pinVal = input_pin.getText().toString();
                SharedPreferences sp = getSharedPreferences("user", Activity.MODE_PRIVATE);
                int pin = sp.getInt("pin", -1);
                if(pin == Integer.parseInt(pinVal)){
                    Intent i = new Intent(Applock.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(Applock.this, "Entered PIN is Wrong !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
