package com.github.martinfrank.cargforce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class GravityForceCurrentView extends View {

    private static final String LOG_TAG = "GForceCurrentView";
    private float mWidth;
    private float mHeight;

    private float xCenter;
    private float yCenter;

    private float verticalValue;
    private float horizontalValue;

    private Drawable backGround;
    private final Paint verticalPaint = new Paint();
    private final Paint horizontalPaint = new Paint();
    private final Paint xyPaint = new Paint();

    public GravityForceCurrentView(Context context) {
        super(context);
        init();
    }

    public GravityForceCurrentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GravityForceCurrentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GravityForceCurrentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        verticalPaint.setStrokeWidth(25f);
        verticalPaint.setColor(0xFFFF0000);
        verticalPaint.setStrokeCap(Paint.Cap.ROUND);

        horizontalPaint.setStrokeWidth(25f);
        horizontalPaint.setColor(0xFF0000FF);
        horizontalPaint.setStrokeCap(Paint.Cap.ROUND);

        xyPaint.setStrokeWidth(25f);
        xyPaint.setColor(0xFFFF00FF);
        xyPaint.setStrokeCap(Paint.Cap.ROUND);

        backGround = getResources().getDrawable(R.drawable.car_back_red_icon, null);
    }

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        this.mWidth = newWidth;
        this.mHeight = newHeight;
        this.xCenter = mWidth / 2f;
        this.yCenter = mHeight / 2f;
        updateBackgroundSize();
    }

    private void updateBackgroundSize() {
        float imageHeight = backGround.getIntrinsicHeight();
        float imageWidth = backGround.getIntrinsicWidth();

        Rect newSize = new Rect();
        FitToSizeCalculator.calculateOptimizeSize(imageWidth, imageHeight, mWidth, mHeight).round(newSize);
        backGround.setBounds(newSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float verticalAcceleration = ((MainActivity.GRAVITY_ACCELERATION_ON_EARTH - verticalValue) / (MainActivity.GRAVITY_ACCELERATION_ON_EARTH * 2)) * yCenter;
        verticalAcceleration = yCenter - verticalAcceleration;

        float horizontalAcceleration = (horizontalValue / (MainActivity.GRAVITY_ACCELERATION_ON_EARTH * 2)) * xCenter;
        horizontalAcceleration = xCenter - horizontalAcceleration;

        backGround.draw(canvas);
        canvas.drawLine(xCenter, yCenter, horizontalAcceleration, verticalAcceleration, xyPaint);
        canvas.drawLine(xCenter, yCenter, xCenter, verticalAcceleration, verticalPaint);
        canvas.drawLine(xCenter, yCenter, horizontalAcceleration, yCenter, horizontalPaint);
    }

    public void setValues(float verticalValue, float horizontalValue) {
        this.verticalValue = verticalValue;
        this.horizontalValue = horizontalValue;
    }

    public void setBackGroundByDrawableId(int drawableId) {
        backGround = getResources().getDrawable(drawableId, null);
        backGround.setAlpha(128);
    }
}
