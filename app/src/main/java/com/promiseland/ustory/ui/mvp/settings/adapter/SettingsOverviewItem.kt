package com.promiseland.ustory.ui.mvp.settings.adapter

import com.promiseland.ustory.ui.mvp.settings.SettingsOverviewItemType

/**
 * Created by joseph on 2018/3/11.
 */

sealed class SettingsOverviewListItem

class SettingsOverviewLogOutItem : SettingsOverviewListItem()
class SettingsOverviewUserProfileItem : SettingsOverviewListItem()
class SettingsOverviewSeparatorItem : SettingsOverviewListItem()
data class SettingsOverviewHeaderItem(val title: Int) : SettingsOverviewListItem()
data class SettingsOverviewItem(val icon: Int, val label: Int, val type: SettingsOverviewItemType) : SettingsOverviewListItem()