package com.promiseland.ustory.base.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.promiseland.ustory.R;

import java.util.Locale;

public class ConfigurationUtils {

    public static boolean isTablet(Context context) {
        return context != null && context.getResources().getBoolean(R.bool.isTablet);
    }

    public static Point getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new Point(metrics.widthPixels, metrics.heightPixels);
    }

    @TargetApi(17)
    public static Point getRealScreenSize(Activity activity) {
        if (activity == null) {
            return new Point(768, 1024);
        }
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        return new Point(size.x, size.y);
    }

    public static int getDrawableScreenHeight(Activity activity, boolean statusBarIsDrawableSpace, boolean toolbarIsDrawableSpace, boolean navBarIsDrawableSpace) {
        int i = 0;
        if (activity == null) {
            return 0;
        }
        int i2;
        int navigationBarHeight = getRealScreenSize(activity).y - (navBarIsDrawableSpace ? 0 : getNavigationBarHeight(activity));
        if (statusBarIsDrawableSpace) {
            i2 = 0;
        } else {
            i2 = getStatusBarHeight(activity);
        }
        i2 = navigationBarHeight - i2;
        if (!toolbarIsDrawableSpace) {
            i = getActionBarHeight(activity);
        }
        return i2 - i;
    }

    public static boolean isLandscapeOrientation(Context context) {
        return context != null && context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static void updateBaseLocale(Context context, Locale preferedLocale) {
        Locale locale;
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (preferedLocale.getCountry() == null) {
            locale = new Locale(preferedLocale.getLanguage());
        } else {
            locale = new Locale(preferedLocale.getLanguage(), preferedLocale.getCountry());
        }
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }

    public static boolean isConnectedToInternet(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static boolean isConnectedToWifi(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null || info.getType() != ConnectivityManager.TYPE_WIFI) {
            return false;
        }
        return true;
    }

    public static int getAppBarOffset(Context context) {
        int offset = getActionBarHeight(context);
        if (APILevelHelper.isAPILevelMinimal(21)) {
            return offset + getStatusBarHeight(context);
        }
        return offset;
    }

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return 0;
    }

    public static int getNavigationBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if ((hasMenuKey || hasBackKey)) {
            return 0;
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static void adjustToolbarHeight(Context context, ViewGroup toolbar) {
        if (toolbar != null) {
            int statusBarHeight = getStatusBarHeight(context);
            toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            toolbar.getLayoutParams().height += statusBarHeight;
        }
    }
}
