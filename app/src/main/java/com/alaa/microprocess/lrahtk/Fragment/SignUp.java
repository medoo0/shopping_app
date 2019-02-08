package com.alaa.microprocess.lrahtk.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.pojo.LoginForm;
import com.alaa.microprocess.lrahtk.pojo.RegisterForm;
import com.alaa.microprocess.lrahtk.pojo.RegisterResponse;
import com.alaa.microprocess.lrahtk.pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends Fragment implements View.OnClickListener{


    TextView skip;
    ImageView skiptoSignInimage;
    MainActivityContract.View mainView;
    Button register_btn;
    EditText UserName , Email , MobilNumber , password ;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    AnimatedDialog dialog ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v            = inflater.inflate(R.layout.signup_layout,container,false);


        if (getActivity()!=null){

            preferences   = getActivity().getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);
            editor        = preferences.edit();

        }

        skip              = v.findViewById(R.id.skip);
        skiptoSignInimage = v.findViewById(R.id.skiptoSignInimage);
        register_btn      = v.findViewById(R.id.register_btn);
        UserName          = v.findViewById(R.id.UserName);
        Email             = v.findViewById(R.id.Email);
        MobilNumber       = v.findViewById(R.id.MobilNumber);
        password          = v.findViewById(R.id.password);
        dialog            = new AnimatedDialog(getActivity());
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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

                mainView.getViewPager().setCurrentItem(1);

            }




        }

        if (view == skiptoSignInimage){

            mainView   = (MainActivityContract.View) getActivity();
            if (mainView!=null){

                mainView.getViewPager().setCurrentItem(1);

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

                UserName.setError(getResources().getString(R.string.Name_error));
                UserName.requestFocus();

            }
            else if (Email.getText().toString().isEmpty()){
                Email.setError(getResources().getString(R.string.email_error));
                Email.requestFocus();
            }
            else if(!isEmailValid(Email.getText().toString())){
                Email.setError(getResources().getString(R.string.email_error2));
                Email.requestFocus();
            }
            else if (MobilNumber.getText().toString().isEmpty()){

                MobilNumber.setError(getResources().getString(R.string.Phone_error));
                MobilNumber.requestFocus();

            }
            else if(password.getText().toString().isEmpty()){

                password.setError(getResources().getString(R.string.password_error));
                password.requestFocus();
            }
            else if (password.getText().toString().length() < 7 ){

                password.setError(getResources().getString(R.string.password_error2));
                password.requestFocus();
            }
            else {

                //show Dialog
                dialog.ShowDialog();

                Register(client,registerForm.getCreatedAt(), registerForm.getUpdatedAt(), registerForm.getId()
                        , registerForm.getEmail(), registerForm.getPassword(), registerForm.getName(), registerForm.getPhone());

            }




        }

    }


public void Register(ApiMethod client , String createdAt, String updatedAt, String id, String email, final String password, String name, String phone) {

    Call<RegisterResponse> call = client.insertData(createdAt,updatedAt,id
            ,email,password, name, phone);

    call.enqueue(new Callback<RegisterResponse>() {
        @Override
        public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
            if (response.isSuccess()) {


                Login(response.body().getEmail(),password);




            } else {
                dialog.Close_Dialog();
                Email.setError("هذا الأيميل متواجد بالفعل.");
            }

        }

        @Override
        public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
            dialog.Close_Dialog();
            Toast.makeText(getActivity(), "تأكد من الاتصال بالخادم.", Toast.LENGTH_SHORT).show();
        }
    });


}

    private void Login(String email, final String password) {
        RegisterForm registerForm = new RegisterForm();

        registerForm.setEmail(email);
        registerForm.setPassword(password);
        ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);

        retrofit2.Call<LoginForm> call = client.login(registerForm.getCreatedAt(),registerForm.getUpdatedAt(),registerForm.getId()
                ,registerForm.getEmail(),registerForm.getPassword());

        call.enqueue(new Callback<LoginForm>() {
            @Override
            public void onResponse(@NonNull Call<LoginForm> call,@NonNull Response<LoginForm> response) {




                if (response.isSuccess()&&response.body()!=null){

                    // hide progressbar and go to NextScreen
                    dialog.Close_Dialog();

                    if (editor!=null){

                        Intent intent = new Intent(getActivity() , HomePage.class);
                        User user    = new User();
                        user.setEmail(response.body().getUser().getEmail());
                        user.setId(response.body().getUser().getId());
                        user.setName(response.body().getUser().getName());
                        user.setPhone(response.body().getUser().getPhone());
                        editor.putString("AreInOrNot","IN");
                        editor.putString("Email",user.getEmail());
                        editor.putString("id",user.getId());
                        editor.putString("Phone",user.getPhone());
                        editor.putString("Name",user.getName());
                        editor.putString("Token",response.body().getToken());
                        editor.putString("password",password);
                        editor.apply();
                        intent.putExtra("Email",user.getEmail());
                        intent.putExtra("id",user.getId());
                        intent.putExtra("Name",user.getName());
                        intent.putExtra("Phone",user.getPhone());
                        startActivity(intent);

                        // finish the firstActivity
                        getActivity().finish();
                    }



                }

                else {

                    Toast.makeText(getActivity(), "حدث خطأ في تسجيل الدخول . سجل دخولك بطريقة يدوية .", Toast.LENGTH_LONG).show();
                    dialog.Close_Dialog();
                    // hide progressbar and still in this Screen //

                }



            }

            @Override
            public void onFailure(@NonNull Call<LoginForm> call, @NonNull Throwable t) {
                // connection poor or exception in retrofit occur .... //
                dialog.Close_Dialog();
                Toast.makeText(getActivity(), getResources().getString(R.string.Check_Internet), Toast.LENGTH_LONG).show();


            }
        });
    }


    boolean isEmailValid(CharSequence email) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); //returen false if not ok //return true if ok

    }

}