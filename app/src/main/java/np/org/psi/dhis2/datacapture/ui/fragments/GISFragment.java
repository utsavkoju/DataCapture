package np.org.psi.dhis2.datacapture.ui.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import np.org.psi.dhis2.datacapture.R;
import np.org.psi.dhis2.datacapture.database.OrgUnits;
import np.org.psi.dhis2.datacapture.objects.OrgUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class GISFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    double latitude, logintude;
    private String TAG = "fragment.gis";
    private int REQUEST_COARSE_LOCATION, REQUEST_FINE_LOCATION;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gis, container, false);
        try {
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                Log.i("COARSE Location: ","REQUESTING");
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
            }
            else {
                Log.i("COARSE Location: ","GRANTED");
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("FINE Location: ","REQUESTING");
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
                } else {
                    Log.i("Location: ","GRANTED");
                    initializeMap();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void initializeMap() {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    loadMap(googleMap);
                }
            });
        } else {
            Toast.makeText(getActivity(), "ERROR!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {

            map.setMyLocationEnabled(true);

            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setMapToolbarEnabled(true);
            // Add a marker in Sydney and move the camera
            LatLng kathmandu = new LatLng(27, 85);
            //map.addMarker(new MarkerOptions().position(kathmandu).title("Kathmandu"));
            map.moveCamera(CameraUpdateFactory.newLatLng(kathmandu));
            mapFacilities(map);
        } else {
            Toast.makeText(getActivity(),"ERROR INSIDE",Toast.LENGTH_SHORT).show();
        }
    }

    public void mapFacilities(GoogleMap googleMap)
    {
        map = googleMap;
        OrgUnits oUnits = new OrgUnits(getActivity().getApplicationContext());
        Boolean hasOutlet = oUnits.facilityExist();
        if(hasOutlet == true) {
            OrgUnits ounits = new OrgUnits(getActivity().getApplicationContext());
            List<OrgUnit> ou = ounits.getOrgUnits();
            for(int i = 0; i<ou.size(); i++) {
                if(!ou.get(i).getCoordinates().equals("")) {
                    String coordinates = ou.get(i).getCoordinates().replace("[", "").replace("]", "");
                    String[] result = coordinates.split(",");
                    Log.i(TAG, Double.valueOf(result[0]).toString());
                    LatLng outlet = new LatLng(Double.valueOf(result[1]), Double.valueOf(result[0]));
                    map.addMarker(new MarkerOptions().position(outlet).title(ou.get(i).getName()));
                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mapFacilities(googleMap);
    }
}
