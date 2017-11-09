package np.org.psi.dhis2.datacapture.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.adapter.DPOReportAdapter;
import np.org.psi.dhis2.datacapture.database.DPOWeeklys;
import np.org.psi.dhis2.datacapture.objects.DPOWeekly;

/**
 * A simple {@link Fragment} subclass.
 */
public class DPOListFragment extends Fragment {

    public DPOListFragment() { }
    private List<DPOWeekly> dpoWeekly = new ArrayList<>();

    Fragment fragment = null;
    FloatingActionButton FAB;
    String title;
    private RecyclerView recyclerView;
    private DPOReportAdapter drAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dpolist, container, false);
        setHasOptionsMenu(false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        drAdapter = new DPOReportAdapter(dpoWeekly);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(drAdapter);

        DPOWeeklys dws = new DPOWeeklys(getActivity().getApplicationContext());
        ArrayList<DPOWeekly> report = dws.getAllDPOWeeklys();

        for(Integer i = 0; i<report.toArray().length; i++) {
            dpoWeekly.add(report.get(i));
        }
        drAdapter.notifyDataSetChanged();

        FAB = (FloatingActionButton) rootView.findViewById(R.id.fab_dpoWeekly);
        FAB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fragment = new DPOWeeklyFragment();
                title = "DPO Weekly";
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();

            }
        });

        return rootView;
    }

}
