package com.promiseland.ustory.ui.base.mvp

import android.os.Bundle
import com.promiseland.ustory.ui.base.IBase
import com.promiseland.ustory.ui.base.ui.BaseFragment
import timber.log.Timber

/**
 * Created by Administrator on 2018/2/24.
 */
abstract class BaseViewFragment<out P : BaseContract.BasePresenter> : BaseFragment(), IBase, BaseContract.BaseView {

    private var mRetainPresenterFragment: BaseRetainPresenterFragment<P>? = null

    abstract fun createPresenterInstance(): P

    private fun getPresenterTag(): String? {
        return this.javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this.mRetainPresenterFragment == null) {
            retainOrCreateFragment()
        }
    }

    private fun retainOrCreateFragment() {
        val fm = childFragmentManager
        this.mRetainPresenterFragment = fm.findFragmentByTag(getPresenterTag()) as BaseRetainPresenterFragment<P>?
        if (this.mRetainPresenterFragment == null) {
            this.mRetainPresenterFragment = BaseRetainPresenterFragment()
            fm.beginTransaction().add(this.mRetainPresenterFragment, getPresenterTag()).commit()
            Timber.d("BaseViewFragment: Presenter created %s", getPresenterTag())
            return
        }
        Timber.d("BaseViewFragment: Presenter retained %s", getPresenterTag())
    }

    protected fun getPresenter(): P? {
        if (this.mRetainPresenterFragment == null) {
            retainOrCreateFragment()
        }
        return this.mRetainPresenterFragment?.getPresenter(this)
    }

    override fun onPause() {
        super.onPause()
        getPresenter()?.detachView()
    }

    override fun onResume() {
        super.onResume()
        getPresenter()?.detachView()
    }

    override fun bindView(savedInstanceState: Bundle?) {
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