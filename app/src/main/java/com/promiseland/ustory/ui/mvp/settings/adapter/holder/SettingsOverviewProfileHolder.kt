package com.promiseland.ustory.ui.mvp.settings.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.mvp.settings.SettingsOverviewContract

/**
 * Created by Administrator on 2018/3/12.
 */
class SettingsOverviewProfileHolder(parent: ViewGroup?, presenter: SettingsOverviewContract.Presenter) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_settings_overview_profile, parent, false)) {
    var profile_pic_placeholder: ImageView = itemView.findViewById(R.id.profile_pic_placeholder)
    var title: TextView = itemView.findViewById(R.id.title)
    var subtitle: TextView = itemView.findViewById(R.id.subtitle)
    var loading_indicator: ProgressBar = itemView.findViewById(R.id.loading_indicator)
}