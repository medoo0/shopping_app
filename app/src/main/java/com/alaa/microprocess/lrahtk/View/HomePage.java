package com.alaa.microprocess.lrahtk.View;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alaa.microprocess.lrahtk.Adapters.Rec_Nav_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.Fragment.Basket;
import com.alaa.microprocess.lrahtk.Fragment.Featured;
import com.alaa.microprocess.lrahtk.Fragment.Gift;
import com.alaa.microprocess.lrahtk.Fragment.MainPage_Fragment;
import com.alaa.microprocess.lrahtk.Fragment.Search;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.pojo.Categories;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

        TextView logout , settings;


        Toolbar toolbar;
        Rec_Nav_Adapter postAdapter;
        BottomNavigationView BottomNavigationView;

        int dp200_To_pixel = 0 ;
        boolean NavIsOpened = false ;
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        ImageView gotoPersonalData;


        String email , id , name , phone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        gotoPersonalData = findViewById(R.id.gotoPersonalData);
        gotoPersonalData.setOnClickListener(this);
        logout   = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);
        toolbar    = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView =  findViewById(R.id.custom_bottom_navigation);
        texttoolbar = findViewById(R.id.texttoolbar);
//        BottomNavigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        logout.setOnClickListener(this);
        settings.setOnClickListener(this);
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




        ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);
        Call<List<com.alaa.microprocess.lrahtk.pojo.Categories>> call = client.getCategories();
        call.enqueue(new Callback<List<com.alaa.microprocess.lrahtk.pojo.Categories>>() {
            @Override
            public void onResponse(@NonNull Call<List<Categories>> call, @NonNull Response<List<Categories>> response) {

                List<Categories> parent = new ArrayList<>();
                List<Categories> childs = new ArrayList<>();
                 for (int i = 0; i < response.body().size(); i++) {

                    if (response.body().get(i).getParent() == null) {
                        parent.add(response.body().get(i));
                    }
                    else {

                        childs.add(response.body().get(i));
                    }


                }

                if(!HomePage.this.isFinishing()) {
                    //adapter (Navigation Drawer)
                    postAdapter = new Rec_Nav_Adapter(parent, childs, HomePage.this, HomePage.this);
                    Rec_Nav.setLayoutManager(new LinearLayoutManager(HomePage.this));
                    Rec_Nav.setAdapter(postAdapter);


                }



            }

            @Override
            public void onFailure(@NonNull Call<List<Categories>> call, @NonNull Throwable t) {

            }
        });











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





    @Override
    public void onClick(View v) {

        if (v == logout){

            editor.putString("AreInOrNot","");
            editor.putString("Email","");
            editor.putString("id","");
            editor.putString("Phone","");
            editor.putString("Name","");
            editor.putString("Token","");
            editor.apply();

            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
            finish();

            //  finsh this activity and go back ///     حنشوف بعد كدا لو فيه حاجه متعلقه بالسرفر بالخروج ولا لا



        }

        if (v == settings){

            Intent intent = new Intent(this , MyPersonalPage.class);
            intent.putExtra("Email",email);
            intent.putExtra("userName",name);
            intent.putExtra("id",id);
            intent.putExtra("phone",phone);
            startActivity(intent);

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
    public void whenClickonIteminRecNav(String id, String s) {

        Intent intent = new Intent(this , Product_Activity.class);
        intent.putExtra("id",id);
        intent.putExtra("name",s);
        startActivity(intent);


    }

    @Override
    public void hideToolbar() {

        if (toolbar!=null){


            setSupportActionBar(toolbar);
            getSupportActionBar().hide();

        }





    }

    @Override
    public void showToobar() {

        if (toolbar!=null){

            setSupportActionBar(toolbar);
            getSupportActionBar().show();

        }
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
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.replaceByFragment, new Featured())
                            .commit();
                    return true;

                case R.id.gifts:
                    getSupportFragmentManager().popBackStack(); //finish
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.replaceByFragment, new Gift())
                            .commit();
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

//                case R.id.navigation_drawer :
//                    open_Navigation_drawer();
//                    return false ;
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






