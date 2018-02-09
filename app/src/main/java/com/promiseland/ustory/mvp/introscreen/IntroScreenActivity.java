package com.promiseland.ustory.mvp.introscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.promiseland.ustory.R;
import com.promiseland.ustory.mvp.base.BaseActivity;

/**
 * Created by Administrator on 2018/2/9.
 */

public class IntroScreenActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
    }

    @Override
    public void setupActivityComponent() {

    }
}
