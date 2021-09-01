package com.example.async_task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Nhớ dùng onCancel() để hủy bỏ khi activity hủy hoặc hoàn thành
 * Đồng thời trong manifed thêm android:configChanges="orientation|keyboardHidden|screenSize" để tránh 1 phần việc lỗi khi xoay màn
 */
public class MainActivity extends AppCompatActivity {
    Button btnXuly;
    TextView txtThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        Log.d("stateActivity", "Oncreate");
        btnXuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CongViec().execute();
            }
        });
    }

    private void Anhxa(){
        btnXuly = (Button) findViewById(R.id.button);
        txtThongTin = (TextView) findViewById(R.id.textView);
    }

    private class CongViec extends AsyncTask<Void,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtThongTin.setText("Bắt đầu!" + "\n");
        }

        @Override
        protected String doInBackground(Void... voids) {
            if(!isCancelled()){
                for(int i = 0 ; i < 5; i++){

                    try{
                        Thread.sleep(2000);
                    }catch(Exception e){

                    }

                    publishProgress("Công việc" + i + "\n", "Hoàn thành");
                }

            }
            return "Xong rồi !" +"\n";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txtThongTin.setText(values[0]);
            Log.i("hihi","1");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtThongTin.append(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("stateActivity", "OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("stateActivity", "Onstop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("stateActivity", "OnDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("stateActivity", "Onstart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("stateActivity", "OnrESUM");
    }
}
