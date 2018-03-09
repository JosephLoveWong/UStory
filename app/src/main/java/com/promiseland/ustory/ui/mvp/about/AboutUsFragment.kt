package com.promiseland.ustory.ui.mvp.about

import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.BaseFragment
import com.promiseland.ustory.ui.base.EmptyPresenter

/**
 * Created by joseph on 2018/3/9.
 */
class AboutUsFragment : BaseFragment<EmptyPresenter>() {
    /**
     * 获取页面布局 id
     */
    override fun getContentLayout(): Int = R.layout.fragment_about_us

    /**
     * 加载数据
     */
    override fun initData() {
    }
}