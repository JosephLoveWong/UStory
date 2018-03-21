package com.promiseland.ustory.ui.base.mvp

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by Administrator on 2018/3/21.
 */
class BaseRetainPresenterFragment<P : BaseContract.BasePresenter> : Fragment() {
    private var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun getPresenter(baseView: BaseViewActivity<P>): P? {
        if (this.mPresenter == null) {
            this.mPresenter = baseView.createPresenterInstance()
        }
        return this.mPresenter
    }

    fun getPresenter(baseView: BaseViewFragment<P>): P? {
        if (this.mPresenter == null) {
            this.mPresenter = baseView.createPresenterInstance()
        }
        return this.mPresenter
    }

}