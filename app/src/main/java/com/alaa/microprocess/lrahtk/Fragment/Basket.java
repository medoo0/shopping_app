package com.alaa.microprocess.lrahtk.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.Adapters.RecyclerItemTouchHelper;
import com.alaa.microprocess.lrahtk.Adapters.rec_Basket_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.FavHelper;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.View.Pay;
import com.alaa.microprocess.lrahtk.pojo.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Basket extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    RecyclerView recyclerView ;
    LinearLayout sendOrder;
    Animation downtoup , uptodown;
    boolean last = false ;
    FavHelper helper;
    SQLiteDatabase db ;
    SharedPreferences preferences ;
    String UserID  , BasketTableName ;
    ArrayList<String> ProductID_List ;
    ArrayList<String> Quantity_List ;
    AnimatedDialog dialog ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         View v = inflater.inflate(R.layout.fragment_basket, container, false);
         HomePage.texttoolbar.setText(getString(R.string.buying));
         HomePageContract.viewMain viewMain = (HomePageContract.viewMain) getActivity();
         if (viewMain!=null){


            viewMain.showToobar();

         }

         ProductID_List = new ArrayList<>();
         Quantity_List = new ArrayList<>();
         recyclerView = v.findViewById(R.id.basket_rec);
         sendOrder    = v.findViewById(R.id.sendOrder);
         dialog = new AnimatedDialog(getActivity());
         dialog.ShowDialog();

         downtoup = AnimationUtils.loadAnimation(getActivity(),R.anim.downtoup);
         uptodown = AnimationUtils.loadAnimation(getActivity(),R.anim.uptodown);




        this.helper   = new FavHelper(getActivity());
        this.db       = helper.getWritableDatabase();
        preferences = getActivity().getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        if (preferences.getString("AreInOrNot","").equals("IN")){


            UserID      = preferences.getString("id","");
            BasketTableName = "B"+UserID;
            helper.CreateBasketTable(BasketTableName);
        }


        //get all FavID .
        String [] Cols = {FavHelper.BasketID,FavHelper.BasketQuantity};
        Cursor Pointer = db.query(BasketTableName,Cols,null,null,null,null,null);

        while (Pointer.moveToNext()){

            ProductID_List.add(Pointer.getString(0));
            Quantity_List.add(Pointer.getString(1));

        }




       //Configrations
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        showItemsinREC();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if ( mLayoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount()-1) {

                    if(!last) {
                        last = true;
                        sendOrder.setVisibility(View.VISIBLE);
                        sendOrder.startAnimation(downtoup);
                    }

                }

                else {

                    if(last) {
                        sendOrder.setVisibility(View.GONE);
                        last = false;
                    }


                }

            }



        });



        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Pay.class);
                startActivity(intent);

            }
        });


        return v ;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Toast.makeText(getActivity(), ""+direction, Toast.LENGTH_SHORT).show();
    }

    public void showItemsinREC(){


        ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);
        Call<List<Products>> call = client.getProducts();
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NonNull Call<List<Products>> call, @NonNull final Response<List<Products>> response) {

                final List<Products> filterProduct = new ArrayList<>();
                for (int i = 0 ; i<response.body().size(); i++){

                    for (int j = 0 ; j < ProductID_List.size(); j++ ) {

                        if(response.body().get(i).getId().equals(ProductID_List.get(j))){
                            filterProduct.add(response.body().get(i));
                        }

                    }
                    if(i == response.body().size() - 1){
                        dialog.Close_Dialog();
                    }
                }


                //Recycler adapter .
                final rec_Basket_Adapter adapter = new rec_Basket_Adapter(getActivity(),filterProduct,Quantity_List);
                recyclerView.setAdapter(adapter);


                //scroll to the end
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if(filterProduct.size() > 0) {
                            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }

                    }
                });


            }

            @Override
            public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {
                dialog.Close_Dialog();
            }
        });





    }

}
