package com.promiseland.ustory.base.util;

import android.content.SharedPreferences;

import com.promiseland.ustory.ultron.USPreferences;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/2/9.
 */

public class USPreferencesImpl implements USPreferences{
    private final EventBus mEventBus;
    private final SharedPreferences mPrefs;

    public USPreferencesImpl(EventBus eventBus, SharedPreferences prefs) {
        mEventBus = eventBus;
        mPrefs = prefs;
    }

    @Override
    public int getLastUsedVersionCodeAndUpdate() {
        return 0;
    }
}
