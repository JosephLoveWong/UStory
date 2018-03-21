package com.promiseland.ustory.ui.mvp.introscreen

import android.content.Context
import android.content.Intent
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.TextView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.RxView
import com.promiseland.ustory.BuildConfig
import com.promiseland.ustory.R
import com.promiseland.ustory.base.util.APILevelHelper
import com.promiseland.ustory.base.util.ConfigurationUtils
import com.promiseland.ustory.base.util.FieldHelper
import com.promiseland.ustory.ui.base.mvp.BaseViewActivity
import com.promiseland.ustory.ui.base.mvp.EmptyPresenter
import com.promiseland.ustory.ui.mvp.feed.FeedActivity
import com.promiseland.ustory.ui.util.ViewHelper
import com.promiseland.ustory.ui.util.viewpager.PageIndicatorListener
import com.promiseland.ustory.ui.widget.util.KSUrlSpan
import com.promiseland.ustory.ui.widget.util.OnClickUrlListener
import kotlinx.android.synthetic.main.activity_intro_screen.*
import timber.log.Timber
import java.util.*

/**
 * Created by Administrator on 2018/3/5.
 */
class IntroScreenActivity : BaseViewActivity<EmptyPresenter>() , View.OnClickListener {
    override fun createPresenterInstance(): EmptyPresenter = EmptyPresenter()

    companion object {
        var mBackgroundVideoUri: Uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + R.raw.introscreen_bg)
        fun launch(context: Context) {
            context.startActivity(Intent(context, IntroScreenActivity::class.java))
        }
    }

    private var mSavedPagerPosition: Int = 0
    private var mSavedPlaybackPosition: Int = 0
    private var mInitSuccessfull = false
    private var mIsFirstScreen = true
    private var mIsLoginButtonLayoutHidden = false
    private var mShouldStartFeed = false
    private var mMediaPlayer: MediaPlayer? = null
    private var mTextureListener: TextureView.SurfaceTextureListener? = null
    private var mPageChangeListener: IntroScreenPageChangeListener? = null
    private var mPageIndicatorListener: PageIndicatorListener? = null
    internal var mFacebookLoginButton: View? = null
    internal var mGoogleLoginButton: View? = null

    override fun getContentLayout(): Int = R.layout.activity_intro_screen

    override fun bindView(view: View, savedInstanceState: Bundle?) {
        var i = 0
        if (savedInstanceState != null) {
            mIsFirstScreen = savedInstanceState.getBoolean("IS_FIRST_SCREEN", true)
            if (!mIsFirstScreen) {
                showInfoPager(false)
            }
        }

        pager.adapter = IntroScreenPagerAdapter()
        if (savedInstanceState != null) {
            i = savedInstanceState.getInt("STATE_VIEW_PAGER_PAGE")
        }
        mSavedPagerPosition = i
        mPageChangeListener = IntroScreenPageChangeListener()

        // 设置全屏
        if (APILevelHelper.isAPILevelMinimal(19)) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
        initBackgroundVideo(savedInstanceState)

        RxView.clicks(btn_been_here).subscribe({ showFeed() })
        RxView.clicks(btn_i_am_new).subscribe({ showInfoPager(true) })
    }

    override fun initData() {
    }

    private fun initBackgroundVideo(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mSavedPlaybackPosition = savedInstanceState.getLong("STATE_PLAYBACK_POSITION", 0).toInt()
        }
        mMediaPlayer = MediaPlayer()
        mMediaPlayer!!.isLooping = true
        mTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
                mMediaPlayer!!.setSurface(Surface(surfaceTexture))
                startVideo()
                centerCropVideo()
            }

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                if (mMediaPlayer!!.isPlaying) {
                    mMediaPlayer!!.pause()
                }
                return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
        }
    }

    internal fun startVideo() {
        try {
            mMediaPlayer!!.setDataSource(this, mBackgroundVideoUri)
            mMediaPlayer!!.setOnPreparedListener({ mp: MediaPlayer? ->
                if (mp != null) {
                    mInitSuccessfull = true
                    mp.start()
                    if (mSavedPlaybackPosition != 0) {
                        mp.seekTo(mSavedPlaybackPosition)
                    }
                    mp.setOnPreparedListener(null)
                }
            })
            mMediaPlayer!!.prepareAsync()
        } catch (e: Exception) {
            Timber.e("Error playing video on IntroScreen")
        }

    }

    internal fun centerCropVideo() {
        val metaRetriever = MediaMetadataRetriever()
        metaRetriever.setDataSource(this, mBackgroundVideoUri)
        val height = metaRetriever.extractMetadata(19)
        val width = metaRetriever.extractMetadata(18)
        val videoHeight = if (FieldHelper.isEmpty(height)) bg_video.height.toFloat() else java.lang.Float.parseFloat(height)
        val videoWidth = if (FieldHelper.isEmpty(width)) bg_video.width.toFloat() else java.lang.Float.parseFloat(width)
        val viewWidth = bg_video.width.toFloat()
        val viewHeight = bg_video.height.toFloat()
        var scaleX = 1.0f
        var scaleY = 1.0f
        if (videoWidth > viewWidth && videoHeight > viewHeight) {
            scaleX = videoWidth / viewWidth
            scaleY = videoHeight / viewHeight
        } else if (videoWidth < viewWidth && videoHeight < viewHeight) {
            scaleY = viewWidth / videoWidth
            scaleX = viewHeight / videoHeight
        } else if (viewWidth > videoWidth) {
            scaleY = viewWidth / videoWidth / (viewHeight / videoHeight)
        } else if (viewHeight > videoHeight) {
            scaleX = viewHeight / videoHeight / (viewWidth / videoWidth)
        }
        val pivotPointX = (viewWidth / 2.0f).toInt()
        val pivotPointY = (viewHeight / 2.0f).toInt()
        val matrix = Matrix()
        matrix.setScale(scaleX, scaleY, pivotPointX.toFloat(), pivotPointY.toFloat())
        bg_video.setTransform(matrix)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.appear_from_bottom, R.anim.do_not_move)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        bg_video.surfaceTextureListener = this.mTextureListener
        if (!(!mInitSuccessfull || mMediaPlayer == null || mMediaPlayer!!.isPlaying)) {
            mMediaPlayer!!.start()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showFeed()
    }

    private fun showFeed() {
        FeedActivity.launch(this)
        finish()
    }

    private fun showInfoPager(showTransitionAnimation: Boolean) {
        if (showTransitionAnimation) {
            animateAppearFromRight(login_layout, pager, btn_skip, page_indicator)
        }
        ViewHelper.makeGone(btn_i_am_new, btn_been_here, welcome_text)
        ViewHelper.makeVisible(login_layout, pager, btn_skip, page_indicator)
        mIsFirstScreen = false
    }

    private fun animateAppearFromRight(vararg views: View) {
        if (views != null && views.isNotEmpty()) {
            val anim = TranslateAnimation(ConfigurationUtils.getRealScreenSize(this).x.toFloat(), 0.0f, 0.0f, 0.0f)
            anim.duration = 300
            anim.interpolator = DecelerateInterpolator()
            anim.fillAfter = true
            for (v in views) {
                v?.startAnimation(anim)
            }
        }
    }

    override fun onClick(v: View?) {

    }

    private inner class IntroScreenPageChangeListener : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            if (position + 1 == 4 && !mIsLoginButtonLayoutHidden) {
                mIsLoginButtonLayoutHidden = true
                animateButtonLayout(true)
                page_indicator.visibility = View.GONE
            } else if (mIsLoginButtonLayoutHidden) {
                mIsLoginButtonLayoutHidden = false
                animateButtonLayout(false)
                page_indicator.visibility = View.VISIBLE
            }
        }

        private fun animateButtonLayout(hide: Boolean) {
            val yDest = (pager.height.toFloat() * 0.5f).toInt()
            val anim = TranslateAnimation(0.0f, 0.0f, if (hide) 0.0f else yDest.toFloat(), if (hide) yDest.toFloat() else 0.0f)
            anim.duration = 400
            anim.interpolator = AccelerateDecelerateInterpolator()
            anim.fillAfter = true
            login_layout.startAnimation(anim)
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }


    private inner class IntroScreenPagerAdapter : PagerAdapter() {

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val layout = LayoutInflater.from(collection.context).inflate(R.layout.intro_screen_page_item, collection, false) as ViewGroup
            layout.tag = Integer.valueOf(position)
            setTexts(position, ButterKnife.findById<View>(layout, R.id.title) as TextView, ButterKnife.findById<View>(layout, R.id.sub_title) as TextView, layout)
            collection.addView(layout)
            return layout
        }

        private fun setTexts(position: Int, title: TextView, sub: TextView, root: ViewGroup) {
            var i = 8
            when (position) {
                0 -> {
                    title.setText(R.string.intro_screen_pager_title_1)
                    sub.setText(R.string.intro_screen_pager_sub_1)
                    return
                }
                1 -> {
                    title.setText(R.string.intro_screen_pager_title_2)
                    sub.setText(R.string.intro_screen_pager_sub_2)
                    return
                }
                2 -> {
                    title.setText(R.string.intro_screen_pager_title_3)
                    sub.setText(R.string.intro_screen_pager_sub_3)
                    return
                }
                3 -> {
                    title.setText(R.string.intro_screen_sign_up)
                    sub.visibility = View.GONE
                    ButterKnife.findById<View>(root as View, R.id.login_buttons).visibility = View.VISIBLE
                    mGoogleLoginButton = ButterKnife.findById(root as View, R.id.fragment_auth_sign_up_root_google)
                    mGoogleLoginButton?.setOnClickListener(this@IntroScreenActivity)
                    mFacebookLoginButton = ButterKnife.findById(root as View, R.id.fragment_auth_sign_up_root_facebook)
                    mFacebookLoginButton?.setOnClickListener(this@IntroScreenActivity)
                    setFormattedTermsOfServiceText(ButterKnife.findById<View>(root as View, R.id.fragment_auth_sign_up_terms) as TextView)
                    return
                }
                else -> return
            }
        }

        private fun setFormattedTermsOfServiceText(termsOfService: TextView) {
            val termsofServiceTitle = this@IntroScreenActivity.getString(R.string.social_auth_terms_title)
            val privacyPolicy = this@IntroScreenActivity.getString(R.string.social_auth_privacy_policy_title)
            val text = String.format(Locale.getDefault(), this@IntroScreenActivity.getString(R.string.social_auth_terms), termsofServiceTitle, privacyPolicy)
            val formattedString = SpannableString(text)
            val termsOfServiceIndex = text.indexOf(termsofServiceTitle)
            if (termsOfServiceIndex >= 0) {
                formattedString.setSpan(KSUrlSpan(this@IntroScreenActivity.getString(R.string.social_auth_terms_url), object : OnClickUrlListener{
                    override fun onClickUrl(str: String) {
                        // TODO
//                        UrlHelper.openUrlInChromeCustomTab(this@IntroScreenActivity, url)
                    }

                }), termsOfServiceIndex, termsofServiceTitle.length + termsOfServiceIndex, 33)
            }
            val privacyPolicyIndex = text.indexOf(privacyPolicy)
            if (privacyPolicyIndex >= 0) {
                formattedString.setSpan(KSUrlSpan(this@IntroScreenActivity.getString(R.string.social_auth_privacy_policy_url), object : OnClickUrlListener{
                    override fun onClickUrl(str: String) {
                        // TODO
//                        UrlHelper.openUrlInChromeCustomTab(this@IntroScreenActivity, url)
                    }

                }), privacyPolicyIndex, privacyPolicy.length + privacyPolicyIndex, 33)
            }
            termsOfService.text = formattedString
            termsOfService.movementMethod = LinkMovementMethod.getInstance()
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getCount(): Int {
            return 4
        }
    }
}