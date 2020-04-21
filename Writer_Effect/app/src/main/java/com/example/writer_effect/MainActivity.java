package com.example.writer_effect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * CharSequence.subSequence(start, end) : lấy chuỗi từ start đến end
 * CharSequence làm việc như việc cắt chuỗi của string thôi
 */
public class MainActivity extends AppCompatActivity {
    TextView txtType;
    Button btnClick;
    CharSequence charSequence;
    int mIndex = 0;
    EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtType = findViewById(R.id.textView);
        btnClick = findViewById(R.id.button);
        edt = findViewById(R.id.editText);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt.getText().toString().equals("")){
                    edt.setHint("Chưa điền kí tự");
                }
                else{
                    new SetText().execute(edt.getText().toString().trim());
                }

            }
        });
    }

    public class SetText extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            charSequence = strings[0];
            while (mIndex <= charSequence.length()){
                try {
                    Thread.sleep(150);
                    publishProgress(charSequence.subSequence(0,mIndex++).toString());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            return "Đã chạy xong";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            txtType.setText(values[0]);
        }
    }
}
