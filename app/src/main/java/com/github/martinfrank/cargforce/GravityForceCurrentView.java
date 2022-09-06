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

    private float verticalValue;
    private float horizontalValue;

    private Drawable backGround;
    private Paint verticalPaint = new Paint();
    private Paint horizontalPaint = new Paint();
    private Paint xyPaint = new Paint();

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
        verticalPaint.setStrokeJoin(Paint.Join.ROUND);

        horizontalPaint.setStrokeWidth(25f);
        horizontalPaint.setColor(0xFF0000FF);
        horizontalPaint.setStrokeJoin(Paint.Join.ROUND);

        xyPaint.setStrokeWidth(25f);
        xyPaint.setColor(0xFFFF00FF);
        xyPaint.setStrokeJoin(Paint.Join.ROUND);

        backGround = getResources().getDrawable(R.drawable.car_back_red_icon, null);

//
    }

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        this.mWidth = newWidth;
        this.mHeight = newHeight;

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

        backGround.draw(canvas);

        float xCenter = mWidth / 2f;
        float yCenter = mHeight / 2f;

        float verticalAcceleration = ((MainActivity.GRAVITY_ACCELERATION_ON_EARTH - verticalValue) / (MainActivity.GRAVITY_ACCELERATION_ON_EARTH * 2)) * yCenter;
        verticalAcceleration = yCenter - verticalAcceleration;
        canvas.drawLine(xCenter, yCenter, xCenter, verticalAcceleration, verticalPaint);

        float horizontalAcceleration = (horizontalValue / (MainActivity.GRAVITY_ACCELERATION_ON_EARTH * 2)) * xCenter;
        horizontalAcceleration = xCenter - horizontalAcceleration;
        canvas.drawLine(xCenter, yCenter, horizontalAcceleration, yCenter, horizontalPaint);

        canvas.drawLine(xCenter, yCenter, horizontalAcceleration, verticalAcceleration, xyPaint);

    }

    public void setValues(float verticalValue, float horizontalValue) {
        this.verticalValue = verticalValue;
        this.horizontalValue = horizontalValue;
    }


    public void setBackGroundByDrawableId(int drawableId) {
        backGround = getResources().getDrawable(drawableId, null);
        //backGround.setAlpha(128);
    }
}
