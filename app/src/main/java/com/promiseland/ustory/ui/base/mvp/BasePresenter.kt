package com.promiseland.ustory.ui.base.mvp

import android.support.annotation.Nullable
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBusException
import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/24.
 */
open class BasePresenter<out T : BaseContract.BaseView> : BaseContract.BasePresenter {
    private var mView: T? = null
    protected var mDisposables: CompositeDisposable? = null

    @Inject
    @JvmField
    @Nullable
    protected var mEventBus: EventBus? = null

    override fun getView(): T = mView as T

    override fun attachView(view: BaseContract.BaseView) {
        mView = view as T
        try {
            this.mEventBus?.register(this)
        } catch (e: EventBusException) {
        }

        this.mDisposables = CompositeDisposable()
        restoreSubscribersIfNeeded()
    }

    override fun detachView() {
        this.mView = null
        if (this.mEventBus != null) {
            this.mEventBus?.unregister(this)
        }
        if (this.mDisposables != null) {
            this.mDisposables?.dispose()
        }
    }

    protected fun restoreSubscribersIfNeeded() {}
}