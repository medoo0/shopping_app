package com.alaa.microprocess.lrahtk.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.FavHelper;
import com.alaa.microprocess.lrahtk.pojo.SqlProduct;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class rec_Basket_Adapter extends RecyclerView.Adapter<rec_Basket_Adapter.ViewHolder> {


    Context context;
    List<SqlProduct> sqlProducts ;
    String BasketTableName;
    FavHelper helper;
    SQLiteDatabase db;
    public rec_Basket_Adapter(Context activity,String BasketTableName,List<SqlProduct> sqlProducts ) {
        this.context = activity;
        this.sqlProducts = sqlProducts;
        this.BasketTableName = BasketTableName;
        helper = new FavHelper(activity);
        db = helper.getWritableDatabase();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_layout,parent,false);
        return new rec_Basket_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position % 2 == 0) {

            holder.delete_background.setX(-1000);
            holder.delete_background.animate().translationXBy(1000).setDuration(500);
            holder.foreground.setX(-1000);
            holder.foreground.animate().translationXBy(1000).setDuration(500);
        }
        else
        {

            holder.delete_background.setX(1000);
            holder.delete_background.animate().translationXBy(-1000).setDuration(500);
            holder.foreground.setX(1000);
            holder.foreground.animate().translationXBy(-1000).setDuration(500);

        }

        //

//        holder.foreground.setScaleX(.9f);
//        holder.foreground.setScaleY(.9f);
//        holder.foreground.animate().scaleX(1f).scaleY(1f).setDuration(500);
//
//        holder.delete_background.setScaleX(.9f);
//        holder.delete_background.setScaleY(.9f);
//        holder.delete_background.animate().scaleX(1f).scaleY(1f).setDuration(500);


        Glide // fast to get Item in Local //Picasso is the faster than glide from internet
                .with(context)
                .load(sqlProducts.get(position).getImage_Url())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.Product_Image);

        holder.productName.setText(sqlProducts.get(position).getBasketName());
        holder.quantity.setText(sqlProducts.get(position).getBasketQuantity()+"");
        holder.product_company.setText(sqlProducts.get(position).getBrand()+"");
        final double price = sqlProducts.get(position).getPrice() *  Integer.parseInt(sqlProducts.get(position).getBasketQuantity());
        holder.Price.setText(price +" L.E");


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.quantity.getText().toString());

                count++;
                holder.quantity.setText(count+"");

                double price = sqlProducts.get(position).getPrice() *  count ;
                holder.Price.setText(price +" L.E");

                Update_Quantity(sqlProducts.get(position).getSqlID(),holder.quantity.getText().toString());
                //Update Total
                Intent intent = new Intent("Update");
                context.sendBroadcast(intent);

            }
        });
        holder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.quantity.getText().toString());
                if(count > 1 ) {
                    count--;
                    holder.quantity.setText(count + "");
                    double price = sqlProducts.get(position).getPrice() *  count ;
                    holder.Price.setText(price +" L.E");
                    Update_Quantity(sqlProducts.get(position).getSqlID(),holder.quantity.getText().toString());

                    //Update Total
                    Intent intent = new Intent("Update");
                    context.sendBroadcast(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return sqlProducts.size();
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

    public void Update_Quantity(String SqlID , String NewQuantity){
        ContentValues row = new ContentValues();
        row.put(helper.BasketQuantity , NewQuantity);


        int Updated = db.update(BasketTableName,row,helper.ID+" = ?",new String[]{SqlID});
        if (Updated > 0 ){

        }
        else {

        }

    }
}
