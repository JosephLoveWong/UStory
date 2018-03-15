package com.promiseland.ustory.ui.mvp.drawer;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.promiseland.ustory.AppComponent;
import com.promiseland.ustory.R;
import com.promiseland.ustory.ui.base.mvp.BaseViewFragment;
import com.promiseland.ustory.ui.mvp.drawer.DrawerItem.TwoStateIcon;
import com.promiseland.ustory.ui.mvp.drawer.DrawerItem.Type;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;


// TODO
public class NavigationDrawerFragment extends BaseViewFragment implements DrawerAdapter.DrawerAdapterCallbacks {
    NavigationDrawerCallbacks mCallbacks;
    private int mCurrentSelectedPosition = 0;
    private DrawerAdapter mDrawerAdapter;
    private DrawerLayout mDrawerLayout;
    @BindView(R.id.frg_navdrawer_main_menu)
    RecyclerView mDrawerListView;
    ActionBarDrawerToggle mDrawerToggle;
    EventBus mEventBus;
    private View mFragmentContainerView;
    private DrawerLayout mSubDrawerLayout;
    private DrawerListener mSubDrawerListener;

    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position, boolean selected);
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_navigation_drawer;
    }

    @Override
    public void setupComponent(@NotNull AppComponent appComponent) {
//        UStoryApp.Companion.getAppComponent().plus(new BaseFragmentModule()).inject(this);
    }

    @Override
    public void initData() {
        this.mDrawerListView.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        onCreateAdapter();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.mCurrentSelectedPosition = savedInstanceState.getInt("selected_navigation_drawer_position");
        }
        if (savedInstanceState == null) {
            selectItem(this.mCurrentSelectedPosition, false);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void onCreateAdapter() {
        this.mDrawerAdapter = createKitchenDrawerAdapter();
        this.mDrawerListView.setAdapter(this.mDrawerAdapter);
        this.mDrawerAdapter.setSelected(this.mCurrentSelectedPosition);
    }

    private DrawerAdapter createKitchenDrawerAdapter() {
        ArrayList<DrawerItem> items = new ArrayList(11);
        items.add(new DrawerItem(Type.TOP, new TwoStateIcon(0, 0), R.string.technical_not_set));
        items.add(new DrawerItem(Type.SEPARATOR, new TwoStateIcon(0, 0), R.string.technical_not_set));
        items.add(new DrawerItem(Type.ITEM, new TwoStateIcon(R.drawable.icon_nav_drawer_home, R.drawable.ic_nd_recipes_selected), R.string.navigation_recipes));
        items.add(new DrawerItem(Type.ITEM, new TwoStateIcon(R.drawable.icon_nav_drawer_howto, R.drawable.ic_nd_howto_selected), R.string.navigation_cooking_tips));
        items.add(new DrawerItem(Type.ITEM, new TwoStateIcon(R.drawable.icon_nav_drawer_categories, R.drawable.ic_nd_search_category_selected), R.string.navigation_search_categories));
        items.add(new DrawerItem(Type.ITEM, new TwoStateIcon(R.drawable.icon_nav_drawer_my_recipes, R.drawable.ic_nd_my_recipes_selected), R.string.navigation_my_recipes));
        items.add(new DrawerItem(Type.ITEM, new TwoStateIcon(R.drawable.icon_nav_drawer_shoppinglist, R.drawable.ic_nd_shopping_selected), R.string.navigation_shopping_list));
        items.add(new DrawerItem(Type.SEPARATOR, new TwoStateIcon(0, 0), R.string.technical_not_set));
        items.add(new DrawerItem(Type.ITEM, new TwoStateIcon(R.drawable.icon_nav_drawer_feedback, R.drawable.icon_nav_drawer_feedback), R.string.MENU_FEEDBACK));
        items.add(new DrawerItem(Type.ITEM, new TwoStateIcon(R.drawable.icon_nav_drawer_info, R.drawable.ic_nd_aboutus_selected), R.string.navigation_about_us));
        items.add(new DrawerItem(Type.ITEM, new TwoStateIcon(R.drawable.icon_nav_drawer_setting, R.drawable.ic_nd_settings_selected), R.string.navigation_settings));
        return new DrawerAdapter(getActivity(), items, this);
    }

    public boolean isDrawerOpen() {
        return isLeftDrawerOpen() || isRightDrawerOpen();
    }

    private boolean isLeftDrawerOpen() {
        return this.mDrawerLayout != null && (this.mDrawerLayout.isDrawerOpen(8388611) || this.mDrawerLayout.isDrawerOpen(8388613));
    }

    private boolean isRightDrawerOpen() {
        return this.mSubDrawerLayout != null && (this.mSubDrawerLayout.isDrawerOpen(8388611) || this.mSubDrawerLayout.isDrawerOpen(8388613));
    }

    public void closeDrawer() {
        if (this.mDrawerLayout != null && isLeftDrawerOpen()) {
            this.mDrawerLayout.closeDrawers();
        } else if (this.mSubDrawerLayout != null && isRightDrawerOpen()) {
            this.mSubDrawerLayout.closeDrawers();
        }
    }

    public void setUp(DrawerLayout drawerLayout, DrawerLayout subDrawerLayout) {
//        this.mFragmentContainerView = ButterKnife.findById(getActivity(), (int) R.id.navigation_drawer);
        this.mSubDrawerLayout = subDrawerLayout;
        this.mDrawerLayout = drawerLayout;
//        this.mDrawerLayout.setDrawerShadow((int) R.drawable.drawer_shadow, 8388611);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        this.mDrawerToggle = new ActionBarDrawerToggle(getActivity(), this.mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (NavigationDrawerFragment.this.isAdded()) {
//                    NavigationDrawerFragment.this.mEventBus.post(new DrawerEvent(false, drawerView.getId() == R.id.navigation_drawer ? 8388611 : 8388613));
                }
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (NavigationDrawerFragment.this.isAdded()) {
//                    NavigationDrawerFragment.this.mEventBus.post(new DrawerEvent(true, drawerView.getId() == R.id.navigation_drawer ? 8388611 : 8388613));
                }
            }
        };
//        this.mDrawerLayout.post(new NavigationDrawerFragment$$Lambda$0(this));
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);
        if (this.mSubDrawerLayout != null) {
            this.mSubDrawerListener = new DrawerListener() {
                public void onDrawerSlide(View drawerView, float slideOffset) {
                }

                public void onDrawerOpened(View drawerView) {
//                    NavigationDrawerFragment.this.mEventBus.post(new DrawerEvent(true, 8388613));
                }

                public void onDrawerClosed(View drawerView) {
//                    NavigationDrawerFragment.this.mEventBus.post(new DrawerEvent(false, 8388613));
                }

                public void onDrawerStateChanged(int newState) {
                }
            };
            this.mSubDrawerLayout.addDrawerListener(this.mSubDrawerListener);
        }
    }

    final /* synthetic */ void lambda$setUp$0$NavigationDrawerFragment() {
        if (this.mDrawerToggle != null) {
            this.mDrawerToggle.syncState();
        }
    }

    public void selectItem(int navSelection, boolean calledByUserClick) {
        if (this.mDrawerListView == null || this.mDrawerListView.getAdapter() == null || this.mDrawerAdapter.isEnabled(navSelection)) {
            int selectedPosition = this.mCurrentSelectedPosition;
            if (!(this.mDrawerListView == null || navSelection == 8)) {
                this.mDrawerAdapter.setSelected(navSelection);
                this.mCurrentSelectedPosition = navSelection;
            }
            if (this.mDrawerLayout != null) {
                if (this.mFragmentContainerView.getParent() instanceof FrameLayout) {
                    this.mDrawerLayout.closeDrawer((FrameLayout) this.mFragmentContainerView.getParent());
                } else {
                    this.mDrawerLayout.closeDrawer(this.mFragmentContainerView);
                }
            }
            FragmentActivity activity = getActivity();
            if (navSelection == selectedPosition) {
                return;
            }
            if (navSelection == 5) {
//                ProfileActivity.launch(activity);
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out);
            } else if (navSelection == 2) {
//                FeedActivity.launch(activity);
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out);
            } else if (navSelection == 3) {
//                HowToFeedActivity.launchFromNavDrawer(activity);
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out);
            } else if (navSelection == 4) {
//                FeaturedSearchActivity.launch(activity);
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out);
            } else if (navSelection == 8) {
//                this.mShareManager.applicationFeedback();
            } else if (navSelection == 9) {
//                AboutUsActivity.launchFromNavDrawer(activity);
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out);
            } else if (navSelection == 10) {
//                SettingsOverviewActivity.launchFromNavDrawer(activity);
                activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out);
            } else {
                if (this.mCallbacks != null) {
                    this.mCallbacks.onNavigationDrawerItemSelected(navSelection, calledByUserClick);
                }
                if (selectedPosition == 5 || selectedPosition == 2 || selectedPosition == 3) {
                    activity.overridePendingTransition(R.anim.do_not_move_with_fade_in, R.anim.do_not_move_with_fade_out);
                }
            }
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    public void onDetach() {
        super.onDetach();
        this.mCallbacks = null;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selected_navigation_drawer_position", this.mCurrentSelectedPosition);
    }

    public int getCurrentSelectedPosition() {
        return this.mCurrentSelectedPosition;
    }

    public void setCurrentSelectedPosition(int navSelection) {
        if (this.mDrawerListView != null && this.mDrawerAdapter != null && this.mDrawerAdapter.getItemCount() > 0 && navSelection < this.mDrawerAdapter.getItemCount()) {
            this.mDrawerAdapter.setSelected(navSelection);
            this.mCurrentSelectedPosition = navSelection;
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return this.mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void onPositionSelected(int position) {
        selectItem(position, true);
    }

    public void setNavDrawerIndicator(boolean toSet_) {
        this.mDrawerToggle.setDrawerIndicatorEnabled(toSet_);
    }

    public void setDrawerLockMode(int lockmode, int gravity) {
        this.mDrawerLayout.setDrawerLockMode(lockmode, gravity);
    }

    public void openRightDrawer() {
        if (this.mSubDrawerLayout == null) {
            return;
        }
        if (this.mSubDrawerLayout.isDrawerOpen(8388613)) {
            this.mSubDrawerLayout.closeDrawer(8388613);
        } else {
            this.mSubDrawerLayout.openDrawer(8388613);
        }
    }

    public void onDestroyView() {
        this.mCallbacks = null;
        if (this.mDrawerAdapter != null) {
            this.mDrawerAdapter.tearDown();
            this.mDrawerAdapter = null;
        }
        if (!(this.mDrawerLayout == null || this.mDrawerToggle == null)) {
            this.mDrawerLayout.removeDrawerListener(this.mDrawerToggle);
            this.mDrawerLayout = null;
        }
        if (!(this.mSubDrawerLayout == null || this.mSubDrawerListener == null)) {
            this.mSubDrawerLayout.removeDrawerListener(this.mSubDrawerListener);
        }
        if (this.mDrawerListView != null) {
            this.mDrawerListView.setAdapter(null);
        }
        this.mDrawerToggle = null;
        this.mFragmentContainerView = null;
        super.onDestroyView();
    }
}
