package com.promiseland.ustory.ui.mvp.settings.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.ViewGroup
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.mvp.settings.SettingsOverviewPresenter
import com.promiseland.ustory.ui.mvp.settings.adapter.holder.*
import com.promiseland.ustory.ui.util.ViewHelper

/**
 * Created by joseph on 2018/3/11.
 */
class SettingsOverviewAdapter(private val presenter: SettingsOverviewPresenter) : Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> SettingsOverviewItemHolder(parent, presenter)
            1 -> SettingsOverviewTitleHolder(parent)
            2 -> HorizontalSeparatorHolder(parent)
            3 -> SettingsOverviewProfileHolder(parent, presenter)
            4 -> SettingsOverviewLogOutHolder(parent, presenter)
            else -> throw IllegalArgumentException("Unknown view type in SettingsOverviewAdapter")
        }
    }

    override fun getItemCount(): Int = presenter.getItemCount()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val item = presenter.getItem(position)
        when(item) {
            is SettingsOverviewHeaderItem -> (holder as SettingsOverviewTitleHolder).title.setText(item.title)
            is SettingsOverviewItem -> {
                (holder as SettingsOverviewItemHolder).title.setText(item.label)
                holder.icon.setImageResource(item.icon)
            }
            is SettingsOverviewUserProfileItem -> {
                holder as SettingsOverviewProfileHolder
                if(presenter.isLoggedIn()) {
                    // TODO
                    holder.title.setText(R.string.settings_overview_profile_logged_out_title)
                    holder.subtitle.setText(R.string.settings_overview_profile_logged_out_subtitle)
                    ViewHelper.makeGone(holder.loading_indicator)
                } else {
                    holder.title.setText(R.string.settings_overview_profile_logged_out_title)
                    holder.subtitle.setText(R.string.settings_overview_profile_logged_out_subtitle)
                    ViewHelper.makeGone(holder.loading_indicator)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = presenter.getItem(position)
        return when (item) {
            is SettingsOverviewItem -> 0
            is SettingsOverviewHeaderItem -> 1
            is SettingsOverviewSeparatorItem -> 2
            is SettingsOverviewUserProfileItem -> 3
            is SettingsOverviewLogOutItem -> 4
            else -> throw IllegalArgumentException("Unknown item type in SettingsOverviewAdapter")
        }
    }
}