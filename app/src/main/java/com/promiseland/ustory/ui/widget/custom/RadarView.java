package com.promiseland.ustory.ui.widget.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 未考虑padding
 */
public class RadarView extends View {
    private static final int COLOR_BULE = 0x800000FF;
    private static final int COLOR_GREY = Color.GRAY;

    private static final int mDefaultWidth = 600;
    private static final int mDefaultHeight = 600;
    private static final int DIMENSION = 6;
    private static final float ANGLE = (float) (2 * Math.PI / DIMENSION);

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mCenterX;
    private int mCenterY;

    private int mRadius;
    private int mDistance;

    private List<RadarData> mRadarDatas = new ArrayList<>();

    public void setRadarDatas(List<RadarData> radarDatas) {
        mRadarDatas = radarDatas;
        invalidate();
    }

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);

        // fake data
        Random random = new Random();
        for (int i = 0; i < DIMENSION; i++) {
            RadarData radarData = new RadarData("name " + i, RadarLevel.values()[random.nextInt(RadarLevel.size())]);
            mRadarDatas.add(radarData);
        }

        setRadarDatas(mRadarDatas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackground(canvas);
        drawLines(canvas);
        drawValues(canvas);
    }

    private void drawValues(Canvas canvas) {
        mPaint.setColor(COLOR_BULE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        Path path = new Path();
        float curAngle;
        float curRadius;
        for (int j = 0; j < DIMENSION; j++) {
            curAngle = ANGLE * (j + 1);
            RadarData radarData = mRadarDatas.get(j);
            curRadius = radarData.level.value * mDistance;
            float dstX = (float) (curRadius * Math.cos(curAngle));
            float dstY = (float) (curRadius * Math.sin(curAngle));
            if(j == 0) {
                path.moveTo(dstX, dstY);
            } else {
                path.lineTo(dstX, dstY);
            }
        }
        path.close();
        canvas.drawPath(path, mPaint);
        canvas.restore();
    }

    private void drawLines(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        for (int i = 0; i < DIMENSION; i++) {
            canvas.drawLine(0,0, mRadius, 0, mPaint);
            canvas.rotate((float) Math.toDegrees(ANGLE));
        }
        canvas.restore();
    }

    private void drawBackground(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        Path path = new Path();
        float curRadius;
        float curAngle;
        for (int i = 0; i < RadarLevel.size(); i++) {
            curRadius = mDistance * (i + 1);
            path.reset();
            path.moveTo(curRadius, 0);
            for (int j = 0; j < DIMENSION; j++) {
                curAngle = ANGLE * (j + 1);
                float dstX = (float) (curRadius * Math.cos(curAngle));
                float dstY = (float) (curRadius * Math.sin(curAngle));
                path.lineTo(dstX, dstY);
            }
            canvas.drawPath(path, mPaint);
        }

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        mRadius = Math.min(mCenterX, mCenterY);

        mDistance = mRadius / RadarLevel.values().length;
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

    public static class RadarData {
        public String name;
        public RadarLevel level;

        public RadarData(String name, RadarLevel level) {
            this.name = name;
            this.level = level;
        }
    }

    public enum RadarLevel {
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

        private int value;
        RadarLevel(int value) {
            this.value = value;
        }

        public static int size() {
            return RadarLevel.values().length;
        }
    }
}
