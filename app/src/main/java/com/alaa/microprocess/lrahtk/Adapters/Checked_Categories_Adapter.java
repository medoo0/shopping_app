package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.pojo.Categories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by microprocess on 2018-12-29.
 */

public class Checked_Categories_Adapter extends RecyclerView.Adapter<Checked_Categories_Adapter.ViewHolder>{




    List<Categories>  childs ;

    Context context;

    public Checked_Categories_Adapter(List<Categories> childs, Context context ) {
        this.context = context;
        this.childs = childs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_checked_categories,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder,  int position) {


        holder.checkbox.setText(childs.get(position).getName());
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return childs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder   {
        CheckBox checkbox;
        public ViewHolder(View itemView) {
            super(itemView);

            checkbox      = itemView.findViewById(R.id.checkbox);



        }

    }
}
