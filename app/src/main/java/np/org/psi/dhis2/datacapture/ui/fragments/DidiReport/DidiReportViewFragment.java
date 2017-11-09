package np.org.psi.dhis2.datacapture.ui.fragments.DidiReport;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.database.DidiReports;

/**
 * A simple {@link Fragment} subclass.
 */
public class DidiReportViewFragment extends Fragment {

    private String TAG = "DIDIReport";
    private String id;
    TextView orgUnitText, periodText, localityText, wardText, newContactText, prevContactText, okIUCDText, nonIUCDText, okImplantText, nonImplantText, pillsText, depoText, sterillizationText, maText;
    FloatingActionButton edit;
    private static final String module = "NP WHP Didi Report Detail";
    Fragment fragment = null;
    List<String> val;

    public DidiReportViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        id = getArguments().getString("id");

        View rootView = inflater.inflate(R.layout.fragment_didireportview, container, false);

        orgUnitText = (TextView) rootView.findViewById(R.id.didiName);
        periodText = (TextView) rootView.findViewById(R.id.period);
        localityText = (TextView) rootView.findViewById(R.id.LocalityText);
        wardText = (TextView) rootView.findViewById(R.id.wardText);
        newContactText = (TextView) rootView.findViewById(R.id.newContactText);
        prevContactText = (TextView) rootView.findViewById(R.id.oldContactText);
        okIUCDText = (TextView) rootView.findViewById(R.id.okIUCDText);
        nonIUCDText = (TextView) rootView.findViewById(R.id.nonIUCDText);
        okImplantText = (TextView) rootView.findViewById(R.id.okImplantText);
        nonImplantText = (TextView) rootView.findViewById(R.id.nonImplantText);
        pillsText = (TextView) rootView.findViewById(R.id.pillsText);
        depoText = (TextView) rootView.findViewById(R.id.depoText);
        sterillizationText = (TextView) rootView.findViewById(R.id.sterilizeText);
        maText = (TextView) rootView.findViewById(R.id.maText);
        edit = (FloatingActionButton) rootView.findViewById(R.id.edit);

        DidiReports drs = new DidiReports(getActivity().getApplicationContext());
        val = drs.getDidiReport(Integer.parseInt(id));
        orgUnitText.setText(val.get(0));
        periodText.setText(val.get(1));
        localityText.setText(val.get(2));
        wardText.setText(val.get(3));
        newContactText.setText(val.get(4));
        prevContactText.setText(val.get(5));
        okIUCDText.setText(val.get(6));
        nonIUCDText.setText(val.get(7));
        okImplantText.setText(val.get(8));
        nonImplantText.setText(val.get(9));
        pillsText.setText(val.get(10));
        depoText.setText(val.get(11));
        sterillizationText.setText(val.get(12));
        maText.setText(val.get(13));

        edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fragment = new DidiReportFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(id));
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragment.setArguments(bundle);
                fragmentTransaction.commit();

            }
        });

        return rootView;
    }

    public void onBackPressed() {
        fragment = new DidiReportListFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }

}
