package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.pojo.Products;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class rec_Basket_Adapter extends RecyclerView.Adapter<rec_Basket_Adapter.ViewHolder> {
    private List<Products> Products;
    private ArrayList<String> quantity_list;
    Context context;


    public rec_Basket_Adapter(Context activity, List<Products> Products, ArrayList<String> quantity_list) {
        this.context = activity;
        this.Products = Products ;
        this.quantity_list = quantity_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_layout,parent,false);
        return new rec_Basket_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.foreground.setScaleX(.9f);
        holder.foreground.setScaleY(.9f);
        holder.foreground.animate().scaleX(1f).scaleY(1f).setDuration(500);


        //

        holder.delete_background.setScaleX(.9f);
        holder.delete_background.setScaleY(.9f);
        holder.delete_background.animate().scaleX(1f).scaleY(1f).setDuration(500);


        Glide // fast to get Item in Local //Picasso is the faster than glide from internet
                .with(context)
                .load(ApiRetrofit.API_IMAGE_BASE_URL + Products.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.Product_Image);

        holder.productName.setText(Products.get(position).getName());
        holder.quantity.setText(quantity_list.get(position));
        holder.product_company.setText(Products.get(position).getBrand().getName()+"");
        holder.Price.setText(Products.get(position).getPrice()+" L.E");
    }

    @Override
    public int getItemCount() {
        return Products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder   {
            ImageButton add , min ;
            TextView productName , product_company , quantity , Price;
            ImageView Product_Image ;
            LinearLayout foreground , delete_background ;

        public ViewHolder(View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.add);
            min = itemView.findViewById(R.id.min);
            productName = itemView.findViewById(R.id.productName);
            product_company = itemView.findViewById(R.id.product_company);
            quantity = itemView.findViewById(R.id.quantity);
            Price   = itemView.findViewById(R.id.Price);
            foreground = itemView.findViewById(R.id.foreground);
            Product_Image = itemView.findViewById(R.id.Product_Image);
            delete_background = itemView.findViewById(R.id.Cardview2);

        }

    }
}
