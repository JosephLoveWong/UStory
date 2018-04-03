package com.promiseland.ustory.ui.widget.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.promiseland.ustory.R;

public class GooView extends View {
    private static final int[] EXPLODE_RES = {R.drawable.explode1, R.drawable.explode2, R.drawable.explode3, R.drawable.explode4, R.drawable.explode5};
    private static final int mDefaultWidth = 100;
    private static final int mDefaultHeight = 100;

    private Path mPath;
    private Paint mPaint;
    private PointF mDragPoint, mStickyPoint, mControlPoint; // 拖拽圆、固定圆、二阶贝塞尔曲线的控制点
    private float mDragDistance;//拖拽的距离
    private float maxDistance = 300;
    private int mDragRadius = 40;//拖拽圆的半径
    private int mDefaultRadius = 30;//固定小圆的默认半径
    private int mStickRadius = mDefaultRadius;//固定圆的半径

    private State mState = State.STATE_INIT;
    private Bitmap[] mExplodeBitmaps;
    private onDragStatusListener mOnDragStatusListener;

    private int mWidth;
    private int mHeight;

    public GooView(Context context) {
        this(context, null);
    }

    public GooView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GooView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPath = new Path();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        mDragPoint = new PointF();
        mStickyPoint = new PointF();

        mExplodeBitmaps = new Bitmap[EXPLODE_RES.length];
        for (int i = 0; i < EXPLODE_RES.length; i++) {
            mExplodeBitmaps[i] = BitmapFactory.decodeResource(getResources(), EXPLODE_RES[i]);
        }
    }

    /**
     * 设置固定点和拖拽点的位置信息
     *
     * @param stickyX
     * @param stickyY
     * @param touchX
     * @param touchY
     */
    public void setStickyPoint(float stickyX, float stickyY, float touchX, float touchY) {
        mDragPoint.set(touchX, touchY);
        mStickyPoint.set(stickyX, stickyY);

        mDragDistance = MathHelper.getTwoPointDistance(mDragPoint, mStickyPoint);
        if (isInsideRange()) {
            //如果拖拽距离小于规定最大距离，则固定的圆应该越来越小，这样看着才符合实际
            mStickRadius = (int) (mDefaultRadius - mDragDistance / 10) < 10 ? 10 : (int) (mDefaultRadius - mDragDistance / 10);
            mState = State.STATE_DRAG;
        } else {
            mState = State.STATE_INIT;
        }
    }

    public void setDragViewLocation(float touchX, float touchY) {
        mDragPoint.set(touchX, touchY);
        mDragDistance = MathHelper.getTwoPointDistance(mDragPoint, mStickyPoint);

        if (mState == State.STATE_DRAG) {
            if (isInsideRange()) {
                mStickRadius = (int) (mDefaultRadius - mDragDistance / 10) < 10 ? 10 : (int) (mDefaultRadius - mDragDistance / 10);
            } else {
                mState = State.STATE_MOVE;
                if (mOnDragStatusListener != null) {
                    mOnDragStatusListener.onMove();
                }
            }
        }
        invalidate();
    }

    /**
     * 拖拽红点reset动画
     */
    private void startResetAnimator() {
        if (mState == State.STATE_DRAG) {
            ValueAnimator animator = ValueAnimator.ofObject(
                    new PointEvaluator(), new PointF(mDragPoint.x, mDragPoint.y), new PointF(mStickyPoint.x, mStickyPoint.y));
            animator.setDuration(500);
            animator.setInterpolator(new TimeInterpolator() {
                @Override
                public float getInterpolation(float input) {
                    //http://inloop.github.io/interpolator/
                    float f = 0.571429f;
                    return (float) (Math.pow(2, -4 * input) * Math.sin((input - f / 4) * (2 * Math.PI) / f) + 1);
                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    PointF curPoint = (PointF) animation.getAnimatedValue();
                    mDragPoint.set(curPoint.x, curPoint.y);
                    invalidate();
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mOnDragStatusListener != null) {
                        mOnDragStatusListener.onDrag();
                    }
                }
            });
            animator.start();
        } else if (mState == State.STATE_MOVE) {
            //先拖拽到范围之外 在拖拽回范围之内
            mDragPoint.set(mStickyPoint.x, mStickyPoint.y);
            invalidate();
            if (mOnDragStatusListener != null) {
                mOnDragStatusListener.onRestore();
            }
        }

    }

    private int explodeIndex;

    /**
     * 爆炸动画
     */
    private void startExplodeAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, EXPLODE_RES.length);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                explodeIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnDragStatusListener != null) {
                    mOnDragStatusListener.onDismiss();
                }
            }
        });
        animator.start();
    }

    public void setDragUp() {
        if (mState == State.STATE_DRAG && isInsideRange()) {
            //拖拽状态且在范围之内
            startResetAnimator();
        } else if (mState == State.STATE_MOVE) {
            if (isInsideRange()) {
                //在范围之内 需要RESET
                startResetAnimator();
            } else {
                //在范围之外 消失动画
                mState = State.STATE_DISMISS;
                startExplodeAnim();
            }
        }
    }

    private boolean isInsideRange() {
        return mDragDistance <= maxDistance;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        PointF centerPoint = new PointF(mWidth / 2, mHeight / 2);
        mStickyPoint.set(centerPoint);
        mDragPoint.set(centerPoint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isInsideRange() && mState == State.STATE_DRAG) {
            mPaint.setColor(Color.RED);
            // 绘制静态圆
            canvas.drawCircle(mStickyPoint.x, mStickyPoint.y, mStickRadius, mPaint);

            Float lineSlope = MathHelper.getLineSlope(mStickyPoint, mDragPoint);
            PointF[] stickyIntersectionPoints = MathHelper.getIntersectionPoints(mStickyPoint, mStickRadius, lineSlope);
            PointF[] dragIntersectionPoints = MathHelper.getIntersectionPoints(mDragPoint, mDragRadius, lineSlope);
            // 取得数据点
            PointF dataPoint1Bezier1 = stickyIntersectionPoints[0];
            PointF dataPoint2Bezier1 = dragIntersectionPoints[0];
            PointF dataPoint1Bezier2 = stickyIntersectionPoints[1];
            PointF dataPoint2Bezier2 = dragIntersectionPoints[1];
            // 取得控制点
            mControlPoint = MathHelper.getMiddlePoint(mDragPoint, mStickyPoint);

            // 绘制贝塞尔曲线
            mPath.reset();
            mPath.moveTo(dataPoint1Bezier1.x, dataPoint1Bezier1.y);
            mPath.quadTo(mControlPoint.x, mControlPoint.y, dataPoint2Bezier1.x, dataPoint2Bezier1.y);
            mPath.lineTo(dataPoint2Bezier2.x, dataPoint2Bezier2.y);
            mPath.quadTo(mControlPoint.x, mControlPoint.y, dataPoint1Bezier2.x, dataPoint1Bezier2.y);
            mPath.lineTo(dataPoint1Bezier1.x, dataPoint1Bezier1.y);
            canvas.drawPath(mPath, mPaint);
        }

        // 绘制拖拽圆
        if (mState != State.STATE_DISMISS) {
            canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);
        }

        //绘制小红点消失时的爆炸动画
        if (mState == State.STATE_DISMISS && explodeIndex < EXPLODE_RES.length) {
            canvas.drawBitmap(mExplodeBitmaps[explodeIndex], mDragPoint.x - mDragRadius / 2, mDragPoint.y - mDragRadius / 2, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float rawX = event.getX();
        float rawY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                setStickyPoint(mStickyPoint.x, mStickyPoint.y, rawX, rawY);
                break;
            case MotionEvent.ACTION_MOVE:
                setDragViewLocation(rawX, rawY);
                break;
            case MotionEvent.ACTION_UP:
                setDragUp();
                break;
        }
        return true;
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

    public void setOnDragStatusListener(onDragStatusListener onDragStatusListener) {
        mOnDragStatusListener = onDragStatusListener;
    }

    public interface onDragStatusListener {
        void onDrag();

        void onMove();

        void onRestore();

        void onDismiss();
    }

    public class PointEvaluator implements TypeEvaluator<PointF> {
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float x = startValue.x + fraction * (endValue.x - startValue.x);
            float y = startValue.y + fraction * (endValue.y - startValue.y);
            return new PointF(x, y);
        }
    }

    public enum State {
        STATE_INIT, STATE_DRAG, STATE_MOVE, STATE_DISMISS
    }

    static class MathHelper {
        /**
         * 获得两点之间的直线距离
         *
         * @param p1 PointF
         * @param p2 PointF
         * @return 两点之间的直线距离
         */
        public static float getTwoPointDistance(PointF p1, PointF p2) {
            return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        }

        /**
         * 根据两个点(x1,y1)(x2,y2)的坐标算出斜率
         *
         * @param x1 x1
         * @param x2 x2
         * @param y1 y1
         * @param y2 y2
         * @return 斜率
         */
        public static Float getLineSlope(float x1, float y1, float x2, float y2) {
            return getLineSlope(new PointF(x1, y1), new PointF(x2, y2));
        }

        /**
         * 根据传入的两点得到斜率
         *
         * @param p1 PointF
         * @param p2 PointF
         * @return 返回斜率
         */
        public static Float getLineSlope(PointF p1, PointF p2) {
            if (p2.x - p1.x == 0) return null;
            return (p2.y - p1.y) / (p2.x - p1.x);
        }

        /**
         * Get middle point between p1 and p2.
         * 获得两点连线的中点
         *
         * @param p1 PointF
         * @param p2 PointF
         * @return 中点
         */
        public static PointF getMiddlePoint(PointF p1, PointF p2) {
            return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
        }

        /**
         * 获取 通过指定圆心，斜率为lineK的直线与圆的交点。
         *
         * @param pMiddle The circle center point.
         * @param radius  The circle radius.
         * @param lineK   The slope of line which cross the pMiddle.
         * @return 两个焦点的集合
         */
        public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Float lineK) {
            PointF[] points = new PointF[2];

            float radian, xOffset, yOffset;
            if (lineK != null) {
                radian = (float) Math.atan(lineK);
                xOffset = (float) (Math.sin(radian) * radius);
                yOffset = (float) (Math.cos(radian) * radius);
            } else {
                xOffset = radius;
                yOffset = 0;
            }
            points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
            points[1] = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);

            return points;
        }
    }
}
