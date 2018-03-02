package com.promiseland.ustory.ui.mvp.main

import android.content.Context
import android.content.Intent
import com.promiseland.ustory.AppComponent
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.BaseActivity
import com.promiseland.ustory.ui.base.BaseContract

/**
 * Created by Administrator on 2018/2/24.
 */
class FeedActivity : BaseActivity<BaseContract.BasePresenter>() {
    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, FeedActivity::class.java))
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_intro_screen

    override fun setupComponent(appComponent: AppComponent) {
    }

    override fun initData() {
    }

}