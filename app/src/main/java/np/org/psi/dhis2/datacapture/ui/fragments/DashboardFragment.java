package np.org.psi.dhis2.datacapture.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.adapter.DashboardAdapter;
import np.org.psi.dhis2.datacapture.database.DashboardItems;
import np.org.psi.dhis2.datacapture.database.Dashboards;
import np.org.psi.dhis2.datacapture.objects.DashboardItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private List<DashboardItem> dashItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private DashboardAdapter dAdapter;
    private String TAG = "Dashboard";

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Bundle bundle = this.getArguments();
        String name = bundle.getString("name");

        Dashboards dbs = new Dashboards(getActivity().getApplicationContext());
        String id = dbs.getId_byName(name);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dAdapter);

        DashboardItems dItems = new DashboardItems(getActivity().getApplicationContext());
        ArrayList<DashboardItem> report = dItems.getItems_by_dashboard_Id(id);
        for(Integer i = 0; i<report.toArray().length; i++) {
            dashItems.add(report.get(i));
            dAdapter = new DashboardAdapter(dashItems);
        }


        return rootView;
    }

}
