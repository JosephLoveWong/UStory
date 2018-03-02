package com.promiseland.ustory.ui.util.viewpager;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.promiseland.ustory.ui.util.formatter.HtmlFormatter;

public class PageIndicatorListener implements OnPageChangeListener {
    private final TextView[] dots;
    private final int mActiveColor;
    private final int mInactiveColor;

    public PageIndicatorListener(LinearLayout dotsLayout, int pageCount, int activeColor, int inactiveColor, int initialPosition) {
        this.dots = new TextView[pageCount];
        this.mInactiveColor = inactiveColor;
        this.mActiveColor = activeColor;
        dotsLayout.removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            this.dots[i] = new TextView(dotsLayout.getContext());
            this.dots[i].setText(HtmlFormatter.fromHtml("&#8226;"));
            this.dots[i].setTextSize(40.0f);
            this.dots[i].setPadding(8, 0, 8, 0);
            this.dots[i].setTextColor(this.mInactiveColor);
            dotsLayout.addView(this.dots[i]);
        }
        this.dots[initialPosition].setTextColor(this.mActiveColor);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
        int i = 0;
        while (i < this.dots.length) {
            this.dots[i].setTextColor(i == position ? this.mActiveColor : this.mInactiveColor);
            i++;
        }
    }

    public void onPageScrollStateChanged(int state) {
    }
}
