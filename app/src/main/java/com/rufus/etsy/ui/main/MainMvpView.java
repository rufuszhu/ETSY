package com.rufus.etsy.ui.main;

import com.rufus.etsy.data.model.Result;
import com.rufus.etsy.ui.base.MvpView;

import java.util.List;


interface MainMvpView extends MvpView {

    void showResults(List<Result> results);

    void showResultsEmpty();

    void showError();

    void displayPage();

    void showLoading();

    void hideLoading();

    void startSyncCurrencyService();

}
