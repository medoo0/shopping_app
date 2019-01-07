package com.alaa.microprocess.lrahtk.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.Helper;
import com.alaa.microprocess.lrahtk.View.HomePage;

import java.util.ArrayList;

import butterknife.BindView;


public class MainPage_Fragment extends Fragment {

    ArrayList<String> items;
    ArrayList<Integer> images;
    ArrayList<String> productID;

    Helper helper;
    @BindView(R.id.recitems)
    RecyclerView recitems;

    SQLiteDatabase dpwrite , dpread;

    ImageView Search_image;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_page_frag_layout,container,false);
        HomePage.texttoolbar.setText("الرئيسية");

        HomePageContract.viewMain viewMain = (HomePageContract.viewMain) getActivity();

        if (viewMain!=null){


            viewMain.showToobar();

        }

        Search_image  = v.findViewById(R.id.search_image);
        helper = new Helper(getActivity());
        items   = new ArrayList<>();
        images  = new ArrayList<>();
        productID= new ArrayList<>();
        dpwrite   = helper.getWritableDatabase();
        dpread    = helper.getReadableDatabase();
        recitems = v.findViewById(R.id.recitems);
        recitems.setNestedScrollingEnabled(false);
        showItemsinREC();




        Search_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack(); //finish
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.replaceByFragment, new Search())
                        .commit();
            }
        });

        return v;
    }





    public void showItemsinREC(){

//        items.add("لبن");
//        items.add("شيكولاته");
//        items.add("خبز");
//        items.add("عصائر");
//        items.add("زبادي");
//        items.add("لبن");
//        items.add("لبن");
//        images.add(R.drawable.millkingone);
//        images.add(R.drawable.checlotes);
//        images.add(R.drawable.breads);
//        images.add(R.drawable.drinks);
//        images.add(R.drawable.johina);
//        images.add(R.drawable.millkingone);
//        images.add(R.drawable.millkingone);
//        productID.add("1");
//        productID.add("2");
//        productID.add("3");
//        productID.add("4");
//        productID.add("5");
//        productID.add("6");
//        productID.add("7");
//
//
//        Rec_Items_Adapter rec_items_adapter = new Rec_Items_Adapter(items,images,productID,getActivity(),dpwrite,dpread);
//        rec_items_adapter.notifyDataSetChanged();
//        LinearLayoutManager HorizontalLayout  =
//                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recitems.setLayoutManager(HorizontalLayout);
//        recitems.setAdapter(rec_items_adapter);

    }
}
