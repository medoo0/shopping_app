package com.alaa.microprocess.lrahtk.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.R;

public class Paying_Fragment extends Fragment  implements View.OnClickListener{


    RadioButton sound;
    Button btn_pay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view     = inflater.inflate(R.layout.paying_fragment_layout,container,false);

        sound        = view.findViewById(R.id.sound);
        btn_pay     =  view.findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {
        if (view  == btn_pay){

            PayScreenContract.payView payView = (PayScreenContract.payView) getActivity();


            if (payView!=null){


                payView.showThanksOrder();

            }
        }
    }
}
