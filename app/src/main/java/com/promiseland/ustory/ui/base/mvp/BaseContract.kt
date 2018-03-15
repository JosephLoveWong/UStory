package com.promiseland.ustory.ui.base.mvp

/**
 * Created by Administrator on 2018/2/24.
 */
interface BaseContract {
    interface BaseView {
        /**
         * 显示加载页面
         */
        fun showLoading()

        /**
         * 显示内容页面
         */
        fun showSuccess()

        /**
         * 显示错误页面
         */
        fun showError()

        /**
         * 显示无网络页面
         */
        fun showNoNet()

        /**
         * 加载失败重试
         */
        fun onRetry()
//        /**
//         * 绑定生命周期
//         *
//         * @param <T>
//         * @return <T>
//         */
//        fun <T> bindToLife(): LifecycleTransformer<T>
    }

    interface BasePresenter {
        /**
         * 绑定View
         */
        fun attachView(view: BaseView)

        /**
         * 解除绑定
         */
        fun detachView()

        /**
         * 获取绑定的View
         */
        fun getView(): BaseView
    }
}