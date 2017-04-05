package com.rufus.etsy.util;

import com.rufus.etsy.data.local.CurrencyEnum;
import com.rufus.etsy.data.local.PreferencesHelper;

/**
 * Created by rufus on 2017-04-04.
 */

public class CurrencyUtil {
    /**
     * Convert the price from original currency to target currency
     * @param original original currency
     * @param target target currency
     * @param price price to be converted
     * @param preferencesHelper the singleton helper to get the rate
     * @return the price in target currency
     */
    public static double convertCurrency(CurrencyEnum original, CurrencyEnum target, double price, PreferencesHelper preferencesHelper) {
        if (preferencesHelper == null)
            return price;

        float originRate = preferencesHelper.getRate(original.name());
        float targetRate = preferencesHelper.getRate(target.name());

        //if the rates has not been downloaded, just display the original currency
        if ((original != CurrencyEnum.USD && originRate == 0) || (target != CurrencyEnum.USD && targetRate == 0))
            return price;

        if (original == target)
            return price;
        if (original == CurrencyEnum.USD) {
            return usdToOther(price, targetRate);
        } else if (target == CurrencyEnum.USD) {
            return otherToUSD(price, originRate);
        } else {
            //convert the original price to usd first, and then convert to target currency
            return usdToOther(otherToUSD(price, originRate), targetRate);
        }
    }

    private static double otherToUSD(double price, float rate) {
        return price / rate;
    }

    private static double usdToOther(double price, float rate) {
        return price * rate;
    }
}
