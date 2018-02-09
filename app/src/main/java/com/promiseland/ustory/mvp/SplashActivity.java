package com.promiseland.ustory.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.promiseland.ustory.R;
import com.promiseland.ustory.UStoryApp;
import com.promiseland.ustory.mvp.base.BaseActivity;
import com.promiseland.ustory.ultron.USPreferences;

/**
 * Created by Administrator on 2018/2/9.
 */

public class SplashActivity extends BaseActivity {
    private USPreferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    public void setupActivityComponent() {
        UStoryApp.get()
                .getAppComponent();
    }
}
