package com.example.json_language;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageButton imgVN, imgEN;
    TextView txtText;
    String noiDung = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        new JsonLanguage().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo3.json");
        imgVN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readLanguage("vn");
            }
        });

        imgEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readLanguage("en");
            }
        });
    }
    private void Anhxa(){
        imgEN = findViewById(R.id.imgButton);
        imgVN = findViewById(R.id.imageButtonVn);
        txtText = findViewById(R.id.textView);
    }
    private class JsonLanguage extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            noiDung = s;
        }
    }

    private void readLanguage(String s){
        try {
            JSONObject object = new JSONObject(noiDung);
            JSONObject objectLanguage = object.getJSONObject("language");
            JSONObject objectLan = objectLanguage.getJSONObject(s);

            String ten = objectLan.getString("name");
            String diaChi = objectLan.getString("address");
            String KH1 = objectLan.getString("course1");
            String KH2 = objectLan.getString("course2");
            String KH3 = objectLan.getString("course3");
            txtText.setText(ten + "\n" + diaChi + "\n" + KH1 + "\n" + KH2 + "\n" + KH3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
