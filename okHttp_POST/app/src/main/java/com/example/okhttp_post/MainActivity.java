package com.example.okhttp_post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button btnPost;
    EditText edtUser, edtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPost = findViewById(R.id.buttonPost);
        edtUser = findViewById(R.id.editTextUser);
        edtPass = findViewById(R.id.editTextPass);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                new postServer(user,pass).execute("http://192.168.1.5/okHttp_server/okhttp_server.php");
            }
        });
    }

    public class postServer extends AsyncTask<String,Void,String>{
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        String user, pass;

        public postServer(String user, String pass) {
            this.user = user;
            this.pass = pass;
        }

        @Override
        protected String doInBackground(String... strings) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("username",user) // lấy từ filer php còn user lấy từ ng dùng nhập
                    .addFormDataPart("password",pass)
                    .setType(MultipartBody.FORM)
                    .build();

            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(requestBody)
                    .build();
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
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
