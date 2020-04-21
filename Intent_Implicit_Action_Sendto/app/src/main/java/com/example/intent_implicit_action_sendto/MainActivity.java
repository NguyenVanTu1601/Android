package com.example.intent_implicit_action_sendto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageViewMess);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.putExtra("sms_body","Chao ban..."); // đoạn tin nhắn mặc định khi mở lên, mặc định là sms_body
                intent.setData(Uri.parse("sms:0365882920")); // truyền số điện thoại để gửi tin, mặc định sms: số điện thoại
                startActivity(intent);
            }
        });
    }
}
