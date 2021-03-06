package com.alaa.microprocess.lrahtk.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.Adapters.rec_Basket_Adapter;
import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.FavHelper;
import com.alaa.microprocess.lrahtk.pojo.SqlProduct;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Suring_Paying extends Fragment implements View.OnClickListener {

    RecyclerView myRec;
    Button sure;
    FavHelper helper;
    SQLiteDatabase db;
    SharedPreferences preferences;
    String UserID, BasketTableName , address;
    double Total = 0 , extra = 0 , finaltotal =  0;
    TextView txTotal1 , txAddress , finalTotal , txextra;
    List<SqlProduct> sqlProduct;
    @SuppressLint("ValidFragment")
    public Suring_Paying(String address , double extra) {
        this.address = address;
        this.extra = extra;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view     = inflater.inflate(R.layout.suring_paying_layout,container,false);

       myRec        = view.findViewById(R.id.myRec);
        sure         = view.findViewById(R.id.sure);
        txTotal1 = view.findViewById(R.id.txTotal1);
        txAddress = view.findViewById(R.id.txAddress);
        finalTotal = view.findViewById(R.id.finalTotal);
        txextra = view.findViewById(R.id.extra);
        sqlProduct = new ArrayList<>();
        sure.setOnClickListener(this);


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

        txAddress.setText(address);
        //get total .
        TotalPrice();
        txextra.setText(String.valueOf(extra) + " L.E");
         finaltotal =  extra + Total ;
        finalTotal.setText( finaltotal +" L.E");

        //show item in Recycler View  .
        showItems();




        return view;
    }



   public void showItems(){
       //Configrations
       final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
       myRec.setLayoutManager(mLayoutManager);
       //  Recycler adapter .
       rec_Basket_Adapter adapter = new rec_Basket_Adapter(getActivity(), BasketTableName, sqlProduct,true);
       myRec.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        if (v == sure){


            PayScreenContract.payView payView = (PayScreenContract.payView) getActivity();

            if (payView!=null){

                payView.showNextLastFragmentPayingFragment(finaltotal);

            }

        }

    }

    public void TotalPrice() {

        //get all basket .
        String[] Cols = {FavHelper.BasketQuantity, FavHelper.prices};
        Cursor Pointer = db.query(BasketTableName, Cols, null, null, null, null, null);

        while (Pointer.moveToNext()) {

            double pricePerQuantity = Integer.parseInt(Pointer.getString(0)) * Pointer.getDouble(1);
            Total += pricePerQuantity;

        }

        txTotal1.setText(Total + " L.E");

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
