package np.org.psi.dhis2.datacapture.ui.fragments.BCC;

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
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.adapter.BccSummaryAdapter;
import np.org.psi.dhis2.datacapture.database.BccSummarys;
import np.org.psi.dhis2.datacapture.objects.BccSummary;

public class BCCListFragment extends Fragment {
    public BCCListFragment() { }
    private List<BccSummary> bccReport = new ArrayList<BccSummary>();

    Fragment fragment = null;
    FloatingActionButton FAB;
    String title;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_bcc_summary_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        BccSummaryAdapter bsAdapter = new BccSummaryAdapter(getActivity().getApplicationContext(), bccReport, getFragmentManager());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ((BccSummaryAdapter) bsAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(bsAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        BccSummarys bs = new BccSummarys(getActivity().getApplicationContext());
        ArrayList<BccSummary> report = bs.getAllBccSummary();

        for(Integer i = 0; i<report.toArray().length; i++)
        {
            bccReport.add(report.get(i));
        }
        bsAdapter.notifyDataSetChanged();

        FAB = (FloatingActionButton) rootView.findViewById(R.id.fab_bccSummary);
        FAB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fragment = new BCCFragment();
                title = "BCC Summary";
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();

            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_sync).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

}