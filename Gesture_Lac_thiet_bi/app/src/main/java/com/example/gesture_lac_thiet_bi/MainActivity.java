package com.example.gesture_lac_thiet_bi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Thêm vào manifes yêu cầu truy cập cảm biến gia tốc
 * Tạo class đếm số lần lắc
 * https://www.youtube.com/watch?v=SvS6SkQer_4&list=PLzrVYRai0riSX1KUdO-ow6nBQ_QNYKLG9&index=7
 */
public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensor;
    ShakeDetector shakeDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bước cuối
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        shakeDetector = new ShakeDetector();

        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                Toast.makeText(MainActivity.this, "Lắc lắc" + count, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector,sensor,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
    }
}
