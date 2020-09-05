package com.example.contact_author;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity {
    ImageButton imgFaceBook, imgMail, imgPlayStore, imgYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgFaceBook = findViewById(R.id.facebook_drawer);
        imgMail = findViewById(R.id.gmail_drawer);
        imgYoutube = findViewById(R.id.youtube_drawer);
        imgPlayStore = findViewById(R.id.rate_me_drawer);

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

        imgPlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayStore();
            }
        });

        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYoutube();
            }
        });

        RateMe();

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

    public void openPlayStore(){
        try{
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + "com.android.chrome")));
        }catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/apps/details?id="+getPackageName())));
        }
    }

    public void openYoutube() {
        String YourPageURL = "https://www.youtube.com/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
        startActivity(browserIntent);
    }

    private void RateMe(){
        AppRate.with(this) // text hiện thị sưa trong file string.xml
                .setInstallDays(0) // số ngày kể từ khi cài đặt app sẽ bắt đầu hỏi vote
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
        //AppRate.with(this).showRateDialog(this); // hiện lại mỗi khi ấn no thanks và tắt app mở lại
    }
}
