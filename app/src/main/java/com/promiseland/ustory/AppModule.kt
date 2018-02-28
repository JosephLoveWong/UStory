package com.promiseland.ustory

import android.app.Application
import android.content.Context
import com.promiseland.ustory.base.util.USPreferencesImpl
import com.promiseland.ustory.ultron.USPreferences
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

    @Singleton
    @Provides
    internal fun providePreferences(context: Context, eventBus: EventBus): USPreferences {
        return USPreferencesImpl(context, eventBus)
    }

}
