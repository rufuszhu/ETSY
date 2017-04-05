package com.rufus.etsy.injection.module;

import android.app.Application;
import android.content.Context;

import com.rufus.etsy.data.remote.CurrencyService;
import com.rufus.etsy.data.remote.ETSYService;
import com.rufus.etsy.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ETSYService provideETSYService() {
        return ETSYService.Creator.newETSYService();
    }

    @Provides
    @Singleton
    CurrencyService provideSyncCurrencyService() {
        return CurrencyService.Creator.newCurrencyService();
    }

}
