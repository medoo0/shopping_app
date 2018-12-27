package com.alaa.microprocess.lrahtk.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;

public class SignIn extends Fragment implements View.OnClickListener{

    TextView skiptoSignUP;
    MainActivityContract.View mainView;
    ImageView skiptosignupimage;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signin_layout,container,false);

        skiptoSignUP = view.findViewById(R.id.skiptoSignUP);
        skiptosignupimage = view.findViewById(R.id.skiptosignupimage);
        button            = view.findViewById(R.id.email_sign_in_button);
        skiptosignupimage.setOnClickListener(this);
        skiptoSignUP.setOnClickListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent intent = new Intent(getActivity() , HomePage.class);
                startActivity(intent);


            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view  == skiptoSignUP){


            mainView = (MainActivityContract.View) getActivity();
            if (mainView!=null){


                mainView.openSignupFragment();

            }


        }

        if (view == skiptosignupimage){


            mainView = (MainActivityContract.View) getActivity();
            if (mainView!=null){


                mainView.openSignupFragment();

            }

        }
    }
}
