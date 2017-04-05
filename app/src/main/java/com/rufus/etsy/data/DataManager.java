package com.rufus.etsy.data;

import com.rufus.etsy.data.local.PreferencesHelper;
import com.rufus.etsy.data.model.CurrencyResult;
import com.rufus.etsy.data.model.Listing;
import com.rufus.etsy.data.remote.CurrencyService;
import com.rufus.etsy.data.remote.ETSYService;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager {

    private final ETSYService mETSYService;
    private final CurrencyService mCurrencyService;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    DataManager(CurrencyService currencyService, ETSYService etsyService, PreferencesHelper preferencesHelper) {
        mCurrencyService = currencyService;
        mETSYService = etsyService;
        mPreferencesHelper = preferencesHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Listing> getListingFromServer(int page) {
        return mETSYService.getListings(page);
    }

    Observable<CurrencyResult> getCurrencyRate() {
        return mCurrencyService.getCurrency();
    }

}
