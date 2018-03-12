package com.promiseland.ustory.ui.mvp.settings.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.mvp.settings.SettingsOverviewContract

/**
 * Created by Administrator on 2018/3/12.
 */
class SettingsOverviewLogOutHolder(parent: ViewGroup?, presenter: SettingsOverviewContract.Presenter)
    :RecyclerView.ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_settings_overview_log_out, parent, false)){
}