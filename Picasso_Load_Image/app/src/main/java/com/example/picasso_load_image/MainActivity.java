package com.example.picasso_load_image;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * https://www.it-swarm.dev/vi/android/lam-cach-nao-de-su-dung-bo-nho-dem-tren-dia-trong-picasso/1047635808/
 * https://square.github.io/picasso/
 */
public class MainActivity extends AppCompatActivity {
    ImageView imgPicasso;
    EditText edtPicasso;
    Button btnPicasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

        btnPicasso.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String url = "https://firebasestorage.googleapis.com/v0/b/app-d8be7.appspot.com/o/Product%20Images%2F2256319Th" +
                        "5%2028%2C2020_15%3A37%3A32%20PM.jpg?alt=media&token=0aff87ab-5ea1-4cdb-bbca-030f7d2e13e1";
                if(url.equals("")){
                    imgPicasso.setImageResource(R.drawable.anhthiennhien);
                }else{
                    Picasso.with(MainActivity.this)
                            .load(url)
                            //.resize(100,100)// Chỉnh lại kích thước ảnh
                            //.rotate(180)// Xoay ảnh 180 độ
                            .placeholder(R.drawable.ic_launcher_background)// Anh mặc định khi load lâu
                            .error(R.drawable.ic_launcher_foreground)// Anh mặc định khi lỗi
                            .into(imgPicasso);
                }

            }
        });

    }
    public void Anhxa(){
        imgPicasso = findViewById(R.id.imageViewPicasso);
        edtPicasso = findViewById(R.id.editTextPicasso);
        btnPicasso = findViewById(R.id.buttonPicasso);
    }
}
