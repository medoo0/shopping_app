package com.alaa.microprocess.lrahtk.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.Fragment.SignUp;
import com.alaa.microprocess.lrahtk.Presenter.MainActivityPresenter;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.Fragment.SignIn;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    MainActivityContract.Presenter presenter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //presenter initialization .
        presenter = new MainActivityPresenter(this);


        // goto Get Started fragement .
        getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragment,new GetStarted()).commit();

    }


    @Override
    public void openSignInFragment() {


        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.Main_fragment,new SignIn())
                .addToBackStack(null)
                .commit();



    }

    @Override
    public void openSignupFragment() {


        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.Main_fragment,new SignUp())
                .addToBackStack(null)
                .commit();


    }
}
