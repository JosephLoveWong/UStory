package com.promiseland.ustory.ultron.base

/**
 * Created by joseph on 2018/3/11.
 */
class Image
(private val url: String, private val source: String?, private val width: Int?, private val height: Int?) {

    fun getUrl(): String {
        return this.url
    }

    fun getSource(): String? {
        return this.source
    }

    fun getWidth(): Int? {
        return this.width
    }

    fun getHeight(): Int? {
        return this.height
    }
}