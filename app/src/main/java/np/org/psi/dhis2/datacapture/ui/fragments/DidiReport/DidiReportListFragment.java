package np.org.psi.dhis2.datacapture.ui.fragments.DidiReport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.adapter.DidiReportAdapter;
import np.org.psi.dhis2.datacapture.database.DidiReports;
import np.org.psi.dhis2.datacapture.objects.DidiReport;
import np.org.psi.dhis2.datacapture.services.HTTPClient;

public class DidiReportListFragment extends Fragment {
    public DidiReportListFragment() { }
    private List<DidiReport> didiReport = new ArrayList<>();

    Fragment fragment = null;
    FloatingActionButton FAB;
    String title;
    private RecyclerView recyclerView;
    private DidiReportAdapter drAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView =  inflater.inflate(R.layout.fragment_didi_report_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        drAdapter = new DidiReportAdapter(getActivity().getApplicationContext(), didiReport, getFragmentManager());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(drAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        DidiReports drs = new DidiReports(getActivity().getApplicationContext());
        ArrayList<DidiReport> report = drs.getAllDidiReports();

        for(Integer i = 0; i<report.toArray().length; i++)
        {
            didiReport.add(report.get(i));
        }
        drAdapter.notifyDataSetChanged();

        FAB = (FloatingActionButton) rootView.findViewById(R.id.fab_didiReport);
        FAB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fragment = new DidiReportFragment();
                title = "DIDI Report";
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();

            }
        });
        getActivity().invalidateOptionsMenu();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        DidiReports drs = new DidiReports(getActivity().getApplicationContext());
        Integer syncCount = drs.needSync();
        if(syncCount > 0)
            menu.findItem(R.id.action_sync).setVisible(true);
        else
            menu.findItem(R.id.action_sync).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync) {
            Toast.makeText(getActivity().getApplicationContext(), "Sync in Progress", Toast.LENGTH_SHORT).show();
            new postData().execute();
        }
        return super.onOptionsItemSelected(item);
    }

    private class postData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            DidiReports drs = new DidiReports(getActivity().getApplicationContext());
            HTTPClient httpClient = new HTTPClient();
            JsonObject dReports = drs.getAllDidiRpts();
            String server = "https://dev.psi-mis.org/api/dataValueSets";
            String creds = "c2t0YW1yYWthcjpVdHNhdjEyMyo=";
            httpClient.post(server, creds, dReports.toString());
            Boolean status = drs.setStatusSync();
            Log.e("Report", "Success");
            return null;
        }
    }
}
