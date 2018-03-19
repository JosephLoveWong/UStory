package com.promiseland.ustory.ui.drawer

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import butterknife.ButterKnife
import com.promiseland.ustory.R
import com.promiseland.ustory.ui.base.ui.BaseFragment
import com.promiseland.ustory.ui.mvp.about.AboutUsActivity
import com.promiseland.ustory.ui.mvp.feed.FeedActivity
import com.promiseland.ustory.ui.mvp.settings.SettingsOverviewActivity
import java.util.ArrayList

/**
 * Created by joseph on 2018/3/17.
 */
class NavigationDrawerFragment : BaseFragment(), DrawerAdapter.DrawerAdapterCallbacks {
    internal var mCallbacks: NavigationDrawerCallbacks? = null
    private var mCurrentSelectedPosition = 0
    private var mDrawerAdapter: DrawerAdapter? = null
    private var mDrawerLayout: DrawerLayout? = null
    internal var mDrawerListView: RecyclerView? = null
    internal var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mFragmentContainerView: View? = null
    private var mSubDrawerLayout: DrawerLayout? = null
    private var mSubDrawerListener: DrawerLayout.DrawerListener? = null

    interface NavigationDrawerCallbacks {
        fun onNavigationDrawerItemSelected(position: Int, selected: Boolean)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            this.mCurrentSelectedPosition = savedInstanceState.getInt("selected_navigation_drawer_position")
        }
        if (savedInstanceState == null) {
            selectItem(this.mCurrentSelectedPosition, false)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getBaseFragmentComponent()?.inject(this)
    }

    override fun checkLocale() {
        super.checkLocale()
        onCreateAdapter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mDrawerListView.setLayoutManager(LinearLayoutManager(activity, 1, false))
        onCreateAdapter()
    }

    private fun onCreateAdapter() {
        this.mDrawerAdapter = createKitchenDrawerAdapter()
        this.mDrawerListView.setAdapter(this.mDrawerAdapter)
        this.mDrawerAdapter.setSelected(this.mCurrentSelectedPosition)
    }

    private fun createKitchenDrawerAdapter(): DrawerAdapter {
        val items = ArrayList(11)
        items.add(DrawerItem(Type.TOP, TwoStateIcon(0, 0), R.string.technical_not_set))
        items.add(DrawerItem(Type.SEPARATOR, TwoStateIcon(0, 0), R.string.technical_not_set))
        items.add(DrawerItem(Type.ITEM, TwoStateIcon(R.drawable.icon_nav_drawer_home, R.drawable.ic_nd_recipes_selected), R.string.navigation_recipes))
        items.add(DrawerItem(Type.ITEM, TwoStateIcon(R.drawable.icon_nav_drawer_howto, R.drawable.ic_nd_howto_selected), R.string.navigation_cooking_tips))
        items.add(DrawerItem(Type.ITEM, TwoStateIcon(R.drawable.icon_nav_drawer_categories, R.drawable.ic_nd_search_category_selected), R.string.navigation_search_categories))
        items.add(DrawerItem(Type.ITEM, TwoStateIcon(R.drawable.icon_nav_drawer_my_recipes, R.drawable.ic_nd_my_recipes_selected), R.string.navigation_my_recipes))
        items.add(DrawerItem(Type.ITEM, TwoStateIcon(R.drawable.icon_nav_drawer_shoppinglist, R.drawable.ic_nd_shopping_selected), R.string.navigation_shopping_list))
        items.add(DrawerItem(Type.SEPARATOR, TwoStateIcon(0, 0), R.string.technical_not_set))
        items.add(DrawerItem(Type.ITEM, TwoStateIcon(R.drawable.icon_nav_drawer_feedback, R.drawable.icon_nav_drawer_feedback), R.string.MENU_FEEDBACK))
        items.add(DrawerItem(Type.ITEM, TwoStateIcon(R.drawable.icon_nav_drawer_info, R.drawable.ic_nd_aboutus_selected), R.string.navigation_about_us))
        items.add(DrawerItem(Type.ITEM, TwoStateIcon(R.drawable.icon_nav_drawer_setting, R.drawable.ic_nd_settings_selected), R.string.navigation_settings))
        return DrawerAdapter(activity, items, this)
    }

    fun isDrawerOpen(): Boolean {
        return isLeftDrawerOpen() || isRightDrawerOpen()
    }

    private fun isLeftDrawerOpen(): Boolean {
        return this.mDrawerLayout != null && (this.mDrawerLayout.isDrawerOpen(8388611) || this.mDrawerLayout.isDrawerOpen(8388613))
    }

    private fun isRightDrawerOpen(): Boolean {
        return this.mSubDrawerLayout != null && (this.mSubDrawerLayout.isDrawerOpen(8388611) || this.mSubDrawerLayout.isDrawerOpen(8388613))
    }

    fun closeDrawer() {
        if (this.mDrawerLayout != null && isLeftDrawerOpen()) {
            this.mDrawerLayout.closeDrawers()
        } else if (this.mSubDrawerLayout != null && isRightDrawerOpen()) {
            this.mSubDrawerLayout.closeDrawers()
        }
    }

    fun setUp(drawerLayout: DrawerLayout, subDrawerLayout: DrawerLayout) {
        this.mFragmentContainerView = ButterKnife.findById(activity, R.id.navigation_drawer as Int)
        this.mSubDrawerLayout = subDrawerLayout
        this.mDrawerLayout = drawerLayout
        this.mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow as Int, 8388611)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
        this.mDrawerToggle = object : ActionBarDrawerToggle(activity, this.mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                if (this@NavigationDrawerFragment.isAdded) {
                    this@NavigationDrawerFragment.mEventBus.post(DrawerEvent(false, if (drawerView!!.id == R.id.navigation_drawer) 8388611 else 8388613))
                }
            }

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                if (this@NavigationDrawerFragment.isAdded) {
                    this@NavigationDrawerFragment.mEventBus.post(DrawerEvent(true, if (drawerView!!.id == R.id.navigation_drawer) 8388611 else 8388613))
                }
            }
        }
        this.mDrawerLayout.post(`NavigationDrawerFragment$$Lambda$0`(this))
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle)
        if (this.mSubDrawerLayout != null) {
            this.mSubDrawerListener = object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

                override fun onDrawerOpened(drawerView: View) {
                    this@NavigationDrawerFragment.mEventBus.post(DrawerEvent(true, 8388613))
                }

                override fun onDrawerClosed(drawerView: View) {
                    this@NavigationDrawerFragment.mEventBus.post(DrawerEvent(false, 8388613))
                }

                override fun onDrawerStateChanged(newState: Int) {}
            }
            this.mSubDrawerLayout.addDrawerListener(this.mSubDrawerListener)
        }
    }

    internal /* synthetic */ fun `lambda$setUp$0$NavigationDrawerFragment`() {
        if (this.mDrawerToggle != null) {
            this.mDrawerToggle.syncState()
        }
    }

    fun selectItem(navSelection: Int, calledByUserClick: Boolean) {
        if (this.mDrawerListView == null || this.mDrawerListView.getAdapter() == null || this.mDrawerAdapter.isEnabled(navSelection)) {
            val selectedPosition = this.mCurrentSelectedPosition
            if (!(this.mDrawerListView == null || navSelection == 8)) {
                this.mDrawerAdapter.setSelected(navSelection)
                this.mCurrentSelectedPosition = navSelection
            }
            if (this.mDrawerLayout != null) {
                if (this.mFragmentContainerView.getParent() is FrameLayout) {
                    this.mDrawerLayout.closeDrawer(this.mFragmentContainerView.getParent() as FrameLayout)
                } else {
                    this.mDrawerLayout.closeDrawer(this.mFragmentContainerView)
                }
            }
            val activity = activity
            if (navSelection == selectedPosition) {
                return
            }
            if (navSelection == 5) {
                ProfileActivity.launch(activity)
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 2) {
                FeedActivity.launch(activity)
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 3) {
                HowToFeedActivity.launchFromNavDrawer(activity)
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 4) {
                FeaturedSearchActivity.launch(activity)
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 8) {
                this.mShareManager.applicationFeedback()
            } else if (navSelection == 9) {
                AboutUsActivity.launchFromNavDrawer(activity)
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 10) {
                SettingsOverviewActivity.launchFromNavDrawer(activity)
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else {
                if (this.mCallbacks != null) {
                    this.mCallbacks.onNavigationDrawerItemSelected(navSelection, calledByUserClick)
                }
                if (selectedPosition == 5 || selectedPosition == 2 || selectedPosition == 3) {
                    activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
                }
            }
        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)

        try {
            this.mCallbacks = activity as NavigationDrawerCallbacks
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity must implement NavigationDrawerCallbacks.")
        }

    }

    override fun onDetach() {
        super.onDetach()
        this.mCallbacks = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected_navigation_drawer_position", this.mCurrentSelectedPosition)
    }

    fun getCurrentSelectedPosition(): Int {
        return this.mCurrentSelectedPosition
    }

    fun setCurrentSelectedPosition(navSelection: Int) {
        if (this.mDrawerListView != null && this.mDrawerAdapter != null && this.mDrawerAdapter.getItemCount() > 0 && navSelection < this.mDrawerAdapter.getItemCount()) {
            this.mDrawerAdapter.setSelected(navSelection)
            this.mCurrentSelectedPosition = navSelection
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        this.mDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        return this.mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    override fun onPositionSelected(position: Int) {
        selectItem(position, true)
    }

    fun setNavDrawerIndicator(toSet_: Boolean) {
        this.mDrawerToggle.setDrawerIndicatorEnabled(toSet_)
    }

    fun setDrawerLockMode(lockmode: Int, gravity: Int) {
        this.mDrawerLayout.setDrawerLockMode(lockmode, gravity)
    }

    fun openRightDrawer() {
        if (this.mSubDrawerLayout == null) {
            return
        }
        if (this.mSubDrawerLayout.isDrawerOpen(8388613)) {
            this.mSubDrawerLayout.closeDrawer(8388613)
        } else {
            this.mSubDrawerLayout.openDrawer(8388613)
        }
    }

    override fun onDestroyView() {
        this.mCallbacks = null
        if (this.mDrawerAdapter != null) {
            this.mDrawerAdapter.tearDown()
            this.mDrawerAdapter = null
        }
        if (!(this.mDrawerLayout == null || this.mDrawerToggle == null)) {
            this.mDrawerLayout.removeDrawerListener(this.mDrawerToggle)
            this.mDrawerLayout = null
        }
        if (!(this.mSubDrawerLayout == null || this.mSubDrawerListener == null)) {
            this.mSubDrawerLayout.removeDrawerListener(this.mSubDrawerListener)
        }
        if (this.mDrawerListView != null) {
            this.mDrawerListView.setAdapter(null)
        }
        this.mDrawerToggle = null
        this.mFragmentContainerView = null
        super.onDestroyView()
    }


}