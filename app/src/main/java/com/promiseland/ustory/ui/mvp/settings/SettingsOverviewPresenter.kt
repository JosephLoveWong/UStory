package com.promiseland.ustory.ui.mvp.settings

import com.promiseland.ustory.R
import com.promiseland.ustory.base.model.ui.user.UserUiModel
import com.promiseland.ustory.base.util.FieldHelper
import com.promiseland.ustory.ui.base.mvp.BasePresenter
import com.promiseland.ustory.ui.mvp.settings.adapter.*
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
                SettingsOverviewSeparatorItem(),
                SettingsOverviewHeaderItem(R.string.settings_overview_title_system),
                SettingsOverviewItem(R.drawable.vec_icon_settings_languages, R.string.settings_header_languages, SettingsOverviewItemType.LANGUAGE),
                SettingsOverviewItem(R.drawable.vec_icon_settings_measurements, R.string.settings_header_measurements, SettingsOverviewItemType.MEASUREMENTS),
                SettingsOverviewItem(R.drawable.vec_icon_settings_autoplay, R.string.settings_video_playback_header, SettingsOverviewItemType.VIDEO_AUTOPLAY),
                SettingsOverviewItem(R.drawable.vec_icon_settings_notifications, R.string.settings_push_notification_title, SettingsOverviewItemType.NOTIFICATIONS),
                SettingsOverviewSeparatorItem(),
                SettingsOverviewHeaderItem(R.string.settings_overview_title_more),
                SettingsOverviewItem(R.drawable.vec_icon_settings_about_us, R.string.navigation_about_us, SettingsOverviewItemType.ABOUT_US),
                SettingsOverviewItem(R.drawable.vec_icon_settings_feedback, R.string.MENU_FEEDBACK, SettingsOverviewItemType.FEEDBACK),
                SettingsOverviewItem(R.drawable.vec_icon_settings_share, R.string.settings_invite_friends_title, SettingsOverviewItemType.SHARE),
                SettingsOverviewItem(R.drawable.vec_icon_settings_legal_info, R.string.settings_item_legal_info_title, SettingsOverviewItemType.LEGAL_INFO),
                SettingsOverviewLogOutItem())
    }

    override fun getItem(position: Int): SettingsOverviewListItem? {
        return if (FieldHelper.hasPosition(settingsOverviewItems, position)) settingsOverviewItems[position] else null
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