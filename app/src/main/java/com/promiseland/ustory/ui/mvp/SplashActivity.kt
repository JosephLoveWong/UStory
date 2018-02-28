package com.promiseland.ustory.ui.mvp

import android.content.Intent
import android.support.annotation.Nullable
import com.promiseland.ustory.AppComponent
import com.promiseland.ustory.R
import com.promiseland.ustory.UStoryApp
import com.promiseland.ustory.base.util.ImageLoaderUtil
import com.promiseland.ustory.module.BaseActivityModule
import com.promiseland.ustory.ui.base.BaseActivity
import com.promiseland.ustory.ui.base.EmptyPresenter
import com.promiseland.ustory.ui.mvp.main.MainActivity
import com.promiseland.ustory.ultron.USPreferences
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Administrator on 2018/2/24.
 */
class SplashActivity : BaseActivity<EmptyPresenter>() {
    private val picUrl = "http://api.dujin.org/bing/1920.php"
    private val mDisposable: CompositeDisposable? = CompositeDisposable()

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
        val lastUsedVersion = mPrefs?.lastUsedVersionCodeAndUpdate ?: 0
//        showWhatsNewScreenIfNeeded(lastUsedVersion)

        ImageLoaderUtil.LoadImage(this, picUrl, iv_ad)

        mDisposable?.add(countDown(4)
                .subscribeWith(object : DisposableObserver<Int>() {
                    override fun onNext(t: Int) {
                        tv_skip.text = "跳过 $t"
                    }

                    override fun onError(e: Throwable) {
                        toMain()
                    }

                    override fun onComplete() {
                        toMain()
                    }

                }))

        fl_ad.setOnClickListener { toMain() }
    }

    override fun onDestroy() {
        mDisposable?.dispose()
        super.onDestroy()
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
        intent.setClass(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}