package com.example.serviceorientedsoftware.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Client {
    public static Retrofit retrofit = null;
    public static Retrofit getClient(String baseurl){
        OkHttpClient builder = new OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS) // cho phép đọc trong bao lâu. Măc định là 10s
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS) // thời gian đợi kết nối
                .retryOnConnectionFailure(true)// khơi động khi gặp lỗi
                .build();

        Gson gson = new GsonBuilder().setLenient().create(); // hỗ trợ việc conver
        retrofit = new Retrofit.Builder().baseUrl(baseurl)
                .client(builder) // các cài đặt muốn set ở trên
                .addConverterFactory(GsonConverterFactory.create(gson)) // chuyển biến của json về java
                .build();

        return retrofit;
    }
}
