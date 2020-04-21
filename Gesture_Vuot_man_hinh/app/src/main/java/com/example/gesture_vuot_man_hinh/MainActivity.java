package com.example.gesture_vuot_man_hinh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    int[] hinh = {R.drawable.image,R.drawable.image1,R.drawable.image2};
    int position = 0;
    GestureDetector gestureDetector;
    int khoang_cach = 100; // nếu > thì thành công
    int van_toc = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView);
        img.setImageResource(hinh[position]);
        gestureDetector = new GestureDetector(this, new gestureDetector());
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true; // để trả về action
            }
        });
    }
    class gestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // e1 là vị trí lần đầu chạm, e2 là vị trí chạm cuối sau khi kéo
            // velocityX, Y là tốc độ vuốt theo 2 trục x, y
            // Bắt sự kiện kéo từ trái sang phải
            if(e2.getX()  - e1.getX() > khoang_cach && Math.abs(velocityX) > van_toc){
                Toast.makeText(MainActivity.this, "Vuốt từ trái sang phải", Toast.LENGTH_SHORT).show();
                position--;
                if(position < 0) position = hinh.length - 1;
                img.setImageResource(hinh[position]);
            }

            // Vuốt phải sang trái
            if(e1.getX()  - e2.getX() > khoang_cach && Math.abs(velocityX) > van_toc){
                Toast.makeText(MainActivity.this, "Vuốt từ phải sang trái", Toast.LENGTH_SHORT).show();
                position++;
                if(position > hinh.length - 1) position = 0;
                img.setImageResource(hinh[position]);
            }

            /*

            // Kéo từ trên xuống dưới
            if(e2.getY()  - e1.getY() > khoang_cach && Math.abs(velocityY) > van_toc){
                Toast.makeText(MainActivity.this, "Vuốt từ trên xuống", Toast.LENGTH_SHORT).show();
            }

            // Kéo từ dưói lên
            if(e1.getY()  - e2.getY() > khoang_cach && Math.abs(velocityY) > van_toc){
                Toast.makeText(MainActivity.this, "Vuốt từ dưới lên", Toast.LENGTH_SHORT).show();
            }

             */
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
