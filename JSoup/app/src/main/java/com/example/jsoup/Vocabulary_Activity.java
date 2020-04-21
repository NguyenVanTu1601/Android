package com.example.jsoup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Locale;

/**
 * Phân tích file html thấy thẻ tuvung lặp đi lặp lại nên đọc thẻ này trước.Tuy nhiên project này cần mỗi từ nên đọc phần noidung thôi
 */
public class Vocabulary_Activity extends AppCompatActivity {
    ArrayList<Vocabulary> listVocabulary;
    ListView listView;
    Vocabulary_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_);
        Intent intent = getIntent();
        String url = intent.getStringExtra("link");

        listView = findViewById(R.id.listVocabulary);
        listVocabulary = new ArrayList<>();
        adapter = new Vocabulary_adapter(listVocabulary,R.layout.line_vocabulary,Vocabulary_Activity.this);
        getDataVocabulary(url);

        listView.setAdapter(adapter);

    }

    private void getDataVocabulary(String url){
        RequestQueue queue = Volley.newRequestQueue(Vocabulary_Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        if(document != null){
                            Elements elements = document.select("div.noidung");

                            for (Element element : elements){
                                String tu = "";
                                String tuLoai = "";
                                String giaiThich = "";
                                String phienAm = "";

                                tu = element.select("span[style=color: blue; font-size: 16px ! important;]").text();
                                phienAm = element.select("span[style=color: red;]").text();


                                giaiThich = element.select("span[class=bold]").first().text(); // Lấy ra chữ giải thích
                                giaiThich = giaiThich + " " + element.childNode(7).toString(); // lấy ra đoạn chữ phía sau phần giải thích
                                // ChildNode để lại lần lượt các thành phần. Xem thêm :http://vi.voidcc.com/question/p-wpxtbljo-z.html
                                // Tuy nhiên vẫn chưa biết vì sao là childNode(7) và 11


                                tuLoai = element.select("span[class=bold]").get(1).text(); // lấy ra chữ từ loại
                                tuLoai = tuLoai + " " + element.childNode(11).toString();

                                listVocabulary.add(new Vocabulary(tu,phienAm,tuLoai,giaiThich));

                            }
                            adapter.notifyDataSetChanged();
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
