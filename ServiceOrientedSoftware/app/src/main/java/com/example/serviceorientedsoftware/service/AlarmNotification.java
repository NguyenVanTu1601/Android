package com.example.serviceorientedsoftware.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.serviceorientedsoftware.R;
import com.example.serviceorientedsoftware.activity.SplashActivity;

public class AlarmNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification")
                .setContentText("Ghé thăm shop xem những thú cưng mới nhất bạn nhé!")
                .setContentTitle("Shop pet")
                .setSmallIcon(R.drawable.ic_pet)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        /**
         * int	PRIORITY_DEFAULT	Default notification priority.
         * int	PRIORITY_HIGH	Higher priority, for more important notifications or alerts.
         * int	PRIORITY_LOW	Lower priority, for items that are less important.
         * int	PRIORITY_MAX	Highest priority, for your application's most important items that require the user's prompt attention or input.
         * int	PRIORITY_MIN	Lowest priority; these items might not be shown to the user except under special circumstances, such as detailed notification logs.
         */
        Intent notificationIntent = new Intent(context, SplashActivity.class);
        PendingIntent conPendingIntent = PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(conPendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());
        //notificationId là một int duy nhất cho mỗi thông báo mà bạn phải xác định

    }
}
