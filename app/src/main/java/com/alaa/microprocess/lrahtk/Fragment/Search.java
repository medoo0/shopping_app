package com.alaa.microprocess.lrahtk.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alaa.microprocess.lrahtk.Adapters.Checked_Categories_Adapter;
import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.Contract.SearchInterface;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.View.Product_Activity;
import com.alaa.microprocess.lrahtk.pojo.Categories;
import com.alaa.microprocess.lrahtk.pojo.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment implements SearchInterface{

    Button BtnFilter , BtnCancel;
    TextView txCategories ,txPrice;
    RecyclerView rec_categories , rec_search;
    EditText ETlessThan , ETMoreThan ,searchView;
    LinearLayout Linear_price ,Linear1,Linear2 , LinearRec;
    Animation downtoup, uptodown;
    CheckBox allChecked;
    ArrayList<String> CategoriesIDs ;
    AnimatedDialog dialog;
    int PerPage = 10 , Page = 0 ;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        ETlessThan = view.findViewById(R.id.ETlessThan);
        ETMoreThan = view.findViewById(R.id.ETMoreThan);
        BtnFilter = view.findViewById(R.id.btn_filter);
        BtnCancel = view.findViewById(R.id.btn_cancel);
        txCategories = view.findViewById(R.id.txCategories);
        txPrice = view.findViewById(R.id.txPrice);
        rec_categories = view.findViewById(R.id.rec_categories);
        rec_search = view.findViewById(R.id.rec_search);
        Linear_price = view.findViewById(R.id.Linear_price);
        Linear2 = view.findViewById(R.id.Linear2);
        Linear1 = view.findViewById(R.id.Linear1);
        searchView = view.findViewById(R.id.search);
        LinearRec = view.findViewById(R.id.LinearRec);
        CategoriesIDs = new ArrayList<>();
        dialog = new AnimatedDialog(getActivity());
        allChecked = view.findViewById(R.id.allChecked);
        downtoup = AnimationUtils.loadAnimation(getActivity(), R.anim.downtoup);
        uptodown = AnimationUtils.loadAnimation(getActivity(), R.anim.uptodown);
        // make sure if toobar is shown or not
        HomePageContract.viewMain viewMain = (HomePageContract.viewMain) getActivity();

        if (viewMain!=null){


            viewMain.hideToolbar();

        }

        BtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Linear2.isShown()){

                    //Here Do Your Code
                    String Cat = SerializeCategories(CategoriesIDs);
                    if(Cat.isEmpty()){
                        Toast.makeText(getActivity(), "الرجاء اختيار القسم", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        SendRequest(Cat);
                    }

                }
                else {

                    BtnCancel.setVisibility(View.VISIBLE);
                    Linear2.setVisibility(View.VISIBLE);
                    Linear2.startAnimation(downtoup);
                    BtnFilter.setText(R.string.FilterNow);
                    Linear1.setVisibility(View.GONE);
                }






            }
        });
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtnCancel.setVisibility(View.GONE);
                Linear2.setVisibility(View.GONE);
                BtnFilter.setText(R.string.filter);
                Linear1.setVisibility(View.VISIBLE);
            }
        });


        txCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txCategories.setBackgroundColor(getResources().getColor(R.color.white));
                txPrice.setBackgroundColor(getResources().getColor(R.color.color2));
                LinearRec.setVisibility(View.VISIBLE);
                Linear_price.setVisibility(View.GONE);
            }
        });

        txPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txCategories.setBackgroundColor(getResources().getColor(R.color.color2));
                txPrice.setBackgroundColor(getResources().getColor(R.color.white));
                LinearRec.setVisibility(View.GONE);
                Linear_price.setVisibility(View.VISIBLE);
            }
        });

        allChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(allChecked.isChecked()){
                    rec_categories.setVisibility(View.INVISIBLE);
                    PutAllCategoriesInCategoriesIDs();
                }
                else {
                    CategoriesIDs.clear();
                    rec_categories.setVisibility(View.VISIBLE);
                    rec_categories.removeAllViews();
                    rec_categories.getAdapter().notifyDataSetChanged();
                }
            }
        });

        //adapter Rec

        Checked_Categories_Adapter adapter = new Checked_Categories_Adapter(HomePage.getChildren(),getActivity(),this);
        rec_categories.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec_categories.setAdapter(adapter);




        return view ;
    }

    private void SendRequest(String Cat) {
        //لزم نضافة  .
        BtnCancel.setVisibility(View.GONE);
        Linear2.setVisibility(View.GONE);
        BtnFilter.setText(R.string.filter);
        Linear1.setVisibility(View.VISIBLE);

        dialog.ShowDialog();


        Map<String,String> map = new HashMap<>();
        map.put("category",Cat);
        map.put("perPage", String.valueOf(PerPage));
        map.put("page", String.valueOf(Page));

        if(!ETlessThan.getText().toString().isEmpty())
            map.put("minPrice",ETlessThan.getText().toString());
        if(!ETMoreThan.getText().toString().isEmpty())
            map.put("maxPrice",ETMoreThan.getText().toString());
        if(!searchView.getText().toString().isEmpty())
            map.put("searchQuery",searchView.getText().toString());



        ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);
        Call<List<Products>> call = client.getProducts(map);
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if(response.isSuccess()){

                    //adapter.
                    Rec_Items_Adapter adapter = new Rec_Items_Adapter(response.body(), getActivity());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                    rec_search.setLayoutManager(gridLayoutManager);
                    rec_search.setAdapter(adapter);


                    dialog.Close_Dialog();
                }
                else {
                    dialog.Close_Dialog();
                    Toast.makeText(getActivity(), "حدث خطأ الرجاء المحاولة مرة اخري .", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                dialog.Close_Dialog();
                Toast.makeText(getActivity(), "الرجاء التحقق من اتصالك .", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(!((AppCompatActivity)getActivity()).getSupportActionBar().isShowing()){

            ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        }
    }

    public String SerializeCategories(ArrayList<String> CategoriesIDs ){
        String Categories = "";
        for(int i = 0 ; i< CategoriesIDs.size() ; i++){

            if(i == CategoriesIDs.size()-1){
                Categories += CategoriesIDs.get(i);
            }
            else {
                Categories += CategoriesIDs.get(i) + ",";
            }

        }
        return Categories;
    }

    public void PutAllCategoriesInCategoriesIDs(){

        CategoriesIDs.clear();
        for(int i = 0 ; i < HomePage.getChildren().size(); i++){
            CategoriesIDs.add(HomePage.getChildren().get(i).getId());
          //  Log.d("Cate", CategoriesIDs.get(i) + " \n");
        }

    }

    @Override
    public void AddinCategoryIDsList(String categoryID) {
        CategoriesIDs.add(categoryID);

//        for(int i=0 ; i<CategoriesIDs.size(); i++){
//
//            Log.d("Cate", CategoriesIDs.get(i) + " \n");
//        }

    }
    @Override
    public void RemoveFromCategoryIDsList( String categoryID){
        CategoriesIDs.remove(categoryID);
//        for(int i=0 ; i<CategoriesIDs.size(); i++){
//
//            Log.d("Cate", CategoriesIDs.get(i) + " \n");
//        }
    }
}
