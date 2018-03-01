package com.promiseland.ustory.service.impl

import com.promiseland.ustory.service.api.InstallationDataService
import com.promiseland.ustory.service.api.UltronService
import com.promiseland.ustory.service.base.BackgroundHandler
import com.promiseland.ustory.service.base.Command
import com.promiseland.ustory.ultron.USPreferences

/**
 * Created by Administrator on 2018/3/1.
 */
class InstallationDataServiceImpl
constructor(val mUltronService: UltronService, val mPreferences: USPreferences, val mBackgroundHandler: BackgroundHandler) : InstallationDataService {

    class CreateInstallationCommand : Command(0) {// TODO commandId
        override fun doInBackground() {
            // TODO
        }

    }

    override fun updateInstallation() {
        mBackgroundHandler.sendMessage(CreateInstallationCommand(), this)
    }

}