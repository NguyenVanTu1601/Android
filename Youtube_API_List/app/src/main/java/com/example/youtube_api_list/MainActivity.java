package com.example.youtube_api_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String API_Key = "AIzaSyBcHvwVFxYMBCvR0aGG9Q-hhc77mtv60Pg";
    String ID_PlayList = "PLNmW_nOXKdrlOs35nuf1x53hVE4BBGtXh";
    String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ID_PlayList+
                 "&key=" + API_Key+"&maxResults=50";
    ListView listView;
    ArrayList<VideoYouTube> arrayList;
    YouTube_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listViewVideo);
        arrayList = new ArrayList<>();
        adapter = new YouTube_adapter(this,R.layout.row_youtube,arrayList);
        listView.setAdapter(adapter);
        // ĐocJSON của youtube
        getJSONYoutube(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,PlayVideo_Activity.class);
                intent.putExtra("idVideo",arrayList.get(position).getIdVideo());
                startActivity(intent);
            }
        });
    }


    private void getJSONYoutube(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Phân tích JSON
                String title = "";
                String urlHinh = "";
                String idVideo = "";
                try {
                    JSONArray jsonItems = response.getJSONArray("items");
                    for(int i = 0; i < jsonItems.length(); i++){
                        JSONObject jsonObjectItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSpinnet = jsonObjectItem.getJSONObject("snippet");
                        title = jsonSpinnet.getString("title");

                        JSONObject jsonThumbnail = jsonSpinnet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");

                        urlHinh = jsonMedium.getString("url");

                        JSONObject jsonResourceID  = jsonSpinnet.getJSONObject("resourceId");
                        idVideo = jsonResourceID.getString("videoId");

                        arrayList.add(new VideoYouTube(title,urlHinh,idVideo));
                    }
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
        queue.add(jsonObjectRequest);
    }
}
