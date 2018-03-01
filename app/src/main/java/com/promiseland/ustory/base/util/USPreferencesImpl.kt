package com.promiseland.ustory.base.util

import android.content.Context
import android.content.SharedPreferences
import com.promiseland.ustory.BuildConfig
import com.promiseland.ustory.base.event.LocaleChangedEvent
import com.promiseland.ustory.base.event.SystemLocaleChangedEvent
import com.promiseland.ustory.ultron.USPreferences
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import java.util.*

/**
 * Created by Administrator on 2018/2/9.
 */

class USPreferencesImpl
constructor(context: Context, private val eventBus: EventBus) : USPreferences {
    override fun getDebugModeEnabled(): Boolean {
        return prefs.getBoolean("debug_mode_enabled", false)
    }

    override fun setDebugModeEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("debug_mode_enabled", enabled).apply()
    }

    override fun getFirstStartDate(): Long {
        return prefs.getLong("first_start_date", 0)
    }

    override fun setFirstStartDate(timeMillis: Long) {
        prefs.edit().putLong("first_start_date", timeMillis).apply()
    }

    override fun getHasSeenIntroScreen(): Boolean {
        return prefs.getBoolean("has_seen_intro_screen", false)
    }

    override fun setHasSeenIntroScreen(hasSeen: Boolean) {
        prefs.edit().putBoolean("has_seen_intro_screen", hasSeen).apply()
    }

    override fun getHasSeenWhatsNewScreen(): Boolean {
        return prefs.getBoolean("has_seen_whats_new_screen", true)
    }

    override fun setHasSeenWhatsNewScreen(set: Boolean) {
        prefs.edit().putBoolean("has_seen_whats_new_screen", set).apply()
    }

    override fun needsFirstTimeFeed(): Boolean {
        return Calendar.getInstance().timeInMillis - getFirstStartDate() < 172800000 //TODO WHO IS 17....
    }

    override fun getLastUsedVersionCodeAndUpdate(): Int {
        val version = prefs.getInt("last_used_version_code", 0)
        if (version < BuildConfig.VERSION_CODE) {
            prefs.edit().putInt("last_used_version_code", BuildConfig.VERSION_CODE).apply()
        }
        if (version == 0) {
            try {
                setFirstStartDate(Calendar.getInstance().timeInMillis)
            } catch (th: Throwable) {
            }

        }
        Timber.d("last used version is %d", Integer.valueOf(version))
        return version
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onLanguageChangeEvent(event: SystemLocaleChangedEvent) {
        currentLocale = systemLocale
    }

    fun setPreferredLanguage(updatedLocale: Locale?) {
        val editor = prefs.edit()
        if (updatedLocale == null) {
            editor.remove("locale")
            currentLocale = systemLocale
        } else {
            editor.putString("locale", updatedLocale.language)
            currentLocale = updatedLocale
        }
        editor.apply()
        eventBus.postSticky(LocaleChangedEvent(currentLocale))
    }

    fun isCurrentLocale(locale: Locale?): Boolean {
        val localeString = prefs.getString("locale", null)
        return if (locale == null || localeString == null) {
            locale == null && localeString == null
        } else {
            localeString == locale.language
        }
    }

    private var currentLocale: Locale? = null
    private val prefs: SharedPreferences

    private val preferredLocale: Locale
        get() {
            if (currentLocale == null) {
                val string = prefs.getString("locale", null)
                currentLocale = if (string == null) {
                    systemLocale
                } else {
                    Locale(string)
                }
            }
            return currentLocale as Locale
        }

    private val systemLocale: Locale
        get() {
            return try {
                Locale.getDefault()
            } catch (e: Exception) {
                Locale.CHINA
            }

        }

    init {
        eventBus.register(this)
        prefs = context.getSharedPreferences("us_preferences", Context.MODE_PRIVATE)
    }
}
