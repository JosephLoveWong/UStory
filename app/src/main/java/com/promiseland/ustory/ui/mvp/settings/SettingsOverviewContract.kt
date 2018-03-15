package com.promiseland.ustory.ui.mvp.settings

import com.promiseland.ustory.base.event.ErrorEvent
import com.promiseland.ustory.base.model.ui.user.UserUiModel
import com.promiseland.ustory.base.model.ui.user.UserUiModelSuccess
import com.promiseland.ustory.ui.base.mvp.BaseContract
import com.promiseland.ustory.ui.mvp.settings.adapter.SettingsOverviewItem
import com.promiseland.ustory.ui.mvp.settings.adapter.SettingsOverviewListItem

/**
 * Created by joseph on 2018/3/11.
 */
interface SettingsOverviewContract {
    interface View : BaseContract.BaseView {
        fun openAboutUs()

        fun openLegalInfo()

        fun openLoginActivity()

        fun openSettingsDetail(settingsOverviewItem: SettingsOverviewItem)

        fun openUserProfile(userUiModelSuccess: UserUiModelSuccess)

        fun showUserLoadedError(errorEvent: ErrorEvent)

        fun updateLogOutButtonState(state: Boolean)

        fun updateUserData()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun getItem(position: Int): SettingsOverviewListItem?

        fun getItemCount(): Int

        fun getUserData(): UserUiModel

        fun isLoggedIn(): Boolean

        fun logOut()

        fun obtainUserData()

        fun onSettingsItemClicked(settingsOverviewItem: SettingsOverviewItem)

        fun onUserProfileClicked()
    }
}