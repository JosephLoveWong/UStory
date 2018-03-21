package com.promiseland.ustory.ui.base.mvp

import android.os.Bundle
import android.view.View
import com.promiseland.ustory.ui.base.IBase
import com.promiseland.ustory.ui.base.ui.BaseActivity
import timber.log.Timber

/**
 * Created by Administrator on 2018/2/24.
 */
abstract class BaseViewActivity<out P : BaseContract.BasePresenter> : BaseActivity(), IBase, BaseContract.BaseView {
    private var mRetainPresenterFragment: BaseRetainPresenterFragment<P>? = null

    abstract fun createPresenterInstance(): P

    private fun getPresenterTag(): String {
        return localClassName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this.mRetainPresenterFragment == null) {
            retainOrCreateFragment()
        }

        overridePendingTransition()
        setContentView(getContentLayout())
        initData()
    }

    private fun retainOrCreateFragment() {
        val fm = supportFragmentManager
        this.mRetainPresenterFragment = fm.findFragmentByTag(getPresenterTag()) as BaseRetainPresenterFragment<P>?
        if (this.mRetainPresenterFragment == null) {
            this.mRetainPresenterFragment = BaseRetainPresenterFragment()
            fm.beginTransaction().add(this.mRetainPresenterFragment, getPresenterTag()).commit()
            Timber.d("BaseViewActivity: Presenter created %s", getPresenterTag())
            return
        }
        Timber.d("BaseViewActivity: Presenter retained %s", getPresenterTag())
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
        getPresenter()?.attachView(this)
    }

    open fun overridePendingTransition() {
        // subclass implements if needed
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