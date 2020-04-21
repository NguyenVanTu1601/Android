package com.example.firebase_storage_database_image;

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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Mô tả project :
 * Project dùng chụp ảnh, sau đó lưu ảnh lên Storage còn đường dẫn tới ảnh lưu vào database đồng thời ảnh load vào listView
 * Bài này lỗi do phương thức lấy địa chỉ hình uri ko còn tồn tại
 */
public class MainActivity extends AppCompatActivity {
    Button btnSave;
    ImageView imgHinh;
    EditText edtTen;
    FirebaseStorage firebaseStorage;
    DatabaseReference myData;
    int REQUEST_CODE_IMAGE = 123;
    ListView listView;
    ArrayList<Image> arrayList;
    Image_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        firebaseStorage = FirebaseStorage.getInstance();
        myData = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://fir-storagedatabase-2f476.appspot.com"); // url là đường dẫn trong storage
        adapter = new Image_adapter(this, R.layout.line_image,arrayList);
        listView.setAdapter(adapter);
        LoadData();

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
                        //Toast.makeText(MainActivity.this, downloadUri.toString(), Toast.LENGTH_SHORT).show();

                        // Đưa dữ liệu lên database
                        Image img = new Image(edtTen.getText().toString(),downloadUri.toString());
                        myData.child("Image").push().setValue(img, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if(databaseError == null){
                                    Toast.makeText(MainActivity.this, "Add Database thành công", Toast.LENGTH_SHORT).show();
                                    edtTen.setText("");
                                    imgHinh.setImageResource(R.drawable.ic_launcher_background);
                                }else{
                                    Toast.makeText(MainActivity.this, "Add thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });


            }
        });

        //
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void LoadData(){
        myData.child("Image").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Image image = dataSnapshot.getValue(Image.class);
                arrayList.add(new Image(image.getTenHinh(),image.getLinkHinh()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void Anhxa() {
        btnSave = findViewById(R.id.buttonSave);
        imgHinh = findViewById(R.id.imageViewHinh);
        edtTen  = findViewById(R.id.editTextTen);
        listView = findViewById(R.id.listViewImage);
        arrayList = new ArrayList<>();

    }

}
