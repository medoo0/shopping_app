package com.alaa.microprocess.lrahtk.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    final List<Fragment> fragments       = new ArrayList<>();
    final List<String> fragmentstitles   = new ArrayList<>();


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    public void addFrag(Fragment fragment , String Title){
        fragments.add(fragment);
        fragmentstitles.add(Title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentstitles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
