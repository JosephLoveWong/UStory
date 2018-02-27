package com.promiseland.ustory

import com.promiseland.ustory.module.BaseActivityComponent
import com.promiseland.ustory.module.BaseActivityModule
import com.promiseland.ustory.module.BaseFragmentComponent
import com.promiseland.ustory.module.BaseFragmentModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Administrator on 2018/2/9.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun plus(module: BaseActivityModule): BaseActivityComponent
    fun plus(module: BaseFragmentModule): BaseFragmentComponent
}
