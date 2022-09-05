package com.github.martinfrank.cargforce;

import android.content.Context;
import android.hardware.SensorManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorDataConsumer {

    private static final String LOG_TAG = "MainActivity";
    private GForceDataCollector gForceDataCollector;
    private GForceCurrentView gForceCurrentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gForceDataCollector = new GForceDataCollector(sensorManager, this);

        gForceCurrentView = findViewById(R.id.gforceView);
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
//        Log.d(LOG_TAG, "updateGui: gravityForceData: "+ sensorData.toString());
        gForceCurrentView.setValues(sensorData.gravityDx, sensorData.gravityDy);
        gForceCurrentView.invalidate();

    }
}