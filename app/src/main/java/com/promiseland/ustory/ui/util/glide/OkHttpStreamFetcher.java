package com.promiseland.ustory.ui.util.glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;

import okhttp3.Call;
import okhttp3.Call.Factory;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpStreamFetcher implements DataFetcher<InputStream> {
    private volatile Call call;
    private final Factory client;
    private ResponseBody responseBody;
    private InputStream stream;
    private final GlideUrl url;

    public OkHttpStreamFetcher(Factory client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }

    public void loadData(Priority priority, DataCallback<? super InputStream> callback) {
        Builder requestBuilder = new Builder().url(this.url.toStringUrl());
        for (Entry<String, String> headerEntry : this.url.getHeaders().entrySet()) {
            requestBuilder.addHeader((String) headerEntry.getKey(), (String) headerEntry.getValue());
        }
        this.call = this.client.newCall(requestBuilder.build());
        try {
            Response response = this.call.execute();
            this.responseBody = response.body();
            if (!response.isSuccessful() || this.responseBody == null) {
                throw new IOException("Request failed with code: " + response.code());
            }
            this.stream = ContentLengthInputStream.obtain(this.responseBody.byteStream(), this.responseBody.contentLength());
            callback.onDataReady(this.stream);
        } catch (IOException e) {
            callback.onLoadFailed(e);
        }
    }

    public void cleanup() {
        try {
            if (this.stream != null) {
                this.stream.close();
            }
        } catch (IOException e) {
        }
        if (this.responseBody != null) {
            this.responseBody.close();
        }
    }

    public void cancel() {
        Call local = this.call;
        if (local != null) {
            local.cancel();
        }
    }
}
