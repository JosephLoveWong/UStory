package com.promiseland.ustory.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class RecyclerViewForVerticalScrollChildren extends RecyclerView {
    private float lastX;
    private float lastY;
    private boolean mShouldPassVerticalScrollsToChildren = false;
    private float xDistance;
    private float yDistance;

    public RecyclerViewForVerticalScrollChildren(Context context) {
        super(context);
    }

    public RecyclerViewForVerticalScrollChildren(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewForVerticalScrollChildren(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void enableVerticalScrollEventPassThrough() {
        this.mShouldPassVerticalScrollsToChildren = true;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return shouldHandleTouchEvent(ev) && super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent e) {
        return shouldHandleTouchEvent(e) && super.onTouchEvent(e);
    }

    public boolean shouldHandleTouchEvent(MotionEvent ev) {
        if (this.mShouldPassVerticalScrollsToChildren) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    this.yDistance = 0.0f;
                    this.xDistance = 0.0f;
                    this.lastX = ev.getX();
                    this.lastY = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float curX = ev.getX();
                    float curY = ev.getY();
                    this.xDistance += Math.abs(curX - this.lastX);
                    this.yDistance += Math.abs(curY - this.lastY);
                    this.lastX = curX;
                    this.lastY = curY;
                    if (this.xDistance >= this.yDistance) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }
}
