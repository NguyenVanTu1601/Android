package com.example.blogsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AuthorActivity extends AppCompatActivity {

    private ImageButton imgFaceBook, imgMail, imgYoutube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        imgFaceBook = findViewById(R.id.facebook_drawer);
        imgMail = findViewById(R.id.gmail_drawer);
        imgYoutube = findViewById(R.id.youtube_drawer);

        imgFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebookPage();
            }
        });

        imgMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmail();
            }
        });


        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYoutube();
            }
        });
    }

    public void openFacebookPage() {
        String YourPageURL = "https://www.facebook.com/banggia.160199";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
        startActivity(browserIntent);
    }

    public void openEmail(){
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

    public void openYoutube() {
        String YourPageURL = "https://www.youtube.com/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
        startActivity(browserIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AuthorActivity.this,MainActivity.class));
        finish();
    }
}