package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHodelApi jsonPlaceHodelApi;
    Button btnGet, btnGetPID, btnGetUID, btnGetUID_sort,btnGetMuch,btnGetMap, btnGetUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        btnGet = findViewById(R.id.gets);
        btnGetUID = findViewById(R.id.getsUID);
        btnGetPID = findViewById(R.id.getsPID);
        btnGetUID_sort = findViewById(R.id.sort_id);
        btnGetMuch = findViewById(R.id.get_much);
        btnGetMap = findViewById(R.id.get_map);
        btnGetUrl = findViewById(R.id.get_url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHodelApi = retrofit.create(JsonPlaceHodelApi.class);



        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPosts();
            }
        });

        btnGetUID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPostsUserId();
            }
        });

        btnGetPID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComments();
            }
        });

        btnGetUID_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu có userID = 4 và sắp xếp giảm của id
                getCommentUidSort();
            }
        });


        btnGetMuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMuchID();
                // Lấy các id nằm trong mảng (1,3,5)
            }
        });

        btnGetMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCommentMap();
            }
        });

        btnGetUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCommentsUrl();
            }
        });

    }

    private void getCommentsUrl() {
        Call<List<Comment>> call = jsonPlaceHodelApi.getComment("posts/1/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }else{
                    textViewResult.setText("");
                    List<Comment> comments = response.body();
                    for(Comment comment : comments){
                        String content = "";
                        content += "ID: " + comment.getId() + "\n";
                        content += "Post ID: " + comment.getPostId() + "\n";
                        content += "Name: " + comment.getName() + "\n";
                        content += "Email: " + comment.getEmail() + "\n";
                        content += "Text: " + comment.getText() + "\n";
                        textViewResult.append(content + "\n");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getCommentMap() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","desc");
        Call<List<Post>> call = jsonPlaceHodelApi.getPost(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                textViewResult.setText("");
                for (Post post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n";
                    textViewResult.append(content + "\n");

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


    private void getCommentUidSort() {
        Call<List<Post>> call = jsonPlaceHodelApi.getPost(4,"id","desc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                textViewResult.setText("");
                for (Post post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n";
                    textViewResult.append(content + "\n");

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getPostsUserId() {
        Call<List<Post>> call = jsonPlaceHodelApi.getPost(4);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                textViewResult.setText("");
                for (Post post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n";
                    textViewResult.append(content + "\n");

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHodelApi.getComment(2);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }else{
                    textViewResult.setText("");
                    List<Comment> comments = response.body();
                    for(Comment comment : comments){
                        String content = "";
                        content += "ID: " + comment.getId() + "\n";
                        content += "Post ID: " + comment.getPostId() + "\n";
                        content += "Name: " + comment.getName() + "\n";
                        content += "Email: " + comment.getEmail() + "\n";
                        content += "Text: " + comment.getText() + "\n";
                        textViewResult.append(content + "\n");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getPosts() {
        Call<List<Post>> call = jsonPlaceHodelApi.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                textViewResult.setText("");
                List<Post> posts = response.body();
                for (Post post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n";
                    textViewResult.append(content + "\n");

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getMuchID(){
        Call<List<Post>> call = jsonPlaceHodelApi.getPost(new Integer[]{1,3,5},"id","desc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                textViewResult.setText("");
                for (Post post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n";
                    textViewResult.append(content + "\n");

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
