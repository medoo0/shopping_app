package com.alaa.microprocess.lrahtk.View;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.SlideShowAdapter;
import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.Fragment.SignIn;
import com.alaa.microprocess.lrahtk.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


public class GetStarted extends Fragment {

    @BindView(R.id.ViewPager)
    ViewPager viewPage;

    @BindView(R.id.ViewPagerLinear)
    LinearLayout ViewPagerLinear;

    @BindView(R.id.linear2)
    LinearLayout linear2;

    @BindView(R.id.Indictor)
    CircleIndicator circleIndicator;
    @BindView(R.id.btn_start)
    Button srart_btn;
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


        //get height
        final RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.relative);

        ViewTreeObserver viewTreeObserver = layout.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ViewPagerLinear.getLayoutParams();
                    params.height = layout.getHeight()/2;
                    ViewPagerLinear.setLayoutParams(params);


                    RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) linear2.getLayoutParams();
                    params2.height = layout.getHeight()/2;
                    linear2.setLayoutParams(params2);


                    LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) circleIndicator.getLayoutParams();
                    params3.setMargins(0,layout.getHeight()/2 - 150 ,0,0);
                    circleIndicator.setLayoutParams(params3);




                }
            });
        }


        srart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MainActivityContract.View mainView = (MainActivityContract.View) getActivity();
                if (mainView!=null){

                    mainView.getViewPager().setCurrentItem(1);
//                mainView.openSignupFragment();

                }


//                getActivity().getSupportFragmentManager().popBackStack();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
//                        .replace(R.id.Main_fragment,new SignIn())
//                        .addToBackStack(null)
//                        .commit();

            }
        });


        return v ;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        asyncTask.cancel(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(asyncTask.getStatus() == AsyncTask.Status.FINISHED){
            // My AsyncTask is done and onPostExecute was called
            asyncTask.execute();
        }
    }
}
