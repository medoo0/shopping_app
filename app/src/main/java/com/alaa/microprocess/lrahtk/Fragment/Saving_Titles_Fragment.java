package com.alaa.microprocess.lrahtk.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaa.microprocess.lrahtk.Adapters.Saved_Address_Adapter;
import com.alaa.microprocess.lrahtk.R;

public class Saving_Titles_Fragment extends Fragment {

    RecyclerView rec_address;


    String [] names = {"MohamedRaslan" , "AlaaDaher","MostfaRaslan"};
    String [] adddress = {"Egypt-Cairo" , "Qweet","Qater_Q_aaa"};
    String [] phone = {"01025458555" , "4568855544454","525555555555"};




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view    = inflater.inflate(R.layout.saving_title_fragment,container,false);

        rec_address  = view.findViewById(R.id.rec_address);

        showItems_in_Rec();
        return view;
    }







    void showItems_in_Rec(){
        Saved_Address_Adapter saved_address_adapter = new Saved_Address_Adapter(getActivity(),names,adddress,phone);
        saved_address_adapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rec_address.setLayoutManager(linearLayoutManager);
        rec_address.setAdapter(saved_address_adapter);
    }



}
