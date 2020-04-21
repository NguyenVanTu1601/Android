package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Để dùng nhớ xin quyền internet
 * Đồng thời thêm dòng android:usesCleartextTraffic="true"
 * Sau đó ra bòng đèn vàng bên trái nó chọn dòng đầu tiên
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.showNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
    }

    // Thêm ảnh vào notify
    @SuppressLint("StaticFieldLeak")
    private void showNotifycationwithImage(){
        new AsyncTask<String, Void,Bitmap>(){

            @Override
            protected Bitmap doInBackground(String... strings) {
                InputStream inputStream;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    inputStream = connection.getInputStream();
                    return BitmapFactory.decodeStream(inputStream);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                showNotification(bitmap);
            }
        }.execute("https://www.google.com.vn/imgres?imgurl=https%3A%2F%2Fznews-photo.zadn.vn%2Fw660%2FUploaded%2Fmdf_drkydd%2F2019_10_09%2F03.jpg&imgrefurl=https%3A%2F%2Fnews.zing.vn%2Fhot-girl-dan-toc-tay-nguoi-thi-hoa-hau-nguoi-la-streamer-noi-tieng-post999693.html&tbnid=OkYbfl5yuwvFQM&vet=12ahUKEwjtrt-Yjs7oAhW9zIsBHYbXDGEQMygCegUIARDhAQ..i&docid=7uuxnSLi1ZKpBM&w=660&h=496&q=girl" +
                "&ved=2ahUKEwjtrt-Yjs7oAhW9zIsBHYbXDGEQMygCegUIARDhAQ");
    }
    private void showNotification(Bitmap bitmap) {
        int notificationID = new Random().nextInt(100);
        String channelID = "notification_channel_2";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);

        builder.setSmallIcon(R.drawable.ic_notifications);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle("What is Loren Ipsum?");
        String str = "Sophia Loren, tên khai sinh Sofia Costanza Brigida Villani Scicolone là " +
                "một nữ diễn viên người Italia đã từng đoạt tượng vàng Oscar. Bà thường được biết đến như nữ diên viên Italia " +
                "nổi tiếng nhất thời bấy giờ và cũng là một biểu tượng sex lớn trên thế giới";
        builder.setContentText(str);

        //builder.setStyle(new NotificationCompat.BigTextStyle().bigText(str));
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelID) == null) {
                CharSequence name = "Notification Channel 1";
                NotificationChannel notificationChannel = new NotificationChannel(channelID, name, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel. setDescription("This notification channel  used to notify user");
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();
        if(notificationManager != null){
            notificationManager.notify(notificationID,notification);
        }
    }

    private void showNotification() {
        int notificationID = new Random().nextInt(100);
        String channelID = "notification_channel_1";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);

        builder.setSmallIcon(R.drawable.ic_notifications);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle("What is Loren Ipsum?");
        String str = "Sophia Loren, tên khai sinh Sofia Costanza Brigida Villani Scicolone là " +
                "một nữ diễn viên người Italia đã từng đoạt tượng vàng Oscar. Bà thường được biết đến như nữ diên viên Italia " +
                "nổi tiếng nhất thời bấy giờ và cũng là một biểu tượng sex lớn trên thế giới";
        builder.setContentText(str);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(str));

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelID) == null) {
                CharSequence name = "Notification Channel 1";
                NotificationChannel notificationChannel = new NotificationChannel(channelID, name, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel. setDescription("This notification channel  used to notify user");
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();
        if(notificationManager != null){
            notificationManager.notify(notificationID,notification);
        }
    }
}
