package com.alaa.microprocess.lrahtk.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Comments_Adapter;
import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.Adapters.Rec_Nav_Adapter2;
import com.alaa.microprocess.lrahtk.Adapters.SlideShowAdapter;
import com.alaa.microprocess.lrahtk.Adapters.rec_Basket_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.View.MainActivity;
import com.alaa.microprocess.lrahtk.View.Product_Activity;
import com.alaa.microprocess.lrahtk.View.ShowProduct;
import com.alaa.microprocess.lrahtk.pojo.Categories;
import com.alaa.microprocess.lrahtk.pojo.Comments;
import com.alaa.microprocess.lrahtk.pojo.Products;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainPage_Fragment extends Fragment {


    @BindView(R.id.yellowCircle_rec)
    RecyclerView yellowCircle_rec ;

    @BindView(R.id.recitems)
    RecyclerView recitems;

    @BindView(R.id.ViewPager)
    ViewPager viewPage;

    ImageView Search_image;

    AsyncTask asyncTask;

    int pos ; //global
    @BindView(R.id.Indictor)
    CircleIndicator circleIndicator;

    @BindView(R.id.Btn_offers)
    Button Btn_offers;
    SharedPreferences preferences ;
    String UserID , FavTableName , BasketTableName , token ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_page_frag_layout,container,false);
        ButterKnife.bind(this,v);

        HomePage.texttoolbar.setText("الرئيسية");

        HomePageContract.viewMain viewMain = (HomePageContract.viewMain) getActivity();

        if (viewMain!=null){


            viewMain.showToobar();

        }

        Search_image  = v.findViewById(R.id.search_image);


        preferences = getActivity().getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        if (preferences.getString("AreInOrNot","").equals("IN")){

            token = preferences.getString("Token","");
            if(token.isEmpty()){
               HomePage.logout();
                getActivity().finish();
                Intent intent = new Intent(getActivity() , MainActivity.class);
                startActivity(intent);
            }

            UserID      = preferences.getString("id","");
            FavTableName = "T"+UserID;
            BasketTableName = "B"+UserID;
        }

        ShowIteminYellowRec();
        ShowTopProduct();


        Btn_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack(); //finish
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.replaceByFragment, new Gift())
                        .commit();
                HomePage.select_bottomNav(R.id.gifts);
            }
        });

        Search_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack(); //finish
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.replaceByFragment, new Search())
                        .commit();
                HomePage.select_bottomNav(R.id.search);
            }
        });

        if(getActivity() != null) {

            viewPage.setAdapter(new SlideShowAdapter(getActivity()));  // Set Adapter
            circleIndicator.setViewPager(viewPage);


            viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    pos = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            final SlideShowAdapter slideShowAdapter = new SlideShowAdapter(getActivity());

            asyncTask = new AsyncTask<Integer, Integer, Void>() {

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    pos = viewPage.getCurrentItem();
                }

                @Override
                protected Void doInBackground(Integer... integers) {
                    boolean still = !false;
                    while (still) {

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pos++;
                        publishProgress(pos);

                    }

                    return null;
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                    if (values[0] == slideShowAdapter.getCount()) {
                        pos = 0;
                        viewPage.setCurrentItem(pos, true);
                    } else {
                        viewPage.setCurrentItem(values[0], true);
                    }

                }
            }.execute();

        }

        return v;
    }



public void ShowIteminYellowRec(){

    //adapter (Yellow Circles)
    Rec_Nav_Adapter2 rec_items_adapter = new Rec_Nav_Adapter2(HomePage.getChildren(),getActivity(), (HomePageContract.viewMain) getActivity());
    rec_items_adapter.notifyDataSetChanged();
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),5);
    yellowCircle_rec.hasFixedSize();
    yellowCircle_rec.setLayoutManager(gridLayoutManager);
    yellowCircle_rec.setAdapter(rec_items_adapter);

//    ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);
//    Call<List<Categories>> call = client.getCategories();
//    call.enqueue(new Callback<List<Categories>>() {
//        @Override
//        public void onResponse(@NonNull Call<List<Categories>> call, @NonNull Response<List<Categories>> response) {
//
//            List<Categories> parent = new ArrayList<>();
//            List<Categories> childs = new ArrayList<>();
//            for (int i = 0; i < response.body().size(); i++) {
//
//                if (response.body().get(i).getParent() == null) {
//                    parent.add(response.body().get(i));
//                }
//                else {
//
//                    childs.add(response.body().get(i));
//                }
//
//
//            }
//
//            //adapter (Yellow Circles)
//            Rec_Nav_Adapter2 rec_items_adapter = new Rec_Nav_Adapter2(childs,getActivity(), (HomePageContract.viewMain) getActivity());
//            rec_items_adapter.notifyDataSetChanged();
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),5);
//            yellowCircle_rec.setLayoutManager(gridLayoutManager);
//            yellowCircle_rec.setAdapter(rec_items_adapter);
//
//        }
//
//        @Override
//        public void onFailure(@NonNull Call<List<Categories>> call, @NonNull Throwable t) {
//
//        }
//    });
}


    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Yellow");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), "Yellow")) {

                ShowIteminYellowRec();

            }


        }
    };

    public void ShowTopProduct (){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(interceptor);
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                request = request
                        .newBuilder()
                        .addHeader("Content-Type","application/json")
                        .addHeader("Authorization", "Bearer " + token)
                        .build();

                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRetrofit.API_BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiMethod service = retrofit.create(ApiMethod.class);
        Call<List<Products>> call = service.getTopProducts();
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NonNull Call<List<Products>> call, @NonNull Response<List<Products>> response) {
                if(response.isSuccess()) {



                        //adapter
                    Rec_Items_Adapter   adapter = new Rec_Items_Adapter(response.body(), getActivity());
                    adapter.notifyDataSetChanged();
                    recitems.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    recitems.setAdapter(adapter);


                }
                else {

                   // Toast.makeText(getActivity(), "حدث خطأ الرجاء المحاولة مرة اخري .", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {

                //Toast.makeText(getActivity(), "الرجاء التحقق من اتصالك .", Toast.LENGTH_LONG).show();
            }
        });




    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStop() {
        super.onStop();

        if (broadcastReceiver != null) {
            try {
                getActivity().unregisterReceiver(broadcastReceiver);
            }
            catch (Exception e){

            }
        }
    }
}
