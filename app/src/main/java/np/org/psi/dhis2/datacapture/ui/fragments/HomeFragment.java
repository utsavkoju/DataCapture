package np.org.psi.dhis2.datacapture.ui.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.database.Dashboards;
import np.org.psi.dhis2.datacapture.services.HTTPClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public HomeFragment() { }
    private FragmentTabHost tabHost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(false);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        tabHost = (FragmentTabHost) rootView.findViewById(R.id.testtabhost1);
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_home);

        Dashboards dbs = new Dashboards(getActivity().getApplicationContext());
        List<String> dashs = dbs.getDashboard();
        for(int i = 0; i<dashs.size(); i++)
        {
            Bundle bundle = new Bundle();
            bundle.putString("name",dashs.get(i));
            tabHost.addTab(tabHost.newTabSpec(dashs.get(i)).setIndicator(dashs.get(i)), DashboardFragment.class, bundle);
        }

        //tabHost.setCurrentTab(0);

        return tabHost;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_sync).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class fetchDashboard extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            HTTPClient httpClient = new HTTPClient();

            return null;
        }
    }

}
