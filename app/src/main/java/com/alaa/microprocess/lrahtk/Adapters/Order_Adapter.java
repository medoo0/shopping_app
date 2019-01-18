package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.R;

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.Holder> {



    private Context context;
     // three array until getting the real data from Api
    private String []  orderTime  ,  orderId  ,  userwantordernoworafterThat ;


    public Order_Adapter(Context context, String[] orderTime, String[] orderId, String[] userwantordernoworafterThat) {
        this.context = context;
        this.orderTime = orderTime;
        this.orderId = orderId;
        this.userwantordernoworafterThat = userwantordernoworafterThat;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_rec_style,parent,false);

        return new Order_Adapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {


        holder.dataofProduct.setText(orderTime[position]);
        holder.idofProduct.setText(orderTime[position]);
        holder.whatqwayuserWantProduct.setText(userwantordernoworafterThat[position]);

    }

    @Override
    public int getItemCount() {
        return orderId.length;
    }

     static class Holder extends RecyclerView.ViewHolder{


        TextView dataofProduct,idofProduct,whatqwayuserWantProduct;



         Holder(View itemView) {

            super(itemView);
            dataofProduct           = itemView.findViewById(R.id.dataofProduct);
            idofProduct             = itemView.findViewById(R.id.idofProduct);
            whatqwayuserWantProduct = itemView.findViewById(R.id.whatqwayuserWantProduct);
        }
    }

}
