package com.example.viewflipper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

/** Mô tả : Tự động chạy chuyển hình ảnh  mà không cần vuốt hay chạm gì để chuyển sang hình ảnh khác
 * Có bao nhiêu ảnh thì trong Viewflipper xml có bấy nhiêu ImageView
 * Tuy nhiên cũng có thể tạo bằng code
 */
public class MainActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    Animation in,out;
    Button btnPre, btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFlipper = findViewById(R.id.ViewFlipper);
        viewFlipper.setFlipInterval(5000); // Thời gian bao lâu sẽ chuyển hình khác
        viewFlipper.setAutoStart(true); // tự động chuyển hình


        in = AnimationUtils.loadAnimation(this, R.anim.face_in);
        out = AnimationUtils.loadAnimation(this, R.anim.face_out);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);

        btnNext = findViewById(R.id.buttonNext);
        btnPre = findViewById(R.id.buttonPre);

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFlipper.isAutoStart()){
                    viewFlipper.stopFlipping();
                    viewFlipper.showNext();
                    viewFlipper.startFlipping();
                    viewFlipper.setAutoStart(true);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFlipper.isAutoStart()){
                    viewFlipper.stopFlipping();
                    viewFlipper.showPrevious();
                    viewFlipper.startFlipping();
                    viewFlipper.setAutoStart(true);
                }
            }
        });


        // Tạo thông qua mảng
        /*
        - int[] Hinh = {R.drawable.image1,R.drawable.image};
        - ViewFlipper viewFlipper = findView()....
        for(int i = 0; i < Hinh.size(); i++){
             ImageView img = new ImageView(this);
             img.setScaleTypr();
             img.setImageResource(Hinh[i]);
             viewFlipper.addView(img);
        }
        viewFlipper.setFlipInterval(5000); // Thời gian bao lâu sẽ chuyển hình khác
        viewFlipper.setAutoStart(true); // tự động chuyển hình
         */

    }
}
