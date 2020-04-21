package com.example.textview_new;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtNew, txt2, txt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNew = findViewById(R.id.textViewNew);
        txt2 = findViewById(R.id.textView2);
        txt3 = findViewById(R.id.textView3);
        autoLink();
        gachChan();
        gachChan_1_phan();
    }
    private void autoLink(){
        // Cài đặt thuộc tính autoLink
        // Cách 1  : thêm trong xml autoLink = "web"
        // Cách 2  : Code
        txtNew.setMovementMethod(LinkMovementMethod.getInstance());
        txtNew.setLinkTextColor(Color.RED);
        txtNew.setLinksClickable(true);
        txtNew.setText("https://vntalking.com/tao-app-wifi-scanner-cho-android.html");

    }
    private void gachChan(){
        txt2.setPaintFlags(txt2.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        txt2.setText("Hello my Friend");
    }

    private void gachChan_1_phan(){
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("Hello anrdoid");
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        stringBuilder.setSpan(
                strikethroughSpan,0 , 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        txt3.setText(stringBuilder);
    }
}
