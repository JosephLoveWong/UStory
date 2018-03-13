package com.promiseland.ustory.ui.util.glide;

import android.annotation.SuppressLint;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class SizeInterceptor implements Interceptor {

    public static class FileSizeExceedsLimitException extends IOException {
        public FileSizeExceedsLimitException(String msg) {
            super(msg);
        }
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String fileUrl = request.url().toString();
        if (fileUrl.endsWith(".gif")) {
            try {
                checkFileSize(fileUrl, chain.proceed(chain.request().newBuilder().head().build()));
            } catch (IOException e) {
                if (e instanceof FileSizeExceedsLimitException) {
                    throw e;
                }
                Timber.w(e, "cannot do head request for url= %s", fileUrl);
            }
        }
        return checkFileSize(fileUrl, chain.proceed(request));
    }

    @SuppressLint({"DefaultLocale"})
    private Response checkFileSize(String fileUrl, Response response) throws IOException {
        if (!response.isSuccessful() || !fileUrl.endsWith(".gif") || response.body().contentLength() <= 1572864) {
            return response;
        }
        throw new FileSizeExceedsLimitException(String.format("The file exceeds the file limit of 1.5 MB: %s currently: %d", new Object[]{fileUrl, Long.valueOf(response.body().contentLength())}));
    }
}
