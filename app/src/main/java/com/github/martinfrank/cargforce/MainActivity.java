package com.github.martinfrank.cargforce;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorDataConsumer {

    public static final float GRAVITY_ACCELERATION_ON_EARTH = 9.80665f;
    private GForceDataCollector gForceDataCollector;
    private GravityForceCurrentView backGravityForceCurrentView;
    private GravityForceCurrentView sideGravityForceCurrentView;
    private GravityForceHistoryGraphView backGravityForceHistoryGraphView;
    private GravityForceHistoryGraphView sideGravityForceHistoryGraphView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gForceDataCollector = new GForceDataCollector(sensorManager, this);

        backGravityForceCurrentView = findViewById(R.id.back_g_force_current_view);
        backGravityForceCurrentView.setBackGroundByDrawableId(R.drawable.car_back_red_icon);
        sideGravityForceCurrentView = findViewById(R.id.side_g_force_current_view);
        backGravityForceCurrentView.setBackGroundByDrawableId(R.drawable.car_left_red_icon);
        backGravityForceHistoryGraphView = findViewById(R.id.back_g_force_history_view);
        sideGravityForceHistoryGraphView = findViewById(R.id.side_g_force_history_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gForceDataCollector.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gForceDataCollector.onResume();
    }

    @Override
    public void updateData(SensorData sensorData) {
        if (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) {
            backGravityForceCurrentView.setValues(sensorData.gravityDy, sensorData.gravityDz);
            sideGravityForceCurrentView.setValues(sensorData.gravityDy, sensorData.gravityDx);
            backGravityForceHistoryGraphView.setValues(sensorData.gravityDy, sensorData.gravityDz);
            sideGravityForceHistoryGraphView.setValues(sensorData.gravityDy, sensorData.gravityDx);
        } else {
            backGravityForceCurrentView.setValues(sensorData.gravityDx, sensorData.gravityDz);
            sideGravityForceCurrentView.setValues(sensorData.gravityDx, sensorData.gravityDy);
            backGravityForceHistoryGraphView.setValues(sensorData.gravityDx, sensorData.gravityDz);
            sideGravityForceHistoryGraphView.setValues(sensorData.gravityDx, sensorData.gravityDy);
        }
        backGravityForceCurrentView.invalidate();
        sideGravityForceCurrentView.invalidate();
        backGravityForceHistoryGraphView.invalidate();
        sideGravityForceHistoryGraphView.invalidate();
    }

    public void calibrate(){
        gForceDataCollector.calibrate();
    }
}