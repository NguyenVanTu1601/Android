package com.example.animation_01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedActivity extends AppCompatActivity {

    Animation btt, bttduo, bttiga, imgAlpha;
    ImageView img;
    TextView txtTitle;
    Button btn;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        // init
        img = findViewById(R.id.iamgeGet);
        txtTitle = findViewById(R.id.titleGet);
        btn = findViewById(R.id.btngetstarted);
        email = findViewById(R.id.edittext);

        // import anim
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        bttduo = AnimationUtils.loadAnimation(this, R.anim.bttduo);
        bttiga = AnimationUtils.loadAnimation(this, R.anim.bttiga);
        imgAlpha = AnimationUtils.loadAnimation(this, R.anim.imgalpha);

        // set anim
        img.setAnimation(imgAlpha);
        txtTitle.setAnimation(btt);
        email.setAnimation(bttduo);
        btn.setAnimation(bttiga);

        // import font
        Typeface MLight = Typeface.createFromAsset(this.getAssets(), "fonts/MLight.ttf");
        Typeface Vidaloka = Typeface.createFromAsset(this.getAssets(),"fonts/Vidaloka.ttf");

        txtTitle.setTypeface(Vidaloka);
        btn.setTypeface(MLight);


    }
}