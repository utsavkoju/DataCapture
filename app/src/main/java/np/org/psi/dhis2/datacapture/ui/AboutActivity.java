package np.org.psi.dhis2.datacapture.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.adapter.ListViewAdapter;
import np.org.psi.dhis2.datacapture.objects.ListDetail;

public class AboutActivity extends AppCompatActivity {

    private ListView lViews;
    private ListViewAdapter lvAdapter;
    private String TAG = "About";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] titles = this.getResources().getStringArray(R.array.about);
        String[] details = this.getResources().getStringArray(R.array.about_detail);
        ArrayList<ListDetail> lDetail = new ArrayList<ListDetail>();


        for(int i = 0; i<titles.length; i++)
        {
            lDetail.add(new ListDetail(titles[i],details[i]));
        }

        lvAdapter = new ListViewAdapter(this, lDetail);
        lViews = (ListView) findViewById(R.id.about_list);
        lViews.setAdapter(lvAdapter);

        lViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        Toast.makeText(AboutActivity.this, "VERSION : 1.0", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        String url = "http://www.psi.org.np";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        break;
                    case 2:
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[] {"utsavkoju@psi.org.np"});
                        email.putExtra(Intent.EXTRA_SUBJECT,"FEEDBACK: ");
                        email.setType("message/rfc822");
                        startActivityForResult(Intent.createChooser(email, "Choose an Email Client"), 1);
                        break;
                    default:
                        break;
                }

            }
        });
    }

}
