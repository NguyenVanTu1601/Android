package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    Button btnBack,  btnTim, btnGo, btnReload;
    EditText edtDiachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        webView.setWebViewClient(new WebViewClient()); // ko văng khỏi trình duyệt
        // Mở trang web:
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edtDiachi.getText().toString().trim();
                webView.loadUrl("http://" + url);
                edtDiachi.setText(webView.getUrl());
            }
        });

        // Bắt sự kiện back, go, reload
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()){
                    webView.goBack();
                    edtDiachi.setText(webView.getUrl());
                    Toast.makeText(MainActivity.this, "Trang trước", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Không có dữ liệu để load", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoForward()){
                    webView.goForward();
                    edtDiachi.setText(webView.getUrl());
                    Toast.makeText(MainActivity.this, "Trang sau", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Không có dữ liệu để load", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
                edtDiachi.setText(webView.getUrl());
                Toast.makeText(MainActivity.this, "Reload", Toast.LENGTH_SHORT).show();
            }
        });
        //Sử dụng các chức năng của trang web và phóng to thu nhỏ web
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true); // mở thu phóng
        webSettings.setDisplayZoomControls(false); // tắt thanh thu phóng mặc định
        webSettings.setJavaScriptEnabled(true); // bật chức năng của web
    }
    private void Anhxa(){
        webView = findViewById(R.id.web);
        btnBack = findViewById(R.id.buttonBack);
        btnGo = findViewById(R.id.buttonGo);
        btnReload = findViewById(R.id.buttonReload);
        btnTim = findViewById(R.id.buttonTim);
        edtDiachi = findViewById(R.id.editText);
    }
}
