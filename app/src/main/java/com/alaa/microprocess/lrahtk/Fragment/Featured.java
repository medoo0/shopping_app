package com.alaa.microprocess.lrahtk.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.View.Product_Activity;
import com.alaa.microprocess.lrahtk.pojo.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Featured extends Fragment {


    public Featured() {
        // Required empty public constructor
    }

    RecyclerView rec_offers;
    AnimatedDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_featured, container, false);
         rec_offers = v.findViewById(R.id.rec_offers);
        HomePage.texttoolbar.setText(getString(R.string.offers));
        dialog = new AnimatedDialog(getActivity());
        dialog.ShowDialog();
        showItemsinREC();


         return v ;
    }

    public void showItemsinREC(){


        ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);
        Call<List<Products>> call = client.getProducts();
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NonNull Call<List<Products>> call, @NonNull Response<List<Products>> response) {

                List<Products> filterProduct = new ArrayList<>();
                for (int i = 0 ; i<response.body().size(); i++){

                    if(response.body().get(i).isFeatured()){

                        filterProduct.add(response.body().get(i));

                    }

                }


                //adapter
                Rec_Items_Adapter  adapter = new Rec_Items_Adapter(filterProduct,getActivity());
                adapter.notifyDataSetChanged();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
                rec_offers.setLayoutManager(gridLayoutManager);
                rec_offers.setAdapter(adapter);
                dialog.Close_Dialog();

            }

            @Override
            public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {
                dialog.Close_Dialog();
            }
        });





    }
}
