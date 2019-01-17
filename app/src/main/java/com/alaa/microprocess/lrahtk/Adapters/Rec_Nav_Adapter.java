package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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




    List<Categories> parent ,childs ;
    HomePageContract.viewMain main;
    Context context;
    public Rec_Nav_Adapter(List<Categories> parent,List<Categories> childs, Context context , HomePageContract.viewMain main) {
        this.parent = parent;
        this.main  = main;
        this.context = context;
        this.childs = childs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



        holder.title.setText(parent.get(position).getName());

        holder.Icon.setImageResource(R.drawable.ic_add_black_24dp);
        holder.clickOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(holder.sub_parent.isShown()){
                    holder.Icon.setImageResource(R.drawable.ic_add_black_24dp);
                    holder.sub_parent.setVisibility(View.GONE);
                }
                else {

                    holder.Icon.setImageResource(R.drawable.ic_remove_black_24dp);
                    holder.sub_parent.setVisibility(View.VISIBLE);

                    List<Categories> categories = new ArrayList<>();

                    for(int i = 0 ; i < childs.size(); i ++ ){

                        if(parent.get(position).getId().equals(childs.get(i).getParent().getId())){

                            categories.add(childs.get(i));

                        }

                    }


                    Rec_Nav_Adapter3 postAdapter = new Rec_Nav_Adapter3(categories,context,main);
                    holder.sub_parent.setLayoutManager(new LinearLayoutManager(context));
                    holder.sub_parent.setAdapter(postAdapter);
                }



            }
        });




    }

    @Override
    public int getItemCount() {
        return parent.size();
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
