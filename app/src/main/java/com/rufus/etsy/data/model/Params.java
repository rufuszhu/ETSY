
package com.rufus.etsy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Params {

    @SerializedName("limit")
    @Expose
    private String limit;
    @SerializedName("offset")
    @Expose
    private String offset;
    @SerializedName("page")
    @Expose
    private Object page;
    @SerializedName("keywords")
    @Expose
    private Object keywords;
    @SerializedName("sort_on")
    @Expose
    private String sortOn;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;
    @SerializedName("min_price")
    @Expose
    private Object minPrice;
    @SerializedName("max_price")
    @Expose
    private Object maxPrice;
    @SerializedName("color")
    @Expose
    private Object color;
    @SerializedName("color_accuracy")
    @Expose
    private Integer colorAccuracy;
    @SerializedName("tags")
    @Expose
    private Object tags;
    @SerializedName("category")
    @Expose
    private Object category;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("lat")
    @Expose
    private Object lat;
    @SerializedName("lon")
    @Expose
    private Object lon;
    @SerializedName("region")
    @Expose
    private Object region;
    @SerializedName("geo_level")
    @Expose
    private String geoLevel;
    @SerializedName("accepts_gift_cards")
    @Expose
    private String acceptsGiftCards;
    @SerializedName("translate_keywords")
    @Expose
    private String translateKeywords;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Params withLimit(String limit) {
        this.limit = limit;
        return this;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public Params withOffset(String offset) {
        this.offset = offset;
        return this;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public Params withPage(Object page) {
        this.page = page;
        return this;
    }

    public Object getKeywords() {
        return keywords;
    }

    public void setKeywords(Object keywords) {
        this.keywords = keywords;
    }

    public Params withKeywords(Object keywords) {
        this.keywords = keywords;
        return this;
    }

    public String getSortOn() {
        return sortOn;
    }

    public void setSortOn(String sortOn) {
        this.sortOn = sortOn;
    }

    public Params withSortOn(String sortOn) {
        this.sortOn = sortOn;
        return this;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Params withSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public Object getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Object minPrice) {
        this.minPrice = minPrice;
    }

    public Params withMinPrice(Object minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public Object getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Object maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Params withMaxPrice(Object maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public Object getColor() {
        return color;
    }

    public void setColor(Object color) {
        this.color = color;
    }

    public Params withColor(Object color) {
        this.color = color;
        return this;
    }

    public Integer getColorAccuracy() {
        return colorAccuracy;
    }

    public void setColorAccuracy(Integer colorAccuracy) {
        this.colorAccuracy = colorAccuracy;
    }

    public Params withColorAccuracy(Integer colorAccuracy) {
        this.colorAccuracy = colorAccuracy;
        return this;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public Params withTags(Object tags) {
        this.tags = tags;
        return this;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public Params withCategory(Object category) {
        this.category = category;
        return this;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Params withLocation(Object location) {
        this.location = location;
        return this;
    }

    public Object getLat() {
        return lat;
    }

    public void setLat(Object lat) {
        this.lat = lat;
    }

    public Params withLat(Object lat) {
        this.lat = lat;
        return this;
    }

    public Object getLon() {
        return lon;
    }

    public void setLon(Object lon) {
        this.lon = lon;
    }

    public Params withLon(Object lon) {
        this.lon = lon;
        return this;
    }

    public Object getRegion() {
        return region;
    }

    public void setRegion(Object region) {
        this.region = region;
    }

    public Params withRegion(Object region) {
        this.region = region;
        return this;
    }

    public String getGeoLevel() {
        return geoLevel;
    }

    public void setGeoLevel(String geoLevel) {
        this.geoLevel = geoLevel;
    }

    public Params withGeoLevel(String geoLevel) {
        this.geoLevel = geoLevel;
        return this;
    }

    public String getAcceptsGiftCards() {
        return acceptsGiftCards;
    }

    public void setAcceptsGiftCards(String acceptsGiftCards) {
        this.acceptsGiftCards = acceptsGiftCards;
    }

    public Params withAcceptsGiftCards(String acceptsGiftCards) {
        this.acceptsGiftCards = acceptsGiftCards;
        return this;
    }

    public String getTranslateKeywords() {
        return translateKeywords;
    }

    public void setTranslateKeywords(String translateKeywords) {
        this.translateKeywords = translateKeywords;
    }

    public Params withTranslateKeywords(String translateKeywords) {
        this.translateKeywords = translateKeywords;
        return this;
    }

}
