package com.promiseland.ustory.module

import com.promiseland.ustory.ui.base.ui.BaseDialogFragment
import com.promiseland.ustory.ui.base.ui.BaseFragment
import dagger.Subcomponent

/**
 * Created by Administrator on 2018/2/26.
 */
@BaseFragmentScope
@Subcomponent(modules = [BaseFragmentModule::class])
interface BaseFragmentComponent {
    fun inject(fragment: BaseFragment)
    fun inject(fragment: BaseDialogFragment)
}