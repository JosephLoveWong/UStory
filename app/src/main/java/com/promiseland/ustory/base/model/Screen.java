package com.promiseland.ustory.base.model;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.DisplayMetrics;
import android.view.Display;

import com.promiseland.ustory.base.util.ConfigurationUtils;

import java.util.ArrayList;

import timber.log.Timber;

public class Screen {
    private static final int[] AVAILABLE_BUCKETS = new int[]{100, 150, 200, 300, 400};
    private static final ArrayList<ImageSize> AVAILABLE_IMAGE_SIZES = new ArrayList(5);
    private static boolean mNeedsMinimalImages;
    private static boolean mOrientationChanged;
    private static String mScreenBucket;
    private static int mScreenHeight;
    private static int mScreenRotation;
    private static int mScreenWidth;

    private static class ImageSize {
        private int calculatedHeight = 1;
        private int calculatedWidth = 1;
        public final String mName;
        private final int mOriginalHeight;
        private final int mOriginalWidth;

        ImageSize(String name, int width, int height) {
            this.mName = name;
            this.mOriginalWidth = width;
            this.mOriginalHeight = height;
        }

        public void calculateSizes(boolean isLandscapeMode, float screenHeight, float screenWidth) {
            if (isLandscapeMode) {
                this.calculatedHeight = (int) (((float) this.mOriginalHeight) * (screenHeight / 768.0f));
                this.calculatedWidth = (int) (((float) this.mOriginalWidth) * (screenWidth / 1024.0f));
                return;
            }
            this.calculatedHeight = (int) (((float) this.mOriginalHeight) * (screenWidth / 768.0f));
            this.calculatedWidth = (int) (((float) this.mOriginalWidth) * (screenHeight / 1024.0f));
        }

        public int getWidth() {
            return this.calculatedWidth;
        }

        public int getHeight() {
            return this.calculatedHeight;
        }
    }

    static {
        AVAILABLE_IMAGE_SIZES.add(new ImageSize("full", 1024, 768));
        AVAILABLE_IMAGE_SIZES.add(new ImageSize("large", 800, 600));
        AVAILABLE_IMAGE_SIZES.add(new ImageSize("medium", 512, 384));
        AVAILABLE_IMAGE_SIZES.add(new ImageSize("small", 256, 192));
        AVAILABLE_IMAGE_SIZES.add(new ImageSize("icon", 128, 96));
    }

    public static void onCreate(Context context, Display display, boolean isOrientationChanged) {
        calculateNeedsMinimalImages();
        updateScreenValues(display);
        calculateBucketSize(context, display);
        mOrientationChanged = isOrientationChanged;
    }

    private static void updateScreenSizes() {
        boolean screenInLandscapeMode = isScreenInLandscapeMode();
        for (int k = 0; k < AVAILABLE_IMAGE_SIZES.size(); k++) {
            AVAILABLE_IMAGE_SIZES.get(k).calculateSizes(screenInLandscapeMode, (float) mScreenHeight, (float) mScreenWidth);
        }
    }

    private static void calculateNeedsMinimalImages() {
        mNeedsMinimalImages = false;
    }

    public static String calculateSize(int width, int height) {
        for (int i = 0; i < 5; i++) {
            ImageSize imageSize = AVAILABLE_IMAGE_SIZES.get(i);
            if ((height < imageSize.getWidth() || height < 0) && (width < imageSize.getHeight() || width < 0)) {
                return imageSize.mName;
            }
        }
        return "medium";
    }

    public static String calculateSize() {
        return calculateSize(mScreenWidth, mScreenHeight);
    }

    public static String calculateOrientation(int width, int height) {
        if (((float) height) > ((float) width) * 1.1f) {
            return "portrait";
        }
        if (((float) width) > ((float) height) * 1.1f) {
            return "landscape";
        }
        return "quad";
    }

    public static String calculateOrientation() {
        return calculateOrientation(mScreenWidth, mScreenHeight);
    }

    public static void onResume(Display display) {
        updateScreenValues(display);
    }

    private static synchronized void updateScreenValues(Display display) {
        synchronized (Screen.class) {
            mScreenRotation = display.getRotation();
            Point sizePoint = new Point();
            display.getSize(sizePoint);
            if (!(sizePoint.x == mScreenWidth && sizePoint.y == mScreenHeight)) {
                Timber.d("actualize screen values and sizes");
                mScreenWidth = sizePoint.x;
                mScreenHeight = sizePoint.y;
                updateScreenSizes();
            }
        }
    }

    private static synchronized void calculateBucketSize(Context context, Display display) {
        synchronized (Screen.class) {
            if (mScreenBucket == null) {
                DisplayMetrics realMetrics;
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                int bucketSize = AVAILABLE_BUCKETS[AVAILABLE_BUCKETS.length - 1];
                int currentDensity = (int) (metrics.density * 100.0f);
                int i = 0;
                int availableBucketsLength = AVAILABLE_BUCKETS.length;
                while (i < availableBucketsLength) {
                    if (currentDensity > AVAILABLE_BUCKETS[i]) {
                        i++;
                    } else if (!mNeedsMinimalImages || i <= 0) {
                        bucketSize = AVAILABLE_BUCKETS[i];
                        if (bucketSize < Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                            if (ConfigurationUtils.isTablet(context)) {
                                bucketSize = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                            } else {
                                realMetrics = new DisplayMetrics();
                                display.getRealMetrics(realMetrics);
                                if ((realMetrics.widthPixels >= 1500 && realMetrics.heightPixels >= 1000) || (realMetrics.widthPixels >= 1000 && realMetrics.heightPixels >= 1500)) {
                                    bucketSize = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                                }
                            }
                        }
                        mScreenBucket = String.valueOf(bucketSize);
                    } else {
                        bucketSize = AVAILABLE_BUCKETS[i - 1];
                        if (bucketSize < Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                            if (ConfigurationUtils.isTablet(context)) {
                                realMetrics = new DisplayMetrics();
                                display.getRealMetrics(realMetrics);
                                bucketSize = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                            } else {
                                bucketSize = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                            }
                        }
                        mScreenBucket = String.valueOf(bucketSize);
                    }
                }
                if (bucketSize < Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    if (ConfigurationUtils.isTablet(context)) {
                        bucketSize = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                    } else {
                        realMetrics = new DisplayMetrics();
                        display.getRealMetrics(realMetrics);
                        bucketSize = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
                    }
                }
                mScreenBucket = String.valueOf(bucketSize);
            }
        }
    }

    public static int getScreenWidth() {
        return mScreenWidth;
    }

    public static int getScreenHeight() {
        return mScreenHeight;
    }

    public static boolean isScreenInLandscapeMode() {
        return mScreenRotation == 1 || mScreenRotation == 3;
    }

    public static boolean isOrientationChanged() {
        return mOrientationChanged;
    }

    public static String getScreenBucket() {
        return mScreenBucket;
    }
}
