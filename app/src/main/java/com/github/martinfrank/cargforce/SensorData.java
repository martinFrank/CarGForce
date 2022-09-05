package com.github.martinfrank.cargforce;

public class SensorData {
    public final float gravityDx;
    public final float gravityDy;
    public final float gravityDz;

    public SensorData(float dx, float dy, float dz){
        this.gravityDx = dx;
        this.gravityDy = dy;
        this.gravityDz = dz;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "gravityDx=" + gravityDx +
                ", gravityDy=" + gravityDy +
                ", gravityDz=" + gravityDz +
                '}';
    }
}
