package com.example.blogsapp.Home;

import com.example.blogsapp.PushNotification.MyRespone;
import com.example.blogsapp.PushNotification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAXp_1uMY:APA91bFPS9wHN9xx4Tq_evndU728mcLpwFNBVp-U-lsZnlFtRVbXiD4HXOgZcuX_ryM2p843f7wn5emWD8Mp4kjxQ3bOcKaBXdrK4mK64SwgyrlMM5B-CfjvBsDzCRTGP2zlDKbXqAzh"
            }
    )

    @POST("fcm/send")
    Call<MyRespone> sendNotification(@Body Sender body);
}
