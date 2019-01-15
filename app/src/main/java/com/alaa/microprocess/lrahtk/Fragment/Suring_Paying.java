package com.alaa.microprocess.lrahtk.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.Contract.PayScreenContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.FavHelper;

public class Suring_Paying extends Fragment implements View.OnClickListener {

    RecyclerView myRec;
    Button sure;
    FavHelper helper;
    SQLiteDatabase db;
    SharedPreferences preferences;
    String UserID, BasketTableName , address;
    double Total = 0 , extra = 0 ;
    TextView txTotal1 , txAddress , finalTotal;

    @SuppressLint("ValidFragment")
    public Suring_Paying(String address) {
        this.address = address;
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

        txAddress.setText(address);
        TotalPrice();
        double finaltotal =  extra + Total ;
        finalTotal.setText( finaltotal +" L.E");

        return view;
    }



    void showItems(){



    }

    @Override
    public void onClick(View v) {

        if (v == sure){


            PayScreenContract.payView payView = (PayScreenContract.payView) getActivity();

            if (payView!=null){

                payView.showNextLastFragmentPayingFragment();

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
}
