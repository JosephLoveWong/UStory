package com.promiseland.ustory.ultron

/**
 * Created by Administrator on 2018/2/9.
 */

interface USPreferences {
    fun getLastUsedVersionCodeAndUpdate(): Int

    fun getDebugModeEnabled(): Boolean
    fun setDebugModeEnabled(enabled: Boolean)

    fun getHasSeenIntroScreen(): Boolean
    fun setHasSeenIntroScreen(hasSeen: Boolean)

    fun getHasSeenWhatsNewScreen(): Boolean
    fun setHasSeenWhatsNewScreen(set: Boolean)

    fun needsFirstTimeFeed(): Boolean

    fun getFirstStartDate(): Long
    fun setFirstStartDate(timeMillis: Long)
}
