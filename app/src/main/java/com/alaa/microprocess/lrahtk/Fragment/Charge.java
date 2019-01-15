package com.alaa.microprocess.lrahtk.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.Dialog.AlertDialog;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.autocomplete.PlaceAutocompleteAdapter;
import com.alaa.microprocess.lrahtk.autocomplete.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Charge extends Fragment implements View.OnClickListener ,OnMapReadyCallback , GoogleApiClient.OnConnectionFailedListener  {


    Button continuation ;

    SupportMapFragment  mapFragment ;
    AutoCompleteTextView CompleteTextView;
    PlaceAutocompleteAdapter placeAutocompleteAdapter;
    GoogleApiClient mGoogleApiClient;
    PlaceInfo mPlace;
    GoogleMap mMap ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_charge, container, false);
        continuation  = v.findViewById(R.id.continuation);
        CompleteTextView = v.findViewById(R.id.search);

        continuation.setOnClickListener(this);
        CompleteTextView.setOnItemClickListener(autocomplteClicklistener);


        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);


        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();
        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, null, null);
        CompleteTextView.setAdapter(placeAutocompleteAdapter);

        return v;
    }

    @Override
    public void onClick(View v) {

        if (v  == continuation){

            PayScreenContract.payView payView = (PayScreenContract.payView) getActivity();


            if (payView!=null){

                if(!CompleteTextView.getText().toString().isEmpty()) {
                    payView.showNextFragment_SuringPay(CompleteTextView.getText().toString());
                }
                else {
                    AlertDialog alertDialog = new AlertDialog(getActivity(), "الرجاء كتابة العنوان الخاص بمكان التسليم . ");
                    alertDialog.show();
                }

            }
        }






    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng center = mMap.getCameraPosition().target;

                CompleteTextView.setText(getCompleteAddressString(center.latitude,center.longitude));

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            if(!places.getStatus().isSuccess()) {

                //عشان ميحصلش Memory leak
                places.release();
                return;
            }

            final Place place = places.get(0);
            MoveCamera(new LatLng(place.getViewport().getCenter().latitude,place.getViewport().getCenter().longitude));
            //  لازم تكون اخر حاجة بتمحي الداتا
            places.release();
        }
    };

    private void MoveCamera(LatLng latLng) {
        CameraPosition cp = CameraPosition.builder().target(latLng).zoom(17f)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 5000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    private AdapterView.OnItemClickListener autocomplteClicklistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            final AutocompletePrediction item = placeAutocompleteAdapter.getItem(i);
            String placeId = null;
            if (item != null) {
                placeId = item.getPlaceId();
                PendingResult<PlaceBuffer> result = Places.GeoDataApi.getPlaceById(mGoogleApiClient,placeId);
                result.setResultCallback(mUpdatePlaceDetailCallback);
            }

        }
    };

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.d("MyCurrentloctionaddress", strReturnedAddress.toString());
            } else {
                Log.d("MyCurrentloctionaddress", "No Address returned!");

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("MyCurrentloctionaddress", "Canont get Address!");
        }
        return strAdd;
    }
}
