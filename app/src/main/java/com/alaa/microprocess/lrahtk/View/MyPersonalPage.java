package com.alaa.microprocess.lrahtk.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.alaa.microprocess.lrahtk.Adapters.TabsPager;
import com.alaa.microprocess.lrahtk.Fragment.Favourite_Fragment;
import com.alaa.microprocess.lrahtk.Fragment.MyAccount_Fragment;
import com.alaa.microprocess.lrahtk.Fragment.MyOrders_Fragment;
import com.alaa.microprocess.lrahtk.Fragment.Saving_Titles_Fragment;
import com.alaa.microprocess.lrahtk.R;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class MyPersonalPage extends AppCompatActivity implements View.OnClickListener {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    String userID,userName , email , phone;
    ImageView backhome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal_page);

        backhome = findViewById(R.id.backhome);
        backhome.setOnClickListener(this);
        Bundle data = getIntent().getExtras();

        if(data!=null){
            userID   = data.getString("id");
            userName = data.getString("userName");
            email    = data.getString("Email");
            phone    = data.getString("phone");

        }
        viewPager =  findViewById(R.id.viewpager);
        setupViewPager(viewPager,userName,email,phone);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setupViewPager(ViewPager viewPager, String userName , String email , String phoneNumber) {

        MyAccount_Fragment myAccount_fragment = new MyAccount_Fragment();
        Bundle data = new Bundle();
        data.putString("Email",email);
        data.putString("userName",userName);
        data.putString("phone",phoneNumber);
        myAccount_fragment.setArguments(data);
        TabsPager adapter = new TabsPager(getSupportFragmentManager());
        adapter.addFrag(myAccount_fragment, "حسابي");
        adapter.addFrag(new MyOrders_Fragment(), "طلباتك");
        adapter.addFrag(new Favourite_Fragment(), "المفضل");
        adapter.addFrag(new Saving_Titles_Fragment(), "العناوين المحفوضه");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        if (v == backhome){

            finish();

        }

    }
}
