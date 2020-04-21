package com.example.select_video_device;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Một số cách lấy video. Xem thêm tại https://demonuts.com/videoview-android/
 * Khi bạn muốn tải video từ tài nguyên cục bộ, bạn cần cung cấp đường dẫn đến tệp video cục bộ đó. Phương pháp này sẽ sử dụng đường dẫn này làm URI.
 *  String uriPath = "android.resource://com.exampledemo.parsaniahardik.videoviewdemonuts/"+R.raw.funn;  //update package name
 *  uri = Uri.parse(uriPath);
 *  vv.setVideoURI(uri);
 *
 * requestF Focus ()
 * Phương pháp này sẽ yêu cầu tập trung cho videoview cụ thể. Nếu bạn có nhiều video trên một trang thì phương pháp này sẽ giúp bắt đầu video cụ thể.
 *  vv.requestFocus();
 *
 * setVideoPath (Đường dẫn chuỗi):
 * Phương pháp này được sử dụng để đặt đường dẫn của video được lưu trữ trong bộ nhớ trong hoặc ngoài của thiết bị Android.
 *      vv.setVideoPath("/sdcard/mwwyt.mpg");
 *      vv.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sample);
 *      String s="https://firebasestorage.googleapis.com/v0/b/lifesaver-18f28.appspot.com/o/flood.mp4?alt=media&token=179d7e4e-7171-4a87-b1f8-b1fc3d976c60";
 *      vv.setVideoPath(s);
 *
 * Load Video URL : https://demonuts.com/load-play-video-url/
 */
public class MainActivity extends AppCompatActivity {
    Button btnSelect;
    VideoView videoView;
    private static final String VIDEO_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSelect = findViewById(R.id.ButtonSelectVideo);
        videoView = findViewById(R.id.VideoView);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }

    //Tạo ra 2 lựa chọn khi người dùng nhấn nút
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    // Chọn video từ bộ nhớ
    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    // Quay và lưu Video
    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    // Nhận kết quả
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("result",""+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            Log.d("what","cancle");
            return;
        }

        // Nếu nhận từ bộ nhớ
        if (requestCode == GALLERY) {
            Log.d("what","gale");
            if (data != null) {

                Uri contentURI = data.getData();  // Lấy đường dẫn video
                String selectedVideoPath = getPath(contentURI); // 1
                Log.d("path",selectedVideoPath);  // 2
                saveVideoToInternalStorage(selectedVideoPath); // 3 ...3 dòng này có thể bỏ được thì phải

                videoView.setVideoURI(contentURI); // chạy video
                videoView.requestFocus();
                videoView.start();

            }

        } else if (requestCode == CAMERA) {   // Quay từ cam
            Uri contentURI = data.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d("frrr",recordedVideoPath);
            saveVideoToInternalStorage(recordedVideoPath); // lưu video
            videoView.setVideoURI(contentURI);
            videoView.requestFocus();
            videoView.start();
        }
    }


    // Chuyển video về mảng byte[]. Lưu lại cả đường dẫn
    private void saveVideoToInternalStorage (String filePath) {
        File newfile;

        try {
            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if(currentFile.exists()){

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v("vii", "Video file saved successfully.");
            }else{
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Lấy đường dẫn tuyêt đối
    // URI chứa cấu trúc dữ liệu của video.
    // Đường dẫn đến video có thể được truy xuất từ ​​URI bằng phương thức getPath () .
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
}

