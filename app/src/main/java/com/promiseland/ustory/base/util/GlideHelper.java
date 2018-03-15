package com.promiseland.ustory.base.util;

import android.content.Context;

import com.promiseland.ustory.ui.util.glide.GlideApp;
import com.promiseland.ustory.ui.util.glide.GlideRequests;


public class GlideHelper {
    public static GlideRequests saveGetGlideWith(Context context) {
        try {
            return GlideApp.with(context);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalStateException e2) {
            return null;
        }
    }
}
