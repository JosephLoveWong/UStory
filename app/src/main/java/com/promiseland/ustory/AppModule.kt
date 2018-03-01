package com.promiseland.ustory

import android.app.Application
import android.content.Context
import com.promiseland.ustory.base.util.USPreferencesImpl
import com.promiseland.ustory.service.api.InstallationDataService
import com.promiseland.ustory.service.api.UltronService
import com.promiseland.ustory.service.base.BackgroundHandler
import com.promiseland.ustory.service.base.BackgroundHandlerImpl
import com.promiseland.ustory.service.impl.InstallationDataServiceImpl
import com.promiseland.ustory.service.impl.UltronServiceImpl
import com.promiseland.ustory.ultron.USPreferences
import com.promiseland.ustory.ultron.Ultron
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
    internal fun provideBackgroundHandler(): BackgroundHandler {
        return BackgroundHandlerImpl()
    }

    @Singleton
    @Provides
    internal fun providePreferences(context: Context, eventBus: EventBus): USPreferences {
        return USPreferencesImpl(context, eventBus)
    }

    @Singleton
    @Provides
    internal fun provideUltronService(eventBus: EventBus, ultron: Ultron, backgroundHandler: BackgroundHandler): UltronService {
        return UltronServiceImpl(eventBus, ultron, backgroundHandler)
    }

    @Singleton
    @Provides
    internal fun provideInstallationDataService(ultronService: UltronService, kitchenPreferences: USPreferences, backgroundHandler: BackgroundHandler): InstallationDataService {
        return InstallationDataServiceImpl(ultronService, kitchenPreferences, backgroundHandler)
    }

}
