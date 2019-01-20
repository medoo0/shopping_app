package com.alaa.microprocess.lrahtk.Adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Dialog.AlertDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.FavHelper;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.View.ShowProduct;
import com.alaa.microprocess.lrahtk.pojo.Products;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Rec_Items_Adapter extends RecyclerView.Adapter<Rec_Items_Adapter.Holder> {


    List<Products> products;
    Context context;
    private List<Products> listnew;
    FavHelper helper;
    SQLiteDatabase db ;
    SharedPreferences preferences ;
    String UserID , TableName , BasketTableName ;

    public Rec_Items_Adapter(List<Products> products , Context context ) {
    this.context = context;
    this.products = products;
    helper   = new FavHelper(context);
    db       = helper.getWritableDatabase();
    preferences = context.getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        if (preferences.getString("AreInOrNot","").equals("IN")){

            UserID      = preferences.getString("id","");
            TableName = "T"+UserID;
            BasketTableName = "B"+UserID;

        }
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
                .into(holder.thumbnail);


        //        Glide.with(context).load(ApiRetrofit.API_IMAGE_BASE_URL + products.get(position).getThumbnail())
//                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);

        holder.text.setText(products.get(position).getName());
        holder.txprice.setText(products.get(position).getPrice()+" L.E");

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
                intent.putExtra("proID",products.get(position).getId());
//                try {
//                    Bitmap bitmap = ((BitmapDrawable)holder.thumbnail.getDrawable()).getBitmap();
//                    intent.putExtra("Image",ConvertTobyteArray(bitmap));
//                }catch (Exception e){
//
//                }

//                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context
//                        , holder.thumbnail, ViewCompat.getTransitionName(holder.thumbnail));
                context.startActivity(intent);

            }
        });

        if(Check_if_product_in_Fav_List(products.get(position).getId())){

            holder.image_add_to_Favout.setImageResource(R.drawable.ic_color_heart);
        }
        else {
            holder.image_add_to_Favout.setImageResource(R.drawable.heart);

        }

        holder.image_add_to_Favout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.CreateFavTable(TableName);

                if(!Check_if_product_in_Fav_List(products.get(position).getId())) {
                    // insert
                    ContentValues row = new ContentValues();
                    row.put(helper.FavID, products.get(position).getId());
                    db.insert(TableName, null, row);
                    holder.image_add_to_Favout.setImageResource(R.drawable.ic_color_heart);
                }
                else {

                    int deleted = db.delete(TableName,FavHelper.FavID+" = ? ",new String[]{products.get(position).getId()} );
                    if (deleted > 0 ){
                       //deleted
                        holder.image_add_to_Favout.setImageResource(R.drawable.heart);


                    }

                }

            }
        });

        holder.addToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.CreateBasketTable(BasketTableName);
                ContentValues row = new ContentValues();
                row.put(FavHelper.BasketName ,products.get(position).getName() );
                row.put(FavHelper.BasketID , products.get(position).getId());
                row.put(FavHelper.BasketQuantity , 1);
                row.put(FavHelper.Brand , products.get(position).getBrand().getName() );
                row.put(FavHelper.Image_Url , ApiRetrofit.API_IMAGE_BASE_URL + products.get(position).getThumbnail() );
                row.put(FavHelper.prices , products.get(position).getPrice() );


                long insert = db.insert(BasketTableName , null , row);
                if(insert > 0 ){
                    AlertDialog alertDialog = new AlertDialog(context,"تم اضافة المنتج داخل سلة المشتريات . ");
                    alertDialog.show();
                }
                //refresh basket fragment if opened  .
                Intent intent = new Intent("Refresh");
                context.sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {


        return products.size();
    }

    public void addMoreItems(List<Products> products){

        this.products.addAll(products);
        if (products.size() != 0) {
            notifyItemInserted(this.products.size() - 1);
        }
    }

    public static class Holder extends RecyclerView.ViewHolder{

        CardView relative;
        ImageView thumbnail;
        TextView text , txprice;
        ImageView image_add_to_Favout , addToBasket;

        public Holder(View itemView) {
            super(itemView);
            relative = itemView.findViewById(R.id.Card);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            text      = itemView.findViewById(R.id.text);
            image_add_to_Favout = itemView.findViewById(R.id.image_add_to_Favout);
            txprice = itemView.findViewById(R.id.price);
            addToBasket = itemView.findViewById(R.id.addToBasket);
        }

    }

    public byte[] ConvertTobyteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public Filter getFilter() {


        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Products> FilteredArrList = new ArrayList<>();
                if (listnew == null) {

                    listnew = new ArrayList<>(products); // saves the original data in mOriginalValues

                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = listnew.size();
                    results.values = listnew;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < listnew.size(); i++) {
                        String data = listnew.get(i).getName();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(listnew.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                products = (ArrayList<Products>) results.values;

                notifyDataSetChanged();

            }
        };
        return filter;
    }


        public boolean Check_if_product_in_Fav_List(String ProductID){
            //create if not exists .
            helper.CreateFavTable(TableName);

            String [] Cols = {FavHelper.FavID};
            Cursor Pointer = db.query(TableName,Cols,Cols[0]+" = ?",new String[]{ProductID},null,null,null);
            if(Pointer.moveToNext()){
             return true ;
            }
            else {

                return false ;

            }

        }


}
