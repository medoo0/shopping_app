package com.alaa.microprocess.lrahtk.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.R;


public class ThanksOrder extends Fragment {

    String orderID;
    Button button;
    TextView orderNumber ;

    @SuppressLint("ValidFragment")
    public ThanksOrder(String orderID) {
        this.orderID =orderID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_thanks_order, container, false);
        orderNumber = view.findViewById(R.id.orderNumber);
        button = view.findViewById(R.id.finish);

        orderNumber.setText(orderID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();

            }
        });


        return view;
    }

}
