package com.promiseland.ustory.ui.widget.util

import android.text.style.URLSpan
import android.view.View

/**
 * Created by Administrator on 2018/3/5.
 */
class KSUrlSpan(url: String, private val listener: OnClickUrlListener) : URLSpan(url) {
    override fun onClick(v: View) {
        listener.onClickUrl(url)
    }
}