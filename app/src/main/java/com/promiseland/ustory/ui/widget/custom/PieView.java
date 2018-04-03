package com.promiseland.ustory.ui.widget.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PieView extends View {
    private static int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080, 0xFFE6B800, 0xFF7CFC00};
    private static final int mDefaultWidth = 400;
    private static final int mDefaultHeight = 400;

    private int mStartAngle = 0;
    private List<PieData> mPieDatas = new ArrayList<>();
    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
        invalidate();
    }

    public void setPieDatas(List<PieData> pieDatas) {
        mPieDatas = pieDatas;
        initData();
        invalidate();
    }

    private void initData() {
        if (mPieDatas == null || mPieDatas.size() == 0) return;

        int total = 0;
        for (int i = 0; i < mPieDatas.size(); i++) {
            PieData pieData = mPieDatas.get(i);
            pieData.color = mColors[i % mColors.length];
            total += pieData.value;
        }

        for (int i = 0; i < mPieDatas.size(); i++) {
            PieData pieData = mPieDatas.get(i);
            pieData.percentage = pieData.value * 100 / total;
            pieData.angle = pieData.value * 360 / total;
        }
    }

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mStartAngle = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    // TODO 未考虑padding
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);

        RectF pieRect = new RectF(-mWidth / 2, -mHeight / 2, mWidth / 2, mHeight / 2);
        for (int i = 0; i < mPieDatas.size(); i++) {
            PieData pieData = mPieDatas.get(i);
            mPaint.setColor(pieData.color);
            canvas.drawArc(pieRect, mStartAngle, pieData.angle, true, mPaint);
            mStartAngle += pieData.angle;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);

        int measuredWidth;
        int measuredHeight;

        if (width_mode == MeasureSpec.AT_MOST && height_mode == MeasureSpec.AT_MOST) {
            measuredWidth = mDefaultWidth;
            measuredHeight = mDefaultHeight;
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (width_mode == MeasureSpec.AT_MOST) {
            measuredWidth = mDefaultWidth;
            measuredHeight = height_size;
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (height_mode == MeasureSpec.AT_MOST) {
            measuredWidth = width_size;
            measuredHeight = mDefaultHeight;
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public static class PieData {
        // 用户关心数据
        public String name;        // 名字
        public float value;        // 数值
        public float percentage;   // 百分比

        // 非用户关心数据
        public int color = 0;      // 颜色
        public float angle = 0;    // 角度

        public PieData(@NonNull String name, @NonNull float value) {
            this.name = name;
            this.value = value;
        }
    }
}
