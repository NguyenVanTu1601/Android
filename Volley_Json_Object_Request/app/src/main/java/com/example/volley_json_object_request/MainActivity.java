package com.example.volley_json_object_request;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button btnClick;
    StringBuilder content = new StringBuilder();
    TextView txtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClick = findViewById(R.id.button);
        txtContent = findViewById(R.id.textView);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://khoapham.vn/KhoaPhamTraining/json/tien/demo1.json";
        JsonObjectRequest object = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String monhoc = response.getString("monhoc");
                            String noihoc = response.getString("noihoc");
                            String website = response.getString("website");
                            String fanpage = response.getString("fanpage");
                            String logo = response.getString("logo");
                            content.append(monhoc + "\n" + noihoc + "\n" + website + "\n" + fanpage + "\n"+ logo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Err", "Lỗi : " + error.toString());
                    }
                });
        queue.add(object);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtContent.setText(content);
            }
        });
    }
}
