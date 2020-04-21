package com.example.intent_camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn;
    ImageView img;
    int REQUEST_CODE_CAMERA = 16; // số tự đặt như bên data Result
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.buttonCamera);
        img = (ImageView) findViewById(R.id.imageViewHinh);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Chụp ảnh xong đổ về Textview
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // mở cam
                startActivityForResult(intent,REQUEST_CODE_CAMERA); // nhận ảnh

                // Hỏi yêu cầu cấp quyền truy cập
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);

            }
        });
    }

    // Kiểm tra sự đồng ý của ng dùng
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_CAMERA && grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,REQUEST_CODE_CAMERA);
        }
        else Toast.makeText(this, "Bạn không cho phép mở Camera", Toast.LENGTH_SHORT).show();
    }

    // Trả về kết quả (hàm mặc định để nhận)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data"); // key data là mặc định
            img.setImageBitmap(bitmap);
        }
    }
}
