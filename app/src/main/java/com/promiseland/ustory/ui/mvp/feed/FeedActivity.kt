package com.promiseland.ustory.ui.mvp.feed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.mvp.BaseViewActivity
import com.promiseland.ustory.ui.base.mvp.EmptyPresenter
import com.promiseland.ustory.ui.mvp.about.AboutUsActivity
import com.promiseland.ustory.ui.mvp.settings.SettingsOverviewActivity
import kotlinx.android.synthetic.main.activity_list_with_search_bar.*

/**
 * Created by Administrator on 2018/2/24.
 */
class FeedActivity : BaseViewActivity<EmptyPresenter>() {
    override fun createPresenterInstance(): EmptyPresenter = EmptyPresenter()

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, FeedActivity::class.java))
        }
    }

    private var mLayoutManager: LinearLayoutManager? = null

    override fun getContentLayout(): Int = R.layout.activity_list_with_search_bar

    override fun onSaveInstanceState(outState: Bundle?) {
        if (mLayoutManager != null) {
            outState?.putParcelable("extra_position", mLayoutManager!!.onSaveInstanceState())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            intent.putExtra("extra_position", savedInstanceState.getParcelable<Parcelable>("extra_position"))
        }

        empty_state_recycler_view.enableVerticalScrollEventPassThrough()
    }

    override fun initData() {
        nv_menu_left.setCheckedItem(R.id.action_home)
        nv_menu_left.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    FeedActivity.launch(this@FeedActivity)
                }
                R.id.action_howto -> {
                    FeedActivity.launch(this@FeedActivity)
                }
                R.id.action_categories -> {
                    FeedActivity.launch(this@FeedActivity)
                }
                R.id.action_my_recipes -> {
                    FeedActivity.launch(this@FeedActivity)
                }
                R.id.action_shoppinglist -> {
                    FeedActivity.launch(this@FeedActivity)
                }
                R.id.action_feedback -> {
                    FeedActivity.launch(this@FeedActivity)
                }
                R.id.action_info -> {
                    AboutUsActivity.launch(this@FeedActivity)
                }
                R.id.action_setting -> {
                    SettingsOverviewActivity.launch(this@FeedActivity)
                }
            }
            this@FeedActivity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            it.isChecked = true
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

}