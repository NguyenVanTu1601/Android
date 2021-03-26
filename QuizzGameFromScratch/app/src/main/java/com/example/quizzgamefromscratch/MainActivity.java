package com.example.quizzgamefromscratch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int presCounter = 0;
    private int maxPresCounter = 4;
    private String[] keys = {"R","I","B","D","X"};
    private String textAnswer = "BIRD";
    private Typeface fredokaOne;
    private TextView textScreen, textQuestion, textTitle;
    private EditText editText;
    private LinearLayout layoutParent; // layout chứa các chữ cái của đáp án
    private Animation smallBigFroth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        keys = shuffleArray(keys);

        for (String key : keys){
            addView(layoutParent, key, editText);
        }

    }

    private String[] shuffleArray(String[] keys) {
        List<String> listKey = new ArrayList<>();
        listKey.addAll(Arrays.asList(keys));
        Collections.shuffle(listKey);
        for(int i = 0; i < listKey.size(); i++){
            keys[i] = listKey.get(i);
        }
        return keys;
    }

    private void init() {
        fredokaOne      = Typeface.createFromAsset(this.getAssets(), "fonts/FredokaOne-Regular.ttf" );
        textScreen      = findViewById(R.id.textScreen);
        textQuestion    = findViewById(R.id.textQuestion);
        textTitle       = findViewById(R.id.textTitle);
        layoutParent    = findViewById(R.id.layoutParent);
        editText        = findViewById(R.id.editText);

        // set fonts
        textScreen.setTypeface(fredokaOne);
        textQuestion.setTypeface(fredokaOne);
        textTitle.setTypeface(fredokaOne);
        editText.setTypeface(fredokaOne);

        smallBigFroth = AnimationUtils.loadAnimation(this, R.anim.smallbigfroth);
    }

    private void addView(LinearLayout viewParent, final String text, final EditText editText){
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                70,
                LinearLayout.LayoutParams.WRAP_CONTENT); // height, width cho 1 view
        linearLayoutParams.rightMargin = 20;

        final TextView textView = new TextView(this);
        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.colorText));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);
        textView.setTypeface(fredokaOne);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presCounter < maxPresCounter){
                    editText.setText(editText.getText().append(textView.getText()));
                    presCounter++;
                    textView.startAnimation(smallBigFroth);
                    textView.animate().alpha(0).setDuration(300);
                    textView.setClickable(false);
                    textView.setFocusable(false);
                    if (presCounter == maxPresCounter){
                       checkAnswer(editText.getText().toString());
                    }
                }
            }
        });

        viewParent.addView(textView);
    }

    private void checkAnswer(String ans){
        if (!ans.equals(textAnswer)){
            editText.setText("");
            Toast.makeText(this, "Wrong answer!!!",Toast.LENGTH_SHORT).show();
            presCounter = 0;
            keys = shuffleArray(keys);
            if (layoutParent.getChildCount() > 0){
                layoutParent.removeAllViews();
            }
            for (String key : keys){
                addView(layoutParent, key, editText);
            }
        }else{
            Toast.makeText(this, "Answer correct!!!",Toast.LENGTH_SHORT).show();
            Intent a = new Intent(this, BossActivity.class);
            startActivity(a);
        }
    }
}