package com.example.retrofit_okhttp_web.Retrofit;

import com.example.retrofit_okhttp_web.Sinhvien;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Các phương thức gửi/nhận lên cho server
 */
public interface DataClient {

    @Multipart // Gửi cái gì đó dạng hình ảnh, âm thanh ko phải chuỗi
    @POST("uploadhinhanh.php")      // phương thức gửi
    Call<String>upLoadPhoto(@Part MultipartBody.Part photo); // kiểu dữ liệu mà file php sv gửi về


    // Phương thức post gửi lên server tài khoản, mật khẩu , hình ảnh vào database
    @FormUrlEncoded // gửi dữ liệu dang chuỗi
    @POST("insert.php")
    Call<String> insertData(@Field("taikhoan") String taikhoan, // @field dùng truyền giá trị lên server, taikhoan là key bên file insert.php
                            @Field("matkhau") String matkhau,
                            @Field("hinhanh") String hinhanh);

    // Phương thức gửi tài khoản, mật khẩu để xác nhận đăng nhập
    @FormUrlEncoded
    @POST("login.php")
    Call<List<Sinhvien>> loginData(@Field("taikhoan") String taikhoan,
                                   @Field("matkhau") String matkhau);


    // Truy cập http://www.jsonschema2pojo.org/ và coppy json mẫu về để conver
    /*
    JSON mẫu : [{"Id":"1","Taikhoan":"gahhen","Matkhau":"banggia160199","Hinhanh":"http:\/\/192.168.1.5\/Retrofit_QLSV\/iamge\/image21584980855346.jpg"}]
     */

    //Phương thức delete của get
    @GET("delete.php")
    Call<String> deleteData(@Query("id") String id,
                            @Query("hinhanh") String hinhanh); // "id" là key gửi đi bên file php
        // @Query dùng truyền dữ liệu vào theo kiểu ?id=1&hinhanh=hinhanh... truyền thẳng vào link luôn

    /**
     * Thêm vào giữa đường link
     * GET("users/{user}/repos")
     *   Call<List<Repo>> listRepos(@Path("user") String user);
     */
}
