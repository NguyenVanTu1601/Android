package com.example.read_newpaper_rss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        webView = findViewById(R.id.webView);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link_Tintuc");
        webView.loadUrl(link); // mở link dưới dang giao diện
        webView.setWebViewClient(new WebViewClient()); // khi ng dung ấn link sẽ mở link trong app mà ko bị văng khỏi app
    }
}
