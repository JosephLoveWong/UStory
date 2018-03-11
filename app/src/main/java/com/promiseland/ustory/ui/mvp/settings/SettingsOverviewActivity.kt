package com.promiseland.ustory.ui.mvp.settings

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.BaseActivity
import com.promiseland.ustory.ui.base.EmptyPresenter

/**
 * Created by joseph on 2018/3/11.
 */
class SettingsOverviewActivity : BaseActivity<EmptyPresenter>() {
    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, SettingsOverviewActivity::class.java))
        }
    }

    private var mLayoutManager:LinearLayoutManager? = null
    /**
     * 获取页面布局 id
     */
    override fun getContentLayout(): Int = R.layout.activity_list_with_toolbar

    /**
     * 加载数据
     */
    override fun initData() {
    }
}