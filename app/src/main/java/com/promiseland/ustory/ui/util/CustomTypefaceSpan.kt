package com.promiseland.ustory.ui.util

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

/**
 * Created by Administrator on 2018/3/16.
 */
class CustomTypefaceSpan(private var typeface: Typeface) : MetricAffectingSpan() {

    override fun updateMeasureState(paint: TextPaint?) {
        paint?.typeface = this.typeface
    }

    override fun updateDrawState(paint: TextPaint?) {
        paint?.typeface = this.typeface
    }
}