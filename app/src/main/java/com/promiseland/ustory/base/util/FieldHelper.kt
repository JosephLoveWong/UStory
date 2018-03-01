package com.promiseland.ustory.base.util

import java.util.*
import kotlin.jvm.internal.Intrinsics

object FieldHelper {
    fun isEmpty(collection: Collection<*>?): Boolean {
        return collection?.isEmpty() ?: true
    }

    fun isNotEmpty(collection: Collection<*>?): Boolean {
        return !isEmpty(collection)
    }

    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.isEmpty()
    }

    fun equals(a: CharSequence?, b: CharSequence?): Boolean {
        if (a === b) {
            return true
        }
        if (a == null || b == null || a.length != b.length) {
            return false
        }
        var areEqual: Boolean = if (a is String && b is String) {
            Intrinsics.areEqual(a, b)
        } else {
            (0..a.length)
                    .filter { a[it] != b[it] }
                    .forEach { return false }
            true
        }
        return areEqual
    }

    fun isLargerZero(value: Int?): Boolean {
        return value != null && value.toInt() > 0
    }

    fun getPrimitive(value: Double?): Double {
        return value ?: 0.0
    }

    fun getPrimitive(value: Int?): Int {
        return value ?: 0
    }

    fun getPrimitive(value: Float?): Float {
        return value ?: 0.0f
    }

    fun hasPosition(list: List<*>?, position: Int): Boolean {
        return if (position < 0) {
            false
        } else position < list?.size ?: 0
    }

    fun getListSize(list: List<*>?): Int {
        return list?.size ?: 0
    }

    fun <T, R> mapList(list: List<T>?, mapFunction: Function1<T, R>): List<R>? {
        if (list == null) {
            return null
        }
        val mappedList = ArrayList<R>(list.size)
        list.mapTo(mappedList) { it -> mapFunction.invoke(it) }
        return mappedList
    }

    fun isLastPosition(list: List<*>, position: Int): Boolean {
        return position == list.size - 1
    }

    fun containsKeys(map: Map<*, *>?, vararg keys: Any): Boolean {
        if (map == null) {
            return false
        }
        return keys.any { it -> map.containsKey(it) }
    }
}
