package com.alaa.microprocess.lrahtk.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Items_Adapter;
import com.alaa.microprocess.lrahtk.Adapters.Rec_Nav_Adapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.pojo.Categories;
import com.alaa.microprocess.lrahtk.pojo.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Product_Activity extends AppCompatActivity implements View.OnClickListener{


    RecyclerView rectwo;
    ImageView backhome;
    ImageView searchindadding;
    TextView CatName;
    AnimatedDialog dialog;
    SearchView searchView;
    Rec_Items_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_);
        rectwo  = findViewById(R.id.rectwo);
        backhome = findViewById(R.id.backhome);
        CatName = findViewById(R.id.categoryName);
        backhome.setOnClickListener(this);
        dialog = new AnimatedDialog(this);
        searchindadding = findViewById(R.id.searchindadding);
        searchView = findViewById(R.id.SearchView);



        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            CatName.setText(bundle.getString("name"));
            showItemsinREC(bundle.getString("id"));
            dialog.ShowDialog();
        }



        searchindadding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchView.isShown()){
                    searchView.setVisibility(View.GONE);
                }
                else {
                    searchView.setVisibility(View.VISIBLE);
                    searchView.setIconified(false);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter!=null){

                    adapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter!=null){
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });

    }





    public void showItemsinREC(final String ID){


        ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);
        Call<List<Products>> call = client.getProducts();
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NonNull Call<List<Products>> call, @NonNull Response<List<Products>> response) {
                if(response.isSuccess()) {
                    List<Products> filterProduct = new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++) {

                        if (response.body().get(i).getCategory().getId().equals(ID)) {
                            filterProduct.add(response.body().get(i));
                        }
                        if (i == response.body().size() - 1) {
                            dialog.Close_Dialog();
                        }
                    }


                    //adapter
                    adapter = new Rec_Items_Adapter(filterProduct, Product_Activity.this);
                    adapter.notifyDataSetChanged();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(Product_Activity.this, 2);
                    rectwo.setLayoutManager(gridLayoutManager);
                    rectwo.setAdapter(adapter);
                }
                else {
                    dialog.Close_Dialog();
                    Toast.makeText(Product_Activity.this, "حدث خطأ الرجاء المحاولة مرة اخري .", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {
                dialog.Close_Dialog();
                Toast.makeText(Product_Activity.this, "الرجاء التحقق من اتصالك .", Toast.LENGTH_LONG).show();
            }
        });





    }

    @Override
    public void onClick(View v) {
        if (v  ==  backhome){

            finish();


        }
    }

}
