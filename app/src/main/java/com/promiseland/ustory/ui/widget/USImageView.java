package com.promiseland.ustory.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.promiseland.ustory.R;
import com.promiseland.ustory.base.model.Screen;
import com.promiseland.ustory.base.util.FieldHelper;
import com.promiseland.ustory.base.util.GlideHelper;
import com.promiseland.ustory.ui.util.glide.GlideRequest;
import com.promiseland.ustory.ui.util.glide.GlideRequests;
import com.promiseland.ustory.ultron.base.Image;

import java.util.Locale;

import timber.log.Timber;

public class USImageView extends AppCompatImageView implements RequestListener<Drawable> {
    protected int mBackground;
    private int mDefaultImage;
    private boolean mDontTransform;
    private boolean mIsCacheAll;
    protected String mOrientation = "toBeDetermined";
    protected String mSize = "toBeDetermined";

    public USImageView(Context context) {
        super(context);
    }

    public USImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public USImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public USImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.USImageView, defStyleAttr, defStyleRes);
            if (a == null) {
                return;
            }
            if (a.hasValue(R.styleable.USImageView_us_size) && (a.hasValue(R.styleable.USImageView_us_size_landscape) || a.hasValue(R.styleable.USImageView_us_size_portrait))) {
                throw new IllegalArgumentException("you are only allowed to use ks_size or ks_size_landscape/ks_size_portrait");
            } else if ((!a.hasValue(R.styleable.USImageView_us_size_landscape) || a.hasValue(R.styleable.USImageView_us_size_portrait))
                    && (a.hasValue(R.styleable.USImageView_us_size_landscape) || !a.hasValue(R.styleable.USImageView_us_size_portrait))) {
                int size;
                if (a.hasValue(R.styleable.USImageView_us_size)) {
                    size = a.getInt(R.styleable.USImageView_us_size, -1);
                } else if (a.hasValue(R.styleable.USImageView_us_size_landscape) && Screen.isScreenInLandscapeMode()) {
                    size = a.getInt(R.styleable.USImageView_us_size_landscape, -1);
                } else {
                    size = a.getInt(R.styleable.USImageView_us_size_portrait, -1);
                }
                if (size == -1) {
                    this.mSize = "icon";
                } else {
                    defineSize(size);
                }
                defineOrientation(a.getInt(R.styleable.USImageView_orientation, 0));
                this.mBackground = a.getResourceId(R.styleable.USImageView_background, 0);
                this.mIsCacheAll = a.getBoolean(R.styleable.USImageView_isCacheAll, false);
                this.mDontTransform = a.getBoolean(R.styleable.USImageView_dontTransform, false);
                a.recycle();
            } else {
                throw new IllegalArgumentException("if you define ks_size_landscape or ks_size_portrait, the corresponding one must be defined");
            }
        }
    }

    private void defineOrientation(int orientation) {
        switch (orientation) {
            case 1:
                this.mOrientation = "original";
                return;
            case 2:
                this.mOrientation = "landscape";
                return;
            case 3:
                this.mOrientation = "portrait";
                return;
            case 4:
                this.mOrientation = "quad";
                return;
            default:
                this.mOrientation = "toBeDetermined";
                return;
        }
    }

    private void defineSize(int size) {
        switch (size) {
            case 0:
                this.mSize = "full";
                return;
            case 1:
                this.mSize = "large";
                return;
            case 2:
                this.mSize = "medium";
                return;
            case 3:
                this.mSize = "small";
                return;
            case 4:
                this.mSize = "icon";
                return;
            default:
                this.mSize = "toBeDetermined";
                return;
        }
    }

    public void loadUrl(String url) {
        loadUrl(url, true);
    }

    public void loadUrl(String url, boolean doSmart) {
        if (FieldHelper.INSTANCE.isEmpty(url)) {
            reset();
            return;
        }
        Object tag = getTag(R.id.ks_imageview_original);
        Drawable drawable = getDrawable();
        boolean isRecycled = (drawable instanceof BitmapDrawable) && ((BitmapDrawable) drawable).getBitmap() != null && ((BitmapDrawable) drawable).getBitmap().isRecycled();
        if (tag == null || !tag.equals(url) || isRecycled) {
            setTag(R.id.ks_imageview_original, url);
            if (doSmart) {
                String smartUrl = getSmartImageServerString(url);
                setTag(R.id.ks_imageview, smartUrl);
                loadInternal(smartUrl);
                return;
            }
            loadInternal(url);
        }
    }

    private boolean isSmartImageUrl(String url) {
        return url.contains("images.kitchenstories") && url.endsWith(Screen.getScreenBucket() + ".jpg");
    }

    void loadInternal(String url) {
        setInternalBackground();
        GlideRequests glide = GlideHelper.saveGetGlideWith(getContext());
        if (glide != null) {
            GlideRequest request = glide.load((Object) url).diskCacheStrategy(this.mIsCacheAll ? DiskCacheStrategy.ALL : DiskCacheStrategy.AUTOMATIC).transition(new DrawableTransitionOptions().crossFade()).listener((RequestListener) this);
            if (this.mDontTransform) {
                request = request.dontTransform();
            }
            request.into(this);
        }
    }

    private String getSmartImageServerString(String url) {
        return getSmartImageServerString(url, getCalculatedSize(), getCalculatedOrientation());
    }

    public static String getSmartImageServerString(String url, String size, String orientation) {
        if (url.endsWith(".jpg")) {
            return createKsUrl(url, size, orientation);
        }
        return url;
    }

    private static String createKsUrl(String url, String size, String orientation) {
        String imageUrl = url.substring(0, url.length() - 4);
        int pos = imageUrl.lastIndexOf('/');
        if (pos <= 0 || pos > imageUrl.length()) {
            return url;
        }
        if (FieldHelper.INSTANCE.isEmpty(imageUrl.substring(pos, imageUrl.length()))) {
            return url;
        }
        return String.format(Locale.ENGLISH, "%s%s-%s-%s-%s.jpg", new Object[]{imageUrl, imageUrl.substring(pos, imageUrl.length()), size, orientation, Screen.getScreenBucket()});
    }

    private String getCalculatedSize() {
        if ("toBeDetermined".equals(this.mSize)) {
            return Screen.calculateSize(getWidth(), getHeight());
        }
        return this.mSize;
    }

    private String getCalculatedOrientation() {
        if ("toBeDetermined".equals(this.mOrientation)) {
            return Screen.calculateOrientation(getWidth(), getHeight());
        }
        return this.mOrientation;
    }

    public void loadUrl(Image image) {
        loadUrl(image, true);
    }

    public void loadUrl(Image image, boolean doSmart) {
        if (image == null || FieldHelper.INSTANCE.isEmpty(image.getUrl())) {
            reset();
        } else {
            loadUrl(image.getUrl(), doSmart);
        }
    }

    public void loadUrl(Image image, int defaultImage, boolean doSmart) {
        this.mDefaultImage = defaultImage;
        loadUrl(image, doSmart);
    }

    public void setImageResource(int resId) {
        if (!Integer.valueOf(resId).equals(getTag(R.id.ks_imageview))) {
            setTag(R.id.ks_imageview, Integer.valueOf(resId));
            setTag(R.id.ks_imageview_original, null);
            super.setImageResource(resId);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        setTag(R.id.ks_imageview, null);
        setTag(R.id.ks_imageview_original, null);
    }

    public void reset() {
        setImageDrawable(null);
        setInternalBackground();
        setTag(R.id.ks_imageview, null);
        setTag(R.id.ks_imageview_original, null);
    }

    private void setInternalBackground() {
        if (this.mDefaultImage != 0) {
            setBackgroundResource(this.mDefaultImage);
        } else if (this.mBackground == 0) {
            setBackgroundResource(R.color.background_grey);
        } else {
            setBackgroundResource(this.mBackground);
        }
    }

    public void setOrientation(String orientation) {
        this.mOrientation = orientation;
    }

    public void setSize(String size) {
        this.mSize = size;
    }

    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        this.mBackground = resId;
    }

    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        if ((model instanceof String) && isSmartImageUrl((String) model)) {
            Timber.w("Cannot load file (%s, %s, %s):", model, target, Boolean.valueOf(isFirstResource));
        } else {
            Timber.w(e, "Cannot load file (%s, %s, %s):", model, target, Boolean.valueOf(isFirstResource));
        }
        if (target instanceof ViewTarget) {
            View view = ((ViewTarget) target).getView();
            String dumbUrl = (String) view.getTag(R.id.ks_imageview_original);
            if (!FieldHelper.INSTANCE.isEmpty(dumbUrl) && !dumbUrl.equals(model)) {
                view.setTag(R.id.ks_imageview_original, "");
                loadInternal(dumbUrl);
                return true;
            } else if (view instanceof USImageView) {
                ((USImageView) view).reset();
            } else {
                Timber.w("Failed for: %s", model);
            }
        }
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        Timber.v("onResourceReady(%s, %s, %s, %s)", resource, model, target, Boolean.valueOf(isFirstResource));
        if (target instanceof ViewTarget) {
            View view = ((ViewTarget) target).getView();
            view.setTag(R.id.ks_imageview, model);
            view.setBackground(null);
        }
        return false;
    }
}
