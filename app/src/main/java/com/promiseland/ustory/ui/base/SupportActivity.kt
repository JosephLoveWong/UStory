package com.promiseland.kotlinandroid.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import me.yokeyword.fragmentation.*
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * Created by joseph on 2018/2/21.
 */
open class SupportActivity : AppCompatActivity(), ISupportActivity {
    override fun post(runnable: Runnable?) {
        mDelegate.post(runnable)
    }

    private val mDelegate = SupportActivityDelegate(this)

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        mDelegate.fragmentAnimator = fragmentAnimator
    }

    override fun getFragmentAnimator(): FragmentAnimator = mDelegate.fragmentAnimator

    override fun onBackPressedSupport() {
        mDelegate.onBackPressedSupport()
    }

    override fun onBackPressed() {
        mDelegate.onBackPressed()
    }

    override fun extraTransaction(): ExtraTransaction = mDelegate.extraTransaction()

    override fun onCreateFragmentAnimator(): FragmentAnimator = mDelegate.onCreateFragmentAnimator()

    override fun getSupportDelegate(): SupportActivityDelegate = mDelegate

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean = mDelegate.dispatchTouchEvent(ev)  || super.dispatchTouchEvent(ev)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDelegate.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        mDelegate.onDestroy()
        super.onDestroy()
    }

    /**
     * 获取栈内的fragment对象
     */
    fun <T : ISupportFragment> findFragment(fragmentClass: Class<T>): T = SupportHelper.findFragment(supportFragmentManager, fragmentClass)

}