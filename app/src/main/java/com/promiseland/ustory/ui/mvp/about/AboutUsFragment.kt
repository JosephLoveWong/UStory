package com.promiseland.ustory.ui.mvp.about

import butterknife.OnClick
import com.promiseland.ustory.AppComponent
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.BaseFragment
import com.promiseland.ustory.ui.base.EmptyPresenter

/**
 * Created by joseph on 2018/3/9.
 */
class AboutUsFragment : BaseFragment<EmptyPresenter>() {

    private var mDebugClickStep = 0
    /**
     * 获取页面布局 id
     */
    override fun getContentLayout(): Int = R.layout.fragment_about_us

    /**
     * 加载数据
     */
    override fun initData() {
    }

    override fun setupComponent(appComponent: AppComponent) {
//        UStoryApp.appComponent.plus(BaseFragmentModule()).inject(this);
    }

    @OnClick(R.id.ks_icon_debug_mode)
    fun onClickKSIcon() {
        if (this.mDebugClickStep < 3 || this.mDebugClickStep >= 6) {
            this.mDebugClickStep++
        }
        if (this.mDebugClickStep == 9) {
            this.mDebugClickStep = 0
            activateDebugMode()
        }
    }

    @OnClick(R.id.fragment_about_us_text)
    fun onClickAboutUsText() {
        if (this.mDebugClickStep >= 3 && this.mDebugClickStep < 6) {
            this.mDebugClickStep++
        }
    }

    private fun activateDebugMode() {
//        this.mKitchenPreferences.setDebugModeEnabled(true)
//        SnackbarHelper.show(activity as BaseActivity, R.string.debug_mode_activated_message as Int)
//        activity.invalidateOptionsMenu()
    }

}