package com.promiseland.ustory.ui.mvp.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.mvp.BaseViewActivity
import com.promiseland.ustory.ui.base.mvp.EmptyPresenter
import com.promiseland.ustory.ui.mvp.settings.adapter.SettingsOverviewAdapter
import kotlinx.android.synthetic.main.activity_list_with_toolbar.*

/**
 * Created by joseph on 2018/3/11.
 */
class SettingsOverviewActivity : BaseViewActivity<EmptyPresenter>() {
    override fun createPresenterInstance(): EmptyPresenter = EmptyPresenter()

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, SettingsOverviewActivity::class.java))
        }
    }

    private var adapter: SettingsOverviewAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    /**
     * 获取页面布局 id
     */
    override fun getContentLayout(): Int = R.layout.activity_list_with_toolbar

    /**
     * 加载数据
     */
    override fun initData() {
        adapter = SettingsOverviewAdapter(SettingsOverviewPresenter())
        layoutManager = LinearLayoutManager(this, 1, false)

        empty_state_recycler_view.recyclerView.adapter = adapter
        empty_state_recycler_view.recyclerView.layoutManager = layoutManager
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        empty_state_recycler_view.updateRecyclerViewPadding(0, resources.getDimensionPixelSize(R.dimen.settings_overview_recycler_view_padding), 0, resources.getDimensionPixelSize(R.dimen.settings_overview_recycler_view_padding))
    }

    override fun onDestroy() {
        adapter = null
        layoutManager = null
        super.onDestroy()
    }
}