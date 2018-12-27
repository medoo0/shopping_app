package com.alaa.microprocess.lrahtk.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.R;

public class SignUp extends Fragment implements View.OnClickListener{


    TextView skip;
    ImageView skiptoSignInimage;
    MainActivityContract.View mainView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.signup_layout,container,false);
        skip   = v.findViewById(R.id.skip);
        skiptoSignInimage = v.findViewById(R.id.skiptoSignInimage);
        skiptoSignInimage.setOnClickListener(this);
        skip.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {

        // open signin Fragment  //

        if (view  == skip){

            mainView   = (MainActivityContract.View) getActivity();
            if (mainView!=null){

                mainView.openSignInFragment();

            }




        }

        if (view == skiptoSignInimage){

            mainView   = (MainActivityContract.View) getActivity();
            if (mainView!=null){

                mainView.openSignInFragment();

            }


        }

    }
}
