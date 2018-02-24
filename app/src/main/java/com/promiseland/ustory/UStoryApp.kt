package com.promiseland.ustory

import android.app.Application
import android.content.Context
import com.promiseland.ustory.data.db.MyObjectBox
import io.objectbox.BoxStore
import timber.log.Timber
import kotlin.properties.Delegates

/**
 * Created by Administrator on 2018/2/9.
 */

class UStoryApp : Application() {
    companion object {
        val ENCRYPTED: Boolean = false // 数据库是否加密
        var context: Context by Delegates.notNull()
        var appComponent: AppComponent by Delegates.notNull()
        var boxStore: BoxStore by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        context = this
        initAppComponent()
        initBoxStore()
    }

    private fun initBoxStore() {
        boxStore = MyObjectBox.builder().androidContext(this).build()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }


}
