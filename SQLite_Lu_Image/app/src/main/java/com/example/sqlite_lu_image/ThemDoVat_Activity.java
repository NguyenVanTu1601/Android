package com.example.sqlite_lu_image;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Xin quyền đọc bộ nhớ
 * Một khi đã xin quyền thì phần intent trong click sẽ không còn nữa vì nó trong hàm rồi
 */
public class ThemDoVat_Activity extends AppCompatActivity {
    EditText edtTen, edtMoTa;
    Button btnThem, btnHuy;
    ImageButton imgCamera, imgFolder;
    ImageView imgHinh;
    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_do_vat_);
        Anhxa();
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);

                // Xin quyền mở camera từ người dùng
                /*ActivityCompat.requestPermissions(ThemDoVat_Activity.this,
                                                          new String[]{Manifest.permission.CAMERA},
                                                          REQUEST_CODE_CAMERA); */

            }
        });

        imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
                // Xin quyền truy cập thư mục đồng thời cần xin quyền user-permit trong menifest:
                /* ActivityCompat.requestPermissions(ThemDoVat_Activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_CAMERA); */
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển imageView -> byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();        // ấy uri ảnh
                Bitmap bitmap = bitmapDrawable.getBitmap();                                    // chuyên ảnh về bitmap
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);  // compress bitmap -> arayoutput
                byte[] hinh = byteArrayOutputStream.toByteArray();                            // lưu vào mảng byte


                MainActivity.database.INSERT_DoVat(edtTen.getText().toString(),
                                                   edtMoTa.getText().toString().trim(), hinh);
                Toast.makeText(ThemDoVat_Activity.this, "Đã thêm!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ThemDoVat_Activity.this, MainActivity.class));
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemDoVat_Activity.this,MainActivity.class));
            }
        });
    }

    // Xử lý chấp nhận hoặc ko chấp nhận sau khi hỏi truy cập
    /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CODE_CAMERA);
                }else{
                    Toast.makeText(this, "Bạn không cho mở camera?", Toast.LENGTH_SHORT).show();
                }
            case REQUEST_CODE_FOLDER:
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,REQUEST_CODE_FOLDER);
                }else{
                    Toast.makeText(this, "Bạn không cho file ảnh?", Toast.LENGTH_SHORT).show();
                }
        }
    } */

    // Nhận ảnh gửi về từ intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Nhận ảnh từ camera
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data"); // key data là mặc định
            imgHinh.setImageBitmap(bitmap);
        }

        // Nhận ảnh từ bộ nhớ
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Anhxa(){
        edtTen = findViewById(R.id.editTextTenDoVat);
        edtMoTa = findViewById(R.id.editTextMoTa);
        btnThem = findViewById(R.id.buttonAdd);
        btnHuy = findViewById(R.id.buttonHuy);
        imgCamera = findViewById(R.id.imageButtonCamera);
        imgFolder = findViewById(R.id.imageButtonFolder);
        imgHinh = findViewById(R.id.imageViewImage);
    }
}
