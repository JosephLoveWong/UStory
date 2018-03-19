package com.promiseland.ustory.ultron

import java.util.*

/**
 * Created by Administrator on 2018/2/9.
 */

interface USPreferences {
    fun getLastUsedVersionCodeAndUpdate(): Int
    fun getPreferredLocale(): Locale

    fun getDebugModeEnabled(): Boolean
    fun setDebugModeEnabled(enabled: Boolean)

    fun getHasSeenIntroScreen(): Boolean
    fun setHasSeenIntroScreen(hasSeen: Boolean)

    fun getHasSeenWhatsNewScreen(): Boolean
    fun setHasSeenWhatsNewScreen(set: Boolean)

    fun getFirstStartDate(): Long
    fun setFirstStartDate(timeMillis: Long)
}
