package com.promiseland.ustory.service.api

import android.app.Activity
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.promiseland.ustory.base.model.ui.user.UserUiModel
import com.promiseland.ustory.service.base.CustomService
import com.promiseland.ustory.ultron.model.upload.UserUploadData
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by joseph on 2018/3/11.
 */
interface UserService : CustomService {
    fun deleteProfilePicture(): Completable

    fun getUserData(): BehaviorSubject<UserUiModel>

    fun getUserId(): String

    fun handleAppStart()

    fun isCurrentUser(str: String): Boolean

    fun isLoggedIn(): Boolean

    fun isUserServiceBusy(): Boolean

    fun loadUserData()

    fun logInOrSignUpWithFacebook(activity: Activity)

    fun logInOrSignUpWithGoogle(fragmentActivity: FragmentActivity)

    fun logOut()

    fun loginViaEmail(str: String, str2: String)

    fun onActivityResult(i: Int, i2: Int, intent: Intent)

    fun registerViaEmail(str: String, str2: String, str3: String)

    fun resetPassword(str: String)

    fun updateUser(userUploadData: UserUploadData, i: Int): Completable

    fun uploadProfilePicture(): Completable
}