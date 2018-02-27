package com.promiseland.ustory.ui.base

import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/24.
 */
open class BasePresenter<T : BaseContract.BaseView> @Inject
constructor(): BaseContract.BasePresenter {
    var mView: T? = null

    override fun attachView(view: BaseContract.BaseView) {
        mView = view as T
    }

    override fun detachView() {
        mView?.let {
            mView = null
        }
    }
}