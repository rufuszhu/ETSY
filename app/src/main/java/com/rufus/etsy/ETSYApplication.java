package com.rufus.etsy;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.rufus.etsy.injection.component.ApplicationComponent;
import com.rufus.etsy.injection.component.DaggerApplicationComponent;
import com.rufus.etsy.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by rufus on 2017-04-03.
 */

public class ETSYApplication extends Application{
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }
    }

    public static ETSYApplication get(Context context) {
        return (ETSYApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
