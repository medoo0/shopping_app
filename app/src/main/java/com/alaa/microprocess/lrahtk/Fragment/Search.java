package com.alaa.microprocess.lrahtk.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {
    SearchView searchView;
    Button BtnFilter , BtnCancel;
    TextView txCategories ,txPrice;
    RecyclerView rec_categories;
    ToggleSwitch ToggleBtn;
    LinearLayout Linear_price ,Linear2;
    Animation downtoup, uptodown;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        BtnFilter = view.findViewById(R.id.btn_filter);
        BtnCancel = view.findViewById(R.id.btn_cancel);
        txCategories = view.findViewById(R.id.txCategories);
        txPrice = view.findViewById(R.id.txPrice);
        rec_categories = view.findViewById(R.id.rec_categories);
        ToggleBtn = view.findViewById(R.id.ToggleBtn);
        Linear_price = view.findViewById(R.id.Linear_price);
        Linear2 = view.findViewById(R.id.Linear2);
        downtoup = AnimationUtils.loadAnimation(getActivity(), R.anim.downtoup);
        uptodown = AnimationUtils.loadAnimation(getActivity(), R.anim.uptodown);
        // make sure if toobar is shown or not
        HomePageContract.viewMain viewMain = (HomePageContract.viewMain) getActivity();

        if (viewMain!=null){


            viewMain.hideToolbar();

        }


        HomePage.texttoolbar.setText("الرئيسية");
        searchView = view.findViewById(R.id.search);
        searchView.setIconified(false); //expand search view .

        BtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BtnCancel.setVisibility(View.VISIBLE);
                Linear2.setVisibility(View.VISIBLE);
                Linear2.startAnimation(downtoup);




            }
        });
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtnCancel.setVisibility(View.GONE);
                Linear2.setVisibility(View.GONE);
            }
        });


        txCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txCategories.setBackgroundColor(getResources().getColor(R.color.white));
                txPrice.setBackgroundColor(getResources().getColor(R.color.color2));
                rec_categories.setVisibility(View.VISIBLE);
                Linear_price.setVisibility(View.GONE);
            }
        });

        txPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txCategories.setBackgroundColor(getResources().getColor(R.color.color2));
                txPrice.setBackgroundColor(getResources().getColor(R.color.white));
                rec_categories.setVisibility(View.GONE);
                Linear_price.setVisibility(View.VISIBLE);
            }
        });


        return view ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(!((AppCompatActivity)getActivity()).getSupportActionBar().isShowing()){

            ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        }
    }
}
