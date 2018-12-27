package com.alaa.microprocess.lrahtk.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.R;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;


public class HomePage extends AppCompatActivity implements View.OnClickListener {
    ResideMenu  resideMenu;

    ResideMenuItem home_item;
    ResideMenuItem setting_item;
    ResideMenuItem signout_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.color.blue);
        resideMenu.attachToActivity(this);




             home_item = new ResideMenuItem(this, R.drawable.user, "Home");
             setting_item = new ResideMenuItem(this, R.drawable.user, "Setting");
             signout_item = new ResideMenuItem(this, R.drawable.user, "Sign Out");

             resideMenu.addMenuItem(home_item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
             resideMenu.addMenuItem(setting_item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
             resideMenu.addMenuItem(signout_item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT


        home_item.setOnClickListener(this);
        setting_item.setOnClickListener(this);
        signout_item.setOnClickListener(this);

        resideMenu.openMenu(ResideMenu.DIRECTION_LEFT); // or ResideMenu.DIRECTION_RIGHT
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);




    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == home_item ){
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }

        else if (view == setting_item ) {

            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }
        else if (view == signout_item ){
            Toast.makeText(this, "sign out", Toast.LENGTH_SHORT).show();
          }

        }

    }



