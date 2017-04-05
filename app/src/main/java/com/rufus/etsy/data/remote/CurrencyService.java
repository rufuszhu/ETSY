package com.rufus.etsy.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rufus.etsy.BuildConfig;
import com.rufus.etsy.data.model.CurrencyResult;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface CurrencyService {
    String ENDPOINT = "http://apilayer.net/api/";

    @GET("live?access_key=6fa487fe0f73882ab29f6be40a480f7f&currencies=CAD,EUR,GBP")
    Observable<CurrencyResult> getCurrency();

    /******** Helper class that sets up a new services *******/
    class Creator {
        public static CurrencyService newCurrencyService() {

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
                    .baseUrl(CurrencyService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();

            return retrofit.create(CurrencyService.class);
        }
    }
}
