package com.example.activity_lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
// Xem hien thi tren logcat nhap AAA

public class MainActivity extends Activity {
    Button btn;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("AAA", "OnStart Main");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("AAA", "OnRestart Main");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("AAA", "OnResume Main");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("AAA", "OnPause Main");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("AAA", "OnStop Main");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("AAA", "OnDestroy Main");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        Log.d("AAA", "OnCreate Main");
    }
}
