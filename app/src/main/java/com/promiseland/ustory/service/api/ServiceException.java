package com.promiseland.ustory.service.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ServiceException extends Exception {
    public final int mCause;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Cause {
    }

    public ServiceException(int cause) {
        this.mCause = cause;
    }
}
