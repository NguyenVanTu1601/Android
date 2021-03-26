package com.example.quizzgamefromscratch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BossActivity extends AppCompatActivity {

    private TextView textScreen, textQuestion, textBack;
    private Button btnContinue;
    private ImageView bigBoss;
    private Typeface fredokaOne;
    private Animation smallToBig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);

        init();


    }

    private void init() {
        textScreen      = findViewById(R.id.textScreen);
        textQuestion    = findViewById(R.id.textQuestion);
        textBack        = findViewById(R.id.textBack);
        btnContinue     = findViewById(R.id.btnContinue);
        bigBoss         = findViewById(R.id.bigBoss);
        fredokaOne      = Typeface.createFromAsset(this.getAssets(), "fonts/FredokaOne-Regular.ttf" );
        smallToBig      = AnimationUtils.loadAnimation(this, R.anim.smalltobig);

        textScreen.setTypeface(fredokaOne);
        textQuestion.setTypeface(fredokaOne);
        textBack.setTypeface(fredokaOne);
        btnContinue.setTypeface(fredokaOne);

        bigBoss.setAnimation(smallToBig);
    }
}