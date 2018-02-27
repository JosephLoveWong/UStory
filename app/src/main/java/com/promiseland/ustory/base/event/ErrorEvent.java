package com.promiseland.ustory.base.event;

import com.promiseland.ustory.base.util.FlagHelper;

import retrofit2.HttpException;

// TODO errorCode
public class ErrorEvent extends MessageEvent {
    public final int errorCode;

    public static int error(int... codes) {
        return FlagHelper.createFlags(codes);
    }

    public static int getErrorCodeForThrowable(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return 1;
        }
        return 2;
    }

    public boolean hasError(int... codes) {
        for (int code : codes) {
            if (!FlagHelper.hasFlag(this.errorCode, code)) {
                return false;
            }
        }
        return true;
    }

    public ErrorEvent(int msgId) {
        this(msgId, 128);
    }

    public ErrorEvent(int msgId, int errorCode) {
        super(msgId);
        this.errorCode = errorCode;
    }
}
