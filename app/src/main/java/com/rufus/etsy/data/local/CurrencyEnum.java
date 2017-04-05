package com.rufus.etsy.data.local;


public enum CurrencyEnum {
    CAD("CAD"),
    EUR("EUR"),
    GBP("GBP"),
    USD("USD"),
    NOT_SUPPORT("NOT_SUPPORT");

    private String currencyCode;

    CurrencyEnum(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public static CurrencyEnum nameToEnum(String name) {
        for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (currencyEnum.name().equals(name))
                return currencyEnum;
        }
        return NOT_SUPPORT;
    }
}