package np.org.psi.dhis2.datacapture.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.org.psi.dhis2.datacapture.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProviderFragment extends Fragment {


    public ProviderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider, container, false);
    }

}
