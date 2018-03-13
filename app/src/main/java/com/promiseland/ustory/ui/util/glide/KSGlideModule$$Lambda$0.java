package com.promiseland.ustory.ui.util.glide;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCache.Factory;

final /* synthetic */ class KSGlideModule$$Lambda$0 implements Factory {
    private final DiskCache arg$1;

    KSGlideModule$$Lambda$0(DiskCache diskCache) {
        this.arg$1 = diskCache;
    }

    public DiskCache build() {
        return KSGlideModule.lambda$applyOptions$0$KSGlideModule(this.arg$1);
    }
}
