package com.alaa.microprocess.lrahtk.View;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaa.microprocess.lrahtk.Adapters.SlideShowAdapter;
import com.alaa.microprocess.lrahtk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


public class GetStarted extends Fragment {

    @BindView(R.id.ViewPager)
    ViewPager viewPage;

    @BindView(R.id.Indictor)
    CircleIndicator circleIndicator;
    AsyncTask asyncTask;

    int pos ; //global
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_get_started, container, false);

        //butter initialize .
        ButterKnife.bind(this,v);

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
        return v ;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        asyncTask.cancel(true);
    }
}
