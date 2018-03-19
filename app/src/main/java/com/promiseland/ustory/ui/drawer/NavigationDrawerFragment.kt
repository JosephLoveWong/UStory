package com.promiseland.ustory.ui.drawer

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout.VERTICAL
import butterknife.ButterKnife
import com.promiseland.ustory.R
import com.promiseland.ustory.base.event.DrawerEvent
import com.promiseland.ustory.ui.base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_navigation_drawer.*
import java.util.*

/**
 * Created by joseph on 2018/3/17.
 */
class NavigationDrawerFragment : BaseFragment(), DrawerAdapter.DrawerAdapterCallbacks {
    private var mCallbacks: NavigationDrawerCallbacks? = null
    private var mCurrentSelectedPosition = 0
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerAdapter: DrawerAdapter? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mSubDrawerLayout: DrawerLayout? = null
    private var mSubDrawerListener: DrawerLayout.DrawerListener? = null
    private var mFragmentContainerView: View? = null

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

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        try {
            this.mCallbacks = activity as NavigationDrawerCallbacks
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity must implement NavigationDrawerCallbacks.")
        }
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
        frg_navdrawer_main_menu.layoutManager = LinearLayoutManager(activity, VERTICAL, false)
        onCreateAdapter()
    }

    override fun onDestroyView() {
        this.mCallbacks = null
        if (this.mDrawerAdapter != null) {
            this.mDrawerAdapter?.tearDown()
            this.mDrawerAdapter = null
        }
        if (!(this.mDrawerLayout == null || this.mDrawerToggle == null)) {
            this.mDrawerLayout?.removeDrawerListener(this.mDrawerToggle!!)
            this.mDrawerLayout = null
        }
        if (!(this.mSubDrawerLayout == null || this.mSubDrawerListener == null)) {
            this.mSubDrawerLayout?.removeDrawerListener(this.mSubDrawerListener!!)
        }
        frg_navdrawer_main_menu.adapter = null
        this.mDrawerToggle = null
        this.mFragmentContainerView = null
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        this.mCallbacks = null
    }

    private fun onCreateAdapter() {
        this.mDrawerAdapter = createKitchenDrawerAdapter()
        frg_navdrawer_main_menu.adapter = this.mDrawerAdapter
        this.mDrawerAdapter?.setSelected(this.mCurrentSelectedPosition)
    }

    private fun createKitchenDrawerAdapter(): DrawerAdapter {
        val items = ArrayList<DrawerItem>(11)
        items.add(DrawerItem(DrawerItem.Type.TOP, DrawerItem.TwoStateIcon(0, 0), R.string.technical_not_set))
        items.add(DrawerItem(DrawerItem.Type.SEPARATOR, DrawerItem.TwoStateIcon(0, 0), R.string.technical_not_set))
        items.add(DrawerItem(DrawerItem.Type.ITEM, DrawerItem.TwoStateIcon(R.drawable.icon_nav_drawer_home, R.drawable.ic_nd_recipes_selected), R.string.navigation_recipes))
        items.add(DrawerItem(DrawerItem.Type.ITEM, DrawerItem.TwoStateIcon(R.drawable.icon_nav_drawer_howto, R.drawable.ic_nd_howto_selected), R.string.navigation_cooking_tips))
        items.add(DrawerItem(DrawerItem.Type.ITEM, DrawerItem.TwoStateIcon(R.drawable.icon_nav_drawer_categories, R.drawable.ic_nd_search_category_selected), R.string.navigation_search_categories))
        items.add(DrawerItem(DrawerItem.Type.ITEM, DrawerItem.TwoStateIcon(R.drawable.icon_nav_drawer_my_recipes, R.drawable.ic_nd_my_recipes_selected), R.string.navigation_my_recipes))
        items.add(DrawerItem(DrawerItem.Type.ITEM, DrawerItem.TwoStateIcon(R.drawable.icon_nav_drawer_shoppinglist, R.drawable.ic_nd_shopping_selected), R.string.navigation_shopping_list))
        items.add(DrawerItem(DrawerItem.Type.SEPARATOR, DrawerItem.TwoStateIcon(0, 0), R.string.technical_not_set))
        items.add(DrawerItem(DrawerItem.Type.ITEM, DrawerItem.TwoStateIcon(R.drawable.icon_nav_drawer_feedback, R.drawable.icon_nav_drawer_feedback), R.string.MENU_FEEDBACK))
        items.add(DrawerItem(DrawerItem.Type.ITEM, DrawerItem.TwoStateIcon(R.drawable.icon_nav_drawer_info, R.drawable.ic_nd_aboutus_selected), R.string.navigation_about_us))
        items.add(DrawerItem(DrawerItem.Type.ITEM, DrawerItem.TwoStateIcon(R.drawable.icon_nav_drawer_setting, R.drawable.ic_nd_settings_selected), R.string.navigation_settings))
        return DrawerAdapter(activity, items, this)
    }

    fun isDrawerOpen(): Boolean {
        return isLeftDrawerOpen() || isRightDrawerOpen()
    }

    private fun isLeftDrawerOpen(): Boolean {
        return this.mDrawerLayout != null && (this.mDrawerLayout!!.isDrawerOpen(GravityCompat.START) || this.mDrawerLayout!!.isDrawerOpen(GravityCompat.END))
    }

    private fun isRightDrawerOpen(): Boolean {
        return this.mSubDrawerLayout != null && (this.mSubDrawerLayout!!.isDrawerOpen(GravityCompat.START) || this.mSubDrawerLayout!!.isDrawerOpen(GravityCompat.END))
    }

    fun closeDrawer() {
        if (this.mDrawerLayout != null && isLeftDrawerOpen()) {
            this.mDrawerLayout?.closeDrawers()
        } else if (this.mSubDrawerLayout != null && isRightDrawerOpen()) {
            this.mSubDrawerLayout?.closeDrawers()
        }
    }

    fun setUp(drawerLayout: DrawerLayout, subDrawerLayout: DrawerLayout) {
        this.mFragmentContainerView = ButterKnife.findById(activity as AppCompatActivity, R.id.navigation_drawer)
        this.mSubDrawerLayout = subDrawerLayout
        this.mDrawerLayout = drawerLayout
        val actionBar = (activity as AppCompatActivity).supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
        this.mDrawerToggle = object : ActionBarDrawerToggle(activity, this.mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                if (this@NavigationDrawerFragment.isAdded) {
                    this@NavigationDrawerFragment.mEventBus?.post(DrawerEvent(false, if (drawerView!!.id == R.id.navigation_drawer) GravityCompat.START else GravityCompat.END))
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                if (this@NavigationDrawerFragment.isAdded) {
                    this@NavigationDrawerFragment.mEventBus?.post(DrawerEvent(true, if (drawerView!!.id == R.id.navigation_drawer) GravityCompat.START else GravityCompat.END))
                }
            }
        }
        this.mDrawerLayout?.post({
            if (this.mDrawerToggle != null) {
                this.mDrawerToggle?.syncState()
            }
        })
        this.mDrawerLayout?.addDrawerListener(this.mDrawerToggle as ActionBarDrawerToggle)
        if (this.mSubDrawerLayout != null) {
            this.mSubDrawerListener = object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

                override fun onDrawerOpened(drawerView: View) {
                    this@NavigationDrawerFragment.mEventBus?.post(DrawerEvent(true, GravityCompat.END))
                }

                override fun onDrawerClosed(drawerView: View) {
                    this@NavigationDrawerFragment.mEventBus?.post(DrawerEvent(false, GravityCompat.END))
                }

                override fun onDrawerStateChanged(newState: Int) {}
            }
            this.mSubDrawerLayout?.addDrawerListener(this.mSubDrawerListener as DrawerLayout.DrawerListener)
        }
    }

    fun selectItem(navSelection: Int, calledByUserClick: Boolean) {
        if (frg_navdrawer_main_menu.adapter == null || this.mDrawerAdapter!!.isEnabled(navSelection)) {
            val selectedPosition = this.mCurrentSelectedPosition
            if (navSelection != 8) {
                this.mDrawerAdapter?.setSelected(navSelection)
                this.mCurrentSelectedPosition = navSelection
            }
            if (this.mDrawerLayout != null) {
                if (this.mFragmentContainerView?.parent is FrameLayout) {
                    this.mDrawerLayout?.closeDrawer(this.mFragmentContainerView?.parent as FrameLayout)
                } else {
                    this.mDrawerLayout?.closeDrawer(this.mFragmentContainerView!!)
                }
            }
            val activity = activity
            if (navSelection == selectedPosition) {
                return
            }
            if (navSelection == 5) {
                // todo
//                ProfileActivity.launch(activity)
                activity?.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 2) {
                //todo
//                FeedActivity.launch(activity)
                activity?.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 3) {
                //todo
//                HowToFeedActivity.launchFromNavDrawer(activity)
                activity?.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 4) {
                //todo
//                FeaturedSearchActivity.launch(activity)
                activity?.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 8) {
                //todo
//                this.mShareManager.applicationFeedback()
            } else if (navSelection == 9) {
                //todo
//                AboutUsActivity.launchFromNavDrawer(activity)
                activity?.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else if (navSelection == 10) {
                //todo
//                SettingsOverviewActivity.launchFromNavDrawer(activity)
                activity?.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
            } else {
                if (this.mCallbacks != null) {
                    this.mCallbacks?.onNavigationDrawerItemSelected(navSelection, calledByUserClick)
                }
                if (selectedPosition == 5 || selectedPosition == 2 || selectedPosition == 3) {
                    activity?.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected_navigation_drawer_position", this.mCurrentSelectedPosition)
    }

    fun getCurrentSelectedPosition(): Int {
        return this.mCurrentSelectedPosition
    }

    fun setCurrentSelectedPosition(navSelection: Int) {
        if (this.mDrawerAdapter != null && this.mDrawerAdapter!!.itemCount > 0 && navSelection < this.mDrawerAdapter!!.itemCount) {
            this.mDrawerAdapter?.setSelected(navSelection)
            this.mCurrentSelectedPosition = navSelection
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        this.mDrawerToggle!!.onConfigurationChanged(newConfig)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        return this.mDrawerToggle!!.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onPositionSelected(position: Int) {
        selectItem(position, true)
    }

    fun setNavDrawerIndicator(enable: Boolean) {
        this.mDrawerToggle?.isDrawerIndicatorEnabled = enable
    }

    fun setDrawerLockMode(lockmode: Int, gravity: Int) {
        this.mDrawerLayout?.setDrawerLockMode(lockmode, gravity)
    }

    fun openRightDrawer() {
        if (this.mSubDrawerLayout == null) {
            return
        }
        if (this.mSubDrawerLayout!!.isDrawerOpen(GravityCompat.END)) {
            this.mSubDrawerLayout!!.closeDrawer(GravityCompat.END)
        } else {
            this.mSubDrawerLayout!!.openDrawer(GravityCompat.END)
        }
    }

    override fun checkLocale() {
        super.checkLocale()
        onCreateAdapter()
    }

}