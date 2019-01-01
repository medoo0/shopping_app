package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alaa.microprocess.lrahtk.R;

public class Saved_Address_Adapter extends RecyclerView.Adapter<Saved_Address_Adapter.MyHolder> {

    private Context context;
    private String [] Name ,address,phone;


    public Saved_Address_Adapter(Context context, String[] name, String[] address, String[] phone) {

        this.context = context;
        Name = name;
        this.address = address;
        this.phone = phone;
    }

    @Override



    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.saved_title_style_rec,parent,false);

        return new Saved_Address_Adapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.tvName.setText(Name[position]);
        holder.detailsAddress.setText(address[position]);
        holder.phone.setText(phone[position]);
        holder.cardView.setScaleX(.9f);
        holder.cardView.setScaleY(.9f);
        holder.cardView.animate().scaleX(1f).scaleY(1f).setDuration(500);


    }

    @Override
    public int getItemCount() {

        return Name.length;
    }

    public static class MyHolder extends RecyclerView.ViewHolder{


        TextView tvName,detailsAddress,phone;
        CardView cardView;

        public MyHolder(View itemView) {

            super(itemView);


            tvName         = itemView.findViewById(R.id.tvName);
            phone          = itemView.findViewById(R.id.phone);
            detailsAddress = itemView.findViewById(R.id.detailsAddress);
            cardView       = itemView.findViewById(R.id.cardView);



        }
    }
}
