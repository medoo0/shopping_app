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
        tabLayout.addTab(tabLayout.newTab().setText(R.string.pay));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.confirm));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    Toast.makeText(Pay.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    Toast.makeText(Pay.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    Toast.makeText(Pay.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public void setCustomFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "Nosifer-Regular.ttf"));
                }
            }
        }
    }
}
