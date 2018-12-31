package com.alaa.microprocess.lrahtk.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import com.alaa.microprocess.lrahtk.R;

import android.view.ViewGroup;
import android.widget.TextView;

public class MyAccount_Fragment extends Fragment {



    String userName , email , phone;
    TextView NameTXT , ShowEmail , showPhoneNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myaccount_fragment,container,false);
        NameTXT   = view.findViewById(R.id.showUserName);
        ShowEmail      = view.findViewById(R.id.ShowEmail);
        showPhoneNumber = view.findViewById(R.id.showPhoneNumber);

        Bundle data = getArguments();
        if (data!=null){

            email    = data.getString("Email","");
            userName = data.getString("userName","");
            phone    = data.getString("phone","");
            NameTXT.setText(userName);
            ShowEmail.setText(email);
            showPhoneNumber.setText(phone);


        }


        return view;
    }
}
