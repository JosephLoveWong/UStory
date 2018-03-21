package com.promiseland.ustory.ui.base

import android.os.Bundle
import android.view.View

/**
 * Created by Administrator on 2018/2/24.
 */
interface IBase {
    /**
     * 获取页面布局 id
     */
    fun getContentLayout(): Int

    /**
     * 初始化布局
     */
    fun bindView(view: View, savedInstanceState: Bundle?)

    /**
     * 加载数据
     */
    fun initData()
}