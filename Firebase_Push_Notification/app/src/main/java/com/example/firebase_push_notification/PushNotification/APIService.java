package com.example.firebase_push_notification.PushNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA6THNMSE:APA91bG6YFxQU8YgW0kaY_6ImeLJGWMRju6ffQlu87dKuMB0002V4jQyuDbaqzIwqGiXnknAdozBdCUemt5AX7e7uD2VBVVRLQPky1h1u8TKoDDRpUZC4_0VzycJGYAcPvbAYLB-i9mq" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}