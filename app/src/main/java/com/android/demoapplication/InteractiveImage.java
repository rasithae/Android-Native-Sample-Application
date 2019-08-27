package com.android.demoapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by kalaydzh on 6/26/2015.
 *
 * <p>Simple view that displays an image that could be:
 * <p>1) dragged inside the view with a pan gesture (tap and
 * hold your finger on the image and then move it over the screen)
 * <p>2) scaled with a pinch gesture (tap the screen with two fingers
 * and then move them outwards or inwards)
 * <p>3) rotated with two fingers (tap the screen with two fingers and
 * simultaneously orbit both fingers around the center point)
 * <p>You can declare this view in xml files in the following way:
 *
 *     <pre>{@code <InteractiveImage
 *          android:id="@+id/image"
 *          android:layout_width="match_parent"
 *          android:layout_height="match_parent"
 *          interactive-image:imgSrc="@drawable/the_image"
 *          interactive-image:imgHeight="64dp"
 *          interactive-image:imgWidth="64dp"/>}</pre>
 *
 * <p>Where <b>imgSrc</b> is a reference to the drawable resource
 * that will be displayed. <b>imgHeight</b> and <b>imgWidth</b>
 * set the size of the image in <b>dp</b> or <b>px</b> values or
 * references to dimension resources.
 *
 * <p>Also the following custom xml namespace needs to be defined in the root layout:
 * <pre>{@code xmlns:interactive-image="http://schemas.android.com/apk/res-auto"}</pre>
 *
 */
public class InteractiveImage extends View {

    private static final int INVALID_POINTER_ID = -1;

    private DragGestureDetector dragDetector;
    private float dragPosX;
    private float dragPosY;

    private ScaleGestureDetector scaleDetector;
    private float scaleFactor = 1.f;

    private RotationGestureDetector rotationDetector;
    private float rotationAngle = 0.f;

    private Drawable imgSource;

    private Rect imgBounds = new Rect();

    private int pivotX;
    private int pivotY;

    public InteractiveImage(Context context) {
        this(context, null, 0);
    }

    public InteractiveImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InteractiveImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        readAttrs(context, attrs);

        dragDetector = new DragGestureDetector(new DragGestureDetector.OnDragGestureListener() {
            @Override
            public void OnDrag(DragGestureDetector dragDetector) {
                dragPosX = dragDetector.posX;
                dragPosY = dragDetector.posY;
            }
        });

        scaleDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                scaleFactor *= detector.getScaleFactor();
                // Don't let the object get too small or too large.
                scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f));
                return true;
            }
        });

        rotationDetector = new RotationGestureDetector(new RotationGestureDetector.OnRotationGestureListener() {
            @Override
            public void OnRotation(RotationGestureDetector rotationDetector) {
                rotationAngle = rotationDetector.getAngle();
            }
        });
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        dragDetector.onTouchEvent(ev);
        scaleDetector.onTouchEvent(ev);
        rotationDetector.onTouchEvent(ev);
        invalidate();
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(dragPosX, dragPosY);
        setPivotPoint();
        canvas.scale(scaleFactor, scaleFactor, pivotX, pivotY);
        canvas.rotate(rotationAngle, pivotX, pivotY);
        imgSource.draw(canvas);
        canvas.restore();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.InteractiveImage,
                0,
                0);

        try {
            // User should set the image that this view shows otherwise a black
            // rectangle will be shown.
            Drawable userDefinedSrc = a.getDrawable(R.styleable.InteractiveImage_imgSrc);
            imgSource = userDefinedSrc != null ? userDefinedSrc : new ColorDrawable(0xFF000000);
            // Show full size image if there are no height and width set.
            int imgHeight = a.getDimensionPixelSize(R.styleable.InteractiveImage_imgHeight,
                    imgSource.getIntrinsicHeight());
            int imgWidth = a.getDimensionPixelSize(R.styleable.InteractiveImage_imgWidth,
                    imgSource.getIntrinsicWidth());
            imgSource.setBounds(0, 0, imgWidth, imgHeight);
        } finally {
            a.recycle();
        }
    }

    private void setPivotPoint() {
        imgSource.copyBounds(imgBounds);
        pivotX = imgBounds.centerX();
        pivotY = imgBounds.centerY();
    }

    static class DragGestureDetector {

        public static interface OnDragGestureListener {
            public void OnDrag(DragGestureDetector dragDetector);
        }

        private OnDragGestureListener listener;

        // The ‘active pointer’ is the one currently moving our object.
        private int activePointerId = INVALID_POINTER_ID;

        private float posX;
        private float posY;

        private float lastTouchX;
        private float lastTouchY;

        public DragGestureDetector(OnDragGestureListener listener){
            this.listener = listener;
        }

        public boolean onTouchEvent(MotionEvent ev) {
            final int action = ev.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {
                    final float x = ev.getX();
                    final float y = ev.getY();

                    lastTouchX = x;
                    lastTouchY = y;
                    activePointerId = ev.getPointerId(0);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    final int pointerIndex = ev.findPointerIndex(activePointerId);
                    final float x = ev.getX(pointerIndex);
                    final float y = ev.getY(pointerIndex);

                    // Only move if other gesture detector like Scale
                    // or Rotate isn't processing a gesture that is when
                    // we have only one active pointer.
                    if (ev.getPointerCount() == 1) {
                        final float dx = x - lastTouchX;
                        final float dy = y - lastTouchY;

                        posX += dx;
                        posY += dy;

                        if (listener != null) {
                            listener.OnDrag(this);
                        }
                    }

                    lastTouchX = x;
                    lastTouchY = y;

                    break;
                }
                case MotionEvent.ACTION_UP: {
                    activePointerId = INVALID_POINTER_ID;
                    break;
                }
                case MotionEvent.ACTION_CANCEL: {
                    activePointerId = INVALID_POINTER_ID;
                    break;
                }
                case MotionEvent.ACTION_POINTER_UP: {
                    final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    final int pointerId = ev.getPointerId(pointerIndex);
                    if (pointerId == activePointerId) {
                        // This was our active pointer going up. Choose a new
                        // active pointer and adjust accordingly.
                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                        lastTouchX = ev.getX(newPointerIndex);
                        lastTouchY = ev.getY(newPointerIndex);
                        activePointerId = ev.getPointerId(newPointerIndex);
                    }
                    break;
                }
            }

            return true;
        }
    }

    static class RotationGestureDetector {

        public static interface OnRotationGestureListener {
            public void OnRotation(RotationGestureDetector rotationDetector);
        }

        private float p1X, p1Y, p2X, p2Y;
        private float latestP1X, latestP1Y, latestP2X, latestP2Y;
        private int p1Idx, p2Idx;
        private float angle;
        // Holds previous state, so that we do not start next rotation from 0 again.
        private float initialAngle;

        private OnRotationGestureListener listener;

        public float getAngle() {
            return angle;
        }

        public RotationGestureDetector(OnRotationGestureListener listener){
            this.listener = listener;
            p1Idx = INVALID_POINTER_ID;
            p2Idx = INVALID_POINTER_ID;
        }

        public boolean onTouchEvent(MotionEvent event){
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    p1Idx = event.getPointerId(event.getActionIndex());
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    p2Idx = event.getPointerId(event.getActionIndex());
                    p1X = event.getX(event.findPointerIndex(p1Idx));
                    p1Y = event.getY(event.findPointerIndex(p1Idx));
                    p2X = event.getX(event.findPointerIndex(p2Idx));
                    p2Y = event.getY(event.findPointerIndex(p2Idx));
                    initialAngle = angle;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(p1Idx != INVALID_POINTER_ID && p2Idx != INVALID_POINTER_ID){
                        latestP1X = event.getX(event.findPointerIndex(p1Idx));
                        latestP1Y = event.getY(event.findPointerIndex(p1Idx));
                        latestP2X = event.getX(event.findPointerIndex(p2Idx));
                        latestP2Y = event.getY(event.findPointerIndex(p2Idx));

                        angle = initialAngle + angleBetweenLines(p2X, p2Y, p1X, p1Y, latestP2X, latestP2Y, latestP1X, latestP1Y);
                        angle %= 360;

                        if (listener != null) {
                            listener.OnRotation(this);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    p1Idx = INVALID_POINTER_ID;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    p2Idx = INVALID_POINTER_ID;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    p1Idx = INVALID_POINTER_ID;
                    p2Idx = INVALID_POINTER_ID;
                    break;
            }
            return true;
        }

        private float angleBetweenLines (float fX, float fY,
                                         float sX, float sY,
                                         float nfX, float nfY,
                                         float nsX, float nsY)
        {
            float angle1 = (float) Math.atan2( (fY - sY), (fX - sX) );
            float angle2 = (float) Math.atan2( (nfY - nsY), (nfX - nsX) );
            float resultAngle = ((float)Math.toDegrees(angle1 - angle2)) % 360;
            if (angle < -180.f) angle += 360.0f;
            if (angle > 180.f) angle -= 360.0f;
            return -resultAngle;
        }
    }
}
