package com.github.martinfrank.cargforce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class GravityForceHistoryGraphView extends View {
    private float mWidth;
    private float mHeight;
    private int ringBufferSize = 256;

    private float[] horizontalBuffer = new float[ringBufferSize];
    private float[] verticalBuffer = new float[ringBufferSize];
    private int ringIndex = 0;

    private Paint verticalPaint = new Paint();
    private Paint horizontalPaint = new Paint();
    private Paint decoPaint = new Paint();

    public GravityForceHistoryGraphView(Context context) {
        super(context);
        init();
    }

    public GravityForceHistoryGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GravityForceHistoryGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GravityForceHistoryGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        verticalPaint.setColor(0xFFFF0000);
        verticalPaint.setStyle(Paint.Style.FILL);

        horizontalPaint.setColor(0xFF0000FF);
        horizontalPaint.setStyle(Paint.Style.FILL);

        decoPaint.setColor(0xFF000000);
        decoPaint.setStyle(Paint.Style.STROKE);
        decoPaint.setStrokeWidth(3f);
    }

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        this.mWidth = newWidth;
        this.mHeight = newHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sliceWidth = mWidth / ringBufferSize;
        float yCenter = mHeight / 2f;

        for(int i = 0; i < ringBufferSize; i ++){
            float dx = horizontalBuffer[mapIndex(i)];
            float dy = verticalBuffer[mapIndex(i)];


            float verticalValue = ((MainActivity.GRAVITY_ACCELERATION_ON_EARTH - dx) / MainActivity.GRAVITY_ACCELERATION_ON_EARTH ) * yCenter;
            verticalValue = yCenter-verticalValue;
            canvas.drawRect(i * sliceWidth, yCenter, (i+1)*sliceWidth, verticalValue, verticalPaint);

            float horizontalValue = (dy / (MainActivity.GRAVITY_ACCELERATION_ON_EARTH) ) * yCenter;
            horizontalValue = yCenter-horizontalValue;
            canvas.drawRect(i * sliceWidth, yCenter, (i+1)*sliceWidth, horizontalValue, horizontalPaint);
        }
        canvas.drawLine(0, yCenter, mWidth, yCenter, decoPaint);
    }
    private int mapIndex(int i){
        int index = i + ringIndex;
        if(index >= ringBufferSize){
            index = index - ringBufferSize;
        }
        return index;
    }

    public void setValues(float gravityDx, float gravityDy) {
        moveRingIndex();
        updateValues(gravityDx, gravityDy);
    }

    private void updateValues(float gravityDx, float gravityDy) {
        horizontalBuffer[ringIndex] = gravityDx;
        verticalBuffer[ringIndex] = gravityDy;
    }

    private void moveRingIndex() {
        ringIndex = ringIndex + 1;
        if(ringIndex == ringBufferSize){
            ringIndex = 0;
        }
    }
}
