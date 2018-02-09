package com.promiseland.ustory;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/2/8.
 */

public class Demo implements Parcelable{

    protected Demo(Parcel in) {
    }

    public static final Creator<Demo> CREATOR = new Creator<Demo>() {
        @Override
        public Demo createFromParcel(Parcel in) {
            return new Demo(in);
        }

        @Override
        public Demo[] newArray(int size) {
            return new Demo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
