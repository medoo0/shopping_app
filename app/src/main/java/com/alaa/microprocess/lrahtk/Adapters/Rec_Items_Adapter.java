package com.alaa.microprocess.lrahtk.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.ShowProduct;

import java.util.ArrayList;
import java.util.List;

public class Rec_Items_Adapter extends RecyclerView.Adapter<Rec_Items_Adapter.Holder> {



    private List<String> words;
    private List<Integer> images;
    private Context context;


    public Rec_Items_Adapter(List<String> words, List<Integer> images, Context context) {
        this.words = words;
        this.images = images;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.rec_in_homepage_style,parent,false);


        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        holder.relative.setScaleX(.9f);
        holder.relative.setScaleY(.9f);
        holder.relative.animate().scaleX(1f).scaleY(1f).setDuration(500);


        holder.thumbnail.setImageResource(images.get(position));
        holder.text.setText(words.get(position));


        //onclick
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowProduct.class);
                intent.putExtra("image",images.get(position));
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context
                        , holder.thumbnail, ViewCompat.getTransitionName(holder.thumbnail));
                context.startActivity(intent , option.toBundle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        RelativeLayout relative;
        ImageView thumbnail;
        TextView text;

        public Holder(View itemView) {
            super(itemView);
            relative = itemView.findViewById(R.id.Card);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            text      = itemView.findViewById(R.id.text);


        }
    }
}
