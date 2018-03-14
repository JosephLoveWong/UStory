package com.promiseland.ustory.base.model.ui.base

import android.os.Parcel
import android.os.Parcelable
import com.promiseland.ustory.base.util.FieldHelper
import com.promiseland.ustory.ultron.base.Image

/**
 * Created by joseph on 2018/3/11.
 */
data class ImageUiModel(val image: Image?, val subPath: String?, val fileName: String?):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Image::class.java.classLoader),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(image, flags)
        parcel.writeString(subPath)
        parcel.writeString(fileName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageUiModel> {
        override fun createFromParcel(parcel: Parcel): ImageUiModel {
            return ImageUiModel(parcel)
        }

        override fun newArray(size: Int): Array<ImageUiModel?> {
            return arrayOfNulls(size)
        }
    }

    fun isValid(): Boolean {
        return !((this.image == null || FieldHelper.isEmpty(this.image.url as CharSequence)) && (FieldHelper.isEmpty(this.subPath as CharSequence) || FieldHelper.isEmpty(this.fileName as CharSequence)))
    }

    fun isLocalImage(): Boolean {
        return isValid() && this.image == null
    }
}