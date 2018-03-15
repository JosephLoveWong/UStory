package com.promiseland.ustory.ui.mvp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.annotation.Nullable
import com.promiseland.ustory.AppComponent
import com.promiseland.ustory.R
import com.promiseland.ustory.UStoryApp
import com.promiseland.ustory.base.util.ImageLoaderUtil
import com.promiseland.ustory.module.BaseActivityModule
import com.promiseland.ustory.service.api.InstallationDataService
import com.promiseland.ustory.ui.base.mvp.BaseViewActivity
import com.promiseland.ustory.ui.base.mvp.EmptyPresenter
import com.promiseland.ustory.ui.mvp.introscreen.IntroScreenActivity
import com.promiseland.ustory.ui.mvp.feed.FeedActivity
import com.promiseland.ustory.ui.mvp.whatsnew.WhatsNewActivity
import com.promiseland.ustory.ultron.USPreferences
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/24.
 */
class SplashActivity : BaseViewActivity<EmptyPresenter>() {
    private val picUrl = "http://api.dujin.org/bing/1920.php"
    private val mDisposable: CompositeDisposable? = CompositeDisposable()
    private var mInstallationDataService: InstallationDataService? = null

    @Inject
    @Nullable
    @JvmField
    var mPrefs: USPreferences? = null

    @Inject
    @Nullable
    @JvmField
    var eventBus: EventBus? = null

    override fun getContentLayout(): Int = R.layout.activity_splash_screen

    override fun setupComponent(appComponent: AppComponent) {
        UStoryApp.appComponent.plus(BaseActivityModule()).inject(this)
    }

    override fun initData() {
        val lastUsedVersion = mPrefs?.getLastUsedVersionCodeAndUpdate() ?: 0
        showWhatsNewScreenIfNeeded(lastUsedVersion)

        ImageLoaderUtil.LoadImage(this, picUrl, iv_ad)

        // zanshi bu yong
//        mDisposable?.add(countDown(4)
//                .subscribeWith(object : DisposableObserver<Int>() {
//                    override fun onNext(t: Int) {
//                        tv_skip.text = "跳过 $t"
//                    }
//
//                    override fun onError(e: Throwable) {
//                        toMain()
//                    }
//
//                    override fun onComplete() {
//                        toMain()
//                    }
//
//                }))

        fl_ad.setOnClickListener { toMain() }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initServices()
        handleLaunchParameter()
    }

    override fun onDestroy() {
        mDisposable?.dispose()
        super.onDestroy()
    }

    private fun initServices() {
        mInstallationDataService?.updateInstallation()
    }

    private fun showWhatsNewScreenIfNeeded(lastUsedVersion: Int) {
        if (lastUsedVersion != 0 && lastUsedVersion < 240) { //TODO WHO IS 240
//            mPrefs?.setHasSeenWhatsNewScreen(false)
        }
    }

    private fun handleLaunchParameter() {
        // TODO DeepLink
        startFeedActivity()
    }

    private fun startFeedActivity() {
        // TODO 临时设置
//        mPrefs?.setHasSeenWhatsNewScreen(false)
//        mPrefs!!.setHasSeenIntroScreen(false)

        Handler().postDelayed({
            if (!mPrefs!!.getHasSeenWhatsNewScreen()) {
                mPrefs!!.setHasSeenWhatsNewScreen(true)
                WhatsNewActivity.launch(this)
                overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)
            } else if (mPrefs!!.getHasSeenIntroScreen() /*|| this.mUserService != null && this.mUserService.isLoggedIn()*/) {// TODO user service
                FeedActivity.launch(this)
                overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)
            } else {
                mPrefs!!.setHasSeenIntroScreen(true)
                IntroScreenActivity.launch(this)
                overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)
            }
            finish()
        }, 1000)
    }

    fun countDown(time: Int): Observable<Int> {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map { time - it.toInt() }
                .take(time.toLong())
    }

    private fun toMain() {
        mDisposable?.dispose()

        val intent = Intent()
        intent.setClass(this@SplashActivity, FeedActivity::class.java)
        startActivity(intent)
        finish()
    }
}