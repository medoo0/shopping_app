package com.alaa.microprocess.lrahtk.View;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
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
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.Fragment.Basket;
import com.alaa.microprocess.lrahtk.Fragment.Favourite_Fragment;
import com.alaa.microprocess.lrahtk.Fragment.MainPage_Fragment;
import com.alaa.microprocess.lrahtk.Fragment.Search;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.Helper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomePage extends AppCompatActivity implements View.OnClickListener,
        HomePageContract.viewMain{


        @BindView(R.id.Rec_Nav)
        RecyclerView Rec_Nav ;

        @BindView(R.id.relativeHome)
         RelativeLayout relativeLayout;

        @BindView(R.id.Nav_Icon)
        ImageView Nav_Icon;


        public static TextView texttoolbar;
        @BindView(R.id.LastLinear)
        LinearLayout LastLinear;

        TextView logout;


        Helper helper;
        Toolbar toolbar;
        SQLiteDatabase dpwrite , dpread;
        ArrayList<String> Categories ;
        ArrayList<Integer> Categories_icon;
        Rec_Nav_Adapter postAdapter;
        BottomNavigationView BottomNavigationView;
        ArrayList <String> items;
        ArrayList<Integer> images;
        ArrayList<String> productID;
        ArrayList<Integer> salary;
        int dp200_To_pixel = 0 ;
        boolean NavIsOpened = false ;
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        ImageView gotoPersonalData;
        int COLUM_NUM = 3;

        String email , id , name , phone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        helper = new Helper(this);
        dpwrite          = helper.getWritableDatabase();
        dpread           = helper.getReadableDatabase();
        gotoPersonalData = findViewById(R.id.gotoPersonalData);
        gotoPersonalData.setOnClickListener(this);
        logout   = findViewById(R.id.logout);
        toolbar    = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView =  findViewById(R.id.custom_bottom_navigation);
        texttoolbar = findViewById(R.id.texttoolbar);
//        BottomNavigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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




        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.replaceByFragment, new MainPage_Fragment())
                .commit();


//        recitems.setNestedScrollingEnabled(false);
        items   = new ArrayList<>();
        images  = new ArrayList<>();
        salary  = new ArrayList<>();
        productID= new ArrayList<>();

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
        postAdapter = new Rec_Nav_Adapter(Categories,Categories_icon,this,this);
        Rec_Nav.setLayoutManager(new LinearLayoutManager(this));
        Rec_Nav.setAdapter(postAdapter);


//        showItemsinREC();





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

                open_Navigation_drawer();

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



//    public void showItemsinREC(){
//
//            items.add("لبن");
//            items.add("شيكولاته");
//            items.add("خبز");
//            items.add("عصائر");
//            items.add("زبادي");
//            items.add("لبن");
//            items.add("لبن");
//            images.add(R.drawable.millkingone);
//            images.add(R.drawable.checlotes);
//            images.add(R.drawable.breads);
//            images.add(R.drawable.drinks);
//            images.add(R.drawable.johina);
//            images.add(R.drawable.millkingone);
//            images.add(R.drawable.millkingone);
//        productID.add("1");
//        productID.add("2");
//        productID.add("3");
//        productID.add("4");
//        productID.add("5");
//        productID.add("6");
//        productID.add("7");
//
//
//        Rec_Items_Adapter rec_items_adapter = new Rec_Items_Adapter(items,images,productID,this,dpwrite,dpread);
//        rec_items_adapter.notifyDataSetChanged();
//        LinearLayoutManager HorizontalLayout  =
//                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recitems.setLayoutManager(HorizontalLayout);
//        recitems.setAdapter(rec_items_adapter);
//
//    }


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

        if (v  == gotoPersonalData){


            Intent intent = new Intent(this , MyPersonalPage.class);
            intent.putExtra("Email",email);
            intent.putExtra("userName",name);
            intent.putExtra("id",id);
            intent.putExtra("phone",phone);
            startActivity(intent);

        }

    }

    @Override
    public void whenClickonIteminRecNav() {

        Intent intent = new Intent(this , Product_Activity.class);
        startActivity(intent);


    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    getSupportFragmentManager().popBackStack(); //finish
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.replaceByFragment, new MainPage_Fragment())
                            .commit();
                    return true;


                case R.id.action_message:
                    getSupportFragmentManager().popBackStack(); //finish
                    Toast.makeText(HomePage.this, "العروض لسه حنفتح فرااااج", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.basket :
                    getSupportFragmentManager().popBackStack(); //finish
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.replaceByFragment, new Basket())
                            .commit();
                    return true ;

                case R.id.search :
                    getSupportFragmentManager().popBackStack(); //finish
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.replaceByFragment, new Search())
                            .commit();
                    return true ;

                case R.id.navigation_drawer :
                    open_Navigation_drawer();
                    return false ;
            }
            return false;
        }
    };

private void open_Navigation_drawer(){

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

}






