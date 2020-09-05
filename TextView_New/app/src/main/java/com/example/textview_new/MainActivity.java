package com.example.textview_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txtNew, txt2, txt3,txt4,txt5,txt6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNew = findViewById(R.id.textViewNew);
        txt2 = findViewById(R.id.textView2);
        txt3 = findViewById(R.id.textView3);
        txt4 = findViewById(R.id.textView4);
        txt5 = findViewById(R.id.textView5);
        txt6 = findViewById(R.id.MarqueeText);

        autoLink();
        gachChan();
        gachChan_1_phan();
        toMau_1phan();
        clickText();
        chu_chạy();

    }

    private void chu_chạy() {
        txt6.setText("START | lunch 20.00 | Dinner 60.00 | Travel 60.00 | Doctor 5000.00 | lunch 20.00 | Dinner 60.00 | Travel 60.00 | Doctor 5000.00 | END");
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

    private void toMau_1phan(){
        String text = "I want this and this color";
        SpannableString ss = new SpannableString(text);
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);

        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.GREEN);
        BackgroundColorSpan bcsYellow = new BackgroundColorSpan(Color.YELLOW);

        ssb.setSpan(fcsRed,7,11,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(fcsGreen,16,20,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(bcsYellow, 22,26,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append("very good!!!");

        txt4.setText(ssb);
    }

    private void clickText(){ // click 1 đoạn nhỏ của text
        String text = "I want this and this click able!";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(MainActivity.this, "One", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(MainActivity.this, "Two", Toast.LENGTH_SHORT).show();
            }
        };

        ss.setSpan(clickableSpan,7,11,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2,16,20,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt5.setText(ss);
        txt5.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
