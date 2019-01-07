package com.alaa.microprocess.lrahtk.ApiClient;

import com.alaa.microprocess.lrahtk.pojo.Categories;
import com.alaa.microprocess.lrahtk.pojo.LoginForm;
import com.alaa.microprocess.lrahtk.pojo.Products;
import com.alaa.microprocess.lrahtk.pojo.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by microprocess on 2018-09-19.
 */

public interface ApiMethod {

    @FormUrlEncoded
    @POST("users/register")
    Call<RegisterResponse> insertData(@Field("createdAt") String createdAt,
                                      @Field("updatedAt") String updatedAt,
                                      @Field("id") String id,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("name") String name,
                                      @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("users/login")
    Call<LoginForm> login(@Field("createdAt") String createdAt,
                          @Field("updatedAt") String updatedAt,
                          @Field("id") String id,
                          @Field("email") String email,
                          @Field("password") String password
    );


    @GET("categories")
    Call<List<Categories>> getCategories();

    @GET("products")
    Call<List<Products>> getProducts();


}
