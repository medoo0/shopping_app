package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.pojo.We_Will_Remove_This_Model_afterThat;

import java.util.List;

public class Adapter_Favourite extends RecyclerView.Adapter<Adapter_Favourite.Myholder> {

    private Context context;
    List<We_Will_Remove_This_Model_afterThat>antherList;


    public Adapter_Favourite(List<We_Will_Remove_This_Model_afterThat> list, Context context){

        this.antherList = list;
        this.context    = context;



    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.adapter_favourite_style,parent,false);


        return new Myholder(v);
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {

        holder.thumbnail.setImageBitmap(antherList.get(position).getProductImage());
        holder.text.setText(antherList.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return antherList.size();
    }

    public static class Myholder extends RecyclerView.ViewHolder{
           TextView text;
           ImageView thumbnail;
        public Myholder(View itemView) {

            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            text = itemView.findViewById(R.id.text);
        }
    }
}
