package com.alaa.microprocess.lrahtk.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.R;


public class Charge extends Fragment implements View.OnClickListener{


    Button continuation;
    EditText Et_firstName,Et_SecondName,Et_Address,Et_Phone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_charge, container, false);
        continuation  = v.findViewById(R.id.continuation);
        Et_firstName =v.findViewById(R.id.first_Name);
        Et_SecondName = v.findViewById(R.id.secondName);
        Et_Address    = v.findViewById(R.id.address);
        Et_Phone      = v.findViewById(R.id.phone);
        continuation.setOnClickListener(this);



        return v;
    }

    @Override
    public void onClick(View v) {

        if (v  == continuation){

            PayScreenContract.payView payView = (PayScreenContract.payView) getActivity();


            if (payView!=null){


                payView.showNextFragment_SuringPay();

            }
        }

    }
}
