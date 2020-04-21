package com.example.webservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Khi click vào button xóa mà nó còn tăng thêm số lượng thì trong hàm getData trước khi for ta phải gọi arrayinhvien.clear để làm sạch array
 */
public class MainActivity extends AppCompatActivity {
    ArrayList<SinhVien> arraySinhVien;
    ListView listView;
    SinhVien_Adapter adapter;
    String urlDelete = "http://192.168.1.5/android_webservice/deleteData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "http://192.168.1.5/android_webservice/getData.php";
        arraySinhVien = new ArrayList<>();
        listView = findViewById(R.id.listViewSinhVien);
        adapter = new SinhVien_Adapter(this, R.layout.line_sinhvien,arraySinhVien);
        listView.setAdapter(adapter);
        getData(url);

    }
    private void getData(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        final JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arraySinhVien.clear(); // nên gọi
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arraySinhVien.add(new SinhVien(object.getInt("ID"),
                                        object.getString("HoTen"),
                                        object.getInt("NamSinh"),
                                        object.getString("DiaChi")));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(arrayRequest);
    }

    // gán menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_student,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // bắt sự kiện menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_add_student){
            startActivity(new Intent(MainActivity.this,Add_Sinhvien_Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }



    public void DeleteStudent(final int id){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success!")){
                    Toast.makeText(MainActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    getData("http://192.168.1.5/android_webservice/getData.php");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idSV",String.valueOf(id));
                return params;
            }
        };
        queue.add(stringRequest);
    }

}
