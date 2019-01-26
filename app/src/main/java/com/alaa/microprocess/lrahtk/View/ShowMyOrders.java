package com.alaa.microprocess.lrahtk.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.Order_Adapter;
import com.alaa.microprocess.lrahtk.Adapters.ShowMyOrder_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.pojo.Basket;
import com.alaa.microprocess.lrahtk.pojo.Basket2;
import com.alaa.microprocess.lrahtk.pojo.MyOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowMyOrders extends AppCompatActivity {
RecyclerView Rec ;
ImageView backhome;
TextView txToolbar;
List<Basket2> basket2;
ProgressBar progress;
String ID ,token ;
SharedPreferences preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_orders);
        Rec = findViewById(R.id.Rec);
        txToolbar = findViewById(R.id.txToolbar);
        backhome = findViewById(R.id.backhome);
        progress = findViewById(R.id.progress);
        basket2 = new ArrayList<>();
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        preferences = getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        if (preferences.getString("AreInOrNot", "").equals("IN")) {

            token = preferences.getString("Token", "");
        }


        Intent intent = getIntent();
        if(intent != null){
            ID = intent.getStringExtra("id");
            showItems_In_rec(ID);
        }
    }


    public void showItems_In_rec(final String ID){

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

        Map<String,String> map = new HashMap<>();
        map.put("perPage", String.valueOf(1000));
        map.put("page", String.valueOf(0));


        ApiMethod service = retrofit.create(ApiMethod.class);
        Call<List<MyOrder>> call = service.getMyOrder(map);
        call.enqueue(new Callback<List<MyOrder>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyOrder>> call, @NonNull Response<List<MyOrder>> response) {

                if(response.isSuccess()) {

                        if (!ShowMyOrders.this.isFinishing()) {
                            progress.setVisibility(View.GONE);

                            List<Basket2>  basket2 =  new ArrayList<>();

                            for(int i = 0 ; i < response.body().size() ; i++){
                                if(response.body().get(i).getId().equals(ID)){
                                    basket2.addAll(response.body().get(i).getBasket());
                                    txToolbar.setText("Total : " + response.body().get(i).getTotal() +" L.E" );
                                }
                            }

                                //adapter
                                ShowMyOrder_Adapter Morder_adapter = new ShowMyOrder_Adapter(ShowMyOrders.this,basket2);
                                Morder_adapter.notifyDataSetChanged();
                                Rec.setLayoutManager(new LinearLayoutManager(ShowMyOrders.this));
                                Rec.setAdapter(Morder_adapter);
                                progress.setVisibility(View.GONE);


                        }
                    }

                else {

                        if(!ShowMyOrders.this.isFinishing()) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(ShowMyOrders.this, "حدث خطأ الرجاء المحاولة مرة اخري .", Toast.LENGTH_LONG).show();
                        }

                }



            }

            @Override
            public void onFailure(@NonNull Call<List<MyOrder>> call, @NonNull Throwable t) {

                    if(!ShowMyOrders.this.isFinishing()) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(ShowMyOrders.this, "تأكد من اتصالك  .  ", Toast.LENGTH_LONG).show();
                    }

            }
        });



    }
}
