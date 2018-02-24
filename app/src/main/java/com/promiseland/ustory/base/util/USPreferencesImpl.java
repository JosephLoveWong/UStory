package com.promiseland.ustory.base.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.promiseland.ustory.BuildConfig;
import com.promiseland.ustory.base.di.ApplicationContext;
import com.promiseland.ustory.base.event.LocaleChangedEvent;
import com.promiseland.ustory.base.event.SystemLocaleChangedEvent;
import com.promiseland.ustory.ultron.USPreferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Administrator on 2018/2/9.
 */

public class USPreferencesImpl implements USPreferences {
    private Locale mCurrentLocale;
    private final EventBus mEventBus;
    private final SharedPreferences mPrefs;

    public USPreferencesImpl(@ApplicationContext Context context, EventBus eventBus) {
        mEventBus = eventBus;
        mEventBus.register(this);
        mPrefs = context.getSharedPreferences("us_preferences", Context.MODE_PRIVATE);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onLanguageChangeEvent(SystemLocaleChangedEvent event) {
        this.mCurrentLocale = getSystemLocale();
    }

    public Locale getPreferredLocale() {
        if (mCurrentLocale == null) {
            String string = mPrefs.getString("locale", null);
            if (string == null) {
                mCurrentLocale = getSystemLocale();
            } else {
                mCurrentLocale = new Locale(string);
            }
        }
        return mCurrentLocale;
    }

    public void setPreferredLanguage(Locale updatedLocale) {
        SharedPreferences.Editor editor = mPrefs.edit();
        if (updatedLocale == null) {
            editor.remove("locale");
            mCurrentLocale = getSystemLocale();
        } else {
            editor.putString("locale", updatedLocale.getLanguage());
            mCurrentLocale = updatedLocale;
        }
        editor.apply();
        mEventBus.postSticky(new LocaleChangedEvent(mCurrentLocale));
    }

    private Locale getSystemLocale() {
        try {
            return Locale.getDefault();
        } catch (Exception e) {
            return Locale.CHINA;
        }
    }

    public boolean isCurrentLocale(Locale locale) {
        String localeString = mPrefs.getString("locale", null);
        if (locale == null || localeString == null) {
            return locale == null && localeString == null;
        } else {
            return localeString.equals(locale.getLanguage());
        }
    }

    @Override
    public int getLastUsedVersionCodeAndUpdate() {
        int version = mPrefs.getInt("last_used_version_code", 0);
        if (version < BuildConfig.VERSION_CODE) {
            mPrefs.edit().putInt("last_used_version_code", BuildConfig.VERSION_CODE).apply();
        }
        if (version == 0) {
            try {
                setFirstStartDate(Calendar.getInstance().getTimeInMillis());
            } catch (Throwable th) {
            }
        }
        Timber.d("last used version is %d", Integer.valueOf(version));
        return version;
    }

    public long getFirstStartDate() {
        return mPrefs.getLong("first_start_date", 0);
    }

    public void setFirstStartDate(long timeMillis) {
        mPrefs.edit().putLong("first_start_date", timeMillis).apply();
    }

    @Override
    public void setDebugModeEnabled(boolean enabled) {
        mPrefs.edit().putBoolean("debug_mode_enabled", enabled).apply();
    }

    @Override
    public boolean getDebugModeEnabled() {
        return mPrefs.getBoolean("debug_mode_enabled", false);
    }
}
