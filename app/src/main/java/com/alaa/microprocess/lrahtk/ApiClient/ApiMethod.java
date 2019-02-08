package com.alaa.microprocess.lrahtk.ApiClient;

import com.alaa.microprocess.lrahtk.pojo.Categories;
import com.alaa.microprocess.lrahtk.pojo.Comments;
import com.alaa.microprocess.lrahtk.pojo.Gift;
import com.alaa.microprocess.lrahtk.pojo.LoginForm;
import com.alaa.microprocess.lrahtk.pojo.MyOrder;
import com.alaa.microprocess.lrahtk.pojo.Order;
import com.alaa.microprocess.lrahtk.pojo.PostComment;
import com.alaa.microprocess.lrahtk.pojo.Products;
import com.alaa.microprocess.lrahtk.pojo.RegisterResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


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



    @POST("orders")
    Call<Order> ORDER_CALL (
            @Body Order order
    );

    @GET("categories")
    Call<List<Categories>> getCategories();

    @GET("products")
    Call<List<Products>> getProducts();

    @GET("products")
    Call<List<Products>> getProducts(@QueryMap Map<String, String> params);

    @POST("review/{Id}")
    Call<PostComment> PutComment(@Path("Id") String Id, @Body PostComment postComment);

    @GET("review/{Id}")
    Call<List<Comments>> getAllComments(@Path("Id") String Id,@QueryMap Map<String, String> params);

    @GET("products/top")
    Call<List<Products>> getTopProducts();

    @GET("orders/user")
    Call<List<MyOrder>> getMyOrder(@QueryMap Map<String, String> params);

//    @Multipart
//    @POST("orders/{id}/gift")
//    Call <ResponseBody> GiftPost (
//            @Path("id") String id,
//            @Body Gift gift,
//            @Part("avatar") MultipartBody.Part file
//            );

}
