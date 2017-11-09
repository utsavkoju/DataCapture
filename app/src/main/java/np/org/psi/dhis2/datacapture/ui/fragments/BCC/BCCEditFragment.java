package np.org.psi.dhis2.datacapture.ui.fragments.BCC;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.database.BccSummarys;
import np.org.psi.dhis2.datacapture.database.Datasets_OrgUnits;
import np.org.psi.dhis2.datacapture.database.OptionSets;
import np.org.psi.dhis2.datacapture.objects.Option;
import np.org.psi.dhis2.datacapture.objects.OrgUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class BCCEditFragment extends Fragment {

    private String TAG = "BCCEditFragment";
    private String id;
    Spinner Spin_orgUnit, Spin_activityType;
    EditText Input_period, Input_reportedBy, Input_tole, Input_male, Input_female, Input_children, Input_referral, Input_pc;
    Button update;
    private static final String module = "NP WHP BCC Program Summary";
    private static final String ACTIVITYTYPE = "NP - Activity Type";
    private Integer mYear, mMonth, mDay;
    private String title = "BCC Summary";
    Fragment fragment = null;
    List<String> val;

    public BCCEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id = getArguments().getString("id");
        //Log.e(TAG, id);
        View rootView =  inflater.inflate(R.layout.fragment_bccedit, container, false);

        setHasOptionsMenu(false);
        final Datasets_OrgUnits dsorgUnit = new Datasets_OrgUnits(getActivity().getApplicationContext());
        final OptionSets optnSets = new OptionSets(getActivity().getApplicationContext());

        Spin_orgUnit = (Spinner) rootView.findViewById(R.id.input_orgUnit);
        Input_period = (EditText) rootView.findViewById(R.id.input_period);
        Spin_activityType = (Spinner) rootView.findViewById(R.id.input_activityType);
        Input_reportedBy = (EditText) rootView.findViewById(R.id.input_reportedBy);
        Input_tole = (EditText) rootView.findViewById(R.id.input_tole);
        Input_male = (EditText) rootView.findViewById(R.id.input_male);
        Input_female = (EditText) rootView.findViewById(R.id.input_female);
        Input_children = (EditText) rootView.findViewById(R.id.input_children);
        Input_referral = (EditText) rootView.findViewById(R.id.input_referral);
        Input_pc = (EditText) rootView.findViewById(R.id.input_pc);
        update = (Button) rootView.findViewById(R.id.btn_update);

        Input_period.setInputType(InputType.TYPE_NULL);
        List<OrgUnit> ou = dsorgUnit.getOrgUnit(module);
        ArrayAdapter<OrgUnit> ouAdapter = new ArrayAdapter<OrgUnit>(getActivity(), android.R.layout.simple_spinner_item, ou);
        ouAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spin_orgUnit.setAdapter(ouAdapter);

        List<Option> optn = optnSets.getOptionSet(ACTIVITYTYPE);
        ArrayAdapter<Option> opAdapter = new ArrayAdapter<Option>(getActivity(), android.R.layout.simple_spinner_item, optn);
        opAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spin_activityType.setAdapter(opAdapter);

        Input_period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int monthOfYear, int dayOfMonth) {
                        mYear = datepicker.getYear();
                        mMonth = datepicker.getMonth() + 1;
                        mDay = datepicker.getDayOfMonth();
                        Input_period.setText(mYear + "/" + mMonth + "/" + mDay);
                    }
                }, mYear, mMonth, mDay);
                mDatepicker.show();
            }
        });

        BccSummarys bsummary = new BccSummarys(getActivity().getApplicationContext());
        val = bsummary.getBCCSummary(Integer.parseInt(id));
        int index = getIndex(ouAdapter, val.get(0));
        Spin_orgUnit.setSelection(index);
        int actIndex = getIndex(opAdapter, val.get(4));
        Spin_activityType.setSelection(actIndex);
        Input_period.setText(val.get(1));
        Input_reportedBy.setText(val.get(2));
        Input_tole.setText(val.get(3));
        Input_male.setText(val.get(5));
        Input_female.setText(val.get(6));
        Input_children.setText(val.get(7));
        Input_referral.setText(val.get(8));
        Input_pc.setText(val.get(9));

        update.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String orgUnitId, prd, reportedBy, tole, activityType, male, female, children, referral, pc;
                orgUnitId = ((OrgUnit) Spin_orgUnit.getSelectedItem()).getId();
                prd = Input_period.getText().toString();
                reportedBy = Input_reportedBy.getText().toString();
                tole = Input_tole.getText().toString();
                activityType = ((Option) Spin_activityType.getSelectedItem()).getCode();
                //Toast.makeText(getActivity(), "Activity Type: "+activityType, Toast.LENGTH_SHORT).show();
                male = Input_male.getText().toString();
                female = Input_female.getText().toString();
                children = Input_children.getText().toString();
                referral = Input_referral.getText().toString();
                pc = Input_pc.getText().toString();

                BccSummarys bccSumm = new BccSummarys(getActivity().getApplicationContext());
                Boolean Status = bccSumm.updateBCCReport(id, orgUnitId, prd, reportedBy, tole, activityType, male, female, children, referral, pc);
                if(Status == true)
                {
                    Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                    fragment = new BCCListFragment();
                    title = "BCC Summary List";
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();
                }
                else { Toast.makeText(getActivity(), "Error while Updating",Toast.LENGTH_SHORT).show(); }
            }
        });

        return rootView;
    }

    private int getIndex(ArrayAdapter adapter, String string) {
        int index = 0;
        for(int i = 0; i<adapter.getCount(); i++)
        {
            if(adapter.getItem(i).toString().equals(string)) {
                index = i;
            }
        }
        return index;
    }

}
