package com.promiseland.ustory.ui.mvp.settings

import com.promiseland.ustory.base.model.ui.user.UserUiModel
import com.promiseland.ustory.ui.base.BasePresenter

/**
 * Created by joseph on 2018/3/11.
 */
class SettingsOverviewPresenter :BasePresenter<SettingsOverviewActivity>(), SettingsOverviewContract.Presenter {
    override fun getItem(position: Int): SettingsOverviewListItem {
    }

    override fun getItemCount(): Int {
    }

    override fun getUserData(): UserUiModel {
    }

    override fun isLoggedIn(): Boolean {
    }

    override fun logOut() {
    }

    override fun obtainUserData() {
    }

    override fun onSettingsItemClicked(settingsOverviewItem: SettingsOverviewItem) {
    }

    override fun onUserProfileClicked() {
    }

}