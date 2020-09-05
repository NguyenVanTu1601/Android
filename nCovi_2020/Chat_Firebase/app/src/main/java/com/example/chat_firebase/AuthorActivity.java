package com.example.chat_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AuthorActivity extends AppCompatActivity {
    ImageButton imgFacebook, imgGmail, imgYoutube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        imgFacebook = findViewById(R.id.facebook_drawer);
        imgGmail = findViewById(R.id.gmail_drawer);
        imgYoutube = findViewById(R.id.youtube_drawer);

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebookPage();
            }
        });

        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYoutube();
            }
        });

        imgGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGmail();
            }
        });
    }
    public void openFacebookPage() {
        String YourPageURL = "https://www.facebook.com/banggia.160199";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
        startActivity(browserIntent);
    }

    private void openYoutube(){
        String YourPageURL = "https://www.youtube.com/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
        startActivity(browserIntent);
    }

    private void openGmail(){
        String[] recipientList = new String[]{"banggia1601@gmail.com"}; // mảng chứa danh sách các email nhận thư
        String subject = "Xin chào!"; // tiêu đề thư gửi
        String message = "Cảm ơn bạn đã tạo ra ứng dụng này"; // nội dung thư gửi

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipientList);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}
