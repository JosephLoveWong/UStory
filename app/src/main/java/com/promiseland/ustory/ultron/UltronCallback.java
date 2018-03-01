package com.promiseland.ustory.ultron;

public interface UltronCallback<T> {
    void onConnectionError(int... iArr);

    void onServerError(int... iArr);

    void onSuccess(T t);
}
