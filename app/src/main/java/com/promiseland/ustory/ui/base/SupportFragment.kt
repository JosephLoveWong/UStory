package com.promiseland.kotlinandroid.ui.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * Created by joseph on 2018/2/21.
 */
open class SupportFragment : Fragment(), ISupportFragment {
    override fun post(runnable: Runnable?) {
        mDelegate.post(runnable)
    }

    override fun enqueueAction(runnable: Runnable?) {
        mDelegate.post(runnable)
    }

    private val mDelegate = SupportFragmentDelegate(this)

    override fun setFragmentResult(resultCode: Int, bundle: Bundle?) {
        mDelegate.setFragmentResult(resultCode, bundle)
    }

    override fun onSupportInvisible() {
        mDelegate.onSupportInvisible()
    }

    override fun onNewBundle(args: Bundle?) {
        mDelegate.onNewBundle(args)
    }

    override fun extraTransaction(): ExtraTransaction = mDelegate.extraTransaction()

    override fun onCreateFragmentAnimator(): FragmentAnimator = mDelegate.onCreateFragmentAnimator()

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        mDelegate.onFragmentResult(requestCode, resultCode, data)
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        mDelegate.fragmentAnimator = fragmentAnimator
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        mDelegate.onLazyInitView(savedInstanceState)
    }

    override fun getFragmentAnimator(): FragmentAnimator = mDelegate.fragmentAnimator

    override fun isSupportVisible(): Boolean = mDelegate.isSupportVisible

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        mDelegate.onEnterAnimationEnd(savedInstanceState)
    }

    override fun onSupportVisible() {
        mDelegate.onSupportVisible()
    }

    override fun onBackPressedSupport(): Boolean = mDelegate.onBackPressedSupport()

    override fun getSupportDelegate(): SupportFragmentDelegate = mDelegate

    override fun putNewBundle(newBundle: Bundle?) {
        mDelegate.putNewBundle(newBundle)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mDelegate.onAttach(activity!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mDelegate.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mDelegate.onSaveInstanceState(outState!!)
    }

    override fun onResume() {
        super.onResume()
        mDelegate.onResume()
    }

    override fun onPause() {
        super.onPause()
        mDelegate.onPause()
    }

    override fun onDestroyView() {
        mDelegate.onDestroyView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mDelegate.onDestroy()
        super.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mDelegate.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mDelegate.setUserVisibleHint(isVisibleToUser)
    }

}