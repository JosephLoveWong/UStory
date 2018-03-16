package com.promiseland.ustory.ui.base.mvp

import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/24.
 */
open class BasePresenter<out T : BaseContract.BaseView> @Inject
constructor() : BaseContract.BasePresenter {
    private var mView: T? = null

    override fun getView(): T = mView as T

    override fun attachView(view: BaseContract.BaseView) {
        mView = view as T
    }

    override fun detachView() {
        mView?.let {
            mView = null
        }
    }
}