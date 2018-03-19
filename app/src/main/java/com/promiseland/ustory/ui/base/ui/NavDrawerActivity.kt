package com.promiseland.ustory.ui.base.ui

import android.app.Activity
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.widget.FrameLayout
import butterknife.ButterKnife
import com.promiseland.ustory.R
import com.promiseland.ustory.base.util.APILevelHelper
import com.promiseland.ustory.base.util.ConfigurationUtils
import com.promiseland.ustory.ui.drawer.NavigationDrawerFragment

/**
 * Created by joseph on 2018/3/9.
 */
class NavDrawerActivity : BaseActivity(), NavigationDrawerFragment.NavigationDrawerCallbacks {
    val EXTRA_NAV_DRAWER_POSITION = "extra_nav_drawer_position"
    protected var mIgnoreBackTransition = false
    protected var mNavigationDrawerFragment: NavigationDrawerFragment? = null

    protected fun initPage(type: Int, titleId: Int) {
        initPage(type)
        setTitle(titleId)
    }

    protected fun initPage(type: Int) {
        val updateContainerMargin: Boolean = type == 2
        var hasIndicator = true
        initToolbar(updateContainerMargin)
        initDrawers()
        if (type != 3) {
            hasIndicator = false
        }
        setNavDrawerIndicator(hasIndicator)
    }

    protected fun initDrawers() {
        this.mNavigationDrawerFragment = supportFragmentManager.findFragmentById(R.id.navigation_drawer) as NavigationDrawerFragment
        val drawerLeft = ButterKnife.findById<View>(this as Activity, R.id.navigation_drawer)
        if (!(drawerLeft == null || drawerLeft!!.layoutParams == null)) {
            drawerLeft!!.layoutParams.width = Math.min(ConfigurationUtils.getScreenSize(this).x as Float - resources.getDimension(R.dimen.navigation_drawer_right_offset), resources.getDimension(R.dimen.navigation_drawer_width)) as Int
        }
        this.mNavigationDrawerFragment?.setUp(ButterKnife.findById<View>(this as Activity, R.id.drawer_layout) as DrawerLayout, ButterKnife.findById<View>(this as Activity, R.id.drawer_layout_inner) as DrawerLayout)
        val statusBarHeight = ConfigurationUtils.getStatusBarHeight(this)
        if (APILevelHelper.isAPILevelMinimal(21) && drawerLeft != null) {
            drawerLeft!!.setPadding(drawerLeft!!.paddingLeft, drawerLeft!!.paddingTop + statusBarHeight, drawerLeft!!.paddingRight, drawerLeft!!.paddingBottom)
        }
        setRightDrawerLockMode(1, null)
    }

    fun setNavDrawerIndicator(hasIndicator: Boolean) {
        if (this.mNavigationDrawerFragment != null) {
            this.mNavigationDrawerFragment?.setNavDrawerIndicator(hasIndicator)
        }
    }

    override fun onNavigationDrawerItemSelected(position: Int, calledByUserClick: Boolean) {
        // TODO
    }

    override fun onBackPressed() {
        if (this.mNavigationDrawerFragment == null || !this.mNavigationDrawerFragment?.isDrawerOpen()!!) {
            super.onBackPressed()
        } else {
            this.mNavigationDrawerFragment?.closeDrawer()
        }
    }

    fun setRightDrawerLockMode(lockmode: Int, view: View?) {
        if (this.mNavigationDrawerFragment != null) {
            this.mNavigationDrawerFragment?.setDrawerLockMode(lockmode, GravityCompat.END)
            if (view != null) {
                val rightDrawer = ButterKnife.findById<View>(this as Activity, R.id.right_drawer) as FrameLayout
                if (lockmode == 1) {
                    rightDrawer.removeView(view)
                } else {
                    rightDrawer.addView(view)
                }
            }
        }
    }

    fun openRightDrawer() {
        this.mNavigationDrawerFragment?.openRightDrawer()
    }

    fun setNavigationSelection(navigationSelection: Int) {
        this.mNavigationDrawerFragment?.setCurrentSelectedPosition(navigationSelection)
    }

    override fun onDestroy() {
        this.mNavigationDrawerFragment = null
        super.onDestroy()
    }

}