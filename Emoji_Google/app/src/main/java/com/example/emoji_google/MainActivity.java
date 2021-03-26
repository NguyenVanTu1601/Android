package com.example.emoji_google;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiTextView;

public class MainActivity extends AppCompatActivity {

    private ImageView imgEmoji, imgSend;
    private EditText edtMessages;
    private LinearLayout linearLayout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();

        //initializa emoji popup
        EmojiPopup popup = EmojiPopup.Builder.fromRootView(
                findViewById(R.id.root_view)
        ).build(edtMessages);

        imgEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle between text and emoji
                popup.toggle();
            }
        });

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate emoji text view
                EmojiTextView emojiTextView = (EmojiTextView) LayoutInflater
                        .from(view.getContext())
                        .inflate(R.layout.emoji_text_view, linearLayout, false);

                // set text on emoji textview
                emojiTextView.setText(edtMessages.getText().toString());

                // add view to linearlayout
                linearLayout.addView(emojiTextView);

                //clear edit text value
                edtMessages.getText().clear();
            }
        });
    }

    private void init() {
        imgEmoji        = findViewById(R.id.bt_emoji);
        imgSend         = findViewById(R.id.bt_send);
        edtMessages     = findViewById(R.id.et_emoji);

        linearLayout    = findViewById(R.id.linear_layout);
    }
}