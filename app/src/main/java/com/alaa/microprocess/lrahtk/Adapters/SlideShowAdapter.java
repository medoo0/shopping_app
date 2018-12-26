package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alaa.microprocess.lrahtk.R;
import com.bumptech.glide.Glide;

import java.util.zip.Inflater;

/**
 * Created by microprocess on 2018-05-22.
 */

public class SlideShowAdapter extends PagerAdapter {
    private Context context ;
    private LayoutInflater inflater ;
    private int[] images = {
            R.drawable.layer1,
            R.drawable.layer2,
            R.drawable.layer3
    };

    public SlideShowAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return  images.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view==(RelativeLayout)object);

    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.slideshow,container,false);

        ImageView img = (ImageView) view.findViewById(R.id.PageViewimage);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do What You wanna Do .

            }
        });

     ///   img.setImageResource(images[position]); //if used this Loading Image gonna be Slow
        Glide // fast to get Item in Local //Picasso is the faster than glide from internet
                .with(context)
                .load(images[position])
                .into(img);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);

    }

}
