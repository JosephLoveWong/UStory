package com.promiseland.ustory.ui.util.glide;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

public class GlideRequest<TranscodeType> extends RequestBuilder<TranscodeType> {
    GlideRequest(Glide glide, RequestManager requestManager, Class<TranscodeType> transcodeClass) {
        super(glide, requestManager, transcodeClass);
    }

    public GlideRequest<TranscodeType> diskCacheStrategy(DiskCacheStrategy arg0) {
        if (getMutableOptions() instanceof GlideOptions) {
            this.requestOptions = ((GlideOptions) getMutableOptions()).diskCacheStrategy(arg0);
        } else {
            this.requestOptions = new GlideOptions().apply(this.requestOptions).diskCacheStrategy(arg0);
        }
        return this;
    }

    public GlideRequest<TranscodeType> dontTransform() {
        if (getMutableOptions() instanceof GlideOptions) {
            this.requestOptions = ((GlideOptions) getMutableOptions()).dontTransform();
        } else {
            this.requestOptions = new GlideOptions().apply(this.requestOptions).dontTransform();
        }
        return this;
    }

    public GlideRequest<TranscodeType> apply(RequestOptions arg0) {
        return (GlideRequest) super.apply(arg0);
    }

    public GlideRequest<TranscodeType> transition(TransitionOptions<?, ? super TranscodeType> arg0) {
        return (GlideRequest) super.transition(arg0);
    }

    public GlideRequest<TranscodeType> listener(RequestListener<TranscodeType> arg0) {
        return (GlideRequest) super.listener(arg0);
    }

    public GlideRequest<TranscodeType> load(Object arg0) {
        return (GlideRequest) super.load(arg0);
    }

    public GlideRequest<TranscodeType> load(String arg0) {
        return (GlideRequest) super.load(arg0);
    }

    public GlideRequest<TranscodeType> clone() {
        return (GlideRequest) super.clone();
    }
}
