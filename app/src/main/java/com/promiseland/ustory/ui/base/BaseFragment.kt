package com.promiseland.ustory.ui.base

import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.promiseland.kotlinandroid.ui.base.SupportFragment
import com.promiseland.ustory.UStoryApp
import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/24.
 */
abstract class BaseFragment<T : BaseContract.BasePresenter> : SupportFragment(), IBase, BaseContract.BaseView {

    @Inject
    @Nullable
    @JvmField
    var mPresenter: T? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getContentLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupComponent(UStoryApp.appComponent)
        attachView()
        initData()
    }

    fun attachView() {
        mPresenter?.attachView(this)
    }

    override fun onDestroyView() {
        mPresenter?.detachView()
        super.onDestroyView()
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