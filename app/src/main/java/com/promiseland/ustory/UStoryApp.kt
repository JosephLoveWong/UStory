package com.promiseland.ustory

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import android.support.annotation.Nullable
import com.promiseland.ustory.data.db.MyObjectBox
import com.promiseland.ustory.service.LanguageChangeBroadcastReceiver
import com.promiseland.ustory.ultron.USPreferences
import com.squareup.leakcanary.LeakCanary
import io.objectbox.BoxStore
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Administrator on 2018/2/9.
 */

class UStoryApp : Application() {
    companion object {
        val ENCRYPTED: Boolean = false // 数据库是否加密
        var appComponent: AppComponent by Delegates.notNull()
        var boxStore: BoxStore by Delegates.notNull()
    }

    private var languageChangeReceiver: LanguageChangeBroadcastReceiver by Delegates.notNull()

    @Inject
    @JvmField
    @Nullable
    var mPrefs: USPreferences? = null//For Debug Mode

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initAppComponent()
        appComponent.inject(this)
//        initLeakCanary()
        initBoxStore()
        registerReceiver()
    }

    override fun onTerminate() {
        unregisterReceiver(languageChangeReceiver)
        super.onTerminate()
    }

    private fun registerReceiver() {
        languageChangeReceiver = LanguageChangeBroadcastReceiver()
        registerReceiver(languageChangeReceiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))
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
        boxStore = MyObjectBox.builder()
                .androidContext(this)
                .build()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}
