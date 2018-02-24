package com.promiseland.ustory.ui.base

/**
 * Created by Administrator on 2018/2/24.
 */
class BasePresenter<T : BaseContract.BaseView> : BaseContract.BasePresenter {
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