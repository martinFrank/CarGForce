package com.github.martinfrank.cargforce;

import android.graphics.RectF;

public class FitToSizeCalculator {

    public static RectF calculateOptimizeSize(float imageWidth, float imageHeight, float boundaryWidth, float boundaryHeight) {
        float newWidth = imageWidth;
        float newHeight = imageHeight;

        if (imageWidth > boundaryWidth) {
            newWidth = boundaryWidth;
            newHeight = (newWidth * imageHeight) / imageWidth;
        }

        if (newHeight > boundaryHeight) {
            newHeight = boundaryHeight;
            newWidth = (newHeight * imageWidth) / imageHeight;
        }

        float newXPos = (boundaryWidth - newWidth) / 2f;
        float newYPos = (boundaryHeight - newHeight) / 2f;

        return new RectF(newXPos, newYPos, newXPos + newWidth, newYPos + newHeight);
    }
}
