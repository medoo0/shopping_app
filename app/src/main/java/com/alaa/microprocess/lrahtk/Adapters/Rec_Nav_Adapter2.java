package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.pojo.Categories;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by microprocess on 2018-12-29.
 */

public class Rec_Nav_Adapter2 extends RecyclerView.Adapter<Rec_Nav_Adapter2.ViewHolder>{




    List<Categories> categories;
    HomePageContract.viewMain main;
    Context context;
    public Rec_Nav_Adapter2(List<Categories> categories, Context context , HomePageContract.viewMain main) {
        this.categories = categories;
        this.main  = main;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.yellow_circle_rec,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {





        Glide.with(context).load(ApiRetrofit.API_IMAGE_BASE_URL + categories.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.Icon);

        holder.Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                main.whenClickonIteminRecNav(categories.get(position).getId(),categories.get(position).getName());



            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder   {

        ImageView Icon;


        public ViewHolder(View itemView) {
            super(itemView);


            Icon       = itemView.findViewById(R.id.Icon);



        }

    }
}
