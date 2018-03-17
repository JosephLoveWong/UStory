package com.promiseland.ustory

import com.promiseland.ustory.module.BaseActivityComponent
import com.promiseland.ustory.module.BaseActivityModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Administrator on 2018/2/9.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(uStoryApp: UStoryApp)
    fun plus(module: BaseActivityModule): BaseActivityComponent
}
