package com.alaa.microprocess.lrahtk.Model;

import com.alaa.microprocess.lrahtk.Contract.MainActivityContract;
import com.alaa.microprocess.lrahtk.Presenter.MainActivityPresenter;

/**
 * Created by microprocess on 2018-12-26.
 */

public class MainActivityModel implements MainActivityContract.Model {
    MainActivityContract.Presenter presenter;

    public MainActivityModel(MainActivityPresenter mainActivityPresenter) {

        presenter = mainActivityPresenter;

    }
}
