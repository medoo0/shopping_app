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

import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.Fragment.Charge;
import com.alaa.microprocess.lrahtk.Fragment.MainPage_Fragment;
import com.alaa.microprocess.lrahtk.Fragment.Paying_Fragment;
import com.alaa.microprocess.lrahtk.Fragment.Suring_Paying;
import com.alaa.microprocess.lrahtk.Fragment.ThanksOrder;
import com.alaa.microprocess.lrahtk.R;

public class Pay extends AppCompatActivity  implements PayScreenContract.payView {

    static  public TabLayout tabLayout;
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

        //disable Touch or Click on tab layout .
        disable(tabLayout);


        //default  fragments .
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.Frame_layout, new Charge())
                .commit();




    }
     static public void setDoneIcon(int index)
    {
        tabLayout.getTabAt(index).setIcon(R.drawable.ic_done_black_24dp);
    }
    static public void moveTo(int index){
        tabLayout.getTabAt(index).select();

    }

    private static void disable(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disable((ViewGroup) child);
            } else {
                child.setEnabled(false);
            }
        }
    }

    @Override
    public void showNextFragment_SuringPay() {
        setDoneIcon(0);
        moveTo(1);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.Frame_layout, new Suring_Paying())
                .commit();
    }

    @Override
    public void showNextLastFragmentPayingFragment() {
        setDoneIcon(1);
        moveTo(2);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.Frame_layout, new Paying_Fragment())
                .commit();
    }

    @Override
    public void showThanksOrder() {
        setDoneIcon(2);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.Frame_layout, new ThanksOrder())
                .commit();
    }

}
