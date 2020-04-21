package com.example.retrofit_okhttp_web;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.retrofit_okhttp_web.Retrofit.APIUtils;
import com.example.retrofit_okhttp_web.Retrofit.DataClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKi_Activity extends AppCompatActivity {
    Button btnHuy, btnDangki;
    EditText edtUser, edtPass;
    ImageView imgHinh;
    int REQUEST_CODE_IMAGE = 123;
    String realPath = "";
    String taikhoan = "";
    String matkhau = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dangki);
        Anhxa();

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKi_Activity.this, MainActivity.class));
            }
        });

        selectImage();

        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = edtUser.getText().toString();
                matkhau = edtPass.getText().toString();
                if(taikhoan.length() > 0 && matkhau.length() > 0){
                    // Bước 3 : Từ đây đến chỗ bước 4 : Lất file ảnh, lưu vào 1 thư mục để quản lý đường dẫn
                    File file = new File(realPath); // chứa đường dẫn của ảnh đưa vào file để tí đưa lên server
                    String file_path = file.getAbsolutePath(); // đường dẫn thực tế chứa file do chưa biết file này nằm đâu

                    // Tránh trường hợp tên file trnugf nhau thì thêm 1 đoạn time vào sau
                    String[] mangtenfile = file_path.split("\\.");
                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." +  mangtenfile[1];


                    // Xác nhận kiểu dữ liệu của file
                    final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"),file );
                    // gửi dữ liệu
                    MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody); // uploaded_file là key trong php
                    DataClient dataClient  = APIUtils.getData(); // tạo kết nối
                    Call<String> callBack = dataClient.upLoadPhoto(multipartBody); // call này trong DataClient interface
                    callBack.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response != null){
                                String message = response.body(); // lấy dữ liệu là tên ảnh
                                if(message.length() > 0 ){

                                    // Bước 4 : Đưa dữ liệu lên server
                                    DataClient insertData = APIUtils.getData(); // gửi cá phương thức từ dataclient lên server sau đó nhận dữ liệu về dưới dạng obbject và đưa vào dataClient
                                    Call<String> callback = insertData.insertData(taikhoan,matkhau,APIUtils.base_url + "image/" + message);
                                    callback.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String result = response.body();
                                            if(result.equals("success")){
                                                Toast.makeText(DangKi_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                }
                else{
                    Toast.makeText(DangKi_Activity.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void Anhxa() {
         btnHuy = findViewById(R.id.buttonHuyDangki);
         btnDangki = findViewById(R.id.buttonSignUp);
         edtUser = findViewById(R.id.editextUser);
         edtPass = findViewById(R.id.editextPass);
         imgHinh = findViewById(R.id.imageview);
    }

    private void selectImage(){
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });
    }


    // Bước 2 : Tạo hàm override để lấy ảnh, lấy đường dãn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Để truy câp trước hết cần xin quyền truy cập READ_EXTERNAL_STORAGE

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData(); // đường dẫn trong máy
            realPath = getRealPathFromURI(uri);
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


    // Bước  1 : Coppy class này về để lấy đường dẫn tuyệt đối
    public String getRealPathFromURI (Uri contentUri) { // lấy đường dẫn thực tế
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}
