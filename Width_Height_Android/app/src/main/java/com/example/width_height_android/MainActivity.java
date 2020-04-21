package com.example.width_height_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int width = AndroidDeviceHelper.getWithScreen(this);
        final int height = AndroidDeviceHelper.getHeightScreen(this);
        findViewById(R.id.buttonShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Cao : " + height + "\n" +
                        "Rộng : " + width, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
