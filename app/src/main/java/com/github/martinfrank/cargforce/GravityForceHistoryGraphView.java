package com.github.martinfrank.cargforce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class GravityForceHistoryGraphView extends View {
    private float mWidth;
    private float sliceWidth;
    private float yCenter;
    private final int ringBufferSize = 256;
    private final RingBuffer<Float> horizontalRingBuffer = new RingBuffer<>(ringBufferSize);
    private final RingBuffer<Float> verticalRingBuffer = new RingBuffer<>(ringBufferSize);
    private final Paint verticalPaint = new Paint();
    private final Paint horizontalPaint = new Paint();
    private final Paint decoPaint = new Paint();

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
        verticalPaint.setColor(0x77FF0000);
        verticalPaint.setStyle(Paint.Style.FILL);

        horizontalPaint.setColor(0x770000FF);
        horizontalPaint.setStyle(Paint.Style.FILL);

        decoPaint.setColor(0xFF000000);
        decoPaint.setStyle(Paint.Style.STROKE);
        decoPaint.setStrokeWidth(3f);
    }

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        this.mWidth = newWidth;
        this.sliceWidth = mWidth / ringBufferSize;
        this.yCenter = newHeight / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < ringBufferSize; i++) {
            float dx = getFloat(i, horizontalRingBuffer, MainActivity.GRAVITY_ACCELERATION_ON_EARTH);
            float dy = getFloat(i, verticalRingBuffer, 0);

            float verticalValue = ((MainActivity.GRAVITY_ACCELERATION_ON_EARTH - dx) / MainActivity.GRAVITY_ACCELERATION_ON_EARTH) * yCenter;
            verticalValue = yCenter - verticalValue;

            float horizontalValue = (dy / (MainActivity.GRAVITY_ACCELERATION_ON_EARTH)) * yCenter;
            horizontalValue = yCenter - horizontalValue;

            canvas.drawRect(i * sliceWidth, yCenter, (i + 1) * sliceWidth, verticalValue, verticalPaint);
            canvas.drawRect(i * sliceWidth, yCenter, (i + 1) * sliceWidth, horizontalValue, horizontalPaint);
        }
        canvas.drawLine(0, yCenter, mWidth, yCenter, decoPaint);
    }

    private float getFloat(int i, RingBuffer<Float> buffer, float defaultValue) {
        Float value = buffer.get(i);
        return value == null ? defaultValue : value;
    }

    public void setValues(float gravityDx, float gravityDy) {
        horizontalRingBuffer.add(gravityDx);
        verticalRingBuffer.add(gravityDy);
    }

}
