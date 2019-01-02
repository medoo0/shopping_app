package com.alaa.microprocess.lrahtk.View;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Fragment.Charge;
import com.alaa.microprocess.lrahtk.Fragment.MainPage_Fragment;
import com.alaa.microprocess.lrahtk.R;

public class Pay extends AppCompatActivity {

    TabLayout tabLayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        tabLayout = findViewById(R.id.tabs);
        imageView = findViewById(R.id.backhome);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.charge));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.confirm));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.pay));


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //default  fragments .
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.Frame_layout, new Charge())
                .commit();



    }
    public void setDoneIcon(int index)
    {
        tabLayout.getTabAt(index).setIcon(R.drawable.ic_done_black_24dp);
    }
    public void moveTo(int index){
        tabLayout.getTabAt(index).select();

    }

}
