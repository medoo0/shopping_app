package com.alaa.microprocess.lrahtk.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.pojo.Comments;

import java.util.ArrayList;
import java.util.List;


public class Rec_Comments_Adapter extends RecyclerView.Adapter<Rec_Comments_Adapter.ViewHolder> {
List<Comments> strings ;

    public Rec_Comments_Adapter(List<Comments> strings) {

        this.strings = strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_comment,parent,false);
        return new Rec_Comments_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder,  int position) {

        holder.txName.setText(strings.get(position).getReviewer().getName());
        holder.Comment.setText(strings.get(position).getDescription());
        holder.rating.setRating(strings.get(position).getStars());

        //animation
        holder.Linear.setScaleX(.9f);
        holder.Linear.setScaleY(.9f);
        holder.Linear.animate().scaleX(1f).scaleY(1f).setDuration(500);



    }

    @Override
    public int getItemCount() {
        return strings.size() ;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder   {

        TextView txName , Comment;
        RatingBar rating;
        LinearLayout Linear;
        public ViewHolder(View itemView) {
            super(itemView);
            txName = itemView.findViewById(R.id.Name);
            Comment = itemView.findViewById(R.id.Comment);
            rating = itemView.findViewById(R.id.rating);
            Linear = itemView.findViewById(R.id.Linear);
        }

    }

}
