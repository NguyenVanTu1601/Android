package com.example.activity_swipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private float x1,y1,x2,y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            // Chạm tay
            case MotionEvent.ACTION_DOWN:
                x1= event.getX();
                y1 = event.getY();
                Toast.makeText(this, x1 + " - " + y1, Toast.LENGTH_SHORT).show();
                break;
            // Nhấc tay
            case MotionEvent.ACTION_UP :
                x2 = event.getX();
                y2 = event.getY();
                Toast.makeText(this, x2 + " - " + y2, Toast.LENGTH_SHORT).show();
                if(x1 < x2 && Math.abs((x2-x1)) > 10.0){
                    startActivity(new Intent(MainActivity.this,SwipeActivity.class));
                }
                break;
        }
        return false;

    }
}