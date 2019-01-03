package com.alaa.microprocess.lrahtk.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.alaa.microprocess.lrahtk.SQLite.Helper;
import com.alaa.microprocess.lrahtk.SQLite.Operation_On_SQLite;
import com.alaa.microprocess.lrahtk.View.ShowProduct;
import com.alaa.microprocess.lrahtk.pojo.We_Will_Remove_This_Model_afterThat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Rec_Items_Adapter extends RecyclerView.Adapter<Rec_Items_Adapter.Holder> {



    private List<String> words;
    private List<Integer> images;
    private List<String> productIdArray;
    private Context context;
    List<We_Will_Remove_This_Model_afterThat>antherList;
    Operation_On_SQLite operation_on_sqLite;
    SQLiteDatabase databasewrite,databaseread;

    String what = "";

    public Rec_Items_Adapter(List<String> words, List<Integer> images, List<String> productIdArray, Context context, SQLiteDatabase databasewrite,SQLiteDatabase databaseread) {
        this.words = words;
        this.images = images;
        this.context = context;
        this.productIdArray = productIdArray;
        operation_on_sqLite = new Operation_On_SQLite();
        this.databaseread = databaseread;
        this.databasewrite = databasewrite;
    }


    public Rec_Items_Adapter(List<We_Will_Remove_This_Model_afterThat>list,Context context,String what){

        this.antherList = list;
        this.context    = context;
        this.what       = what;


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
        CheckIfItem_Favourite_Or_Not(holder,position,context);
    }

    @Override
    public int getItemCount() {


        return images.size();
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



    public void CheckIfItem_Favourite_Or_Not(final Holder holder, final int position, final Context context) {


        if (operation_on_sqLite.getData(databaseread, productIdArray.get(position))) {

            holder.image_add_to_Favout.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_color_heart));
            holder.image_add_to_Favout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (operation_on_sqLite.deleterow(databaseread, productIdArray.get(position))) {


                        holder.image_add_to_Favout.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));


                    }


                }
            });





        }else {

            holder.image_add_to_Favout.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));


            holder.image_add_to_Favout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (operation_on_sqLite.addProduct(databasewrite, productIdArray.get(position), words.get(position), "125", getBitmapAsByteArray(images.get(position), context))) {
                        holder.image_add_to_Favout.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_color_heart));

                    }


                }
            });




        }





//
//
//            holder.image_add_to_Favout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String s = productIdArray.get(position);
//                    if (operation_on_sqLite.getData(databaseread, productIdArray.get(position))) {
//
//
//                        if (operation_on_sqLite.deleterow(databaseread, productIdArray.get(position))) {
//
//                            holder.image_add_to_Favout.setBackgroundColor(context.getResources().getColor(R.color.white));
//
//
//                        }
//
//
//                    } else {
//
//                        if (operation_on_sqLite.addProduct(databasewrite, productIdArray.get(position), words.get(position), "125", getBitmapAsByteArray(images.get(position), context))) {
//                            holder.image_add_to_Favout.setBackgroundColor(context.getResources().getColor(R.color.blue));
//
//                        }
//
//
//                    }
//
//
//                }
//            });



    }


    public static byte[] getBitmapAsByteArray(int image ,Context context) {

        Bitmap Icon = BitmapFactory.decodeResource(context.getResources(),image);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Icon.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


}
