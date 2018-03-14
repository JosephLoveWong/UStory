package com.promiseland.ustory.ultron.okhttp;

import android.os.Build.VERSION;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import timber.log.Timber;

public final class OkHttpHelper {
    public static final void overwriteSslBelowLollipop(okhttp3.OkHttpClient.Builder builder) {
        if (shouldOverwriteSSL()) {
            SSLSocketFactory tlsSocketFactory = getTLSSocketFactory();
            X509TrustManager trustManager = getTrustManager();
            if (tlsSocketFactory != null && trustManager != null) {
                builder.sslSocketFactory(tlsSocketFactory, trustManager);
            }
        }
    }

    public static final SSLSocketFactory getTLSSocketFactory() {
        TLSSocketFactory tLSSocketFactory;
        try {
            tLSSocketFactory = new TLSSocketFactory();
        } catch (Exception e) {
            Timber.w(e, "could not initialize TlsSocketFactory");
            tLSSocketFactory = null;
        }
        return tLSSocketFactory;
    }

    public static final X509TrustManager getTrustManager() {
        if (shouldOverwriteSSL()) {
            return TLSTrustManagerFactory.getTrustManagerFactory();
        }
        return null;
    }

    private static final boolean shouldOverwriteSSL() {
        return VERSION.SDK_INT < 21;
    }
}
