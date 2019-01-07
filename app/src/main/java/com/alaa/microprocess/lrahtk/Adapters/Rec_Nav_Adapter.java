package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.pojo.Categories;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by microprocess on 2018-12-29.
 */

public class Rec_Nav_Adapter extends RecyclerView.Adapter<Rec_Nav_Adapter.ViewHolder>{




    List<Categories> categories;
    HomePageContract.viewMain main;
    Context context;
    public Rec_Nav_Adapter( List<Categories> categories,Context context ,HomePageContract.viewMain main) {
        this.categories = categories;
        this.main  = main;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        holder.title.setText(categories.get(position).getName());

        Glide.with(context).load(ApiRetrofit.API_IMAGE_BASE_URL + categories.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.Icon);

        holder.clickOn.setOnClickListener(new View.OnClickListener() {
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
        LinearLayout clickOn;
        TextView title ;
        ImageView Icon;
        RecyclerView sub_parent;

        public ViewHolder(View itemView) {
            super(itemView);

            title      = itemView.findViewById(R.id.title);
            Icon       = itemView.findViewById(R.id.Icon);
            sub_parent = itemView.findViewById(R.id.sub_parent);
            clickOn    = itemView.findViewById(R.id.clickOn);


        }

    }
}
