package com.promiseland.ustory.ui.mvp.whatsnew

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Interpolator
import android.widget.Scroller
import android.widget.TextView
import butterknife.ButterKnife
import com.airbnb.lottie.LottieAnimationView
import com.jakewharton.rxbinding2.view.RxView
import com.promiseland.ustory.AppComponent
import com.promiseland.ustory.R
import com.promiseland.ustory.UStoryApp
import com.promiseland.ustory.base.util.APILevelHelper
import com.promiseland.ustory.module.BaseActivityModule
import com.promiseland.ustory.ui.base.BaseActivity
import com.promiseland.ustory.ui.mvp.main.FeedActivity
import com.promiseland.ustory.ui.util.viewpager.PageIndicatorListener
import kotlinx.android.synthetic.main.activity_whats_new.*
import timber.log.Timber

/**
 * Created by Administrator on 2018/3/2.
 */
class WhatsNewActivity : BaseActivity<WhatsNewPresenter>(), WhatsNewContract.View {
    companion object {
        var pagerScrollDelay = 4
        val pagerCount = 4

        fun launch(context: Context) {
            context.startActivity(Intent(context, WhatsNewActivity::class.java))
        }
    }

    private val ANIM_POINTS = arrayOf(floatArrayOf(0.0f, 0.0f, 2.0f), floatArrayOf(-0.5f, 0.5f, 1.5f), floatArrayOf(-0.5f, 0.5f, 1.5f), floatArrayOf(-1.0f, 1.0f, 1.0f))
    private var mLottieAnimationViews: SparseArray<LottieAnimationView> = SparseArray(pagerCount)
    private lateinit var mPageChangeListener: ViewPager.OnPageChangeListener
    private lateinit var mPageIndicatorListener: PageIndicatorListener
    private var mSavedPagerPosition: Int = 0

    override fun showFeed() {
        FeedActivity.launch(this)
        finish()
    }

    override fun showNextPage() {
        pager.setCurrentItem(pager.currentItem + 1, true)
    }

    override fun getContentLayout(): Int = R.layout.activity_whats_new

    override fun setupComponent(appComponent: AppComponent) {
        UStoryApp.appComponent.plus(BaseActivityModule()).inject(this)
    }

    override fun bindView(view: View, savedInstanceState: Bundle?) {
        mSavedPagerPosition = savedInstanceState?.getInt("STATE_VIEW_PAGER_PAGE") ?: 0
        if (mSavedPagerPosition == 3) {
            updateNextPageButtonText(true)
        }

        // 设置全屏
        if (APILevelHelper.isAPILevelMinimal(19)) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }

        mPageIndicatorListener = PageIndicatorListener(page_indicator, 4, ContextCompat.getColor(this, R.color.text_grey), ContextCompat.getColor(this, R.color.inactive_grey), mSavedPagerPosition)
        mPageChangeListener = WhatsNewOnPageChangeListener()
    }

    override fun initData() {
        RxView.clicks(btn_skip).subscribe({
            mPresenter?.closeClick()
        })

        RxView.clicks(btn_next).subscribe({
            pagerScrollDelay = 8
            if (pager.currentItem == 3) {
                mPresenter?.closeClick()
            } else {
                mPresenter?.nextClicked()
            }
        })

        pager.adapter = WhatsNewPagerAdapter()
        pager.offscreenPageLimit = 4
        setViewPagerScroller()

    }

    private fun updateNextPageButtonText(isLastPosition: Boolean) {
        btn_next.setText(if (isLastPosition) R.string.dialog_got_it else R.string.recipe_edit_next)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.appear_from_bottom, R.anim.do_not_move)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        pager.addOnPageChangeListener(mPageChangeListener)
        pager.addOnPageChangeListener(mPageIndicatorListener)
    }

    override fun onPause() {
        super.onPause()
        pager.removeOnPageChangeListener(mPageChangeListener)
        pager.removeOnPageChangeListener(mPageIndicatorListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("STATE_VIEW_PAGER_PAGE", if (pager != null) pager.currentItem else 0)
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        showFeed()
    }

    private fun setViewPagerScroller() {
        try {
            val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            val interpolator = ViewPager::class.java.getDeclaredField("sInterpolator")
            interpolator.isAccessible = true
            scrollerField.set(pager, object : Scroller(this, interpolator.get(null) as Interpolator) {
                override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
                    super.startScroll(startX, startY, dx, dy, duration * pagerScrollDelay)
                }
            })
        } catch (e: Exception) {
        }

    }

    inner class WhatsNewPagerAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, any: Any): Boolean = view == any

        override fun getCount(): Int = pagerCount

        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layout = LayoutInflater.from(container.context).inflate(R.layout.whats_new_page_item, container, false) as ViewGroup
            layout.tag = Integer.valueOf(position)
            mLottieAnimationViews.put(position, ButterKnife.findById(layout, R.id.image))
            setTexts(position, ButterKnife.findById(layout, R.id.title), ButterKnife.findById(layout, R.id.sub_title))
            container.addView(layout)
            return layout
        }

        private fun setTexts(position: Int, title: TextView, sub: TextView) {
            when (position) {
                0 -> {
                    (mLottieAnimationViews.get(position) as LottieAnimationView).setAnimation("animation/whats_new_1.json")
                    title.setText(R.string.whats_new_page_headline_1)
                    sub.setText(R.string.whats_new_page_text_1)
                    return
                }
                1 -> {
                    (mLottieAnimationViews.get(position) as LottieAnimationView).setAnimation("animation/whats_new_2.json")
                    title.setText(R.string.whats_new_page_headline_2)
                    sub.setText(R.string.whats_new_page_text_2)
                    return
                }
                2 -> {
                    (mLottieAnimationViews.get(position) as LottieAnimationView).setAnimation("animation/whats_new_3.json")
                    title.setText(R.string.whats_new_page_headline_3)
                    sub.setText(R.string.whats_new_page_text_3)
                    return
                }
                3 -> {
                    (mLottieAnimationViews.get(position) as LottieAnimationView).setAnimation("animation/whats_new_4.json")
                    title.setText(R.string.whats_new_page_headline_4)
                    sub.setText(R.string.whats_new_page_text_4)
                    return
                }
                else -> return
            }
        }
    }

    inner class WhatsNewOnPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            try {
                val firstLottieView = mLottieAnimationViews.get(position) as LottieAnimationView
                if (firstLottieView != null) {
                    firstLottieView.progress = lerp(ANIM_POINTS[position][1], ANIM_POINTS[position][2], positionOffset)
                }
                val nexPosition = position + 1
                val secondLottieView = mLottieAnimationViews.get(nexPosition) as LottieAnimationView
                if (secondLottieView != null) {
                    secondLottieView.progress = lerp(ANIM_POINTS[nexPosition][0], ANIM_POINTS[nexPosition][1], positionOffset)
                }
            } catch (e: Exception) {
                Timber.d(e)
            }

        }

        override fun onPageSelected(position: Int) {
            pagerScrollDelay = 4
            updateNextPageButtonText(position == 3)
        }

        private fun lerp(startValue: Float, endValue: Float, scrollPercent: Float): Float {
            val value = startValue + (endValue - startValue) * scrollPercent
            if (value < 0.0f) {
                return 0.0f
            }
            return if (value > 1.0f) 1.0f else value
        }

    }
}