package com.promiseland.ustory.ultron;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface Ultron {

    @Retention(RetentionPolicy.SOURCE)
    public @interface TestGroup {
    }

    @DELETE("users/me/likes/{id}/")
    Call<Void> deleteLike(@Path("id") String str);
}
