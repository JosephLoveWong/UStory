package com.promiseland.ustory.module

import com.promiseland.ustory.ui.base.ui.BaseActivity
import com.promiseland.ustory.ui.mvp.SplashActivity
import com.promiseland.ustory.ui.mvp.whatsnew.WhatsNewActivity
import dagger.Subcomponent

/**
 * Created by Administrator on 2018/2/26.
 */
@BaseActivityScope
@Subcomponent(modules = [BaseActivityModule::class])
interface BaseActivityComponent {
    fun inject(activity: BaseActivity)
    fun inject(activity: SplashActivity)
    fun inject(activity: WhatsNewActivity)
}