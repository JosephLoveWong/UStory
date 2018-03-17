package com.promiseland.ustory.ui.base.ui

import android.content.Context
import android.support.annotation.Nullable
import android.support.v4.app.DialogFragment
import com.promiseland.ustory.base.util.ConfigurationUtils
import com.promiseland.ustory.module.BaseFragmentModule
import com.promiseland.ustory.ultron.USPreferences
import org.greenrobot.eventbus.EventBus
import java.util.*
import javax.inject.Inject

/**
 * Created by joseph on 2018/3/17.
 */
class BaseDialogFragment : DialogFragment() {
    @Inject
    @JvmField
    @Nullable
    var mEventBus: EventBus? = null
    @Inject
    @JvmField
    @Nullable
    var mPrefs: USPreferences? = null

    private var mCurrentLocale: Locale? = null
    protected var mParent: BaseActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.mParent = context
            this.mParent?.getBaseActivityComponent()?.plus(BaseFragmentModule())?.inject(this)
            return
        }
        throw IllegalStateException("Parent Activity should extend RealmActivity")
    }

    override fun onDetach() {
        super.onDetach()
        this.mParent = null
    }

    override fun onResume() {
        try {
            this.mEventBus?.register(this)
        } catch (e: Exception) {
        }

        super.onResume()
        updateLanguage()
    }

    override fun onPause() {
        this.mEventBus?.unregister(this)
        super.onPause()
    }

    protected fun updateLanguage() {
        val preferredLocale = this.mPrefs?.getPreferredLocale()
        if (this.mCurrentLocale != preferredLocale) {
            this.mCurrentLocale = preferredLocale
            updateLocaleSpecificUi()
        }
    }

    protected fun updateLocaleSpecificUi() {}

    protected fun setUpDialogWindow(width: Int, height: Int, maxPercentageHeight: Float) {
        if (dialog != null) {
            val window = dialog.window
            if (window != null) {
                val screenSize = ConfigurationUtils.getScreenSize(activity)
                val attributes = window.attributes
                attributes.width = width
                attributes.height = height
                val adjustedHeight = screenSize.y.toFloat() * maxPercentageHeight
                if (adjustedHeight > 0.0f && (adjustedHeight < attributes.height.toFloat() || attributes.height == -1)) {
                    attributes.height = adjustedHeight.toInt()
                }
                window.attributes = attributes
            }
        }
    }
}