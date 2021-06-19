package com.atul.mangatain.view;


import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;

import com.atul.mangatain.MTConstants;

public class PageImageView extends AppCompatImageView {
    private MTConstants.PageViewMode mViewMode;
    private boolean mHaveFrame = false;
    private boolean mSkipScaling = false;
    private boolean mTranslateRightEdge = false;
    private View.OnTouchListener mOuterTouchListener;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mDragGestureDetector;
    private OverScroller mScroller;
    private float mMinScale, mMaxScale;
    private float mOriginalScale;
    private float[] m = new float[9];
    private Matrix mMatrix;

    public PageImageView(Context context) {
        super(context);
        init();
    }

    public PageImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void setViewMode(MTConstants.PageViewMode viewMode) {
        mViewMode = viewMode;
        mSkipScaling = false;
        requestLayout();
        invalidate();
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        mHaveFrame = true;
        scale();
        return changed;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mSkipScaling = false;
        scale();
    }

    private void init() {
        mMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        setImageMatrix(mMatrix);

        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new PrivateScaleDetector());
        mDragGestureDetector = new GestureDetector(getContext(), new PrivateDragListener());
        super.setOnTouchListener((v, event) -> {
            mScaleGestureDetector.onTouchEvent(event);
            mDragGestureDetector.onTouchEvent(event);
            if (mOuterTouchListener != null) mOuterTouchListener.onTouch(v, event);
            return true;
        });

        mScroller = new OverScroller(getContext());
        mScroller.setFriction(ViewConfiguration.getScrollFriction() * 2);
        mViewMode = MTConstants.PageViewMode.ASPECT_FIT;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        mOuterTouchListener = l;
    }

    public void setTranslateToRightEdge(boolean translate) {
        mTranslateRightEdge = translate;
    }

    private void scale() {
        Drawable drawable = getDrawable();
        if (drawable == null || !mHaveFrame || mSkipScaling) return;

        int dwidth = drawable.getIntrinsicWidth();
        int dheight = drawable.getIntrinsicHeight();

        int vwidth = getWidth();
        int vheight = getHeight();

        if (mViewMode == MTConstants.PageViewMode.ASPECT_FILL) {
            float scale;
            float dx = 0;

            if (dwidth * vheight > vwidth * dheight) {
                scale = (float) vheight / (float) dheight;
                if (mTranslateRightEdge)
                    dx = vwidth - dwidth * scale;
            } else {
                scale = (float) vwidth / (float) dwidth;
            }

            mMatrix.setScale(scale, scale);
            mMatrix.postTranslate((int) (dx + 0.5f), 0);
        }
        else if (mViewMode == MTConstants.PageViewMode.ASPECT_FIT) {
            RectF mTempSrc = new RectF(0, 0, dwidth, dheight);
            RectF mTempDst = new RectF(0, 0, vwidth, vheight);

            mMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.CENTER);
        }
        else if (mViewMode == MTConstants.PageViewMode.FIT_WIDTH) {
            float widthScale = (float)getWidth()/drawable.getIntrinsicWidth();
            mMatrix.setScale(widthScale, widthScale);
            mMatrix.postTranslate(0, 0);
        }

        // calculate min/max scale
        float heightRatio = (float)vheight / dheight;
        float w = dwidth * heightRatio;
        if (w < vwidth) {
            mMinScale = vheight * 0.75f / dheight;
            mMaxScale = Math.max(dwidth, vwidth) * 1.5f / dwidth;
        }
        else {
            mMinScale = vwidth * 0.75f / dwidth;
            mMaxScale = Math.max(dheight, vheight) * 1.5f / dheight;
        }
        setImageMatrix(mMatrix);
        mOriginalScale = getCurrentScale();
        mSkipScaling = true;
    }

    private class PrivateScaleDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mMatrix.getValues(m);

            float scale = m[Matrix.MSCALE_X];
            float scaleFactor = detector.getScaleFactor();
            float scaleNew = scale * scaleFactor;
            boolean scalable = true;

            if (scaleFactor > 1 && mMaxScale - scaleNew < 0) {
                scaleFactor = mMaxScale / scale;
                scalable = false;
            }
            else if (scaleFactor < 1 && mMinScale - scaleNew > 0) {
                scaleFactor = mMinScale / scale;
                scalable = false;
            }

            mMatrix.postScale(
                    scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());
            setImageMatrix(mMatrix);

            return scalable;
        }
    }

    private class PrivateDragListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            mScroller.forceFinished(true);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mMatrix.postTranslate(-distanceX, -distanceY);
            setImageMatrix(mMatrix);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Point imageSize = computeCurrentImageSize();
            Point offset = computeCurrentOffset();

            int minX = -imageSize.x - PageImageView.this.getWidth();
            int minY = -imageSize.y - PageImageView.this.getHeight();
            int maxX = 0;
            int maxY = 0;

            if (offset.x > 0) {
                minX = offset.x;
                maxX = offset.x;
            }
            if (offset.y > 0) {
                minY = offset.y;
                maxY = offset.y;
            }

            mScroller.fling(
                    offset.x, offset.y,
                    (int) velocityX, (int) velocityY,
                    minX, maxX, minY, maxY);
            ViewCompat.postInvalidateOnAnimation(PageImageView.this);
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            if (e.getAction() == MotionEvent.ACTION_UP) {
                float scale = (mOriginalScale == getCurrentScale()) ? mMaxScale : mOriginalScale;
                zoomAnimated(e, scale);
            }
            return true;
        }
    }

    private void zoomAnimated(MotionEvent e, float scale) {
        post(new ZoomAnimation(e.getX(), e.getY(), scale));
    }

    @Override
    public void computeScroll() {
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            int curX = mScroller.getCurrX();
            int curY = mScroller.getCurrY();

            mMatrix.getValues(m);
            m[Matrix.MTRANS_X] = curX;
            m[Matrix.MTRANS_Y] = curY;
            mMatrix.setValues(m);
            setImageMatrix(mMatrix);
            ViewCompat.postInvalidateOnAnimation(this);
        }
        super.computeScroll();
    }

    private float getCurrentScale() {
        mMatrix.getValues(m);
        return m[Matrix.MSCALE_X];
    }

    private Point computeCurrentImageSize() {
        final Point size = new Point();
        Drawable d = getDrawable();
        if (d != null) {
            mMatrix.getValues(m);

            float scale = m[Matrix.MSCALE_X];
            float width = d.getIntrinsicWidth() * scale;
            float height = d.getIntrinsicHeight() * scale;

            size.set((int)width, (int)height);

            return size;
        }

        size.set(0, 0);
        return size;
    }

    private Point computeCurrentOffset() {
        final Point offset = new Point();

        mMatrix.getValues(m);
        float transX = m[Matrix.MTRANS_X];
        float transY = m[Matrix.MTRANS_Y];

        offset.set((int)transX, (int)transY);

        return offset;
    }

    @Override
    public void setImageMatrix(Matrix matrix) {
        super.setImageMatrix(fixMatrix(matrix));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            postInvalidate();
        }
    }

    private Matrix fixMatrix(Matrix matrix) {
        if (getDrawable() == null)
            return matrix;

        matrix.getValues(m);

        Point imageSize = computeCurrentImageSize();

        int imageWidth = imageSize.x;
        int imageHeight = imageSize.y;
        int maxTransX = imageWidth - getWidth();
        int maxTransY = imageHeight - getHeight();

        if (imageWidth > getWidth())
            m[Matrix.MTRANS_X] = Math.min(0, Math.max(m[Matrix.MTRANS_X], -maxTransX));
        else
            m[Matrix.MTRANS_X] = getWidth() / 2 - imageWidth / 2;
        if (imageHeight > getHeight())
            m[Matrix.MTRANS_Y] = Math.min(0, Math.max(m[Matrix.MTRANS_Y], -maxTransY));
        else
            m[Matrix.MTRANS_Y] = getHeight() / 2 - imageHeight / 2;

        matrix.setValues(m);
        return matrix;
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        if (getDrawable() == null)
            return false;

        float imageWidth = computeCurrentImageSize().x;
        float offsetX = computeCurrentOffset().x;

        if (offsetX >= 0 && direction < 0) {
            return false;
        }
        else if (Math.abs(offsetX) + getWidth() >= imageWidth && direction > 0) {
            return false;
        }
        return true;
    }

    private class ZoomAnimation implements Runnable {
        public final static int ZOOM_DURATION = 200;
        float mX;
        float mY;
        float mScale;
        Interpolator mInterpolator;
        float mStartScale;
        long mStartTime;

        ZoomAnimation(float x, float y, float scale) {
            mMatrix.getValues(m);
            mX = x;
            mY = y;
            mScale = scale;

            mInterpolator = new AccelerateDecelerateInterpolator();
            mStartScale = getCurrentScale();
            mStartTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
            float t = (float)(System.currentTimeMillis() - mStartTime) / ZOOM_DURATION;
            float interpolateRatio = mInterpolator.getInterpolation(t);
            t = Math.min(t, 1f);

            mMatrix.getValues(m);
            float newScale = mStartScale + interpolateRatio * (mScale - mStartScale);
            float newScaleFactor = newScale / m[Matrix.MSCALE_X];

            mMatrix.postScale(newScaleFactor, newScaleFactor, mX, mY);
            setImageMatrix(mMatrix);

            if (t < 1f) {
                post(this);
            }
            else {
                // set exact scale
                mMatrix.getValues(m);
                mMatrix.setScale(mScale, mScale);
                mMatrix.postTranslate(m[Matrix.MTRANS_X], m[Matrix.MTRANS_Y]);
                setImageMatrix(mMatrix);
            }
        }
    }
}