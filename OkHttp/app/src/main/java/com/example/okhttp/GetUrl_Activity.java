package com.example.okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * Có thể xem VD khác tại :
 * https://www.youtube.com/watch?v=oGWJ8xD2W6k&list=PLrnPJCHvNZuCPNCW2xdriIUgxmo9-QcFi&index=16
 */
public class GetUrl_Activity extends AppCompatActivity {
    Button btnCheck;
    EditText edtUrl;
    TextView txtUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_url_);
        btnCheck = findViewById(R.id.buttonXacNhan);
        edtUrl = findViewById(R.id.editTextUrl);
        txtUrl = findViewById(R.id.textViewUrl);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getURL().execute("https://"+edtUrl.getText().toString().trim());
            }
        });
    }
    public class getURL extends AsyncTask<String,String,String>{
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)// thời gian kết nối 15s
                .writeTimeout(15,TimeUnit.SECONDS)   // thời gian ghi
                .readTimeout(15,TimeUnit.SECONDS)    // thời gian đọc
                .retryOnConnectionFailure(true)              // cố gắng kết nối lại
                .build();                                    // build để hoạt động
        @Override
        protected String doInBackground(String... strings) {
            // Xử lý request
            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);
            Request request = builder.build();

            // Biến repone lấy dữ liệu về
            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(!s.equals("")){
                txtUrl.setText(s);
            }else{
                txtUrl.setText("Đường dẫn lỗi ");
            }
            super.onPostExecute(s);
        }
    }
}
