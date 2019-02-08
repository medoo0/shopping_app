package com.alaa.microprocess.lrahtk.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.Dialog.AlertDialog;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.FavHelper;
import com.alaa.microprocess.lrahtk.pojo.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

@SuppressLint("ValidFragment")
public class Paying_Fragment extends Fragment  implements View.OnClickListener{


    RadioButton sound;
    Button btn_pay , apply;
    String address, UserID, BasketTableName ,token ;
    FavHelper helper;
    SQLiteDatabase db;
    double Total = 0 , finaltotal;
    SharedPreferences preferences;
    List<com.alaa.microprocess.lrahtk.pojo.Basket> BasketList;
    TextView txTotal ;
    EditText Coupon;
    AnimatedDialog dialog;
    String couponNumber = null;

    @SuppressLint("ValidFragment")
    public Paying_Fragment(String address , double finaltotal) {
        this.address = address ;
        this.finaltotal =finaltotal;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view     = inflater.inflate(R.layout.paying_fragment_layout,container,false);

        sound        = view.findViewById(R.id.sound);
        btn_pay     =  view.findViewById(R.id.btn_pay);
        txTotal     = view.findViewById(R.id.txTotal);
        Coupon       = view.findViewById(R.id.edPay);
        apply       = view.findViewById(R.id.apply);

        dialog = new AnimatedDialog(getActivity());
        BasketList = new ArrayList<>();
        btn_pay.setOnClickListener(this);

        this.helper = new FavHelper(getActivity());
        this.db = helper.getWritableDatabase();
        preferences = getActivity().getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        if (preferences.getString("AreInOrNot", "").equals("IN")) {

            token = preferences.getString("Token","");
            UserID = preferences.getString("id", "");
            BasketTableName = "B" + UserID;
            //لازم السطر ده
            helper.CreateBasketTable(BasketTableName);
        }

        getBasketList();
        TotalPrice();

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                couponNumber = Coupon.getText().toString();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view  == btn_pay){

            final PayScreenContract.payView payView = (PayScreenContract.payView) getActivity();
            dialog.ShowDialog();

            if (payView!=null){




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


                Order order = new Order();
                order.setAddress(address);
                order.setCoupon(couponNumber);
                order.setBasket(BasketList);

                Call<Order> call = service.ORDER_CALL(order);

                call.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {

                        if(response.isSuccess()){

                            dialog.Close_Dialog();
                            payView.showThanksOrder(response.body().getId());


                            //delete Basket
                            db.execSQL("delete from "+BasketTableName );
                            Intent intent = new Intent("Refresh");
                            getActivity().sendBroadcast(intent);

                        }
                        else {

                            dialog.Close_Dialog();
                            Toast.makeText(getActivity(), " حدثت  مشكلة الرجاء اعادة المحاولة .", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure( @NonNull Call<Order> call, @NonNull Throwable t) {

                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.Close_Dialog();
                    }
                });


            }
        }
    }

    public void TotalPrice() {

//        //get all basket .
//        String[] Cols = {FavHelper.BasketQuantity, FavHelper.prices};
//        Cursor Pointer = db.query(BasketTableName, Cols, null, null, null, null, null);
//
//        while (Pointer.moveToNext()) {
//
//            double pricePerQuantity = Integer.parseInt(Pointer.getString(0)) * Pointer.getDouble(1);
//            Total += pricePerQuantity;
//
//        }

        txTotal.setText(finaltotal + " L.E");

    }
    public void getBasketList() {
        //get all basket .
        String[] Cols = {FavHelper.BasketID, FavHelper.BasketQuantity};
        Cursor Pointer = db.query(BasketTableName, Cols, null, null, null, null, null);

        while (Pointer.moveToNext()) {
            com.alaa.microprocess.lrahtk.pojo.Basket product = new com.alaa.microprocess.lrahtk.pojo.Basket();
            product.setId(Pointer.getString(0));
            product.setQuantity(Pointer.getInt(1));
            BasketList.add(product);

        }
    }
}
