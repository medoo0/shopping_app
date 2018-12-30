package com.alaa.microprocess.lrahtk.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alaa.microprocess.lrahtk.Adapters.PagerAdapter;
import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.Fragment.SignUp;
import com.alaa.microprocess.lrahtk.Presenter.MainActivityPresenter;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.Fragment.SignIn;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    MainActivityContract.Presenter presenter;

    ViewPager viewPager;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences   = getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);


        if (preferences.getString("AreInOrNot","").equals("IN")){


            Intent intent = new Intent(this , HomePage.class);
            intent.putExtra("Email",preferences.getString("Email",""));
            intent.putExtra("Phone",preferences.getString("Phone",""));
            intent.putExtra("id"   ,preferences.getString("id",""));
            intent.putExtra("Name", preferences.getString("Name",""));
            startActivity(intent);
            finish();


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









//
//    @Override
//    public void openSignInFragment() {
//
//        getSupportFragmentManager().popBackStack();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
//                .replace(R.id.Main_fragment, new SignIn())
//                .addToBackStack(null)
//                .commit();
//
//
//    }
//
//    @Override
//    public void openSignupFragment() {
//
//        getSupportFragmentManager().popBackStack();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
//                .replace(R.id.Main_fragment, new SignUp())
//                .addToBackStack(null)
//                .commit();
//
//
//    }

}

