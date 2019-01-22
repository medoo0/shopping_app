package com.alaa.microprocess.lrahtk.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.FavHelper;
import com.alaa.microprocess.lrahtk.View.MyPersonalPage;
import com.alaa.microprocess.lrahtk.View.Product_Activity;
import com.alaa.microprocess.lrahtk.pojo.Products;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favourite_Fragment extends Fragment {


    RecyclerView rec_favourite;
    FavHelper helper;
    SQLiteDatabase db ;
    SharedPreferences preferences ;
    String UserID , TableName ;
    ArrayList<String> fav_id_list;
    Rec_Items_Adapter adapter;
    AnimatedDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view  = inflater.inflate(R.layout.favourite_layout,container,false);
        rec_favourite = view.findViewById(R.id.rec_favourite);
        fav_id_list = new ArrayList<>();
        dialog = new AnimatedDialog(getActivity());
        this.helper   = new FavHelper(getActivity());
        this.db       = helper.getWritableDatabase();
        preferences = getActivity().getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        if (preferences.getString("AreInOrNot","").equals("IN")){


            UserID      = preferences.getString("id","");
            TableName = "T"+UserID;
            helper.CreateFavTable(TableName);
        }


        //get all FavID .
        String [] Cols = {FavHelper.FavID};
        Cursor Pointer = db.query(TableName,Cols,null,null,null,null,null); // if there are two conditions use "owner=? and price=?"

        while (Pointer.moveToNext()){

            fav_id_list.add(Pointer.getString(0));

        }


        MyPersonalPage.startProgress();
        showItemsinREC();








       return view;
    }
    public void showItemsinREC(){


        ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);
        Call<List<Products>> call = client.getProducts();
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NonNull Call<List<Products>> call, @NonNull Response<List<Products>> response) {

                List<Products> filterProduct = new ArrayList<>();
                for (int i = 0 ; i<response.body().size(); i++){

                   for (int j = 0 ; j < fav_id_list.size(); j++ ) {

                       if(response.body().get(i).getId().equals(fav_id_list.get(j))){
                           filterProduct.add(response.body().get(i));
                       }

                   }
                    if(i == response.body().size() - 1){
                        if(!getActivity().isFinishing()) {
                            MyPersonalPage.endProgress();
                        }
                    }
                }


                //adapter
                adapter = new Rec_Items_Adapter(filterProduct,getActivity());
                adapter.notifyDataSetChanged();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
                rec_favourite.setLayoutManager(gridLayoutManager);
                rec_favourite.setAdapter(adapter);

            }

            @Override
            public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {
                if(!getActivity().isFinishing()) {
                    MyPersonalPage.endProgress();
                }
            }
        });





    }
}
