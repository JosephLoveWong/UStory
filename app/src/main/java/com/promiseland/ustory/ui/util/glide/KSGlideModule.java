package com.promiseland.ustory.ui.util.glide;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.promiseland.ustory.ultron.okhttp.OkHttpHelper;

import java.io.File;
import java.io.InputStream;

import okhttp3.OkHttpClient.Builder;
import timber.log.Timber;

public class KSGlideModule extends AppGlideModule {
    private static DiskCache sDiskCache = null;

    public void applyOptions(Context context, GlideBuilder builder) {
        DiskCache diskCache = getDiskCache(context);
        if (diskCache != null) {
            builder.setDiskCache(new KSGlideModule$$Lambda$0(diskCache));
        }
    }

    static final /* synthetic */ DiskCache lambda$applyOptions$0$KSGlideModule(DiskCache diskCache) {
        return diskCache;
    }

    public void registerComponents(Context context, Glide glide, Registry registry) {
        Builder builder = new Builder();
        OkHttpHelper.overwriteSslBelowLollipop(builder);
        builder.addInterceptor(new SizeInterceptor());
        registry.append(GlideUrl.class, InputStream.class, new Factory(builder.build()));
    }

    public static synchronized DiskCache getDiskCache(Context context) {
        DiskCache diskCache;
        synchronized (KSGlideModule.class) {
            if (sDiskCache == null) {
                sDiskCache = createDiskCache(context);
            }
            diskCache = sDiskCache;
        }
        return diskCache;
    }

    private static synchronized DiskCache createDiskCache(Context context) {
        DiskCache diskCache = null;
        synchronized (KSGlideModule.class) {
            if (context != null) {
                try {
                    File externalCache;
                    if ("mounted".equals(Environment.getExternalStorageState())) {
                        File[] externalCacheDirs = ContextCompat.getExternalCacheDirs(context);
                        externalCache = externalCacheDirs == null ? null : externalCacheDirs[0];
                    } else {
                        externalCache = null;
                    }
                    if (externalCache != null && externalCache.exists()) {
                        diskCache = createCacheDir(externalCache);
                    }
                } catch (Exception e) {
                    Timber.w(e, "cannot load external cache");
                }
                File cacheDir = context.getCacheDir();
                if (cacheDir != null) {
                    diskCache = createCacheDir(cacheDir);
                }
            }
        }
        return diskCache;
    }

    private static DiskCache createCacheDir(File cacheDir) {
        File cacheLocation = new File(cacheDir, "imageCache");
        if (!cacheLocation.exists()) {
            cacheLocation.mkdirs();
        }
        Timber.d("create cache in %s", cacheLocation.getPath());
        return DiskLruCacheWrapper.get(cacheLocation, 104857600);
    }
}
