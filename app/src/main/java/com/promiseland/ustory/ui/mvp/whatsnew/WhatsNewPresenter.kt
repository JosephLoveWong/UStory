package com.promiseland.ustory.ui.mvp.whatsnew

import com.promiseland.ustory.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Administrator on 2018/3/2.
 */
class WhatsNewPresenter @Inject
constructor(): BasePresenter<WhatsNewActivity>(), WhatsNewContract.Presenter {
    override fun closeClick() {
        getView().showFeed()
    }

    override fun nextClicked() {
        getView().showNextPage()
    }
}