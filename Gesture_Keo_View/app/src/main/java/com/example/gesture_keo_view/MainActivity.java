package com.example.gesture_keo_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Nếu thay ảnh mặc định kia bằng ảnh thật thì bị lỗi !!!
 */
public class MainActivity extends AppCompatActivity {
    ImageView img;
    int mode = 0; // Chế độ trạng thái
    int drag = 1; // Chế độ đang kéo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView_Girl);

        // Bước 1 : Tạo layoutParam để thay đổi thông tin của img
        // Set lại layout cho imageView thông qua Params
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(250, 250);
        layoutParams.leftMargin = 50;
        layoutParams.topMargin = 50;
        img.setLayoutParams(layoutParams);


        // Bước 2 :
        // Bắt sự kiên
        img.setOnTouchListener(new View.OnTouchListener() {
            RelativeLayout.LayoutParams layoutParams;   // Lấy lại params để set lại khi kéo
            float dx = 0; // dx,dy là vị trí ban đầu khi chạm tay vào so với lề
            float dy = 0;
            float x = 0; // x y là vị trí mới
            float y = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView img = (ImageView) v;
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN: // sự kiện vừa chạm vào
                        layoutParams = (RelativeLayout.LayoutParams) img.getLayoutParams();
                        // Lấy tọa độ hiện tại - đi khoảng cách so với 2 lề
                        dx = event.getRawX() - layoutParams.leftMargin; // vị trí của param - kc so với lề
                        dy = event.getRawY() - layoutParams.topMargin;
                        // Bước 3 Quay trợ lại khai báo mode + drag bên trên rồi tiếp tục đoạn sau

                        mode = drag;
                        break;

                    case MotionEvent.ACTION_MOVE: // sự kiện kéo
                        if(mode == drag){
                            x = event.getRawX();
                            y = event.getRawY();


                            layoutParams.leftMargin = (int) (x - dx); // set lại vị trí cho cách lề ra do ban đầu để cách lề 1 khoảng
                            layoutParams.topMargin = (int) (y - dy);

                            // Cách lề phải, dưới để hình ko bị mất hay thu bé lại
                            layoutParams.rightMargin = 0;
                            layoutParams.bottomMargin = 0;
                            layoutParams.rightMargin = layoutParams.leftMargin + (5 * layoutParams.width);
                            layoutParams.bottomMargin = layoutParams.topMargin + (10 * layoutParams.height);

                            img.setLayoutParams(layoutParams);
                        }

                }
                return true;
            }
        });
    }
}
