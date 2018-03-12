package com.promiseland.ustory.ui.mvp.settings

import com.promiseland.ustory.R
import com.promiseland.ustory.base.model.ui.user.UserUiModel
import com.promiseland.ustory.service.api.UserService
import com.promiseland.ustory.ui.base.BasePresenter
import com.promiseland.ustory.ui.mvp.settings.adapter.SettingsOverviewHeaderItem
import com.promiseland.ustory.ui.mvp.settings.adapter.SettingsOverviewItem
import com.promiseland.ustory.ui.mvp.settings.adapter.SettingsOverviewListItem
import com.promiseland.ustory.ui.mvp.settings.adapter.SettingsOverviewUserProfileItem
import java.util.Arrays.asList

/**
 * Created by joseph on 2018/3/11.
 */
class SettingsOverviewPresenter : BasePresenter<SettingsOverviewActivity>(), SettingsOverviewContract.Presenter {
    //    var shareManager: ShareManager? = null
//    private var userData: UserUiModel? = null
//    var userService: UserService? = null
    private val settingsOverviewItems: List<SettingsOverviewListItem> by lazy {
        asList(SettingsOverviewUserProfileItem(),
                SettingsOverviewHeaderItem(R.string.settings_overview_title_system)/*,
                SettingsOverviewItem(R.string.settings_header_languages),
                SettingsOverviewItem(R.string.settings_header_measurements),
                SettingsOverviewItem(R.string.settings_video_playback_header),
                SettingsOverviewItem(R.string.settings_push_notification_title),
                SettingsOverviewHeaderItem(R.string.settings_overview_title_more),
                SettingsOverviewItem(R.string.navigation_about_us),
                SettingsOverviewItem(R.string.MENU_FEEDBACK),
                SettingsOverviewItem(R.string.menu_tell_a_friend)*/)
    }

    override fun getItem(position: Int): SettingsOverviewListItem {
    }

    override fun getItemCount(): Int {
        return settingsOverviewItems.size - if (isLoggedIn()) 0 else 1
    }

    override fun getUserData(): UserUiModel {
        // TODO
        return object : UserUiModel {

        }
    }

    override fun isLoggedIn(): Boolean {
        // TODO
        return true
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