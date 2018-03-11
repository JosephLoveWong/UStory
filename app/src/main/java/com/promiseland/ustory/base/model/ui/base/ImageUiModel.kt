package com.promiseland.ustory.base.model.ui.base

import com.promiseland.ustory.base.util.FieldHelper
import com.promiseland.ustory.ultron.base.Image

/**
 * Created by joseph on 2018/3/11.
 */
class ImageUiModel(private val image: Image?, private val subPath: String?, private val fileName: String?) {

    constructor(image: Image) : this(image, null, null)

    constructor(subPath: String, fileName: String) : this(null, subPath, fileName)

    fun getImage(): Image? {
        return this.image
    }

    fun getSubPath(): String? {
        return this.subPath
    }

    fun getFileName(): String? {
        return this.fileName
    }

    private fun isValid(): Boolean {
        return !((this.image == null || FieldHelper.isEmpty(this.image.getUrl() as CharSequence)) && (FieldHelper.isEmpty(this.subPath as CharSequence) || FieldHelper.isEmpty(this.fileName as CharSequence)))
    }

    fun isLocalImage(): Boolean {
        return isValid() && this.image == null
    }

}