package np.org.psi.dhis2.datacapture.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.adapter.ListViewAdapter;
import np.org.psi.dhis2.datacapture.objects.ListDetail;

public class SettingsActivity extends AppCompatActivity{
    private ListView lViews;
    private ListViewAdapter lvAdapter;
    private String TAG = "Settings";
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] titles = this.getResources().getStringArray(R.array.settings);
        String[] details = this.getResources().getStringArray(R.array.settings_detail);
        ArrayList<ListDetail> lDetail = new ArrayList<ListDetail>();


        for(int i = 0; i<titles.length; i++)
        {
            lDetail.add(new ListDetail(titles[i],details[i]));
        }

        lvAdapter = new ListViewAdapter(this, lDetail);
        lViews = (ListView) findViewById(R.id.setting_list);
        lViews.setAdapter(lvAdapter);

        lViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.prompt_pin, null);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                        alertDialog.setView(promptsView);

                        final EditText input_pin = (EditText) promptsView.findViewById(R.id.input_pin);
                        SharedPreferences sp = getSharedPreferences("user", Activity.MODE_PRIVATE);
                        int pin = sp.getInt("pin", -1);
                        if(sp.contains("pin") && pin != 0) {
                            input_pin.setText(String.valueOf(pin));
                        }
                        alertDialog.setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                SharedPreferences sp = getSharedPreferences("user", Activity.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sp.edit();
                                                if(input_pin.getText().toString() == null || input_pin.getText().toString() == "")
                                                {

                                                }
                                                else {
                                                    editor.putInt("pin", Integer.parseInt(input_pin.getText().toString()));
                                                    editor.commit();
                                                }
                                            }
                                        }) .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog adialog = alertDialog.create();
                        adialog.show();
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    default:
                        break;
                }

            }
        });
    }


}
