package com.example.retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHodelApi {

    // https://jsonplaceholder.typicode.com/

    @GET("posts") // do phương thức trả về từ web api là GET và có tên posts (...com/posts)
    Call<List<Post>> getPost();

    @GET("posts") // phương thức có dạng : ...com/posts?userId=1 sẽ truy vấn với @Query
    Call<List<Post>> getPost(@Query("userId") int userId); // userId phải viết đúng theo tên trên truy vấn , viết userID hay tương tự cũng sai

    @GET("posts") // dùng khi phương thức có dạng : ...com/posts?userId=1 sẽ truy vấn với @Query
    Call<List<Post>> getPost(@Query("userId") int userId, // hoặc Integer userId
                             @Query("_sort") String sort,
                             @Query("_order") String order);
    // Lấy dữ liệu theo 1 userID và sắp xếp theo sort(id), chiều order(desc)
    // https://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc : tuy nhiên phần & sort tự thêm vào, ko có trong truy vấn gốc


    @GET("posts") // dùng khi phương thức có dạng : ...com/posts?userId=1 sẽ truy vấn với @Query
    Call<List<Post>> getPost(@Query("userId") Integer[] userId, // Lấy thông tin khi userId nằm trong mảng
                             @Query("_sort") String sort,
                             @Query("_order") String order);
    // Lấy dữ liệu theo 1 tập userId


    @GET("posts") // truy vấn kiểu này khi có dạng ...com/posts?userId=1
    Call<List<Post>> getPost(@QueryMap Map<String,String> parameters);
    // Map này chứa thông tin : key là tên khóa vd userId, values là là giá trị vd 1 <=> tìm userId = 1


    @GET("posts/{id}/comments") // do các phương thức trả về có dạng: ...com/posts/id/comments : lấy kq theo postid
    Call<List<Comment>> getComment(@Path("id") int postId);


    @GET // dạng : ...com/posts/1/comments
    Call<List<Comment>> getComment(@Url String Url);

}
