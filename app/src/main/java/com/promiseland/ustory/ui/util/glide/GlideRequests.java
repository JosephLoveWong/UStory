package com.promiseland.ustory.ui.util.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.request.RequestOptions;

public class GlideRequests extends RequestManager {
    public GlideRequests(Glide glide, Lifecycle lifecycle, RequestManagerTreeNode treeNode) {
        super(glide, lifecycle, treeNode);
    }

    public GlideRequest as(Class resourceClass) {
        return new GlideRequest(this.glide, this, resourceClass);
    }

    public GlideRequest<Bitmap> asBitmap() {
        return (GlideRequest) super.asBitmap();
    }

    public GlideRequest<Drawable> asDrawable() {
        return (GlideRequest) super.asDrawable();
    }

    public GlideRequest<Drawable> load(Object arg0) {
        return (GlideRequest) super.load(arg0);
    }

    protected void setRequestOptions(RequestOptions toSet) {
        if (toSet instanceof GlideOptions) {
            super.setRequestOptions(toSet);
        } else {
            super.setRequestOptions(new GlideOptions().apply(toSet));
        }
    }
}
