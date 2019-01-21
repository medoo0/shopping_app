package com.alaa.microprocess.lrahtk.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.Dialog.AlertDialog;
import com.alaa.microprocess.lrahtk.R;
import java.util.ArrayList;



public class Charge extends Fragment implements View.OnClickListener /*,OnMapReadyCallback , GoogleApiClient.OnConnectionFailedListener */ {


    Button continuation ;

    //SupportMapFragment  mapFragment ;
    //AutoCompleteTextView CompleteTextView;
    //PlaceAutocompleteAdapter placeAutocompleteAdapter;
    // GoogleApiClient mGoogleApiClient;
    // GoogleMap mMap ;
    Spinner CitySpinner , VillageSpinner;
    EditText moreDetails , nots;
    String[] CitiesArray  ;
    ArrayList<String> VillageArray;
    String City ="" , village ="" ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_charge, container, false);
        continuation  = v.findViewById(R.id.continuation);
        CitySpinner = v.findViewById(R.id.CitySpinner);
        VillageSpinner = v.findViewById(R.id.VillageSpinner);
        moreDetails = v.findViewById(R.id.address_Details);
        nots = v.findViewById(R.id.nots);

      //  CompleteTextView = v.findViewById(R.id.search);
        continuation.setOnClickListener(this);
//        CompleteTextView.setOnItemClickListener(autocomplteClicklistener);
//
//
//        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment == null) {
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            mapFragment = SupportMapFragment.newInstance();
//            fragmentTransaction.replace(R.id.map, mapFragment).commit();
//        }
//        mapFragment.getMapAsync(this);
//
//
//        mGoogleApiClient = new GoogleApiClient
//                .Builder(getActivity())
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .enableAutoManage(getActivity(), this)
//                .build();
//        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, null, null);
//        CompleteTextView.setAdapter(placeAutocompleteAdapter);

        CitiesArray = new String[]{"حدد المدينة","العاشر","العبور","الشروق","بدر"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        CitiesArray){
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTypeface(ResourcesCompat.getFont(getActivity(),R.font.cairo));
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.parseColor("#bb377d"));
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        CitySpinner.setAdapter(spinnerArrayAdapter);
        CitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    City = CitiesArray[i];
                    if(i == 1 ){
                        VillageSpinner.setVisibility(View.VISIBLE);
                    }
                    else {
                        VillageSpinner.setVisibility(View.GONE);
                    }
                }

                else {
                    City="";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        VillageArray = new ArrayList<>();
        VillageArray.add("حدد المجاورة");
        for(int i=1 ; i <= 30 ; i++){
            VillageArray.add("المجاورة رقم " + (i));
        }
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        VillageArray){
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTypeface(ResourcesCompat.getFont(getActivity(),R.font.cairo));
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.parseColor("#bb377d"));
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        VillageSpinner.setAdapter(spinnerArrayAdapter2);
        VillageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    village = VillageArray.get(i);
                }
                else {
                    village = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return v;
    }

    @Override
    public void onClick(View v) {

        if (v  == continuation){

            PayScreenContract.payView payView = (PayScreenContract.payView) getActivity();


            if (payView!=null){

//                if(!CompleteTextView.getText().toString().isEmpty()) {
//                    payView.showNextFragment_SuringPay(CompleteTextView.getText().toString());
//                }
                if(!City.isEmpty()){

                    double extra = 0;
                    if(City.equals(CitiesArray[1])){
                        extra = 10 ;
                    }
                    else {
                        extra = 20 ;
                    }

                    payView.showNextFragment_SuringPay(City + " "+ village
                            + "\n" + moreDetails.getText().toString()
                            +  "\n" + nots.getText().toString() ,extra);
                }
                else {
                    AlertDialog alertDialog = new AlertDialog(getActivity(), "الرجاء اختيار عنوان التسليم . ");
                    alertDialog.show();
                }

            }
        }






    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//                LatLng center = mMap.getCameraPosition().target;
//
//                CompleteTextView.setText(getCompleteAddressString(center.latitude,center.longitude));
//
//            }
//        });
//    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailCallback = new ResultCallback<PlaceBuffer>() {
//        @Override
//        public void onResult(@NonNull PlaceBuffer places) {
//
//            if(!places.getStatus().isSuccess()) {
//
//                //عشان ميحصلش Memory leak
//                places.release();
//                return;
//            }
//
//            final Place place = places.get(0);
//            MoveCamera(new LatLng(place.getViewport().getCenter().latitude,place.getViewport().getCenter().longitude));
//            //  لازم تكون اخر حاجة بتمحي الداتا
//            places.release();
//        }
//    };
//
//    private void MoveCamera(LatLng latLng) {
//        CameraPosition cp = CameraPosition.builder().target(latLng).zoom(17f)
//                .build();
//
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp), 5000, new GoogleMap.CancelableCallback() {
//            @Override
//            public void onFinish() {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//    }
//
//    private AdapterView.OnItemClickListener autocomplteClicklistener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            final AutocompletePrediction item = placeAutocompleteAdapter.getItem(i);
//            String placeId = null;
//            if (item != null) {
//                placeId = item.getPlaceId();
//                PendingResult<PlaceBuffer> result = Places.GeoDataApi.getPlaceById(mGoogleApiClient,placeId);
//                result.setResultCallback(mUpdatePlaceDetailCallback);
//            }
//
//        }
//    };
//
//    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
//        String strAdd = "";
//        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//        try {
//            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
//            if (addresses != null) {
//                Address returnedAddress = addresses.get(0);
//                StringBuilder strReturnedAddress = new StringBuilder("");
//
//                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
//                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
//                }
//                strAdd = strReturnedAddress.toString();
//                Log.d("MyCurrentloctionaddress", strReturnedAddress.toString());
//            } else {
//                Log.d("MyCurrentloctionaddress", "No Address returned!");
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("MyCurrentloctionaddress", "Canont get Address!");
//        }
//        return strAdd;
//    }

}
