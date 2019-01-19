package com.alaa.microprocess.lrahtk.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.RecyclerItemTouchHelper;
import com.alaa.microprocess.lrahtk.Adapters.rec_Basket_Adapter;
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.FavHelper;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.View.Pay;
import com.alaa.microprocess.lrahtk.pojo.SqlProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Basket extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    RecyclerView recyclerView;
    LinearLayout sendOrder;
    Animation downtoup, uptodown;
    boolean last = false;
    FavHelper helper;
    SQLiteDatabase db;
    SharedPreferences preferences;
    String UserID, BasketTableName;
    List<SqlProduct> sqlProduct;
    TextView txTotal;
    rec_Basket_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_basket, container, false);
        HomePage.texttoolbar.setText(getString(R.string.buying));
        HomePageContract.viewMain viewMain = (HomePageContract.viewMain) getActivity();

        if (viewMain != null) {


            viewMain.showToobar();

        }


        recyclerView = v.findViewById(R.id.basket_rec);
        sendOrder = v.findViewById(R.id.sendOrder);
        txTotal = v.findViewById(R.id.total);
        sqlProduct = new ArrayList<>();


        downtoup = AnimationUtils.loadAnimation(getActivity(), R.anim.downtoup);
        uptodown = AnimationUtils.loadAnimation(getActivity(), R.anim.uptodown);


        this.helper = new FavHelper(getActivity());
        this.db = helper.getWritableDatabase();
        preferences = getActivity().getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        if (preferences.getString("AreInOrNot", "").equals("IN")) {


            UserID = preferences.getString("id", "");
            BasketTableName = "B" + UserID;
            //لازم السطر ده
            helper.CreateBasketTable(BasketTableName);
        }
        //get List
        getBasketList();
        //Get Total in Button .
        TotalPrice();


        //Configrations
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        //  Recycler adapter .
        adapter = new rec_Basket_Adapter(getActivity(), BasketTableName, sqlProduct,false);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1) {

                    if (!last) {
                        last = true;
                        sendOrder.setVisibility(View.VISIBLE);
                        sendOrder.startAnimation(downtoup);
                    }

                } else if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {

                    if (!last) {
                        last = true;
                        sendOrder.setVisibility(View.VISIBLE);
                        sendOrder.startAnimation(downtoup);
                    }

                } else {

                    if (last) {
                        sendOrder.setVisibility(View.GONE);
                        last = false;
                    }


                }

            }


        });

        //scroll to the end
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (adapter.getItemCount() > 0) {
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                }

            }
        });

        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Double.parseDouble(txTotal.getText().toString()) != 0) {

                    Intent intent = new Intent(getActivity(), Pay.class);
                    startActivity(intent);

                }
            }
        });


        return v;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        int deleted = db.delete(BasketTableName, helper.ID + " = ? ", new String[]{sqlProduct.get(position).getSqlID()});
        if (deleted > 0) {

            sqlProduct.clear();
            recyclerView.removeAllViews();

            getBasketList();
            //  Recycler adapter .
            adapter = new rec_Basket_Adapter(getActivity(), BasketTableName, sqlProduct,false);
            adapter.notifyDataSetChanged();
            //recyclerView.setAdapter(adapter);


            //get total again
            TotalPrice();


        }


    }


    public void TotalPrice() {
        double Total = 0;
        //get all basket .
        String[] Cols = {FavHelper.BasketQuantity, FavHelper.prices};
        Cursor Pointer = db.query(BasketTableName, Cols, null, null, null, null, null);

        while (Pointer.moveToNext()) {

            double pricePerQuantity = Integer.parseInt(Pointer.getString(0)) * Pointer.getDouble(1);
            Total += pricePerQuantity;

        }

        txTotal.setText(Total + "");

    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Update");
        intentFilter.addAction("Refresh");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), "Update")) {
                TotalPrice();

            } else if (Objects.equals(intent.getAction(), "Refresh")) {

                sqlProduct.clear();
                recyclerView.removeAllViews();

                getBasketList();
                //  Recycler adapter .
                adapter = new rec_Basket_Adapter(getActivity(), BasketTableName, sqlProduct,false);
                adapter.notifyDataSetChanged();
                //recyclerView.setAdapter(adapter);



                //get total again
                TotalPrice();
            }


        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            try {
                getActivity().unregisterReceiver(broadcastReceiver);
            }
            catch (Exception e)
            {

            }
        }

    }

    public void getBasketList() {
        //get all basket .
        String[] Cols = {FavHelper.ID, FavHelper.BasketName, FavHelper.BasketID, FavHelper.BasketQuantity, FavHelper.Brand, FavHelper.Image_Url, FavHelper.prices};
        Cursor Pointer = db.query(BasketTableName, Cols, null, null, null, null, null);

        while (Pointer.moveToNext()) {
            SqlProduct product = new SqlProduct(Pointer.getString(0), Pointer.getString(1), Pointer.getString(2), Pointer.getString(3)
                    , Pointer.getString(4), Pointer.getString(5), Pointer.getDouble(6));
            sqlProduct.add(product);

        }
    }

}