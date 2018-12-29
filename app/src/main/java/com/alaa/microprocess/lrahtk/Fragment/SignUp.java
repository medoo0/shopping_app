package com.alaa.microprocess.lrahtk.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.pojo.RegisterForm;
import com.alaa.microprocess.lrahtk.pojo.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends Fragment implements View.OnClickListener{


    TextView skip;
    ImageView skiptoSignInimage;
    MainActivityContract.View mainView;
    Button register_btn;
    EditText UserName , Email , MobilNumber , password ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v            = inflater.inflate(R.layout.signup_layout,container,false);
        skip              = v.findViewById(R.id.skip);
        skiptoSignInimage = v.findViewById(R.id.skiptoSignInimage);
        register_btn      = v.findViewById(R.id.register_btn);
        UserName          = v.findViewById(R.id.UserName);
        Email             = v.findViewById(R.id.Email);
        MobilNumber       = v.findViewById(R.id.MobilNumber);
        password          = v.findViewById(R.id.password);


        register_btn.setOnClickListener(this);
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

        if(view == register_btn) {

            ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);

            RegisterForm registerForm = new RegisterForm();
            registerForm.setName(UserName.getText().toString());
            registerForm.setPhone(MobilNumber.getText().toString());
            registerForm.setEmail(Email.getText().toString());
            registerForm.setPassword(password.getText().toString());

            if(UserName.getText().toString().isEmpty()){

                UserName.setError(R.string.Name_error);
                UserName.requestFocus();

            }
            else if (Email.getText().toString().isEmpty()){
                Email.setError(R.string.email_error);
                Email.requestFocus();
            }
            else if(isEmailValid(Email.getText().toString())){
                Email.setError(R.string.email_error2);
                Email.requestFocus();
            }
            else if(password.getText().toString().isEmpty()){

                password.setError(R.string.password_error);
                password.requestFocus();
            }
            else if (password.getText().toString().length() < 6 ){

                password.setError(R.string.password_error2);
                password.requestFocus();
            }
            else {

                Register(client,registerForm.getCreatedAt(), registerForm.getUpdatedAt(), registerForm.getId()
                        , registerForm.getEmail(), registerForm.getPassword(), registerForm.getName(), registerForm.getPhone());

            }




        }

    }


public void Register(ApiMethod client ,String createdAt, String updatedAt, String id, String email, String password, String name, String phone) {

    Call<RegisterResponse> call = client.insertData(createdAt,updatedAt,id
            ,email,password, name, phone);

    call.enqueue(new Callback<RegisterResponse>() {
        @Override
        public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
            if (response.isSuccess()) {
                //lOGIN
                Toast.makeText(getActivity(), "Tmama", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getActivity(), "no tmam", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
            Toast.makeText(getActivity(), "not registered", Toast.LENGTH_SHORT).show();
        }
    });


}



    boolean isEmailValid(CharSequence email) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); //returen false if not ok //return true if ok
    }

}