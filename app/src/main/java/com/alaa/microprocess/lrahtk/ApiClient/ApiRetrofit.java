package com.alaa.microprocess.lrahtk.ApiClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by microprocess on 2018-09-19.
 */

public class ApiRetrofit {


    public static Retrofit retrofit1 = null;
    static String API_BASE_URL = "http://142.93.207.35:8080/api/";

    static public Retrofit getRetrofit(){
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit1==null){
            retrofit1 =
                    new Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .client(client)
                            .addConverterFactory(
                                    GsonConverterFactory.create(gson)
                            ).build();
        }
        return retrofit1 ;
    }

}
