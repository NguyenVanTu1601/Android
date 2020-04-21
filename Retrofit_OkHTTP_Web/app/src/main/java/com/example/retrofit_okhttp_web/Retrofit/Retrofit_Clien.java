package com.example.retrofit_okhttp_web.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class này có mục đích :
 * + Trỏ đến đường dẫn gửi request lên server
 * + Khởi tạo và cấu hình cho retrofit
 */
public class Retrofit_Clien {
    public static Retrofit retrofit = null;
    public static Retrofit getClient(String baseurl){
        OkHttpClient  builder = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS) // cho phép đọc trong bao lâu. Măc định là 3s
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
