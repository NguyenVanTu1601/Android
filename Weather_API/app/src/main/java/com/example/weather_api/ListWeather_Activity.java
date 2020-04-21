package com.example.weather_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListWeather_Activity extends AppCompatActivity {
    ArrayList<weather> listWeather;
    ListView listView;
    TextView txtName;
    weather_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_weather_);
        listWeather = new ArrayList<weather>();
        listView = findViewById(R.id.listViewWeather);
        txtName = findViewById(R.id.nameCity);

        // Nhận dữ liệu từ MainActivity
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        if(city.equals("")){
            city = "Thái Bình";
        }
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+ city
                + "&units=metric&appid=12c7d880033f4cf59bec573f12eafe33&cnt=8";

        txtName.setText(city);

        // Khởi tạo thông số cho listView
        getData(url);
        adapter = new weather_adapter(ListWeather_Activity.this,R.layout.line_weather,listWeather);
        listView.setAdapter(adapter);

    }

    private void getData(String url){
        RequestQueue queue = Volley.newRequestQueue(ListWeather_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");


                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                                String time = jsonObjectList.getString("dt_txt");


                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                                String temp_min = jsonObjectMain.getString("temp_min");
                                String temp_max = jsonObjectMain.getString("temp_max");

                                JSONArray jsonArrayW = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayW.getJSONObject(0);
                                String description = jsonObjectWeather.getString("description"); // thời tiết lúc này
                                String icon = jsonObjectWeather.getString("icon");
                                weather w = new weather(description,time,temp_min,temp_max,icon);
                                listWeather.add(w);
                             }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(stringRequest);
    }

}
