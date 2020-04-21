package com.example.weather_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Web lấy dữ liệu : https://home.openweathermap.org/api_keys
 * https://openweathermap.org/current đọc phần này để biết cách sử dụng
 * tài khoản : banggia1601 - banggia1601@99
 * Nhớ xin quyền internet
 * Sử dụng thư viện của volley và picasso
 * API Key : 12c7d880033f4cf59bec573f12eafe33
 *
 * Activity2 là đọc thời tiết 16 ngày tiếp theo
 * Tuy nhiên cần tài khoản có phí nên sử dụng dịch vụ 5 ngày với mỗi 3h tức là thành 40 object trả về thời gian 3h/1 lân
 */
public class MainActivity extends AppCompatActivity {
    Button btnSearch, btnChange;
    ImageView imgWeather;
    EditText edtSearch;
    TextView txtCity, txtCountry, txtRaindrop, txtCloud, txtWind, txtTime, txtWeather;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        getCurrenWeatherData("Thái Bình");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                if(city.equals("")) city = "Thái Bình";
                getCurrenWeatherData(city);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this,ListWeather_Activity.class);
                intent.putExtra("name",city);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        btnChange = findViewById(R.id.buttonChange);
        btnSearch = findViewById(R.id.buttonSearch);
        imgWeather = findViewById(R.id.ImageViewWeather);
        edtSearch = findViewById(R.id.editTextSearch);
        txtCity = findViewById(R.id.textViewCity);
        txtCountry = findViewById(R.id.Quocgia);
        txtRaindrop = findViewById(R.id.textViewRaindrop);
        txtCloud = findViewById(R.id.textViewCloud);
        txtWind = findViewById(R.id.textViewWind);
        txtTime = findViewById(R.id.textViewDay);
        txtWeather = findViewById(R.id.textViewWeather);
    }

    // Đọc JSON phân tích
    public void getCurrenWeatherData(String data){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=12c7d880033f4cf59bec573f12eafe33";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt"); // thời gian
                            String nameCity = jsonObject.getString("name");
                            txtCity.setText("Tên thành phố : "+nameCity);

                            // Cập nhật thời gian
                            long time = Long.valueOf(day);
                            Date date = new Date(time*1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            txtTime.setText(Day);

                            // Lấy icon thời tiết và trạng thái
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String statues = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + icon + ".png")
                                    .into(imgWeather);
                            txtWeather.setText(statues);

                            //Lấy nhiệt độ, độ ẩm
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String temp = jsonObjectMain.getString("temp");
                            String humidity = jsonObjectMain.getString("humidity");
                            txtRaindrop.setText(humidity + "%");
                            txtWeather.append("\n" + temp + "°C");

                            // Lấy gió
                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String tocdo = jsonObjectWind.getString("speed");
                            txtWind.setText(tocdo + "m/s");

                            // Lấy mây
                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String cloud = jsonObjectCloud.getString("all");
                            txtCloud.setText(cloud + "%");

                            // Lấy tên quốc gia
                            JSONObject jsonObjectCountry = jsonObject.getJSONObject("sys");
                            String country = jsonObjectCountry.getString("country");
                            txtCountry.setText("Quốc gia : " + country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
                }
        });
        queue.add(stringRequest);
    }
}
