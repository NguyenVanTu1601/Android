package com.example.okhttp_image_url;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btnGetImage;
    EditText edtUrl;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetImage = findViewById(R.id.buttonGetImage);
        edtUrl = findViewById(R.id.editTextUrl);
        img = findViewById(R.id.imageView);

        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getImage().execute("https://img.thuthuatphanmem.vn/uploads/2018/10/09/anh-bien-thien-nhien-dep-nhat-the-gioi_041753368.jpg");
            }
        });
    }

    public class getImage extends AsyncTask<String,Void,byte[]>{
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();


        @Override
        protected byte[] doInBackground(String... strings) {
            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);

            Request request = builder.build();
            try {
                Response response = okHttpClient.newCall(request).execute(); // thực thi
                return response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            if(bytes.length > 0){
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                img.setImageBitmap(bitmap);
            }

    }
    }
}
