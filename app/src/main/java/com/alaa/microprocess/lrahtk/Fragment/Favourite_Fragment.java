package com.alaa.microprocess.lrahtk.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alaa.microprocess.lrahtk.Adapters.Adapter_Favourite;
import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.Enums.Variables;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.Helper;
import com.alaa.microprocess.lrahtk.SQLite.Operation_On_SQLite;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.pojo.We_Will_Remove_This_Model_afterThat;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Favourite_Fragment extends Fragment {


    RecyclerView rec_favourite;
    Helper helper;
    SQLiteDatabase dpwrite , dpread;
    Operation_On_SQLite operation_on_sqLite;
    List<We_Will_Remove_This_Model_afterThat> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view  = inflater.inflate(R.layout.favourite_layout,container,false);
        HomePage.texttoolbar.setText("المفضلة");
        rec_favourite = view.findViewById(R.id.rec_favourite);
        helper    = new Helper(getActivity());
        dpread    = helper.getReadableDatabase();
        operation_on_sqLite = new Operation_On_SQLite() ;
        list = new ArrayList<>();



        // getting favourite product from sqlite database
        Cursor allDataHere = operation_on_sqLite.getAllData(dpread);




        if (allDataHere.moveToFirst()) {

            while (!allDataHere.isAfterLast()) {

                String name    = allDataHere.getString(allDataHere.getColumnIndex("COL_ProductName"));

                Bitmap p       = BitmapFactory.decodeByteArray(allDataHere.getBlob(3),0,allDataHere.getBlob(3).length);

                We_Will_Remove_This_Model_afterThat we_will_remove_this_model_afterThat = new We_Will_Remove_This_Model_afterThat(name,"1225&",p);

                list.add(we_will_remove_this_model_afterThat);
                allDataHere.moveToNext();


            }
        }



        if (!list.isEmpty()){

            Adapter_Favourite rec_items_adapter = new Adapter_Favourite(list,getActivity());
            rec_items_adapter.notifyDataSetChanged();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
            rec_favourite.setLayoutManager(gridLayoutManager);
            rec_favourite.setAdapter(rec_items_adapter);



        }


       return view;
    }
}
