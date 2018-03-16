package com.promiseland.ustory.ui.base.ui

import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import com.promiseland.ustory.R
import com.promiseland.ustory.UStoryApp
import com.promiseland.ustory.base.Constants
import com.promiseland.ustory.base.event.LocaleChangedEvent
import com.promiseland.ustory.base.event.MessageEvent
import com.promiseland.ustory.base.model.Screen
import com.promiseland.ustory.base.util.APILevelHelper
import com.promiseland.ustory.base.util.ConfigurationUtils
import com.promiseland.ustory.base.util.USPreferencesImpl
import com.promiseland.ustory.module.BaseActivityComponent
import com.promiseland.ustory.module.BaseActivityModule
import com.promiseland.ustory.ui.util.SnackbarHelper
import com.promiseland.ustory.ui.util.SpannableStringHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * Created by joseph on 2018/3/15.
 */
class BaseActivity : AppCompatActivity() {
    var mEventBus: EventBus? = null
    private var currentLocale: Locale? = null
    private var mBaseActivityComponent: BaseActivityComponent? = null

    private var mPreferences: USPreferencesImpl? = null
    private val mHandler = Handler()

    fun getBaseActivityComponent(): BaseActivityComponent? {
        if (mBaseActivityComponent == null) {
            mBaseActivityComponent = UStoryApp.appComponent.plus(BaseActivityModule())
        }

        return mBaseActivityComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        var isOrientationChanged = true
        getBaseActivityComponent()?.inject(this)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            isOrientationChanged = false
        }
        Screen.onCreate(this, windowManager.defaultDisplay, isOrientationChanged)
        checkLocale()
    }

    override fun onResume() {
        try {
            this.mEventBus?.register(this)
        } catch (e: EventBusException) {
        }

        super.onResume()
        Screen.onResume(windowManager.defaultDisplay)
        checkLocale()
    }

    override fun onPause() {
        this.mEventBus?.unregister(this)
        super.onPause()
    }

    override fun onDestroy() {
        mBaseActivityComponent = null
        mHandler?.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun checkLocale() {
        val preferredLocale = mPreferences?.getPreferredLocale()
        if (this.currentLocale != preferredLocale) {
            this.currentLocale = preferredLocale
            ConfigurationUtils.updateBaseLocale(this, this.currentLocale)
        }
    }

    override fun setTitle(title: CharSequence?) {
        if (title != null) {
            super.setTitle(SpannableStringHelper.createSpannableStringWithTypeface(this, R.font.brandon_text_medium, title))
        }
    }

    fun getSnackBarView(): View? {
        val root = findViewById<View>(16908290) as ViewGroup
        return if (root == null || root.childCount <= 0) {
            null
        } else root.getChildAt(0)
    }

    fun setActionBarTransparent(shadow: View?) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            val actionBarBG = ContextCompat.getDrawable(this, R.drawable.actionbar_background)
            actionBarBG?.alpha = 0
            actionBar.setBackgroundDrawable(actionBarBG)
        }
        shadow?.visibility = View.GONE
    }

    fun adjustScrimOffset(scrim: View?) {
        if (scrim != null && APILevelHelper.isAPILevelMinimal(LOLLIPOP)) {
            val layoutParams = scrim.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.height += ConfigurationUtils.getStatusBarHeight(this)
            scrim.layoutParams = layoutParams
        }
    }

    protected fun initToolbar(updateContainerMargin: Boolean) {
        val toolbar = findViewById<View>(R.id.toolbar_layout) as Toolbar
        val appBarLayout = findViewById<View>(R.id.appbar_layout) as AppBarLayout
        if (toolbar != null) {
            initToolbar(toolbar, updateContainerMargin)
        } else {
            initToolbar(appBarLayout, updateContainerMargin)
        }
    }

    protected fun initToolbar(barView: ViewGroup, updateContainerMargin: Boolean) {
        if (barView is Toolbar) {
            try {
                setSupportActionBar(barView)
            } catch (e: NoClassDefFoundError) {
            }

        }
        if (APILevelHelper.isAPILevelMinimal(LOLLIPOP)) {
            val view = findViewById<View>(R.id.drawer_layout)
            if (view != null) {
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            ConfigurationUtils.adjustToolbarHeight(this, barView)
        }
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
            if (updateContainerMargin && APILevelHelper.isAPILevelMinimal(LOLLIPOP)) {
                updateContainerMargin(ConfigurationUtils.getStatusBarHeight(this))
            }
        }
    }

    private fun updateContainerMargin(statusBarSize: Int) {
        translateView(findViewById(R.id.container), statusBarSize)
    }

    protected fun translateView(view: View?, statusBarSize: Int) {
        if (view != null) {
            val containerParams = view.layoutParams
            if (containerParams is ViewGroup.MarginLayoutParams) {
                containerParams.setMargins(containerParams.leftMargin, containerParams.topMargin - statusBarSize, containerParams.rightMargin, containerParams.bottomMargin)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun localeChanged(event: LocaleChangedEvent) {
        checkLocale()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun showMessage(event: MessageEvent) {
        if (event.msgId !== Constants.NO_MESSAGE) {
            SnackbarHelper.show(this, event.msgId)
            this.mEventBus.removeStickyEvent(event as Any)
        }
    }
}