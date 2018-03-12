package com.promiseland.ustory.ui.mvp.settings.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.promiseland.ustory.R

/**
 * Created by Administrator on 2018/3/12.
 */
class SettingsOverviewTitleHolder(parent: ViewGroup?) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_item_settings_overview_title, parent, false)) {
    var title: TextView = itemView.findViewById(R.id.title)
}