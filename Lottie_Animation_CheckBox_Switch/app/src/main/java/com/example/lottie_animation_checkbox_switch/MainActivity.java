package com.example.lottie_animation_checkbox_switch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Thêm thư viện đã có trong file build..
 * Truy cập https://lottiefiles.com/ click vào hình lấy cái file Json hiệu ứng về
 * Tạo trong res thư mục raw và đặt những thứ vừa tải về
 * Tạo thẻ lottie trong xml . Chú ý 1 số thuộc tính
 * + loop : lặp có các animation động như bus, dlivery man
 * + autoplay : tự động chạy cho các animation động như trên
 * + rawRes : đường dẫn tới các thư mục
 */
public class MainActivity extends AppCompatActivity {

    // Thiếu cái preLoader : là cái kiểu mạng lag nó xoay xoay thì chưa làm do màn hình thiếu

    private boolean isCheck = false;
    LottieAnimationView viewCheckDone;

    boolean isSwitch = false;
    LottieAnimationView viewSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewCheckDone = findViewById(R.id.lottie_checked_done);
        viewCheckDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck){
                    viewCheckDone.setSpeed(-1);
                    viewCheckDone.playAnimation();
                    isCheck = false;
                }else{
                    viewCheckDone.setSpeed(1);
                    viewCheckDone.playAnimation();
                    isCheck = true;
                }
            }
        });


        viewSwitch = findViewById(R.id.switch_button);
        // có thể thêm viewSwitch.setSpeed(3f) để tăng tốc độ với số 3 thay đổi được
        viewSwitch.setSpeed(2f);
        viewSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSwitch){
                    viewSwitch.setMinAndMaxProgress(0.5f,1.5f); // switch off
                    viewSwitch.playAnimation();
                    isSwitch = false;
                }else{
                    viewSwitch.setMinAndMaxProgress(0.0f,0.5f); // switch on
                    viewSwitch.playAnimation();
                    isSwitch = true;
                }
            }
        });
    }
}
