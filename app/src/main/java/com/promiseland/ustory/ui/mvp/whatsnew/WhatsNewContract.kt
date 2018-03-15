package com.promiseland.ustory.ui.mvp.whatsnew

import com.promiseland.ustory.ui.base.mvp.BaseContract

/**
 * Created by Administrator on 2018/3/1.
 */
interface WhatsNewContract {
    interface View : BaseContract.BaseView {
        fun showFeed()

        fun showNextPage()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun closeClick()

        fun nextClicked()
    }
}