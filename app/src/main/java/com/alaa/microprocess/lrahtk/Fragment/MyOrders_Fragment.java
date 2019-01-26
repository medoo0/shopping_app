package com.alaa.microprocess.lrahtk.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.Order_Adapter;
import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.pojo.MyOrder;
import com.alaa.microprocess.lrahtk.pojo.Products;

import java.io.IOException;
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

public class MyOrders_Fragment extends Fragment {
    int PerPage = 10;
    int Page = 0 ;
    Order_Adapter order_adapter;
    RecyclerView rec_order;
    boolean isThatyourfirstTime = true , ListaFinished = false;
    ProgressBar progress;
    LinearLayoutManager linearLayoutManager;
    SharedPreferences preferences ;
    String token  ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view     = inflater.inflate(R.layout.myorder_fragment,container,false);

        rec_order     = view.findViewById(R.id.rec_order);
        progress = view.findViewById(R.id.progress);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        HomePage.texttoolbar.setText("طلباتي");
        preferences = getActivity().getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);
        if (preferences.getString("AreInOrNot","").equals("IN")) {

            token = preferences.getString("Token", "");
        }

        showItems_In_rec();

        rec_order.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1) {
                    if(!ListaFinished) {
                        progress.setVisibility(View.VISIBLE);
                        Page++;
                        showItems_In_rec();

                    }
                }
            }
        });

        return view;
    }





    public void showItems_In_rec(){

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
        map.put("perPage", String.valueOf(PerPage));
        map.put("page", String.valueOf(Page));


        ApiMethod service = retrofit.create(ApiMethod.class);
        Call<List<MyOrder>> call = service.getMyOrder(map);
        call.enqueue(new Callback<List<MyOrder>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyOrder>> call, @NonNull Response<List<MyOrder>> response) {

                if(response.isSuccess()) {
                    if(getActivity() != null) {
                        if (!getActivity().isFinishing()) {
                            progress.setVisibility(View.GONE);

                            if (isThatyourfirstTime) {

                                //adapter

                                order_adapter = new Order_Adapter(getActivity(), response.body());
                                order_adapter.notifyDataSetChanged();
                                rec_order.setLayoutManager(linearLayoutManager);
                                rec_order.setAdapter(order_adapter);
                                progress.setVisibility(View.GONE);
                                isThatyourfirstTime = false;
                            } else {
                                order_adapter.addMoreItems(response.body());
                                progress.setVisibility(View.GONE);

                                if (response.body().size() < PerPage) {
                                    ListaFinished = true;
                                }
                            }
                        }
                    }
                }
                else {
                    if(getActivity() != null) {
                     if(!getActivity().isFinishing()) {
                         progress.setVisibility(View.GONE);
                         Toast.makeText(getActivity(), "حدث خطأ الرجاء المحاولة مرة اخري .", Toast.LENGTH_LONG).show();
                     }
                    }
                }



            }

            @Override
            public void onFailure(@NonNull Call<List<MyOrder>> call, @NonNull Throwable t) {
                if(getActivity() != null) {
                    if(!getActivity().isFinishing()) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "تأكد من اتصالك  .  ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



    }
}
