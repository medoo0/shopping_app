package com.alaa.microprocess.lrahtk.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.PagerAdapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.Fragment.SignUp;
import com.alaa.microprocess.lrahtk.Presenter.MainActivityPresenter;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.Fragment.SignIn;
import com.alaa.microprocess.lrahtk.pojo.LoginForm;
import com.alaa.microprocess.lrahtk.pojo.RegisterForm;
import com.alaa.microprocess.lrahtk.pojo.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    MainActivityContract.Presenter presenter;

    ViewPager viewPager;
    SharedPreferences preferences;
    AnimatedDialog dialog;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new AnimatedDialog(this);
        preferences   = getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        editor        = preferences.edit();
        if (preferences.getString("AreInOrNot","").equals("IN")){


//            Intent intent = new Intent(this , HomePage.class);
//            intent.putExtra("Email",preferences.getString("Email",""));
//            intent.putExtra("Phone",preferences.getString("Phone",""));
//            intent.putExtra("id"   ,preferences.getString("id",""));
//            intent.putExtra("Name", preferences.getString("Name",""));
//            startActivity(intent);
//            finish();
            dialog.ShowDialog();
            Login(preferences.getString("Email",""),preferences.getString("password",""));

        }




        //presenter initialization .
        presenter = new MainActivityPresenter(this);
        viewPager = findViewById(R.id.viewpager);
        setPager(viewPager);
        // goto Get Started fragement .
//        getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragment,new GetStarted()).commit();


    }



    public void setPager(ViewPager viewPager) {

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFrag(new GetStarted(), "");
        pagerAdapter.addFrag(new SignIn(), "");
        pagerAdapter.addFrag(new SignUp(), "");

//        pagerAdapter.addFrag(new LocationFragTab(),getResources().getString(R.string.my_location));
        pagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);


    }
    @Override
    public ViewPager getViewPager() {

            return viewPager;

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
            public void onResponse(@NonNull Call<LoginForm> call, @NonNull Response<LoginForm> response) {




                if (response.isSuccess()&&response.body()!=null){

                    // hide progressbar and go to NextScreen
                    dialog.Close_Dialog();


                        Intent intent = new Intent(MainActivity.this , HomePage.class);
                        editor.putString("Token",response.body().getToken());
                        editor.apply();

                        intent.putExtra("Email",preferences.getString("Email",""));
                        intent.putExtra("id",preferences.getString("id",""));
                        intent.putExtra("Name",preferences.getString("Name",""));
                        intent.putExtra("Phone",preferences.getString("Phone",""));
                        startActivity(intent);

                        // finish the firstActivity
                       finish();




                }

                else {

                    Toast.makeText(MainActivity.this, "حدث خطأ في تسجيل الدخول . سجل دخولك بطريقة يدوية .", Toast.LENGTH_LONG).show();
                    dialog.Close_Dialog();
                    // hide progressbar and still in this Screen //

                }



            }

            @Override
            public void onFailure(@NonNull Call<LoginForm> call, @NonNull Throwable t) {
                // connection poor or exception in retrofit occur .... //
                dialog.Close_Dialog();
                Toast.makeText(MainActivity.this, getResources().getString(R.string.Check_Internet), Toast.LENGTH_LONG).show();


            }
        });
    }




}

