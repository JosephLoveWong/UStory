package com.promiseland.ustory.ui.util.formatter;

import android.annotation.SuppressLint;
import android.text.Html;
import android.text.Spanned;

import com.promiseland.ustory.base.util.APILevelHelper;

public class HtmlFormatter {
    @SuppressLint({"NewApi"})
    public static Spanned fromHtml(String initialText) {
        if (APILevelHelper.isAPILevelMinimal(24)) {
            return Html.fromHtml(initialText, 0);
        }
        return Html.fromHtml(initialText);
    }
}
