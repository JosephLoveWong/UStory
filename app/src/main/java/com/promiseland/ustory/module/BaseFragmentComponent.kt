package com.promiseland.ustory.module

import dagger.Subcomponent

/**
 * Created by Administrator on 2018/2/26.
 */
@BaseFragmentScope
@Subcomponent(modules = [BaseFragmentModule::class])
interface BaseFragmentComponent {
//    fun inject(fragment: NavigationDrawerFragment)
}