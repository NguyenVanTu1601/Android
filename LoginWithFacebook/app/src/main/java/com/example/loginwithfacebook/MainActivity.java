package com.example.loginwithfacebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Connect to firebase trong authen
 * Theo dõi thêm tại : https://firebase.google.com/docs/auth/android/facebook-login
 * B1 : Bật firebase trên android studio , vào authenticate using google sign -in
 * B2 : Ân connect your app và chuyển lên trình duyệt thực hiện
 * B3 : Add firebase Authentication SDK
 * B4 : Vào link trên để làm
 * B5 : Lấy App ID and an App Secret từ fb developer điền sang firebase
 * B6 : Lấy cái bên dưới điền sang facebook setting - xem video 8p50 - 9p10 : https://www.youtube.com/watch?v=mapLbSKNc6I
 * B7 : Làm theo : https://developers.facebook.com/docs/facebook-login/android
 * Để tạo hash key : tải ssl về, giải nén trong ổ C, đồng thời coppy 3 file trong bin vào C:\Program Files\Java\jdk1.8.0_111\bin,
 * mở cmd chuyển đường dẫn sang cd C:\Program Files\Java\jdk1.8.0_111\bin và coppy keytool bên dưới vào
 * keytool -exportcert -alias YOUR_RELEASE_KEY_ALIAS -keystore YOUR_RELEASE_KEY_PATH | openssl sha1 -binary | openssl base64
 * -> thu được key : zY00cczs2SG28RTn1bZQvsCftvI=
 * Xem get key tại : https://www.youtube.com/watch?v=ZqbhSipsmWE
 * hoặc https://www.youtube.com/watch?v=6oMf_lXg7sI&t=0s
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}