package com.example.ncov_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    ArrayList<nCov> list_nCov;
    ListView listView;
    ImageButton imgSearch;
    EditText edtNameCountry;
    nCov_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

        String url = "https://corona-api.com/countries?fbclid=IwAR3K-IKHwfwz7AUfJNkpopzpxYnEkeNr1WJRVvvgtyhqmWVwPNtawsSGaBk";
        getData(url);

        adapter = new nCov_Adapter(MainActivity.this,R.layout.covid_line,list_nCov);
        listView.setAdapter(adapter);
        searchData();

    }

    private void Anhxa(){
        list_nCov = new ArrayList<>();
        listView = findViewById(R.id.listViewCovid);
        imgSearch = findViewById(R.id.imageButtonSearch);
        edtNameCountry = findViewById(R.id.editTextNameCountry);

    }

    private void getData(String url){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray arrayCountry = jsonObject.getJSONArray("data");
                    String nameCountry = "";
                    for(int i = 0; i < arrayCountry.length(); i++){

                        JSONObject objectNCOV = arrayCountry.getJSONObject(i);
                        nameCountry = objectNCOV.getString("name");

                        JSONObject objectData = objectNCOV.getJSONObject("latest_data");

                        int deaths = objectData.getInt("deaths");
                        int confirmed = objectData.getInt("confirmed");
                        int recovered = objectData.getInt("recovered");

                        nCov CoVid19 = new nCov(nameCountry,deaths,confirmed,recovered);
                        list_nCov.add(CoVid19);
                    }
                    Collections.sort(list_nCov, new Sort_by_deaths());
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    private void searchData(){
        edtNameCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.equals("")){
                    adapter = new nCov_Adapter(MainActivity.this, R.layout.covid_line,list_nCov);
                    listView.setAdapter(adapter);
                }
                else{

                    // Biến đổi chuỗi viết hoa chữ cái đầu
                    String str = s.toString();
                    if(str.length() >= 2){
                        str = str.substring(0,1).toUpperCase() + str.substring(1);
                    }else{
                        str = str.toUpperCase();
                    }

                    // Tìm kiếm gần đúng
                    String regex = "^" + str; // tìm từ bắt đầu bằng str
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher;
                    ArrayList<nCov> newList = new ArrayList<>();
                    for(int i = 0; i < list_nCov.size(); i++){
                        matcher = pattern.matcher(list_nCov.get(i).getNameCountry());
                        if(matcher.find()){
                            newList.add(list_nCov.get(i));
                        }
                    }
                    nCov_Adapter adapterNew = new nCov_Adapter(MainActivity.this,R.layout.covid_line,newList);
                    adapterNew.notifyDataSetChanged();
                    listView.setAdapter(adapterNew);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
