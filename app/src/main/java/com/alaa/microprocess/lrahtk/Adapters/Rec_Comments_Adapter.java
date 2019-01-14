package com.alaa.microprocess.lrahtk.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.alaa.microprocess.lrahtk.R;

import java.util.ArrayList;
import java.util.List;


public class Rec_Comments_Adapter extends RecyclerView.Adapter<Rec_Comments_Adapter.ViewHolder> {
List<String> strings ;

    public Rec_Comments_Adapter(ArrayList<String> strings) {

        this.strings = strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_comment,parent,false);
        return new Rec_Comments_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder,  int position) {
        holder.txName.setText(strings.get(position));


    }

    @Override
    public int getItemCount() {
        return strings.size() ;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder   {

        TextView txName , Comment;
        public ViewHolder(View itemView) {
            super(itemView);
            txName = itemView.findViewById(R.id.Name);
            Comment = itemView.findViewById(R.id.Comment);
        }

    }

}
