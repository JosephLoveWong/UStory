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
import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/9.
 */

class USPreferencesImpl @Inject
constructor(context: Context, private val eventBus: EventBus) : USPreferences {
    private var currentLocale: Locale? = null
    private val prefs: SharedPreferences

    val preferredLocale: Locale
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

    override val lastUsedVersionCodeAndUpdate: Int
        get() {
            val version = prefs.getInt("last_used_version_code", 0)
            if (version < BuildConfig.VERSION_CODE) {
                prefs.edit().putInt("last_used_version_code", BuildConfig.VERSION_CODE).apply()
            }
            if (version == 0) {
                try {
                    firstStartDate = Calendar.getInstance().timeInMillis
                } catch (th: Throwable) {
                }

            }
            Timber.d("last used version is %d", Integer.valueOf(version))
            return version
        }

    var firstStartDate: Long
        get() = prefs.getLong("first_start_date", 0)
        set(timeMillis) = prefs.edit().putLong("first_start_date", timeMillis).apply()

    override var debugModeEnabled: Boolean
        get() = prefs.getBoolean("debug_mode_enabled", false)
        set(enabled) = prefs.edit().putBoolean("debug_mode_enabled", enabled).apply()

    init {
        eventBus.register(this)
        prefs = context.getSharedPreferences("us_preferences", Context.MODE_PRIVATE)
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
}
