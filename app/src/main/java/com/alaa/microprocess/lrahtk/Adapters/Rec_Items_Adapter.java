package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.R;

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
    public void onBindViewHolder(Holder holder, int position) {

        holder.card_view.setScaleX(.9f);
        holder.card_view.setScaleY(.9f);
        holder.card_view.animate().scaleX(1f).scaleY(1f).setDuration(500);


        holder.thumbnail.setImageResource(images.get(position));
        holder.text.setText(words.get(position));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        CardView card_view;
        ImageView thumbnail;
        TextView text;
        public Holder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            text      = itemView.findViewById(R.id.text);

        }
    }
}
