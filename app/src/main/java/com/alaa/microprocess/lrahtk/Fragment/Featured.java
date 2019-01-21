package com.alaa.microprocess.lrahtk.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.View.Product_Activity;
import com.alaa.microprocess.lrahtk.pojo.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Featured extends Fragment {
    GridLayoutManager gridLayoutManager;
    int PerPage = 10;
    int Page = 0 ;
    Rec_Items_Adapter adapter;
    boolean isThatyourfirstTime = true , ListaFinished = false;
    ProgressBar progress;
    RecyclerView rec_offers;
    AnimatedDialog dialog;
    ArrayList<String> CategoriesIDs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_featured, container, false);
         rec_offers = v.findViewById(R.id.rec_offers);
        progress = v.findViewById(R.id.progress);
        CategoriesIDs = new ArrayList<>();
        PutAllCategoriesInCategoriesIDs();
        HomePage.texttoolbar.setText(getString(R.string.offers));
        dialog = new AnimatedDialog(getActivity());
        dialog.ShowDialog();

        showItemsinREC(SerializeCategories(CategoriesIDs));
        gridLayoutManager = new GridLayoutManager(getActivity(),2);

        rec_offers.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1) {
                    if(!ListaFinished) {
                        progress.setVisibility(View.VISIBLE);
                        Page++;
                        showItemsinREC(SerializeCategories(CategoriesIDs));

                    }
                }
            }
        });

         return v ;
    }

    public void showItemsinREC(String IDs ){

        Map<String,String> map = new HashMap<>();
        map.put("category",IDs);
        map.put("perPage", String.valueOf(PerPage));
        map.put("page", String.valueOf(Page));
        map.put("featured","true");
        ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);
        Call<List<Products>> call = client.getProducts(map);
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NonNull Call<List<Products>> call, @NonNull Response<List<Products>> response) {

                if(response.isSuccess()) {
                    dialog.Close_Dialog();

                    if (isThatyourfirstTime) {

                        //adapter
                        adapter = new Rec_Items_Adapter(response.body(),getActivity());
                        adapter.notifyDataSetChanged();

                        rec_offers.setLayoutManager(gridLayoutManager);
                        rec_offers.setAdapter(adapter);
                        dialog.Close_Dialog();
                        isThatyourfirstTime = false;
                    }
                    else {
                        adapter.addMoreItems(response.body());
                        progress.setVisibility(View.GONE);

                        if(response.body().size() < PerPage){
                            ListaFinished = true ;
                        }
                    }
                }
                else {
                    dialog.Close_Dialog();
                    Toast.makeText(getActivity(), "حدث خطأ الرجاء المحاولة مرة اخري .", Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {
                dialog.Close_Dialog();
                Toast.makeText(getActivity(), "تأكد من اتصالك  .  ", Toast.LENGTH_LONG).show();
            }
        });





    }

    public String SerializeCategories(ArrayList<String> CategoriesIDs ){
        String Categories = "";
        for(int i = 0 ; i< CategoriesIDs.size() ; i++){

            if(i == CategoriesIDs.size()-1){
                Categories += CategoriesIDs.get(i);
            }
            else {
                Categories += CategoriesIDs.get(i) + ",";
            }

        }
        return Categories;
    }

    public void PutAllCategoriesInCategoriesIDs(){

        CategoriesIDs.clear();
        for(int i = 0 ; i < HomePage.getChildren().size(); i++){
            CategoriesIDs.add(HomePage.getChildren().get(i).getId());
            //  Log.d("Cate", CategoriesIDs.get(i) + " \n");
        }

    }
}
