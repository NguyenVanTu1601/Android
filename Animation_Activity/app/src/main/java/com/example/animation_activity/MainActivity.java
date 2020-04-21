package com.example.animation_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

/**
 * Imporrt thư viện https://github.com/mohammadatif/Animatoo
 * Hỗ trợ : in and out.
 * swipe left.
 * swipe right.
 * split.
 * shrink.
 * card.
 * zoom.
 * fade.
 * spin.
 * diagonal.
 * windmill.
 * slide up.
 * slide down.
 * slide left.
 * slide right.
 * Chả hiểu sao vẫn lỗi trên máy thật, máy ảo chưa test
 *
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonChangeActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_two.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

            }
        });
    }


}
