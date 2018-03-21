package com.promiseland.ustory.ui.mvp.about

import android.content.Context
import android.content.Intent
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.mvp.BaseViewActivity
import com.promiseland.ustory.ui.base.mvp.EmptyPresenter

/**
 * Created by joseph on 2018/3/9.
 */
class AboutUsActivity : BaseViewActivity<EmptyPresenter>() {
    override fun createPresenterInstance(): EmptyPresenter = EmptyPresenter()

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, AboutUsActivity::class.java).putExtra("extra_open_from", "NAV"))
        }
    }

    /**
     * 获取页面布局 id
     */
    override fun getContentLayout(): Int = R.layout.activity_empty_with_toolbar_framed

    override fun overridePendingTransition() {
        overridePendingTransition(R.anim.appear_from_right, R.anim.disappear_to_left)
    }

    /**
     * 加载数据
     */
    override fun initData() {
        supportFragmentManager.beginTransaction().replace(R.id.container, AboutUsFragment()).commit()
    }
}