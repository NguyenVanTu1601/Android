package com.example.jsoup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Dùng volley đọc nội dung thẻ html từ url
 * JSOUP giúp ta đọc dữ liệu trong các cặp thẻ html
 * Đọc thông tin và chuyển vào listView, bao gồm thông tin về ảnh và nội dung
 * Các bước :
 * B1. Đọc html về bằng Volley dưới dạng String
 * B2. Chuyển string về dạng doc qua Document doc = JSoup.parse(String)
 */
public class MainActivity extends AppCompatActivity {
    ArrayList<English> listEnglish;
    English_adapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listEnglish = new ArrayList<>();
        adapter = new English_adapter(MainActivity.this, R.layout.line_english,listEnglish);
        listView.setAdapter(adapter);


        String url = "https://600tuvungtoeic.com/";
        readHTML(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Vocabulary_Activity.class);
                intent.putExtra("link", listEnglish.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    public void readHTML(String url){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String ten = "";
                        String hinhanh = "";

                        Document document = Jsoup.parse(response);
                        if(document != null){
                            // Cách chuyển 1 số thẻ html để gọi từ androdi
                            // <td name = "phat"> -> td[name="phat"]
                            // <div class ="phat"> -> div[class = phat] hoặc div.phat

                            // Đọc các thẻ có tên là gallery-item
                            Elements elements = document.select("div.gallery-item"); // Vì sao phải lấy thẻ gallery. Vì thẻ này là thẻ lặp
                            // đi lặp lại của các thành phần


                            // Duyệt for đọc các thẻ phía trong thẻ gallery-item
                            for (Element ele : elements){ // element trong  jsoup.nodes
                                Element elementTen = ele.getElementsByTag("h3").first();
                                Element elementHinh = ele.getElementsByTag("img").first(); // .first là vị trí thẻ bên trong thẻ tên img
                                Element elementDuongdan = ele.getElementsByTag("a").first();

                                // Lấy đường dẫn
                                StringBuilder Duongdan = new StringBuilder(); // đường dẫn tới trang mở rộng
                                Duongdan.append("https://600tuvungtoeic.com/");
                                // Chuyển các cặp thẻ về chuỗi
                                if(elementTen != null){
                                    ten = elementTen.text();
                                }
                                if(elementHinh != null){
                                    // do thẻ hình ảnh chứa thêm nhiều thẻ bên trong nên nếu chỉ lấy đoạn link hình thì
                                    hinhanh = elementHinh.attr("src");
                                }
                                if(elementDuongdan != null){
                                    Duongdan = Duongdan.append(elementDuongdan.attr("href"));
                                }
                                listEnglish.add(new English(hinhanh,ten,Duongdan.toString()));

                            }


                            adapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Not!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(stringRequest);
    }
}
