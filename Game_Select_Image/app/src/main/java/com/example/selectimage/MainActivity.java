package com.example.selectimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> arrayName;
    ImageView imgView1, imgView2;
    int REQUES_CODE_IMAGE = 1;
    String tenHinhGoc = "";
    TextView txtScore;
    int Score = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        String[] mangTen = getResources().getStringArray(R.array.list_name); // Lấy mảng tên từ string resources()
        arrayName = new ArrayList<>(Arrays.asList(mangTen)); // Chuyển mảng về arrayList

        // -------Mục đích để lấy một phần tử ngẫu nhiên của mảng---------------
        Collections.shuffle(arrayName); // trộn mảng
        int idHinh = getResources().getIdentifier(arrayName.get(5),"drawable",getPackageName()); // Lấy mã tấm hình
        tenHinhGoc = arrayName.get(5);
        imgView1.setImageResource(idHinh);
        //--------------Cách khác là dùng random-----------------


        // --------Bắt sự kiện chọn hình 2 đổi tab -------
        imgView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ImageActivity.class),REQUES_CODE_IMAGE);
            }
        });
    }

    // Lấy dữ liệu về
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUES_CODE_IMAGE && data != null && resultCode == RESULT_OK){
            String kq = data.getStringExtra("tenhinhchon");
            int idHinhNhan = getResources().getIdentifier(kq,"drawable",getPackageName());
            imgView2.setImageResource(idHinhNhan);
            // Kiểm tra hình
            if(tenHinhGoc.equals(kq)){
                Toast.makeText(this, "Bạn chọn đúng rồi ! Cộng 10 điểm", Toast.LENGTH_SHORT).show();
                // Cộng điểm khi đúng
                Score += 10;
                // Reload tự động
                new CountDownTimer(2000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        Collections.shuffle(arrayName); // trộn mảng
                        int idHinh = getResources().getIdentifier(arrayName.get(5),"drawable",getPackageName()); // Lấy mã tấm hình
                        tenHinhGoc = arrayName.get(5);
                        imgView1.setImageResource(idHinh);
                    }
                }.start();

            }else{
                Toast.makeText(this, "Bạn chọn sai rồi! Trừ 5 điểm", Toast.LENGTH_SHORT).show();
                Score -= 5;
            }
            txtScore.setText("Score : " + Score);
        }

        // Kiểm tra có thao tác trên màn hình 2 không
        if(REQUES_CODE_IMAGE == requestCode && resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Bạn chưa chọn hình", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reload,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuReload){
            Collections.shuffle(arrayName); // trộn mảng
            int idHinh = getResources().getIdentifier(arrayName.get(5),"drawable",getPackageName()); // Lấy mã tấm hình
            tenHinhGoc = arrayName.get(5);
            imgView1.setImageResource(idHinh);
        }
        return super.onOptionsItemSelected(item);
    }

    // Anh xạ
    private void Anhxa(){
        imgView1 = (ImageView) findViewById(R.id.imageView);
        imgView2 = (ImageView) findViewById(R.id.imageView2);
        txtScore = (TextView) findViewById(R.id.textViewDiem);
    }
}
