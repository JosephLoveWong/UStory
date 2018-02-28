package com.promiseland.ustory.service.api;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.ajnsnewmedia.kitchenstories.service.base.CustomService;
import com.ajnsnewmedia.kitchenstories.service.impl.GoogleLoginServiceImpl.GoogleLoginCallback;

public interface GoogleLoginService extends CustomService {
    void onActivityResult(int i, int i2, Intent intent);

    void signIn(FragmentActivity fragmentActivity, GoogleLoginCallback googleLoginCallback);

    void signOut(Context context);
}
