
package com.rufus.etsy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quotes {

    @SerializedName("USDCAD")
    @Expose
    private float uSDCAD;
    @SerializedName("USDEUR")
    @Expose
    private float uSDEUR;
    @SerializedName("USDGBP")
    @Expose
    private float uSDGBP;

    public float getUSDCAD() {
        return uSDCAD;
    }

    public void setUSDCAD(float uSDCAD) {
        this.uSDCAD = uSDCAD;
    }

    public float getUSDEUR() {
        return uSDEUR;
    }

    public void setUSDEUR(float uSDEUR) {
        this.uSDEUR = uSDEUR;
    }

    public float getUSDGBP() {
        return uSDGBP;
    }

    public void setUSDGBP(float uSDGBP) {
        this.uSDGBP = uSDGBP;
    }

}
