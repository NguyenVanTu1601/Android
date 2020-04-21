package com.example.activity_lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends Activity {
    Button btn2;
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("AAA", "OnStart Main2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("AAA", "OnRestart Main2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("AAA", "OnResume Main2");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("AAA", "OnPause Main2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("AAA", "OnStop Main2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("AAA", "OnDestroy Main2");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Log.d("AAA","onCreate Main2");
    }
}
