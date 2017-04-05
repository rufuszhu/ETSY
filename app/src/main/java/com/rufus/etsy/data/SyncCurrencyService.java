package com.rufus.etsy.data;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.rufus.etsy.ETSYApplication;
import com.rufus.etsy.data.local.PreferencesHelper;
import com.rufus.etsy.data.model.CurrencyResult;
import com.rufus.etsy.util.RxUtil;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SyncCurrencyService extends Service {

    @Inject
    DataManager mDataManager;
    @Inject
    PreferencesHelper mPreferenceHelper;

    private Subscription mSubscription;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncCurrencyService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ETSYApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Timber.i("Starting sync...");

        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getCurrencyRate()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CurrencyResult>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Synced successfully!");
                        stopSelf(startId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.w(e, "Error syncing.");
                        stopSelf(startId);

                    }

                    @Override
                    public void onNext(CurrencyResult currencyResult) {
                        mPreferenceHelper.storeCurrency(currencyResult);
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}