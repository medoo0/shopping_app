package com.alaa.microprocess.lrahtk.ApiClient;

import com.alaa.microprocess.lrahtk.pojo.LoginForm;
import com.alaa.microprocess.lrahtk.pojo.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by microprocess on 2018-09-19.
 */

public interface ApiMethod {

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> insertData(@Field("createdAt") String createdAt,
                                      @Field("updatedAt") String updatedAt,
                                      @Field("id") String id,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("name") String name,
                                      @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginForm> login(@Field("createdAt") String createdAt,
                          @Field("updatedAt") String updatedAt,
                          @Field("id") String id,
                          @Field("email") String email,
                          @Field("password") String password
    );

}
