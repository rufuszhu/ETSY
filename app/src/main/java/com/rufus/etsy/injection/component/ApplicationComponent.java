package com.rufus.etsy.injection.component;

import android.app.Application;
import android.content.Context;

import com.rufus.etsy.data.DataManager;
import com.rufus.etsy.data.SyncCurrencyService;
import com.rufus.etsy.data.local.PreferencesHelper;
import com.rufus.etsy.data.remote.ETSYService;
import com.rufus.etsy.injection.ApplicationContext;
import com.rufus.etsy.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(ETSYService etsyService);
    void inject(SyncCurrencyService syncCurrencyService);

    @ApplicationContext
    Context context();
    Application application();
    ETSYService etsyService();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();

}
