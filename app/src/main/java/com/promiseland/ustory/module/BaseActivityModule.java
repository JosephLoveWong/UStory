package com.promiseland.ustory.module;

import android.app.Activity;

import dagger.Module;

@Module
public final class BaseActivityModule {
    private final Activity activity;

    public BaseActivityModule(Activity activity) {
        this.activity = activity;
    }
}
