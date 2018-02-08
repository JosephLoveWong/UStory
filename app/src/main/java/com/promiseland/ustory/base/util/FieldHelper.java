package com.promiseland.ustory.base.util;

import java.util.Collection;
import java.util.List;

public final class FieldHelper {
    public static final boolean isEmpty(Collection<?> collection) {
        return collection != null ? collection.isEmpty() : true;
    }

    public static final boolean isNotEmpty(Collection<?> collection) {
        return !(collection != null ? collection.isEmpty() : true);
    }

    public static final boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static final boolean isLargerZero(Integer value) {
        return value != null && value.intValue() > 0;
    }

    public static final double getPrimitive(Double value) {
        return value != null ? value.doubleValue() : 0.0d;
    }

    public static final int getPrimitive(Integer value) {
        return value != null ? value.intValue() : 0;
    }

    public static final float getPrimitive(Float value) {
        return value != null ? value.floatValue() : 0.0f;
    }

    public static final boolean hasPosition(List<?> list, int position) {
        if (position < 0) {
            return false;
        }
        return position < (list != null ? list.size() : 0);
    }

    public static final int getListSize(List<?> list) {
        return list != null ? list.size() : 0;
    }
}
