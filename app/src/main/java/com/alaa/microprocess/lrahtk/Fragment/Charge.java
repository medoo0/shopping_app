package com.alaa.microprocess.lrahtk.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.R;


public class Charge extends Fragment implements View.OnClickListener{


    Button continuation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_charge, container, false);
        continuation  = v.findViewById(R.id.continuation);
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
