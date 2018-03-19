package com.promiseland.ustory.ui.base.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.promiseland.ustory.base.event.LocaleChangedEvent
import com.promiseland.ustory.module.BaseFragmentComponent
import com.promiseland.ustory.module.BaseFragmentModule
import com.promiseland.ustory.ultron.USPreferences
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import javax.inject.Inject

/**
 * Created by joseph on 2018/3/17.
 */
abstract class BaseFragment : Fragment() {
    @Inject
    @JvmField
    @Nullable
    var mEventBus: EventBus? = null
    @Inject
    @JvmField
    @Nullable
    var mPrefs: USPreferences? = null

    private var mCurrentLocale: Locale? = null
    private var mHasBeenDestroyed = false
    private var mUnbinder: Unbinder? = null
    protected var mParent: BaseActivity? = null
    private var mBaseFragmentComponent: BaseFragmentComponent? = null

    fun getBaseFragmentComponent(): BaseFragmentComponent? {
        if (this.mBaseFragmentComponent == null) {
            this.mBaseFragmentComponent = this.mParent?.getBaseActivityComponent()?.plus(BaseFragmentModule())
        }
        return this.mBaseFragmentComponent
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.mParent = context
            getBaseFragmentComponent()?.inject(this)
            return
        }
        throw IllegalStateException("Parent Activity should extend RealmActivity")
    }

    override fun onDetach() {
        this.mParent = null
        super.onDetach()
    }

    fun hasDestroyBeenCalled(): Boolean {
        return this.mHasBeenDestroyed
    }

    override fun onResume() {
        this.mEventBus?.register(this)
        super.onResume()
    }

    override fun onPause() {
        this.mEventBus?.unregister(this)
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mUnbinder = ButterKnife.bind(this, view)
    }

    override fun onDestroyView() {
        this.mHasBeenDestroyed = true
        this.mUnbinder?.unbind()
        super.onDestroyView()
    }

    protected open fun checkLocale() {
        val preferredLocale = this.mPrefs?.getPreferredLocale()
        if (this.mCurrentLocale != preferredLocale) {
            this.mCurrentLocale = preferredLocale
            updateLocaleSpecificUi()
        }
    }

    protected fun updateLocaleSpecificUi() {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun changedLocale(event: LocaleChangedEvent) {
        checkLocale()
    }
}