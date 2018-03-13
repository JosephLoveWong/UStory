package com.promiseland.ustory.ui.util.glide;

import android.content.Context;
import com.bumptech.glide.Glide;

public final class GlideApp {
    private GlideApp() {
    }

    public static GlideRequests with(Context context) {
        return (GlideRequests) Glide.with(context);
    }
}
