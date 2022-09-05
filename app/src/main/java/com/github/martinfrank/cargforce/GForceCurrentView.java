package com.github.martinfrank.cargforce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class GForceCurrentView extends View {

    private static final String LOG_TAG = "GForceCurrentView" ;

    private int mWidth;
    private int mHeight;

    private float gravityDx;
    private float gravityDy;

    private float dxOffset = 0;
    private float dyOffset = 0;

    private Drawable background;

    private static final float gEarth = 9.809989f;
    private Paint yPaint = new Paint();
    private Paint xPaint = new Paint();
    private Paint xyPaint = new Paint();

    public GForceCurrentView(Context context) {
        super(context);
        init();
    }

    public GForceCurrentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GForceCurrentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GForceCurrentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        yPaint.setStrokeWidth(4f);
        yPaint.setColor(0xFFFF0000);

        xPaint.setStrokeWidth(4f);
        xPaint.setColor(0xFF0000FF);

        xyPaint.setStrokeWidth(4f);
        xyPaint.setColor(0xFFFF00FF);
    }

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        this.mWidth = newWidth;
        this.mHeight = newHeight;

        background = getResources().getDrawable(R.drawable.car_back_red_icon, null);
        background.setBounds(0, 0, mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        background.draw(canvas);

        float xCenter = mWidth / 2f;
        float yCenter = mHeight / 2f;

        float yValue = ((gEarth-gravityDy) / (gEarth*2) ) * yCenter;
        float yPos = yCenter-yValue;
        canvas.drawLine(xCenter, yCenter, xCenter, yPos, yPaint);

        float xValue = (gravityDx / (gEarth*2) ) * xCenter;
        float xPos = xCenter-xValue;
        canvas.drawLine(xCenter, yCenter, xPos, yCenter, xPaint);

        canvas.drawLine(xCenter, yCenter, xPos, yPos, xyPaint);

    }

    public void setValues(float gravityDx, float gravityDy) {
        this.gravityDx = gravityDx;
        this.gravityDy = gravityDy;
    }

    public void calibrate(){
        this.dxOffset = gravityDx;
        this.dyOffset = gravityDy;
    }

}
