package np.org.psi.dhis2.datacapture.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.database.Datasets;
import np.org.psi.dhis2.datacapture.database.OptionSets;
import np.org.psi.dhis2.datacapture.database.OrgUnits;
import np.org.psi.dhis2.datacapture.database.Users;
import np.org.psi.dhis2.datacapture.ui.fragments.BCC.BCCListFragment;
import np.org.psi.dhis2.datacapture.ui.fragments.DidiReport.DidiReportListFragment;
import np.org.psi.dhis2.datacapture.ui.fragments.GISFragment;
import np.org.psi.dhis2.datacapture.ui.fragments.HomeFragment;
import np.org.psi.dhis2.datacapture.ui.fragments.ProviderFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = null;
    String title;
    private Context context;
    TextView user_name;
    private ProgressDialog progress;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progress = new ProgressDialog(this);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        Users usr = new Users(this);
        HashMap<String, String> user = usr.getUserDetails();

        user_name = (TextView) header.findViewById(R.id.header_title_name);
        user_name.setText(user.get("firstName") + " " + user.get("lastName"));


        fragment = new HomeFragment();
        title = "Dashboard";
        loadFragment(fragment, title);

        // set the toolbar title
        getSupportActionBar().setTitle(title);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_sync).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            fragment = new HomeFragment();
            title = "Dashboard";
        } else if (id == R.id.nav_didi_report) {
            fragment = new DidiReportListFragment();
            title = "DIDI Report List";
        } else if (id == R.id.nav_bcc_report) {
            fragment = new BCCListFragment();
            title = "BCC Summary";
        } else if (id == R.id.nav_reports) {
            fragment = new ProviderFragment();
            title = "Provider Performance";
        } else if (id == R.id.nav_navigation) {
            fragment = new GISFragment();
            title = "Outlet Around Me";
        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_about) {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            new clearDetails().execute();
        }

        if(fragment != null) {
            loadFragment(fragment, title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void clearDetails() {
        Users usr = new Users(MainActivity.this);
        OrgUnits ounit = new OrgUnits(MainActivity.this);
        usr.deleteUser();
        ounit.deleteOrgUnit();
    }

    public void loadFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();

        // set the toolbar title
        getSupportActionBar().setTitle(title);
    }

    private class clearDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progress.setTitle("Logging Out");
            progress.setMessage("Please Wait!!");
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Users usr = new Users(MainActivity.this);
            OrgUnits ounit = new OrgUnits(MainActivity.this);
            Datasets ds = new Datasets(MainActivity.this);
            OptionSets os = new OptionSets(MainActivity.this);
            usr.deleteUser();
            ounit.deleteOrgUnit();
            ds.deleteDataset();
            os.deleteOptionset();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}
