package com.promiseland.ustory.ui.base.mvp

import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View
import com.promiseland.kotlinandroid.ui.base.SupportActivity
import com.promiseland.ustory.AppComponent
import com.promiseland.ustory.UStoryApp
import com.promiseland.ustory.ui.base.IBase
import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/24.
 */
abstract class BaseViewActivity<T : BaseContract.BasePresenter> : SupportActivity(), IBase, BaseContract.BaseView {
    lateinit var mRootView: View

    @Inject
    @Nullable
    @JvmField
    var mPresenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootView = layoutInflater.inflate(getContentLayout(), null)
        setContentView(mRootView)
        bindView(mRootView, savedInstanceState)

        setupComponent(UStoryApp.appComponent)
        attachView()
        initData()
    }

    fun attachView() {
        mPresenter?.attachView(this)
    }

    override fun onDestroy() {
        mPresenter?.detachView()
        super.onDestroy()
    }

    override fun setupComponent(appComponent: AppComponent) {
    }

    override fun bindView(view: View, savedInstanceState: Bundle?) {
    }

    override fun showLoading() {
    }

    override fun showSuccess() {
    }

    override fun showError() {
    }

    override fun showNoNet() {
    }

    override fun onRetry() {
    }

}