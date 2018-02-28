package com.promiseland.ustory

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.promiseland.ustory.data.db.MyObjectBox
import com.promiseland.ustory.service.LanguageChangeBroadcastReceiver
import com.squareup.leakcanary.LeakCanary
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

        var languageChangeReceiver: LanguageChangeBroadcastReceiver by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        context = this

        initLeakCanary()
        initAppComponent()
        initBoxStore()

        languageChangeReceiver = LanguageChangeBroadcastReceiver()
        registerReceiver(languageChangeReceiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))
    }

    override fun onTerminate() {
        unregisterReceiver(languageChangeReceiver)
        super.onTerminate()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
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
