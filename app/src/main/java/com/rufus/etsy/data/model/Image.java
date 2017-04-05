
package com.rufus.etsy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("url_75x75")
    @Expose
    private String url75x75;

    public String getUrl75x75() {
        return url75x75;
    }

    public void setUrl75x75(String url75x75) {
        this.url75x75 = url75x75;
    }

    public Image withUrl75x75(String url75x75) {
        this.url75x75 = url75x75;
        return this;
    }

}
