package com.alaa.microprocess.lrahtk.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.Presenter.MainActivityPresenter;
import com.alaa.microprocess.lrahtk.R;

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


}
