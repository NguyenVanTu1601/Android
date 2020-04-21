package com.example.gesture_phongto_thunho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Hầu hết các bài trong này nếu return false phải sửa thành return true mới thực hiện được
 */
public class MainActivity extends AppCompatActivity {
    ImageView img;
    ScaleGestureDetector scaleGestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView);
        img.setImageResource(R.drawable.image1);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            float scale = 1.0f;// tỉ lệ bình thường
            float onScaleStart = 0;
            float onScaleEnd = 0;
            @Override
            public boolean onScale(ScaleGestureDetector detector) { //
                scale *= detector.getScaleFactor(); // hàm detector... là lấy só lần phóng
                img.setScaleX(scale);
                img.setScaleY(scale); // Phóng cả chiều ngang và cao
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) { // Bắt đầu scale
                onScaleStart = scale; // Khi bắt đầu phóng lấy giá trị phóng từ lần trước đó
                Toast.makeText(MainActivity.this, "Bắt đầu phóng từ " + onScaleStart, Toast.LENGTH_SHORT).show();

                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) { // Kết thức scale (thu phóng)
                onScaleEnd = scale; // Sau khi phóng kết thúc
                Toast.makeText(MainActivity.this, "Kết thúc phóng " + scale, Toast.LENGTH_SHORT).show();
            }
        });

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }
}
