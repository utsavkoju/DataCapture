package np.org.psi.dhis2.datacapture.ui.fragments.DidiReport;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.database.Datasets_OrgUnits;
import np.org.psi.dhis2.datacapture.database.DidiReports;
import np.org.psi.dhis2.datacapture.essentials.Constant;
import np.org.psi.dhis2.datacapture.objects.OrgUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class DidiReportFragment extends Fragment {

    public DidiReportFragment() { }

    Spinner Spin_orgUnit, Spin_prd;
    EditText Input_locality, Input_wardNo, Input_newContact, Input_previousContact, Input_okIUCD, Input_nonNetworkIUCD, Input_okImplant, Input_nonNetworkImplant, Input_pills, Input_depo, Input_sterilization, Input_ma;
    Button submit;
    private static final String module = "NP WHP Didi Report";
    private static final String TAG = "DIDI REPORT";
    private String title = "Didi Report";
    Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView =  inflater.inflate(R.layout.fragment_didireport, container, false);
        // set the toolbar title

        final Datasets_OrgUnits dsorgUnit = new Datasets_OrgUnits(getActivity().getApplicationContext());

        Spin_orgUnit = (Spinner) rootView.findViewById(R.id.input_orgUnit);
        Spin_prd = (Spinner) rootView.findViewById(R.id.input_weekNo);
        Input_locality = (EditText) rootView.findViewById(R.id.input_locality);
        Input_wardNo = (EditText) rootView.findViewById(R.id.input_wardNo);
        Input_newContact = (EditText) rootView.findViewById(R.id.input_newContact);
        Input_previousContact = (EditText) rootView.findViewById(R.id.input_prevContact);
        Input_okIUCD = (EditText) rootView.findViewById(R.id.input_refOkIUCD);
        Input_nonNetworkIUCD = (EditText) rootView.findViewById(R.id.input_refNonIUCD);
        Input_okImplant = (EditText) rootView.findViewById(R.id.input_refOkImplant);
        Input_nonNetworkImplant = (EditText) rootView.findViewById(R.id.input_refNonImplant);
        Input_pills = (EditText) rootView.findViewById(R.id.input_refPills);
        Input_depo = (EditText) rootView.findViewById(R.id.input_refDepo);
        Input_sterilization = (EditText) rootView.findViewById(R.id.input_refSterilize);
        Input_ma = (EditText) rootView.findViewById(R.id.input_refMedAbort);
        submit = (Button) rootView.findViewById(R.id.btn_save);

        //Orgunit
        List<OrgUnit> ou = dsorgUnit.getOrgUnit(module);
        ArrayAdapter<OrgUnit> ouAdapter = new ArrayAdapter<OrgUnit>(getActivity(), android.R.layout.simple_spinner_item, ou);
        ouAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spin_orgUnit.setAdapter(ouAdapter);

        //Period
        ArrayList<String> week = new ArrayList<String>();
        Integer currentWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
        for (int i = 1; i <= (currentWeek-1); i++) {
            ArrayList<String> dates = getStartEndOFWeek(i, Integer.parseInt(Constant.YEAR));
            week.add("Week: " +Integer.toString(i)+"  ( "+dates.get(0)+" - "+dates.get(1)+" )");
        }
        ArrayAdapter<String> pAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, week);
        pAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spin_prd.setAdapter(pAdapter);
        Spin_prd.setSelection(currentWeek-2);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String orgUnitId, prd, locality, wardNo, newContact, prevContact, okIUCD, nonNetworkIUCD, okImplant, nonNetworkImplant, pills, depo, sterilization, ma = "";
                orgUnitId = ((OrgUnit) Spin_orgUnit.getSelectedItem()).getId();
                prd = String.valueOf(Spin_prd.getSelectedItemId()+1);
                //Toast.makeText(getActivity(), "Period: " + prd, Toast.LENGTH_SHORT).show();
                locality = Input_locality.getText().toString();
                wardNo = Input_wardNo.getText().toString();
                newContact = Input_newContact.getText().toString();
                prevContact = Input_previousContact.getText().toString();
                okIUCD = Input_okIUCD.getText().toString();
                nonNetworkIUCD = Input_nonNetworkIUCD.getText().toString();
                okImplant = Input_okImplant.getText().toString();
                nonNetworkImplant = Input_nonNetworkImplant.getText().toString();
                pills = Input_pills.getText().toString();
                depo = Input_depo.getText().toString();
                sterilization = Input_sterilization.getText().toString();
                ma = Input_ma.getText().toString();

                DidiReports dr = new DidiReports(getActivity().getApplicationContext());
                Boolean Status = dr.saveDidiReport(orgUnitId, prd, locality, wardNo, newContact, prevContact, okIUCD, nonNetworkIUCD, okImplant, nonNetworkImplant, pills, depo, sterilization, ma);
                if(Status == true) {
                    Toast.makeText(getActivity(), "Saved Successfully",Toast.LENGTH_SHORT).show();
                    fragment = new DidiReportListFragment();
                    title = "DIDI Report List";
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();
                }
                else { Toast.makeText(getActivity(), "Error while Saving",Toast.LENGTH_SHORT).show(); }

            }
        });

        return rootView;
    }

    ArrayList<String> getStartEndOFWeek(int enterWeek, int enterYear){
        ArrayList<String> dates = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, enterWeek);
        calendar.set(Calendar.YEAR, enterYear);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // PST`
        Date startDate = calendar.getTime();
        String startDateInStr = formatter.format(startDate);
        dates.add(0,startDateInStr);

        calendar.add(Calendar.DATE, 6);
        Date enddate = calendar.getTime();
        String endDaString = formatter.format(enddate);
        dates.add(1,endDaString);
        return dates;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_sync).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}
