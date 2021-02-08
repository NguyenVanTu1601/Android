package com.example.crawldata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private EditText edtUser, edtPass;
    private Button btnLogin;
    private Socket client;
    private User user;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUser = findViewById(R.id.username);
        edtPass = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        loadingBar = new ProgressDialog(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textUser = edtUser.getText().toString();
                String textPass = edtPass.getText().toString();

                user = new User();
                user.setUserName(textUser);
                user.setPassword(textPass);

                loadingBar.setTitle("Đang đăng nhập...");
                loadingBar.setMessage("Chờ trong giây lát..");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();

                new Login().execute(user.getUserName(),user.getPassword());
            }
        });
    }

    // kiểm tra đăng nhập
    public class Login extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "No Login"; // login ko được do server chưa bật thì báo lại cho bên onPostExecute()

            String user = strings[0];
            String pass = strings[1];

            try {
                client = new Socket(IP.ip,60000);

                PrintWriter pw = new PrintWriter(client.getOutputStream(),true);
                pw.println("login" + ";" + user + ";" + pass);

                DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                result = dataInputStream.readUTF();

                client.close();
            }catch (Exception ex){
                ex.printStackTrace();

            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("Quên mật khẩu!:")){
                loadingBar.dismiss();
                Toast.makeText(MainActivity.this, "Sai thông tin đăng nhập...", Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("No Login")){
                loadingBar.dismiss();
                Toast.makeText(MainActivity.this, "Login thất bại", Toast.LENGTH_SHORT).show();
            }else{
                StringTokenizer st = new StringTokenizer(s, ":");
                List<String> listInfo = new ArrayList<>();
                while (st.hasMoreTokens()){
                    listInfo.add(st.nextToken());
                }
                user.setMaSV(listInfo.get(0));
                user.setTenSV(listInfo.get(1));
                user.setGioitinh(listInfo.get(2));
                user.setLop(listInfo.get(3));
                user.setNganh(listInfo.get(4));
                user.setKhoa(listInfo.get(5));
                user.setHedaotao(listInfo.get(6));
                user.setKhoahoc(listInfo.get(7));
                user.setCovan(listInfo.get(8));


                if(!user.getTenSV().equals("")){
                    loadingBar.dismiss();
                    //Toast.makeText(MainActivity.this, "Login thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }else{
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Login thất bại", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}