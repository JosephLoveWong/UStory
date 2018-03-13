package com.promiseland.ustory.ui.util.glide;

import android.content.Context;
import com.bumptech.glide.signature.ObjectKey;
import java.io.File;

public class GlideImageCacheFinder {
    public static File findInDiskCache(Context context, String url) {
        return KSGlideModule.getDiskCache(context).get(new ObjectKey(url));
    }
}
