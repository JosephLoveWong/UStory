package com.promiseland.ustory.ui.widget.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.promiseland.ustory.R;

public class LeafLoadingView extends View {
    // 淡白色
    private static final int WHITE_COLOR = 0xfffde399;
    // 橙色
    private static final int ORANGE_COLOR = 0xffffa800;

    // 边框宽度
    private static final int BORDER_WIDTH = 20;

    private Bitmap mLeafBitmap;
    private int mLeafWidth, mLeafHeight;

    private Bitmap mFanBitmap;
    private int mFanWidth, mFanHeight;

    private Rect mFanSrcRect, mFanDstRect, mBackgroundRect;
    private RectF mBgArcRect;

    private int mWidth;
    private int mHeight;

    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;

    private int mBackgroundWidth;
    private int mBackgroundHeight;
    private int mBackgroundRadius;

    public void setCurrentProgress(int currentProgress) {
        mCurrentProgress = currentProgress;
        invalidate();
    }

    public int getCurrentProgress() {
        return mCurrentProgress;
    }

    private int mCurrentProgress = 0;

    private Paint mPaint;

    public LeafLoadingView(Context context) {
        this(context, null);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBitmap();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private void initBitmap() {
        mLeafBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf);
        mLeafWidth = mLeafBitmap.getWidth();
        mLeafHeight = mLeafBitmap.getHeight();

        mFanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fan);
        mFanWidth = mFanBitmap.getWidth();
        mFanHeight = mFanBitmap.getHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();
        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();

        mWidth = w;
        mHeight = h;

        mBackgroundWidth = mWidth - mPaddingLeft - mPaddingRight;
        mBackgroundHeight = mHeight - mPaddingTop - mPaddingBottom;
        mBackgroundRadius = Math.min(mBackgroundWidth, mBackgroundHeight) / 2;
        mBgArcRect = new RectF(-mBackgroundRadius, -mBackgroundRadius, mBackgroundRadius, mBackgroundRadius);

        int innerMargin = (mBackgroundHeight - mFanHeight) / 2;
        mFanSrcRect = new Rect(0, 0, mFanWidth, mFanHeight);
        mFanDstRect = new Rect(
                mWidth - mPaddingRight - innerMargin - mFanWidth,
                mPaddingTop + innerMargin,
                mWidth - mPaddingRight - innerMargin,
                mPaddingTop + innerMargin + mFanHeight);

        mBackgroundRect = new Rect(mPaddingLeft, mPaddingTop, mPaddingLeft + mBackgroundWidth, mPaddingTop + mBackgroundHeight);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredSize_width;
        int measuredSize_height;

        int specMode_width = MeasureSpec.getMode(widthMeasureSpec);
        int specSize_width = MeasureSpec.getSize(widthMeasureSpec);

        int specMode_height = MeasureSpec.getMode(heightMeasureSpec);
        int specSize_height = MeasureSpec.getSize(heightMeasureSpec);

        int wrap_width = mFanWidth + 2 * BORDER_WIDTH + getPaddingLeft() + getPaddingRight();
        int wrap_height = mFanHeight + 2 * BORDER_WIDTH + getPaddingTop() + getPaddingBottom();

        if (specMode_width == MeasureSpec.AT_MOST && specMode_height == MeasureSpec.AT_MOST) {
            measuredSize_width = wrap_width;
            measuredSize_height = wrap_height;
            setMeasuredDimension(measuredSize_width, measuredSize_height);
        } else if (specMode_width == MeasureSpec.AT_MOST) {
            measuredSize_width = wrap_width;
            measuredSize_height = specSize_height;
            setMeasuredDimension(measuredSize_width, measuredSize_height);
        } else if (specMode_height == MeasureSpec.AT_MOST) {
            measuredSize_width = specSize_width;
            measuredSize_height = wrap_height;
            setMeasuredDimension(measuredSize_width, measuredSize_height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawProcess(canvas);
        drawFan(canvas);
    }

    /**
     * 绘制进度
     *
     * @param canvas
     */
    private void drawProcess(Canvas canvas) {
        canvas.save();
        mPaint.setColor(ORANGE_COLOR);
        mPaint.setStyle(Paint.Style.FILL);

        int progress = mBackgroundWidth * mCurrentProgress / 100;
        canvas.translate(mPaddingLeft + mBackgroundRadius, mPaddingTop + mBackgroundRadius);
        if (progress <= mBackgroundRadius) {
            float asin = (float) Math.toDegrees(Math.asin((mBackgroundRadius - progress) / (float) mBackgroundRadius));
            canvas.drawArc(mBgArcRect, 90 + asin, 180 - 2 * asin, false, mPaint);
        } else if (progress <= mBackgroundWidth - mBackgroundRadius) {
            canvas.drawArc(mBgArcRect, 90, 180, true, mPaint);
            RectF bgRect = new RectF(0, -mBackgroundRadius, progress - mBackgroundRadius, mBackgroundRadius);
            canvas.drawRect(bgRect, mPaint);
        } else {
            canvas.drawArc(mBgArcRect, 90, 180, true, mPaint);
            RectF bgRect = new RectF(0, -mBackgroundRadius, mBackgroundWidth - 2 * mBackgroundRadius, mBackgroundRadius);
            canvas.drawRect(bgRect, mPaint);
        }
        canvas.restore();
    }

    /**
     * 绘制风扇
     *
     * @param canvas
     */
    private void drawFan(Canvas canvas) {
        canvas.save();
        // 绘制后面的描边
        canvas.translate(mPaddingLeft + mBackgroundWidth - mBackgroundRadius, mPaddingTop + mBackgroundRadius);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(mBgArcRect, 0, 360, true, mPaint);
        // 绘制后面的填充
        mPaint.setColor(ORANGE_COLOR);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(mBgArcRect, 0, 360, true, mPaint);
        canvas.restore();
        canvas.drawBitmap(mFanBitmap, mFanSrcRect, mFanDstRect, mPaint);
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    void drawBackground(Canvas canvas) {
        canvas.save();
        canvas.translate(mPaddingLeft + mBackgroundRadius, mPaddingTop + mBackgroundRadius);
        // 绘制前面的圆弧
        mPaint.setColor(WHITE_COLOR);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(BORDER_WIDTH);
        canvas.drawArc(mBgArcRect, 90f, 180f, true, mPaint);
        // 绘制中间的矩形
        RectF bgRect = new RectF(0, -mBackgroundRadius, mBackgroundWidth - 2 * mBackgroundRadius, mBackgroundRadius);
        canvas.drawRect(bgRect, mPaint);
        canvas.restore();
    }

    void drawProgressAndLeafs(Canvas canvas) {

    }
}
