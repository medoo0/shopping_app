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

import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;

import java.util.ArrayList;

/**
 * Created by microprocess on 2018-12-29.
 */

public class Rec_Nav_Adapter extends RecyclerView.Adapter<Rec_Nav_Adapter.ViewHolder>{




    private ArrayList<String> Categories;
    private ArrayList<Integer> categories_icon;
    private Context context;
    HomePageContract.viewMain main;
    public Rec_Nav_Adapter(ArrayList<String> posts, ArrayList<Integer> categories_icon,Context context,HomePageContract.viewMain main) {
        this.categories_icon = categories_icon;
        this.Categories = posts;
        this.context    = context;
        this.main  = main;
    }

    @Override
    public Rec_Nav_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Rec_Nav_Adapter.ViewHolder holder, int position) {

        holder.title.setText(Categories.get(position));
        holder.Icon.setBackgroundResource(categories_icon.get(position));
        holder.clickOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                main.whenClickonIteminRecNav();



            }
        });
    }

    @Override
    public int getItemCount() {
        return Categories.size();
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
