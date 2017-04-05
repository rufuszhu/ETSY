package com.rufus.etsy.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rufus.etsy.BuildConfig;
import com.rufus.etsy.data.model.Listing;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ETSYService {

    String ENDPOINT = "https://openapi.etsy.com/v2/";

    @GET("listings/active?includes=Images(url_75x75):1:0&fields=listing_id,title,price,currency_code,url&limit=10&sort_on=score&api_key=wshptdawfsiuxvk18lblu1lg")
    Observable<Listing> getListings(@Query("page") int page);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static ETSYService newETSYService() {

            OkHttpClient client;
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                client = new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build();
            } else {
                client = new OkHttpClient.Builder().build();
            }

            Gson gson = new GsonBuilder()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ETSYService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(ETSYService.class);
        }
    }
}
