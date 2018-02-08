package com.promiseland.ustory.base.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ActivityData implements Parcelable {
    public static final Creator<ActivityData> CREATOR = new Creator<ActivityData>() {
        public ActivityData createFromParcel(Parcel source) {
            return new ActivityData(source);
        }

        public ActivityData[] newArray(int size) {
            return new ActivityData[size];
        }
    };
    public int mDataSubType;
    public int mDataType;
    public String mStringData;

    public ActivityData() {
        this.mDataType = 0;
        this.mDataSubType = 0;
        this.mStringData = "";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mDataType);
        dest.writeInt(this.mDataSubType);
        dest.writeString(this.mStringData);
    }

    protected ActivityData(Parcel in) {
        this.mDataType = in.readInt();
        this.mDataSubType = in.readInt();
        this.mStringData = in.readString();
    }

    public boolean isInvalid() {
        return this.mDataType == 0;
    }
}
