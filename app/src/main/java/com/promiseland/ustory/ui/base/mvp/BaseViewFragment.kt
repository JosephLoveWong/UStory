package com.promiseland.ustory.ui.base.mvp

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.promiseland.kotlinandroid.ui.base.SupportFragment
import com.promiseland.ustory.AppComponent
import com.promiseland.ustory.UStoryApp
import com.promiseland.ustory.ui.base.IBase
import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/24.
 */
abstract class BaseViewFragment<T : BaseContract.BasePresenter> : SupportFragment(), IBase, BaseContract.BaseView {

    @Inject
    @Nullable
    @JvmField
    var mPresenter: T? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setupComponent(UStoryApp.appComponent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getContentLayout(), container, false)
        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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