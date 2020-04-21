package com.example.firebase_storage_save_image;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
/**
 * Nói chung rảnh ngồi đọc tại liệu là pro hết :))
 * https://firebase.google.com/docs/storage/android/start?authuser=0
 */

/**
 * Tạo mới 1 firebase sau đó vào Storage->Rules và thay đổi các quyền
 * Với bào học này chuyển quyền public nên sửa thành :  allow write,read;
 * Anh phải chuyển về bitmap -> byte[] mới up lên được
 *
 * URI là nơi chính xác lấy được nội dung của cái ta cần lấy. URI có thể là 1 URL
 * URL < URI thể hiện cách ta có thể lấy nội dung về
 */
public class MainActivity extends AppCompatActivity {
    FirebaseStorage firebaseStorage;
    Button btnSave;
    ImageView imgHinh;
    int REQUEST_CODE_IMAGE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        // Khởi tạo
        firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://fir-storageimage-94c2a.appspot.com"); // url là đường dẫn trong storage

        //Click vào hình mở cam của máy
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo cho cây thư mục của storage
                Calendar calendar = Calendar.getInstance();
                StorageReference mStorage = storageReference.child("image" + calendar.getTimeInMillis() + ".png"); // tạo thư mục ảnh tương tự up dữ liệu

                // Đổi hình về mảng byte[]
                imgHinh.setDrawingCacheEnabled(true);
                imgHinh.buildDrawingCache();

                BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] data = stream.toByteArray();

                //Tạo đối tượng upload
                UploadTask uploadTask = mStorage.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() { // sự kiện up ảnh thất bại
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Không thể đưa ảnh lên", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Nếu thành công trả về 1 uri địa chỉ tấm hình
                        Uri downloadUri = taskSnapshot.getUploadSessionUri();
                        Toast.makeText(MainActivity.this, downloadUri.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Anhxa(){
        btnSave = findViewById(R.id.buttonSave);
        imgHinh = findViewById(R.id.imageViewHinh);
    }
}
