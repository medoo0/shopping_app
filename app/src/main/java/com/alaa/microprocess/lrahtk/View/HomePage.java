package com.alaa.microprocess.lrahtk.View;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.R;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.lang.reflect.Field;


public class HomePage extends AppCompatActivity implements View.OnClickListener {
    ResideMenu  resideMenu;

    ResideMenuItem Bakery_item , milk_item ,fruit_item ,meats_item , drinks_item,canned_item , foods_item,Pasta_item,sweets_item ,child_item , health_item ,clean_item,cook_item , signout_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.color.blue);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.5f);




            Bakery_item = new ResideMenuItem(this, R.drawable.user, "المخبوزات");
            milk_item = new ResideMenuItem(this, R.drawable.user, "البان و بيض");
            fruit_item = new ResideMenuItem(this, R.drawable.user, "فاكهة وخضراوات");
            meats_item = new ResideMenuItem(this, R.drawable.user, "لحوم و دجاج واسماك");
            drinks_item = new ResideMenuItem(this, R.drawable.user, "مشروبات");
            canned_item = new ResideMenuItem(this, R.drawable.user, "معلبات");
            foods_item = new ResideMenuItem(this, R.drawable.user, "محضرات طعام");
            Pasta_item = new ResideMenuItem(this, R.drawable.user, "مكرونة وحبوب");
            sweets_item = new ResideMenuItem(this, R.drawable.user, "حلويات");
            child_item = new ResideMenuItem(this, R.drawable.user, "العناية بالاطفال");
            health_item = new ResideMenuItem(this, R.drawable.user, "الصحة والجمال");
            clean_item = new ResideMenuItem(this, R.drawable.user, "منظفات");
            cook_item = new ResideMenuItem(this, R.drawable.user, "مستلزمات الطبخ");
            signout_item = new ResideMenuItem(this, R.drawable.door, "");

        resideMenu.addMenuItem(Bakery_item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        resideMenu.addMenuItem(milk_item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        resideMenu.addMenuItem(fruit_item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        resideMenu.addMenuItem(meats_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(drinks_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(canned_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(foods_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(Pasta_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(sweets_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(child_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(health_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(clean_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(cook_item,  ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(signout_item,  ResideMenu.DIRECTION_LEFT);



        Bakery_item.setOnClickListener(this);
        milk_item.setOnClickListener(this);
        fruit_item.setOnClickListener(this);
        meats_item.setOnClickListener(this);
        drinks_item.setOnClickListener(this);
        canned_item.setOnClickListener(this);
        foods_item.setOnClickListener(this);
        Pasta_item.setOnClickListener(this);
        sweets_item.setOnClickListener(this);
        child_item.setOnClickListener(this);
        health_item.setOnClickListener(this);
        clean_item.setOnClickListener(this);
        cook_item.setOnClickListener(this);
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

        if (view == Bakery_item ){
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }

        else if (view == milk_item ) {

            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }
        else if (view == fruit_item ){
            Toast.makeText(this, "sign out", Toast.LENGTH_SHORT).show();
          }

        }


    }







