package com.promiseland.ustory.ui.util

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.text.SpannableString
import android.text.Spanned.SPAN_POINT_MARK_MASK

/**
 * Created by Administrator on 2018/3/16.
 */
object SpannableStringHelper {

    fun createSpannableStringWithTypeface(context: Context, font: Int, text: CharSequence): SpannableString {
        return createSpannableStringWithTypeface(context, font, text, 0, 0)
    }

    fun createSpannableStringWithTypeface(typeface: Typeface, text: CharSequence): SpannableString {
        return createSpannableStringWithTypeface(typeface, text, 0, 0)
    }

    private fun createSpannableStringWithTypeface(context: Context, font: Int, text: CharSequence, start: Int, end: Int): SpannableString {
        val typeface = ResourcesCompat.getFont(context, font) ?: return SpannableString(text)
        return createSpannableStringWithTypeface(typeface, text, start, end)
    }

    private fun createSpannableStringWithTypeface(typeface: Typeface, text: CharSequence, start: Int, end: Int): SpannableString {
        val result = SpannableString(text)
        result.setSpan(CustomTypefaceSpan(typeface), start, end, SPAN_POINT_MARK_MASK)
        return result
    }
}