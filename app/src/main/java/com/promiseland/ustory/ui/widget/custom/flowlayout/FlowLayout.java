package com.promiseland.ustory.ui.widget.custom.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.text.TextUtilsCompat;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.View;
import android.view.ViewGroup;

import com.promiseland.ustory.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FlowLayout extends ViewGroup {
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;

    protected List<Integer> mLineHeight = new ArrayList<>();
    protected List<Integer> mLineWidth = new ArrayList<>();
    protected List<List<View>> mAllViews = new ArrayList<>();
    protected List<View> mLineViews = new ArrayList<>();
    private int mGravity;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mGravity = ta.getInt(R.styleable.TagFlowLayout_tag_gravity, LEFT);
        int layoutDirection = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
        if (layoutDirection == LayoutDirection.RTL) {
            if (mGravity == LEFT) {
                mGravity = RIGHT;
            } else {
                mGravity = LEFT;
            }
        }
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0, height = 0; // wrapContent size
        int lineWidth = 0, lineHeight = 0; // 当前行的宽高

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                // 最后一行的结果
                if (i == childCount - 1) {
                    width = Math.max(width, lineWidth);
                    height += lineHeight;
                }
                continue;
            }
            // 测量子控件
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            if (lineWidth + childWidth > width_size - getPaddingLeft() - getPaddingRight()) {
                // 换行
                width = Math.max(width, lineWidth);
                height += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;
            } else {
                // 更新当前行的宽高
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        setMeasuredDimension(
                width_mode == MeasureSpec.AT_MOST ? width + getPaddingLeft() + getPaddingRight() : width_size,
                height_mode == MeasureSpec.AT_MOST ? height + getPaddingTop() + getPaddingBottom() : height_size
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLineWidth.clear();
        mLineHeight.clear();
        mLineViews.clear();
        mAllViews.clear();

        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) continue;

            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            if (lineWidth + measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin > getWidth() - getPaddingLeft() - getPaddingRight()) {
                mLineWidth.add(lineWidth);
                mLineHeight.add(lineHeight);
                mAllViews.add(mLineViews);

                mLineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin;
            }

            lineWidth += measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            lineHeight = Math.max(lineHeight, measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin);
            mLineViews.add(child);
        }
        // 最后一行数据
        mLineWidth.add(lineWidth);
        mLineHeight.add(lineHeight);
        mAllViews.add(mLineViews);

        // layout
        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineNum = mAllViews.size();
        for (int i = 0; i < lineNum; i++) {
            mLineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);
            lineWidth = mLineWidth.get(i);

            switch (mGravity) {
                case LEFT:
                    left = getPaddingLeft();
                    break;
                case CENTER:
                    left = getPaddingLeft() + (getWidth() - lineWidth) / 2;
                    break;
                case RIGHT:
                    //  适配了rtl，需要补偿一个padding值
                    left = getWidth() - (lineWidth + getPaddingLeft()) - getPaddingRight();
                    //  适配了rtl，需要把lineViews里面的数组倒序排
                    Collections.reverse(mLineViews);
                    break;
            }

            for (int j = 0; j < mLineViews.size(); j++) {
                View child = mLineViews.get(j);
                if (child.getVisibility() == GONE) continue;
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
