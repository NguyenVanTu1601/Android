package com.example.retrofit_post;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private JsonPlaceHodelApi jsonPlaceHodelApi;
    private TextView textViewResult;
    private Button btnPost, btnPostField,btnPostFieldMap, btnPut, btnPatch, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.textResult);
        btnPost = findViewById(R.id.post);
        btnPostField = findViewById(R.id.postField);
        btnPostFieldMap = findViewById(R.id.postFieldMap);
        btnPut = findViewById(R.id.put);
        btnPatch = findViewById(R.id.patch);
        btnDelete = findViewById(R.id.delete);

        Gson gson = new GsonBuilder().serializeNulls().create(); // hiện thông báo cả những dữ liệu null khi loggingHttp

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() { // cái này có k cần lắm
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Interceptor-Header", "xyz")
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        jsonPlaceHodelApi = retrofit.create(JsonPlaceHodelApi.class);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();

            }
        });

        btnPostField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPostField();
            }
        });

        btnPostFieldMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPostFieldMap();
            }
        });

        btnPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Put();
            }
        });

        btnPatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patch();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePost();
            }
        });
    }

    private void createPostFieldMap() {
        Map<String, String> fields = new HashMap<>();
        fields.put("userId","25");
        fields.put("title","New Title");
        fields.put("body", "New Text");

        Call<Post> call = jsonPlaceHodelApi.createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                Post postReponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postReponse.getId() + "\n";
                content += "User ID: " + postReponse.getUserId() + "\n";
                content += "Title: " + postReponse.getTitle() + "\n";
                content += "Text: " + postReponse.getText() + "\n";
                textViewResult.setText(content + "\n");

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPostField() {
        Call<Post> call = jsonPlaceHodelApi.createPost(23,"New Title", "New Body");
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                Post postReponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postReponse.getId() + "\n";
                content += "User ID: " + postReponse.getUserId() + "\n";
                content += "Title: " + postReponse.getTitle() + "\n";
                content += "Text: " + postReponse.getText() + "\n";
                textViewResult.setText(content + "\n");

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Post post = new Post(23,"New title","New Text");

        Call<Post> call = jsonPlaceHodelApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                Post postReponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postReponse.getId() + "\n";
                content += "User ID: " + postReponse.getUserId() + "\n";
                content += "Title: " + postReponse.getTitle() + "\n";
                content += "Text: " + postReponse.getText() + "\n";
                textViewResult.setText(content + "\n");

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
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


    // Put : thay đổi toàn bộ đối tượng đã chọn, dù thuộc tính gửi lên có hay ko
    private void Put(){
        Post post = new Post(12,null,"New Text");

        Call<Post> call = jsonPlaceHodelApi.putPost("abc",5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                Post postReponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postReponse.getId() + "\n";
                content += "User ID: " + postReponse.getUserId() + "\n";
                content += "Title: " + postReponse.getTitle() + "\n";
                content += "Text: " + postReponse.getText() + "\n";
                textViewResult.setText(content + "\n");

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    // patch chỉ thay đổi các thuộc tính thay đổi so với bản cũ của đối tương
    private void Patch(){
        Post post = new Post(12,null,"New Text");

        Map<String, String> map = new HashMap<>();
        map.put("Map-Headers1","def");
        map.put("Map-Headers2","ghi");
        Call<Post> call = jsonPlaceHodelApi.patchPost(map,5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                Post postReponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postReponse.getId() + "\n";
                content += "User ID: " + postReponse.getUserId() + "\n";
                content += "Title: " + postReponse.getTitle() + "\n";
                content += "Text: " + postReponse.getText() + "\n";
                textViewResult.setText(content + "\n");

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost(){
        Call<Void> call = jsonPlaceHodelApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
