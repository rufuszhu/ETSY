package com.rufus.etsy.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.rufus.etsy.data.model.CurrencyResult;
import com.rufus.etsy.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    private static final String PREF_FILE_NAME = "etsy_pref_file";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void storeCurrency(CurrencyResult currencyResult) {
        mPref.edit().putFloat(CurrencyEnum.CAD.name(), currencyResult.getQuotes().getUSDCAD()).apply();
        mPref.edit().putFloat(CurrencyEnum.EUR.name(), currencyResult.getQuotes().getUSDEUR()).apply();
        mPref.edit().putFloat(CurrencyEnum.GBP.name(), currencyResult.getQuotes().getUSDGBP()).apply();
    }

    public float getRate(String currencyCode) {
        return mPref.getFloat(currencyCode, 0);
    }

    public void registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mPref.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregister(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mPref.unregisterOnSharedPreferenceChangeListener(listener);
    }

}
