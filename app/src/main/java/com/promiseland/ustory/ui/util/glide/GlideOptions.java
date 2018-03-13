package com.promiseland.ustory.ui.util.glide;

import android.graphics.Bitmap;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestOptions;

public final class GlideOptions extends RequestOptions {
    public GlideOptions sizeMultiplier(float sizeMultiplier) {
        return (GlideOptions) super.sizeMultiplier(sizeMultiplier);
    }

    public GlideOptions diskCacheStrategy(DiskCacheStrategy arg0) {
        return (GlideOptions) super.diskCacheStrategy(arg0);
    }

    public GlideOptions priority(Priority arg0) {
        return (GlideOptions) super.priority(arg0);
    }

    public GlideOptions skipMemoryCache(boolean skip) {
        return (GlideOptions) super.skipMemoryCache(skip);
    }

    public GlideOptions override(int width, int height) {
        return (GlideOptions) super.override(width, height);
    }

    public GlideOptions signature(Key arg0) {
        return (GlideOptions) super.signature(arg0);
    }

    public GlideOptions clone() {
        return (GlideOptions) super.clone();
    }

    public <T> GlideOptions set(Option<T> arg0, T arg1) {
        return (GlideOptions) super.set(arg0, arg1);
    }

    public GlideOptions decode(Class<?> arg0) {
        return (GlideOptions) super.decode(arg0);
    }

    public GlideOptions downsample(DownsampleStrategy arg0) {
        return (GlideOptions) super.downsample(arg0);
    }

    public GlideOptions optionalCenterCrop() {
        return (GlideOptions) super.optionalCenterCrop();
    }

    public GlideOptions optionalFitCenter() {
        return (GlideOptions) super.optionalFitCenter();
    }

    public GlideOptions optionalCenterInside() {
        return (GlideOptions) super.optionalCenterInside();
    }

    public GlideOptions transform(Transformation<Bitmap> arg0) {
        return (GlideOptions) super.transform(arg0);
    }

    public GlideOptions optionalTransform(Transformation<Bitmap> transformation) {
        return (GlideOptions) super.optionalTransform(transformation);
    }

    public <T> GlideOptions optionalTransform(Class<T> resourceClass, Transformation<T> transformation) {
        return (GlideOptions) super.optionalTransform((Class) resourceClass, (Transformation) transformation);
    }

    public GlideOptions dontTransform() {
        return (GlideOptions) super.dontTransform();
    }

    public GlideOptions apply(RequestOptions other) {
        return (GlideOptions) super.apply(other);
    }

    public GlideOptions lock() {
        return (GlideOptions) super.lock();
    }

    public GlideOptions autoClone() {
        return (GlideOptions) super.autoClone();
    }
}
