package com.promiseland.ustory.ui.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ListViewEx extends ListView {
    private HorizontalScrollViewEx mHorizontalScrollViewEx;
    private float mLastX;
    private float mLastY;

    public ListViewEx(Context context) {
        this(context, null);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        float deltaX = x - mLastX;
        float deltaY = y - mLastY;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(mHorizontalScrollViewEx != null) {
                    mHorizontalScrollViewEx.requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (mHorizontalScrollViewEx != null) {
                        mHorizontalScrollViewEx.requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }

    public void setHorizontalScrollViewEx(HorizontalScrollViewEx horizontalScrollViewEx) {
        mHorizontalScrollViewEx = horizontalScrollViewEx;
    }
}
