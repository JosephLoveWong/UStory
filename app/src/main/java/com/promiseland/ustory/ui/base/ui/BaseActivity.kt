package com.promiseland.ustory.ui.base.ui

import android.support.v7.app.AppCompatActivity
import com.promiseland.ustory.module.BaseActivityComponent
import java.util.*

/**
 * Created by joseph on 2018/3/15.
 */
class BaseActivity : AppCompatActivity() {
    private var currentLocale: Locale? = null
    private var mBaseActivityComponent: BaseActivityComponent? = null
}
