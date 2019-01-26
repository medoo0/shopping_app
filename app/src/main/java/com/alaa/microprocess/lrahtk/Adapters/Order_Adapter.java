package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.ShowMyOrders;
import com.alaa.microprocess.lrahtk.pojo.MyOrder;

import java.util.List;

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.Holder> {



    private Context context;
     // three array until getting the real data from Api
    private List<MyOrder> myorder ;


    public Order_Adapter(Context context, List<MyOrder> myorder) {
        this.context = context;
        this.myorder = myorder ;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_rec_style,parent,false);

        return new Order_Adapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        holder.cardView.setScaleX(.9f);
        holder.cardView.setScaleY(.9f);
        holder.cardView.animate().scaleX(1f).scaleY(1f).setDuration(500);

        holder.dataofProduct.setText(myorder.get(position).getCreatedAt());
        holder.idofProduct.setText(myorder.get(position).getId());
        holder.whatqwayuserWantProduct.setText(myorder.get(position).getStatus());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMyOrders.class);
                intent.putExtra("id", myorder.get(position).getId() );
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myorder.size();
    }
    public void addMoreItems(List<MyOrder> MyOrder){

        this.myorder.addAll(MyOrder);
        if (MyOrder.size() != 0) {
            notifyItemInserted(this.myorder.size() - 1);
        }
    }
     static class Holder extends RecyclerView.ViewHolder{


        TextView dataofProduct,idofProduct,whatqwayuserWantProduct;
         CardView cardView ;


         Holder(View itemView) {

            super(itemView);
            dataofProduct           = itemView.findViewById(R.id.dataofProduct);
            idofProduct             = itemView.findViewById(R.id.idofProduct);
            whatqwayuserWantProduct = itemView.findViewById(R.id.whatqwayuserWantProduct);
            cardView =itemView.findViewById(R.id.Card);
        }
    }

}
