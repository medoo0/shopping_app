package com.alaa.microprocess.lrahtk.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.Adapters.Order_Adapter;
import com.alaa.microprocess.lrahtk.R;

public class MyOrders_Fragment extends Fragment {


    RecyclerView rec_order;
    TextView dataofProduct,idofProduct,whatqwayuserWantProduct;

    String [] orderTime = {"8-jun-2016","7-jun-2016","20-jun-2016","1-jun-2016"};
    String [] orderId   = {"ccccc5555","55ddftrt","aa11qqwit","whatway4444"};

    //  دي خاصه بالمستخد هوا عاوز الحاجه دي مستعجله ولا ايه نظامه
    String [] userwantordernoworafterThat   = {"Dispatched","OnWay","Dispatched","Delivered"};


    //  الحاجات دي حتتعرض مؤقتا لحد ما نشوف الداتا بتاعه ال apis


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view     = inflater.inflate(R.layout.myorder_fragment,container,false);

        rec_order     = view.findViewById(R.id.rec_order);

        // حنعرض حاجه مؤقته دلوقت   الفراج ده بتاع الطلبات لسه حنعرض الطلبات الي هوا طلبها في ريسيكلار فيو متنساس لسه مستنين ال APIS
        showItems_In_rec();

        return view;
    }





    public void showItems_In_rec(){

        Order_Adapter order_adapter = new Order_Adapter(getActivity(),orderTime,orderId,userwantordernoworafterThat);
        order_adapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rec_order.setLayoutManager(linearLayoutManager);
        rec_order.setAdapter(order_adapter);



    }
}
