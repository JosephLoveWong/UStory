package com.promiseland.ustory

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

/**
 * Created by Administrator on 2018/2/9.
 */

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    internal fun provideContext(): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    internal fun provideEventBus(): EventBus {
        return EventBus.getDefault()
    }
}
