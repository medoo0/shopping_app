package com.alaa.microprocess.lrahtk.Presenter;

import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.Model.MainActivityModel;
import com.alaa.microprocess.lrahtk.View.MainActivity;

/**
 * Created by microprocess on 2018-12-26.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {
    MainActivityContract.Model  model ;
    MainActivityContract.View view ;
    public MainActivityPresenter(MainActivity mainActivity) {
        view = mainActivity ;
        model = new MainActivityModel(this);
    }
}
