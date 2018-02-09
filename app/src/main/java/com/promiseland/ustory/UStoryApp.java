package com.promiseland.ustory;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 * Created by Administrator on 2018/2/9.
 */

public class UStoryApp extends Application {
    private AppComponent mAppComponent;

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static UStoryApp get() {
        return (UStoryApp) mContext.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initAppComponent();
    }

    private void initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
