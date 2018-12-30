package com.alaa.microprocess.lrahtk.View;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.Adapters.Rec_Nav_Adapter;
import com.alaa.microprocess.lrahtk.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomePage extends AppCompatActivity implements View.OnClickListener{


        @BindView(R.id.Rec_Nav)
        RecyclerView Rec_Nav ;

        @BindView(R.id.relativeHome)
         RelativeLayout relativeLayout;

        @BindView(R.id.Nav_Icon)
        ImageView Nav_Icon;

        @BindView(R.id.recitems)
        RecyclerView recitems;


        @BindView(R.id.LastLinear)
        LinearLayout LastLinear;

        TextView logout;

        ArrayList<String> Categories ;
        ArrayList<Integer> Categories_icon;
        Rec_Nav_Adapter postAdapter;

        ArrayList <String> items;
        ArrayList<Integer> images;
        ArrayList<Integer> salary;
        int dp200_To_pixel = 0 ;
        boolean NavIsOpened = false ;
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        int COLUM_NUM = 3;

        String email , id , name , phone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        logout   = findViewById(R.id.logout);
        logout.setOnClickListener(this);
        ButterKnife.bind(this);
        preferences   = getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);
        editor        = preferences.edit();

        // all Details for User

        Bundle b   =  getIntent().getExtras();

        if (b!=null){

            email      = b.getString("Email" );
            id         = b.getString("id");
            name       = b.getString("Name");
            phone      = b.getString("Phone");
        }






        recitems.setNestedScrollingEnabled(false);
        items   = new ArrayList<>();
        images  = new ArrayList<>();
        salary  = new ArrayList<>();

        Categories = new ArrayList<>();
        Categories_icon = new ArrayList<>();

        Categories.add("المخبوزات");
        Categories.add("ألبان و بيض");
        Categories.add("فاكهة و خضراوات");
        Categories.add("لحوم و دجاج واسماك");
        Categories.add("مشروبات");
        Categories.add("معلبات");
        Categories.add("محضرات طعام");
        Categories.add("مكرونة و حبوب");
        Categories.add("حلويات");
        Categories.add("العناية بالاطفال");
        Categories.add("الصحة و الجمال");
        Categories.add("مستلزمات المنزل");
        Categories.add("منظفات");
        Categories.add("مستلزمات الطبخ");

        Categories_icon.add(R.drawable.ic_bread);
        Categories_icon.add(R.drawable.ic_milk);
        Categories_icon.add(R.drawable.ic_healthy_food);
        Categories_icon.add(R.drawable.ic_chicken_leg);
        Categories_icon.add(R.drawable.ic_coffee_cup);
        Categories_icon.add(R.drawable.ic_can);
        Categories_icon.add(R.drawable.ic_cooking_time);
        Categories_icon.add(R.drawable.ic_bean);
        Categories_icon.add(R.drawable.ic_cupcake);
        Categories_icon.add(R.drawable.ic_baby);
        Categories_icon.add(R.drawable.ic_care);
        Categories_icon.add(R.drawable.ic_detergent);
        Categories_icon.add(R.drawable.ic_detergent2);
        Categories_icon.add(R.drawable.ic_chef_hat);


        //adapter
        postAdapter = new Rec_Nav_Adapter(Categories,Categories_icon);
        Rec_Nav.setLayoutManager(new LinearLayoutManager(this));
        Rec_Nav.setAdapter(postAdapter);


        showItemsinREC();





        // get width in dp .
        final float density = this.getResources().getDisplayMetrics().density;
        ViewTreeObserver viewTreeObserver = relativeLayout.getViewTreeObserver();

        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);




                    // dp = (int) (relativeLayout.getWidth() / density);

                     dp200_To_pixel = (int) (200 * density);





                }
            });

        }


        //animation
        Nav_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(NavIsOpened) {
                    //close
                    relativeLayout.animate().translationX(0).setDuration(500);
                    relativeLayout.animate().scaleX(1f).scaleY(1f).setDuration(500);
                    NavIsOpened = false;
                    LastLinear.setVisibility(View.GONE);

                }
                else {
                    //open
                    relativeLayout.setX(0);
                    relativeLayout.animate().translationXBy(dp200_To_pixel).setDuration(500);
                    relativeLayout.animate().scaleX(.7f).scaleY(.7f).setDuration(500);
                    NavIsOpened = true;
                    LastLinear.setVisibility(View.VISIBLE);
                }

            }
        });

        LastLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   //close
                    relativeLayout.animate().translationX(0).setDuration(500);
                    relativeLayout.animate().scaleX(1f).scaleY(1f).setDuration(500);
                    NavIsOpened = false;
                    LastLinear.setVisibility(View.GONE);
            }
        });



    }



    public void showItemsinREC(){

            items.add("لبن");
            items.add("شيكولاته");
            items.add("خبز");
            items.add("عصائر");
            items.add("زبادي");
            items.add("لبن");
            items.add("لبن");
            images.add(R.drawable.millkingone);
            images.add(R.drawable.checlotes);
            images.add(R.drawable.breads);
            images.add(R.drawable.drinks);
            images.add(R.drawable.johina);
            images.add(R.drawable.millkingone);
            images.add(R.drawable.millkingone);


        Rec_Items_Adapter rec_items_adapter = new Rec_Items_Adapter(items,images,this);
        rec_items_adapter.notifyDataSetChanged();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,COLUM_NUM);
        recitems.setLayoutManager(gridLayoutManager);
        recitems.setAdapter(rec_items_adapter);

    }


    @Override
    public void onClick(View v) {

        if (v == logout){

            editor.putString("AreInOrNot","");
            editor.putString("Email","");
            editor.putString("id","");
            editor.putString("Phone","");
            editor.putString("Name","");
            editor.apply();

            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
            finish();

            //  finsh this activity and go back ///     حنشوف بعد كدا لو فيه حاجه متعلقه بالسرفر بالخروج ولا لا



        }

    }
}






