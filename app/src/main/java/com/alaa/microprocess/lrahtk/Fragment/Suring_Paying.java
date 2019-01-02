package com.alaa.microprocess.lrahtk.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.R;

public class Suring_Paying extends Fragment implements View.OnClickListener {

    RecyclerView myRec;
    Button sure;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view     = inflater.inflate(R.layout.suring_paying_layout,container,false);

        myRec        = view.findViewById(R.id.myRec);
        sure         = view.findViewById(R.id.sure);
        sure.setOnClickListener(this);
        return view;
    }



    void showItems(){


    }

    @Override
    public void onClick(View v) {

        if (v == sure){


            PayScreenContract.payView payView = (PayScreenContract.payView) getActivity();

            if (payView!=null){

                payView.showNextLastFragmentPayingFragment();

            }

        }

    }
}
