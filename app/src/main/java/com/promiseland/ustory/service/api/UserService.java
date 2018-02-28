package com.promiseland.ustory.service.api;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.ajnsnewmedia.kitchenstories.base.model.ui.user.UserUiModel;
import com.ajnsnewmedia.kitchenstories.service.base.CustomService;
import com.ajnsnewmedia.kitchenstories.ultron.model.upload.UserUploadData;
import io.reactivex.Completable;
import io.reactivex.subjects.BehaviorSubject;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0012\u001a\u00020\u0013H&J\b\u0010\u0014\u001a\u00020\u0015H&J\u0012\u0010\u0016\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH&J\b\u0010\u0017\u001a\u00020\u0015H&J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001aH&J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001cH&J\b\u0010\u001d\u001a\u00020\u0015H&J\u0018\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u000fH&J \u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020&H&J \u0010'\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\u000fH&J\u0010\u0010)\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u000fH&J\u001a\u0010*\u001a\u00020\u00132\u0006\u0010%\u001a\u00020+2\b\b\u0001\u0010,\u001a\u00020#H&J\b\u0010-\u001a\u00020\u0013H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0004R\u0018\u0010\u0006\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0004\"\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006."}, d2 = {"Lcom/ajnsnewmedia/kitchenstories/service/api/UserService;", "Lcom/ajnsnewmedia/kitchenstories/service/base/CustomService;", "isLoadingUserData", "", "()Z", "isLoggedIn", "isUserServiceBusy", "setUserServiceBusy", "(Z)V", "userData", "Lio/reactivex/subjects/BehaviorSubject;", "Lcom/ajnsnewmedia/kitchenstories/base/model/ui/user/UserUiModel;", "getUserData", "()Lio/reactivex/subjects/BehaviorSubject;", "userId", "", "getUserId", "()Ljava/lang/String;", "deleteProfilePicture", "Lio/reactivex/Completable;", "handleAppStart", "", "isCurrentUser", "loadUserData", "logInOrSignUpWithFacebook", "activity", "Landroid/app/Activity;", "logInOrSignUpWithGoogle", "Landroid/support/v4/app/FragmentActivity;", "logOut", "loginViaEmail", "email", "password", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "registerViaEmail", "nickname", "resetPassword", "updateUser", "Lcom/ajnsnewmedia/kitchenstories/ultron/model/upload/UserUploadData;", "messageId", "uploadProfilePicture", "app_playRelease"}, k = 1, mv = {1, 1, 7})
/* compiled from: UserService.kt */
public interface UserService extends CustomService {
    Completable deleteProfilePicture();

    BehaviorSubject<UserUiModel> getUserData();

    String getUserId();

    void handleAppStart();

    boolean isCurrentUser(String str);

    boolean isLoggedIn();

    boolean isUserServiceBusy();

    void loadUserData();

    void logInOrSignUpWithFacebook(Activity activity);

    void logInOrSignUpWithGoogle(FragmentActivity fragmentActivity);

    void logOut();

    void loginViaEmail(String str, String str2);

    void onActivityResult(int i, int i2, Intent intent);

    void registerViaEmail(String str, String str2, String str3);

    void resetPassword(String str);

    Completable updateUser(UserUploadData userUploadData, int i);

    Completable uploadProfilePicture();
}
