package com.github.martinfrank.cargforce;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class GFroceHistoryGraphView extends View {

    private int mWidth;
    private int mHeight;

    public GFroceHistoryGraphView(Context context) {
        super(context);
    }

    public GFroceHistoryGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GFroceHistoryGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GFroceHistoryGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
    }
}
