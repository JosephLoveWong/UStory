package com.promiseland.ustory.ui.mvp.settings.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.mvp.settings.SettingsOverviewContract

/**
 * Created by joseph on 2018/3/11.
 */
class SettingsOverviewItemHolder(parent: ViewGroup?, presenter: SettingsOverviewContract.Presenter) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_settings_overview, parent, false)) {
    var icon: ImageView = itemView.findViewById(R.id.icon)
    var title: TextView = itemView.findViewById(R.id.title)
}