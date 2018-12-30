package com.alaa.microprocess.lrahtk.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.R;

import java.util.ArrayList;

public class Product_Activity extends AppCompatActivity implements View.OnClickListener{


    RecyclerView rectwo;
    ArrayList <String> items;
    int COLUM_NUM = 2;
    ImageView backhome;
    ArrayList<Integer> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_);
        rectwo  = findViewById(R.id.rectwo);
        backhome = findViewById(R.id.backhome);
        backhome.setOnClickListener(this);
        items   = new ArrayList<>();
        images  = new ArrayList<>();
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


        Rec_Items_Adapter rec_items_adapter = new Rec_Items_Adapter(items,images,this);
        rec_items_adapter.notifyDataSetChanged();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,COLUM_NUM);
        rectwo.setLayoutManager(gridLayoutManager);
        rectwo.setAdapter(rec_items_adapter);

    }

    @Override
    public void onClick(View v) {
        if (v  ==  backhome){

            Intent intent = new Intent(this , HomePage.class);
            startActivity(intent);
            finish();


        }
    }
}
