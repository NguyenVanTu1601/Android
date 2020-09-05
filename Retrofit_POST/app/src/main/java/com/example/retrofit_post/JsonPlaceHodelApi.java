package com.example.retrofit_post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHodelApi {

    @GET("posts") // do phương thức trả về từ web api là GET và có tên posts (...com/posts)
    Call<List<Post>> getPost();

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@Field("userId") int userId,
                          @Field("title") String title,
                          @Field("body") String text);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String,String> fields);


    // Bỏ các dòng có header nếu k cần
    @Headers({"Static-Header1: 123","Static-Header2: 456"})
    @PUT("posts/{id}") // dạng ...../post/2
    Call<Post>putPost(@Header("Dynamic-Header") String header,
                      @Path("id") int id,
                      @Body Post post);


    @PATCH("posts/{id}") // dạng ...../post/2
    Call<Post>patchPost(@HeaderMap Map<String, String> headers,
                        @Path("id") int id,
                        @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
