package com.rufus.etsy.injection.component;

import com.rufus.etsy.injection.PerActivity;
import com.rufus.etsy.injection.module.ActivityModule;
import com.rufus.etsy.ui.main.MainActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
