package com.promiseland.ustory.ui.mvp.main

import com.promiseland.ustory.AppComponent
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.BaseActivity
import com.promiseland.ustory.ui.base.BaseContract

/**
 * Created by Administrator on 2018/2/24.
 */
class MainActivity : BaseActivity<BaseContract.BasePresenter>() {
    override fun getContentLayout(): Int = R.layout.activity_intro_screen

    override fun setupComponent(appComponent: AppComponent) {
    }

    override fun initData() {
    }

}