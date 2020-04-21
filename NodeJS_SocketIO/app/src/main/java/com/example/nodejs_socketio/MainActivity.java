package com.example.nodejs_socketio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity{
    private Socket mSocket;// của socket.io.client
    EditText edtNhap;
    ListView listChat;
    ImageButton imgUser, imgSend;
    ArrayList<String> arrayListChat;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        listChat.setAdapter(adapter);

        // Bước 1 : Kết nối (18->24)
        try {
            mSocket = IO.socket("http://192.168.1.5:3000/"); // kết nối tới đường dẫn localhost
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect(); // tiến hành kết nối


        // Gửi tin nhắn
        sendData();

        // Nhận dữ liệu chat
        mSocket.on("server-send-chat", onListChat ); // gõ tên này trc để tự phát sinh function


        // Đăng kí user
        registerUser();

        // Nhận kq đăng kí user
        mSocket.on("server-send-result", onResult);

        // Nhận danh sách user nếu cần dùng
        //mSocket.on("server-send-danhsach", listUser);


    }

    // Gửi nội dung chat
    private void sendData() {
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtNhap.getText().toString();
                if(s.length()  > 0){
                    mSocket.emit("client-send-chat",s);
                }
            }
        });
    }


    // Nhận dữ liệu nội dung chat
    private Emitter.Listener onListChat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        String tinnhan = object.getString("noidung");
                        arrayListChat.add(tinnhan);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };




    // Dăng kí thành viên
    private void registerUser(){
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNhap.getText().toString().length() > 0){
                    mSocket.emit("client-register-user", edtNhap.getText().toString());
                }
                else{
                    Toast.makeText(MainActivity.this, "Vui lòng nhập trc khi đăng kí thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    // Nhận kq đăng kí tài khoản
    private Emitter.Listener onResult = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        Boolean result = object.getBoolean("ketqua");
                        if(result == false){
                            Toast.makeText(MainActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "User đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    // Nhận danh sách thành viên đăng kí
    private Emitter.Listener listUser = new Emitter.Listener(){
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    ArrayList<String> userName = new ArrayList<>();
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = object.getJSONArray("danhsach");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    userName.clear(); // do mỗi lần truyền về nó truyền ca mảng
                    for (int i = 0; i < jsonArray.length(); i++){
                        try {
                            String uname = jsonArray.getString(i);
                            userName.add(uname);
                            Toast.makeText(MainActivity.this, uname, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    };


    // Anh xạ
    private void Anhxa() {
        edtNhap = findViewById(R.id.editextContent);
        imgUser = findViewById(R.id.user);
        imgSend = findViewById(R.id.send);
        listChat = findViewById(R.id.listViewChat);
        arrayListChat = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListChat);
    }
}
