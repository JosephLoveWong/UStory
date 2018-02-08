package com.promiseland.ustory.base.util;

import android.os.Build.VERSION;

public final class APILevelHelper {
    public static final boolean isAPILevelMinimal(int minAPILevel) {
        return VERSION.SDK_INT >= minAPILevel;
    }
}
