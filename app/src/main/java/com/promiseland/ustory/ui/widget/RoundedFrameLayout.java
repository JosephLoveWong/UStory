package com.promiseland.ustory.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.promiseland.ustory.R;
import com.promiseland.ustory.base.util.APILevelHelper;

public class RoundedFrameLayout extends FrameLayout {
    private float mCornerRadius;
    private final Path mPath;

    public RoundedFrameLayout(Context context) {
        super(context);
        this.mPath = new Path();
    }

    public RoundedFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPath = new Path();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedFrameLayout, 0, 0);
        this.mCornerRadius = (float) typedArray.getDimensionPixelSize(R.styleable.RoundedFrameLayout_cornerRadius, 0);
        typedArray.recycle();
    }

    @TargetApi(21)
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (APILevelHelper.isAPILevelMinimal(21)) {
            this.mPath.reset();
            this.mPath.addRoundRect(0.0f, 0.0f, (float) w, (float) h, this.mCornerRadius, this.mCornerRadius, Direction.CW);
            this.mPath.close();
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        if (APILevelHelper.isAPILevelMinimal(21)) {
            int save = canvas.save();
            canvas.clipPath(this.mPath);
            super.dispatchDraw(canvas);
            canvas.restoreToCount(save);
            return;
        }
        super.dispatchDraw(canvas);
    }
}
