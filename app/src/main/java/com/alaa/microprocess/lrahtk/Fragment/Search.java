package com.alaa.microprocess.lrahtk.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {
    SearchView searchView;
    Button BtnFilter , BtnCancel;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(((AppCompatActivity)getActivity()).getSupportActionBar().isShowing()){
//
//            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        BtnFilter = view.findViewById(R.id.btn_filter);
        BtnCancel = view.findViewById(R.id.btn_cancel);


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
