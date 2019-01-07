package com.alaa.microprocess.lrahtk.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
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

import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.Helper;
import com.alaa.microprocess.lrahtk.SQLite.Operation_On_SQLite;
import com.alaa.microprocess.lrahtk.View.ShowProduct;
import com.alaa.microprocess.lrahtk.pojo.Products;
import com.alaa.microprocess.lrahtk.pojo.We_Will_Remove_This_Model_afterThat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Rec_Items_Adapter extends RecyclerView.Adapter<Rec_Items_Adapter.Holder> {


    List<Products> products;
    Context context;

    public Rec_Items_Adapter(List<Products> products , Context context ) {
    this.context = context;
    this.products = products;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_in_homepage_style,parent,false);


        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {



        holder.relative.setScaleX(.9f);
        holder.relative.setScaleY(.9f);
        holder.relative.animate().scaleX(1f).scaleY(1f).setDuration(500);


        Glide.with(context)
                .load(ApiRetrofit.API_IMAGE_BASE_URL + products.get(position).getThumbnail())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        holder.thumbnail.setImageBitmap(resource); // Possibly runOnUiThread()
                    }
                });


        //        Glide.with(context).load(ApiRetrofit.API_IMAGE_BASE_URL + products.get(position).getThumbnail())
//                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);

        holder.text.setText(products.get(position).getName());


        //onclick
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ShowProduct.class);
                intent.putExtra("name",products.get(position).getName());
                intent.putExtra("description",products.get(position).getDescription());
                intent.putExtra("price",products.get(position).getPrice());
                intent.putExtra("quantity",products.get(position).getQuantity());
                intent.putExtra("rating",products.get(position).getRating().getRate());
                intent.putExtra("length",products.get(position).getRating().getLength());
                intent.putExtra("brand",products.get(position).getBrand().getName());
                intent.putExtra("category",products.get(position).getCategory().getName());
                intent.putExtra("ImageURl",ApiRetrofit.API_IMAGE_BASE_URL + products.get(position).getThumbnail());

                try {
                    Bitmap bitmap = ((BitmapDrawable)holder.thumbnail.getDrawable()).getBitmap();
                    intent.putExtra("Image",bitmap);
                }catch (Exception e){}


                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context
                        , holder.thumbnail, ViewCompat.getTransitionName(holder.thumbnail));
                context.startActivity(intent , option.toBundle());

            }
        });

    }

    @Override
    public int getItemCount() {


        return products.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        CardView relative;
        ImageView thumbnail;
        TextView text;
        ImageView image_add_to_Favout;

        public Holder(View itemView) {
            super(itemView);
            relative = itemView.findViewById(R.id.Card);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            text      = itemView.findViewById(R.id.text);
            image_add_to_Favout = itemView.findViewById(R.id.image_add_to_Favout);


        }
    }









}
