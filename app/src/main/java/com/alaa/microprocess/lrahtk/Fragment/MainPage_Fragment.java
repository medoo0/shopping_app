package com.alaa.microprocess.lrahtk.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alaa.microprocess.lrahtk.Adapters.Rec_Nav_Adapter2;
import com.alaa.microprocess.lrahtk.Adapters.SlideShowAdapter;
import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Contract.HomePageContract;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.pojo.Categories;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPage_Fragment extends Fragment {

    ArrayList<String> items;
    ArrayList<Integer> images;
    ArrayList<String> productID;

    @BindView(R.id.yellowCircle_rec)
    RecyclerView yellowCircle_rec ;

    @BindView(R.id.recitems)
    RecyclerView recitems;

    @BindView(R.id.ViewPager)
    ViewPager viewPage;

    ImageView Search_image;

    AsyncTask asyncTask;

    int pos ; //global
    @BindView(R.id.Indictor)
    CircleIndicator circleIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_page_frag_layout,container,false);
        ButterKnife.bind(this,v);

        HomePage.texttoolbar.setText("الرئيسية");

        HomePageContract.viewMain viewMain = (HomePageContract.viewMain) getActivity();

        if (viewMain!=null){


            viewMain.showToobar();

        }

        Search_image  = v.findViewById(R.id.search_image);

        items   = new ArrayList<>();
        images  = new ArrayList<>();
        productID= new ArrayList<>();

        recitems = v.findViewById(R.id.recitems);
        recitems.setNestedScrollingEnabled(false);
        ShowIteminYellowRec();
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

        if(getActivity() != null) {

            viewPage.setAdapter(new SlideShowAdapter(getActivity()));  // Set Adapter
            circleIndicator.setViewPager(viewPage);


            viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    pos = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            final SlideShowAdapter slideShowAdapter = new SlideShowAdapter(getActivity());

            asyncTask = new AsyncTask<Integer, Integer, Void>() {

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    pos = viewPage.getCurrentItem();
                }

                @Override
                protected Void doInBackground(Integer... integers) {
                    boolean still = !false;
                    while (still) {

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pos++;
                        publishProgress(pos);

                    }

                    return null;
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                    if (values[0] == slideShowAdapter.getCount()) {
                        pos = 0;
                        viewPage.setCurrentItem(pos, true);
                    } else {
                        viewPage.setCurrentItem(values[0], true);
                    }

                }
            }.execute();

        }

        return v;
    }



public void ShowIteminYellowRec(){

    ApiMethod client = ApiRetrofit.getRetrofit().create(ApiMethod.class);
    Call<List<Categories>> call = client.getCategories();
    call.enqueue(new Callback<List<Categories>>() {
        @Override
        public void onResponse(@NonNull Call<List<Categories>> call, @NonNull Response<List<Categories>> response) {


            //adapter (Yellow Circles)
            Rec_Nav_Adapter2 rec_items_adapter = new Rec_Nav_Adapter2(response.body(),getActivity(), (HomePageContract.viewMain) getActivity());
            rec_items_adapter.notifyDataSetChanged();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),5);
            yellowCircle_rec.setLayoutManager(gridLayoutManager);
            yellowCircle_rec.setAdapter(rec_items_adapter);

        }

        @Override
        public void onFailure(@NonNull Call<List<Categories>> call, @NonNull Throwable t) {

        }
    });
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
