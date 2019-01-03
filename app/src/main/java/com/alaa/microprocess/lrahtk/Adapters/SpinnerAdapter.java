package com.alaa.microprocess.lrahtk.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alaa.microprocess.lrahtk.R;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {


    Context context;
    String [] arrayList;
    LayoutInflater inflter;
    public SpinnerAdapter(Context applicationContext , String [] arrayList) {
        this.context = applicationContext;
        this.arrayList = arrayList;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return arrayList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view      = inflter.inflate(R.layout.spinner_style, null);
        TextView names = view.findViewById(R.id.tvcontainsworsd);
        names.setText(arrayList[position]);
        return view;
    }
}
