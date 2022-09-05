package com.github.martinfrank.cargforce;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GForceDataCollector implements SensorEventListener {
    private final SensorManager sensorManager;
    private final Sensor gForceSensor;
    private final SensorDataConsumer sensorDataConsumer;

    public GForceDataCollector(SensorManager sensorManager, SensorDataConsumer sensorDataConsumer) {
        this.sensorManager = sensorManager;
        this.sensorDataConsumer = sensorDataConsumer;

        //https://developer.android.com/guide/topics/sensors/sensors_motion
        gForceSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, gForceSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        updateGravityData(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
    }

    private void updateGravityData(float dx, float dy, float dz) {
        SensorData sensorData = new SensorData(dx, dy, dz);
        sensorDataConsumer.updateData(sensorData);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int newAccuracy) {
    }
    public void onPause() {
        sensorManager.unregisterListener(this, gForceSensor);
    }
    public void onResume() {
        sensorManager.registerListener(this, gForceSensor, SensorManager.SENSOR_DELAY_UI);
    }
}
