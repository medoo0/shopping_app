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
public class MyPersonalPage extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    String userName , email , phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal_page);

        Bundle data = getIntent().getExtras();

        if(data!=null){

            userName = data.getString("Name");
            email    = data.getString("Email");
            phone    = data.getString("Phone");
        }
        viewPager =  findViewById(R.id.viewpager);
        setupViewPager(viewPager,userName,email,phone);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }






    private void setupViewPager(ViewPager viewPager,String userName , String email , String phoneNumber) {
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
}
