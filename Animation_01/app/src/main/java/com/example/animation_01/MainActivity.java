package com.example.animation_01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView titleGet, subtitleGet;
    Button btngetStarted;
    Animation btt, bttduo, bttiga, imgAlpha;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleGet        = findViewById(R.id.titleGet);
        subtitleGet     = findViewById(R.id.subtitleGet);
        btngetStarted   = findViewById(R.id.btngetstarted);
        img             = findViewById(R.id.iamgeGet);

        // import anim
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        bttduo = AnimationUtils.loadAnimation(this, R.anim.bttduo);
        bttiga = AnimationUtils.loadAnimation(this, R.anim.bttiga);
        imgAlpha = AnimationUtils.loadAnimation(this, R.anim.imgalpha);

        // set anim
        titleGet.startAnimation(btt);
        subtitleGet.startAnimation(bttduo);
        btngetStarted.startAnimation(bttiga);
        img.startAnimation(imgAlpha);

        // import font
        Typeface MLight = Typeface.createFromAsset(this.getAssets(), "fonts/MLight.ttf");
        Typeface Vidaloka = Typeface.createFromAsset(this.getAssets(),"fonts/Vidaloka.ttf");

        // customize font
        titleGet.setTypeface(Vidaloka);
        subtitleGet.setTypeface(MLight);
        btngetStarted.setTypeface(Vidaloka);

        //overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        // onClick
        btngetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetStartedActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());


            }
        });

    }
}