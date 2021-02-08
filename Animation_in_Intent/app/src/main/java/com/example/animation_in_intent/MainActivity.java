package com.example.animation_in_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/** Chua biết lý do tại sao chạy trên máy ảo oke mà máy thật k chạy
 *  Phần trong file style ko cần , có thể xóa, do thêm vào nó cũng vẫn thế :((
 *  Lý do đã phát hiện :D do đt cùi quá nên vậy
 */
public class MainActivity extends AppCompatActivity {
    Button btnMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMain = findViewById(R.id.buttonMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);

            }
        });
    }
}
