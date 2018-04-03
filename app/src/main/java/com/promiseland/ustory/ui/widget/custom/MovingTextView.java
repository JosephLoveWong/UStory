package com.promiseland.ustory.ui.widget.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Scroller;

public class MovingTextView extends android.support.v7.widget.AppCompatTextView {

    Scroller mScroller;

    public MovingTextView(Context context) {
        this(context, null);
    }

    public MovingTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller = new Scroller(getContext());
    }

    public void smoothScrollTo(int destX, int destY) {
        int startX = getScrollX();
        int startY = getScrollY();

        int deltaX = destX - startX;
        int deltaY = destY - startY;

        mScroller.startScroll(startX, startY, deltaX, deltaY);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();  // So we draw again
        }
    }
}
