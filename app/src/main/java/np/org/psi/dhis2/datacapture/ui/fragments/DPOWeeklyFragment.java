package np.org.psi.dhis2.datacapture.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
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
import np.org.psi.dhis2.datacapture.database.DPOWeeklys;
import np.org.psi.dhis2.datacapture.database.Datasets_OrgUnits;
import np.org.psi.dhis2.datacapture.essentials.Constant;
import np.org.psi.dhis2.datacapture.objects.OrgUnit;

public class DPOWeeklyFragment extends Fragment {
    public DPOWeeklyFragment() { }

    Spinner Spin_orgUnit, Spin_period;
    String title;
    EditText input_contact, input_referral, input_service;
    Button Submit;
    private static final String module = "NP WHP DPO Weekly";
    private static final String TAG = "DPO WEEKLY";
    Fragment fragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dpoweekly, container, false);

        final Datasets_OrgUnits dsorgUnit = new Datasets_OrgUnits(getActivity().getApplicationContext());

        Spin_orgUnit = (Spinner) rootView.findViewById(R.id.input_orgUnit);
        Spin_period = (Spinner) rootView.findViewById(R.id.input_weekNo);
        input_contact = (EditText) rootView.findViewById(R.id.input_contact);
        input_referral = (EditText) rootView.findViewById(R.id.input_referral);
        input_service = (EditText) rootView.findViewById(R.id.input_service);
        Submit = (Button) rootView.findViewById(R.id.btn_save);

        //Orgunit
        List<OrgUnit> ou = dsorgUnit.getOrgUnit(module);
        ArrayAdapter<OrgUnit> ouAdapter = new ArrayAdapter<OrgUnit>(getActivity(), android.R.layout.simple_spinner_item, ou);
        ouAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spin_orgUnit.setAdapter(ouAdapter);

        //Period
        ArrayList<String> week = new ArrayList<String>();
        Integer currentWeek = new GregorianCalendar().get(Calendar.WEEK_OF_YEAR);
        for (int i = 1; i <= currentWeek; i++) {
            ArrayList<String> dates = getStartEndOFWeek(i, Integer.parseInt(Constant.YEAR));
            week.add("Week: " +Integer.toString(i)+"  ( "+dates.get(0)+" - "+dates.get(1)+" )");
        }
        ArrayAdapter<String> pAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, week);
        pAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spin_period.setAdapter(pAdapter);
        Spin_period.setSelection(currentWeek - 1);

        Submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String orgUnitId, prd, contact, referral, insertion;
                orgUnitId = ((OrgUnit) Spin_orgUnit.getSelectedItem()).getId();
                prd = String.valueOf(Spin_period.getSelectedItemId() + 1);
                contact = input_contact.getText().toString();
                referral = input_referral.getText().toString();
                insertion = input_service.getText().toString();

                DPOWeeklys dw = new DPOWeeklys(getActivity().getApplicationContext());
                Boolean Status = dw.saveDPOWeekly(orgUnitId, prd, contact, referral, insertion);
                if(Status == true) {
                    Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                    fragment = new DPOListFragment();
                    title = "DPO Weekly Report List";
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();
                }
                Toast.makeText(getActivity(), "Error while Saving", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
    ArrayList<String> getStartEndOFWeek(int enterWeek, int enterYear){
        ArrayList<String> dates = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, enterWeek);
        calendar.set(Calendar.YEAR, enterYear);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // PST`
        Date startDate = calendar.getTime();
        String startDateInStr = formatter.format(startDate);
        dates.add(0, startDateInStr);

        calendar.add(Calendar.DATE, 6);
        Date enddate = calendar.getTime();
        String endDaString = formatter.format(enddate);
        dates.add(1,endDaString);
        return dates;
    }
}
