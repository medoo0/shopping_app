package com.alaa.microprocess.lrahtk.View;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.Helper;
import com.alaa.microprocess.lrahtk.SQLite.Operation_On_SQLite;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Product_Activity extends AppCompatActivity implements View.OnClickListener{


    RecyclerView rectwo;
    ArrayList <String> items,productID;
    int COLUM_NUM = 2;
    ImageView backhome;
    SQLiteDatabase dpwrite , dpread;
    ArrayList<Integer> images;
    Helper helper;
    Operation_On_SQLite operation_on_sqLite;
    ImageView searchindadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_);
        rectwo  = findViewById(R.id.rectwo);
        helper = new Helper(this);
        dpwrite   = helper.getWritableDatabase();
        dpread    = helper.getReadableDatabase();
        operation_on_sqLite = new Operation_On_SQLite() ;
        backhome = findViewById(R.id.backhome);
        backhome.setOnClickListener(this);
        searchindadding = findViewById(R.id.searchindadding);
        items   = new ArrayList<>();
        images  = new ArrayList<>();
        productID= new ArrayList<>();
        showItemsinREC();


    }





    public void showItemsinREC(){

        items.add("لبن");
        items.add("شيكولاته");
        items.add("خبز");
        items.add("عصائر");
        items.add("زبادي");
        items.add("لبن");
        items.add("لبن");
        images.add(R.drawable.millkingone);
        images.add(R.drawable.checlotes);
        images.add(R.drawable.breads);
        images.add(R.drawable.drinks);
        images.add(R.drawable.johina);
        images.add(R.drawable.millkingone);
        images.add(R.drawable.millkingone);
        productID.add("1");
        productID.add("2");
        productID.add("3");
        productID.add("4");
        productID.add("5");
        productID.add("6");
        productID.add("7");

        Rec_Items_Adapter rec_items_adapter = new Rec_Items_Adapter(items,images,productID,this,dpwrite,dpread);
        rec_items_adapter.notifyDataSetChanged();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,COLUM_NUM);
        rectwo.setLayoutManager(gridLayoutManager);
        rectwo.setAdapter(rec_items_adapter);

    }

    @Override
    public void onClick(View v) {
        if (v  ==  backhome){

            finish();


        }
    }

}
