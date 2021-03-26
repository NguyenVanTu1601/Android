package com.example.videomeetingapp.firebase;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.videomeetingapp.R;
import com.example.videomeetingapp.activities.IncomingInvitationActivity;
import com.example.videomeetingapp.utilities.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //Log.d("FCM", "Token : " + token);
    }

    // remoteMessageReceived : là các thông tin nhận được từ outgoingInvitationActivity
    // các thong tin này được đặt trong body của gói tin
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String type = remoteMessage.getData().get(Constants.REMOTE_MSG_TYPE);

        if (type != null){
            if (type.equals(Constants.REMOTE_MSG_INVITATION)){ // người nhận nhận cuộc gọi từ người gửi (LỜI MỜI)
                // type = invitation (lời mời)
                Intent intent = new Intent(getApplicationContext(), IncomingInvitationActivity.class);
                intent.putExtra(Constants.REMOTE_MSG_MEETING_TYPE,
                        remoteMessage.getData().get(Constants.REMOTE_MSG_MEETING_TYPE)); // type video hay audio
                intent.putExtra(Constants.KEY_FIRST_NAME,
                        remoteMessage.getData().get(Constants.KEY_FIRST_NAME)); // firt name
                intent.putExtra(Constants.KEY_LAST_NAME,
                        remoteMessage.getData().get(Constants.KEY_LAST_NAME)); // last name
                intent.putExtra(Constants.KEY_EMAIL,
                        remoteMessage.getData().get(Constants.KEY_EMAIL)); // email
                intent.putExtra(Constants.REMOTE_MSG_INVITER_TOKEN,
                        remoteMessage.getData().get(Constants.REMOTE_MSG_INVITER_TOKEN)); //gửi token người mời lại

                intent.putExtra(Constants.REMOTE_MSG_MEETING_ROOM,
                        remoteMessage.getData().get(Constants.REMOTE_MSG_MEETING_ROOM)); // dùng cho jitsi meet

                // Bật Notification
                showNotification(remoteMessage.getData().get(Constants.KEY_FIRST_NAME) + " " +
                        remoteMessage.getData().get(Constants.KEY_LAST_NAME), "Call with you...");

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else if (type.equals(Constants.REMOTE_MSG_INVITATION_RESPONSE)){ // (Lời trả lời - response)
                // nếu là gói tin nhận được do ng nhận cuộc gọi trả lời/ từ chối cuộc gọi hoặc người gửi hủy cuộc gọi thì đều tác động lên phía
                // màn hình của người nhận cuộc gọi hết
                //Tạo 1 LocalBoardcasrManager gửi intent đến OutComing để thông báo đối phương đã chấp nhận kết nối / incoming thông báo ng gửi hủy kết nối

                Log.d("RESPONSE", remoteMessage.getData().get(Constants.REMOTE_MSG_INVITATION_RESPONSE));


                Intent intent = new Intent(Constants.REMOTE_MSG_INVITATION_RESPONSE);
                intent.putExtra(Constants.REMOTE_MSG_INVITATION_RESPONSE,
                        remoteMessage.getData().get(Constants.REMOTE_MSG_INVITATION_RESPONSE)); // response này là accept/reject/cancel
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                // broadcast này gửi intent accept/reject sang outgoing để bên gửi yêu cầu biết bên nhận đã chấp nhận/ từ chối
                // gửi intent cancel sang cho incoming biết ng gửi hủy kết nối

            }
        }
    }

    private void showNotification(String title, String body ) {
        int notificationID = new Random().nextInt(100);
        String channelID = "notification_channel_1";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);

        builder.setSmallIcon(R.drawable.ic_videocam);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        // builder.setSound()

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
