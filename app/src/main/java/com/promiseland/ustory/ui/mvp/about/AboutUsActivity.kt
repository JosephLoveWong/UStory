package com.promiseland.ustory.ui.mvp.about

import android.content.Context
import android.content.Intent
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.BaseActivity
import com.promiseland.ustory.ui.base.EmptyPresenter
import kotlinx.android.synthetic.main.activity_empty_with_toolbar_framed.*
import kotlin.jvm.internal.Intrinsics

/**
 * Created by joseph on 2018/3/9.
 */
class AboutUsActivity : BaseActivity<EmptyPresenter>() {
    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, AboutUsActivity::class.java).putExtra("extra_open_from", "NAV"))
        }
    }

    private var openedFromNavDrawer:Boolean = false

    /**
     * 获取页面布局 id
     */
    override fun getContentLayout(): Int = R.layout.activity_empty_with_toolbar_framed

    /**
     * 加载数据
     */
    override fun initData() {
        openedFromNavDrawer = Intrinsics.areEqual(intent.getStringExtra("extra_open_from"), "NAV")
        if (openedFromNavDrawer) {
            nv_menu_left.setCheckedItem(R.id.nav_about)
        } else {
            overridePendingTransition(R.anim.appear_from_right, R.anim.disappear_to_left)
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, AboutUsFragment()).commit()
    }
}