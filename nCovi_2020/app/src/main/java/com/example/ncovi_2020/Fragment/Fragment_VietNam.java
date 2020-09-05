package com.example.ncovi_2020.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ncovi_2020.R;
import com.example.ncovi_2020.VietNam.Thongke;
import com.example.ncovi_2020.World.Sort_by_deaths;
import com.example.ncovi_2020.World.nCov_World;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_VietNam#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_VietNam extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_VietNam() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_VietNam.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_VietNam newInstance(String param1, String param2) {
        Fragment_VietNam fragment = new Fragment_VietNam();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private ProgressDialog loadingBar;
    private ArrayList<Thongke> listTK;
    private TextView VN_Socanhiem, VN_Dangchuatri, VN_Dakhoi, VN_Tuvong;
    private TextView TG_Socanhiem, TG_Dangchuatri, TG_Dakhoi, TG_Tuvong;
    private TextView ncov_Chitiet;
    private BarChart barChartVietNam, barChartTheGioi;
    private String cacheFileName = "tnCovVN.DAT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment__viet_nam, container, false);

        // Lấy thông tin chung về số lượng ca nhiễm, tử vong
        String api = "https://code.junookyo.xyz/api/ncov-moh/data.json?fbclid=IwAR0fnFMUX_n0Pi" +
                "_25Kxf62PIoVJZE2QANVGnARqkGmx3Cm8afauLqgieXio";

        String url = "https://corona.kompa.ai/?fbclid=IwAR1FEv3aGPdCWOhw63uhusdJwWP9QSbx_Qsi1_Glc0eG5Rysab8o2fCyn0U";


        initialization(view);

        getData(api);



//        ncov_Chitiet.setPaintFlags(ncov_Chitiet.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        ncov_Chitiet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String PageURL = "https://ncov.moh.gov.vn/";
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PageURL));
//                startActivity(browserIntent);
//            }
//        });


        return view;
    }

    private void initialization(View view){
        VN_Socanhiem = view.findViewById(R.id.VN_Socanhiem);
        VN_Dangchuatri = view.findViewById(R.id.VN_Dangdieutri);
        VN_Dakhoi = view.findViewById(R.id.VN_Dakhoi);
        VN_Tuvong = view.findViewById(R.id.VN_TuVong);

        TG_Socanhiem = view.findViewById(R.id.TG_Socanhiem);
        TG_Dangchuatri = view.findViewById(R.id.TG_Dangdieutri);
        TG_Dakhoi = view.findViewById(R.id.TG_Dakhoi);
        TG_Tuvong = view.findViewById(R.id.TG_Tuvong);

        ncov_Chitiet = view.findViewById(R.id.ncov_Chitiet);
        barChartVietNam = view.findViewById(R.id.barCharVietNam);
        barChartTheGioi = view.findViewById(R.id.barCharThegioi);



        loadingBar = new ProgressDialog(getContext());
        loadingBar.setTitle("Load dữ liệu... ");
        loadingBar.setMessage("Đang cập nhật dữ liệu từ Bộ Y Tế...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        listTK = new ArrayList<>();
    }

    private void setData(ArrayList<Thongke> listTK){
        if(listTK.size() != 0){
            VN_Socanhiem.setText("Số ca nhiễm : " + listTK.get(1).getSoCaNhiem());
            VN_Dangchuatri.setText("Đang điều trị : " + listTK.get(1).getDangDieuTri());
            VN_Dakhoi.setText("Đã khỏi : " + listTK.get(1).getKhoi());
            VN_Tuvong.setText("Tử vong : " + listTK.get(1).getTuVong());
            createChar(listTK.get(1), barChartVietNam);

            TG_Socanhiem.setText("Số ca nhiễm : " + listTK.get(0).getSoCaNhiem());
            TG_Dangchuatri.setText("Đang điều trị : " + listTK.get(0).getDangDieuTri());
            TG_Dakhoi.setText("Đã khỏi : " + listTK.get(0).getKhoi());
            TG_Tuvong.setText("Tử vong : " + listTK.get(0).getTuVong());
            createChar(listTK.get(0), barChartTheGioi);
        }
    }

    private void getData(String url){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Ktra link có dữ liệu hay không
                    String check = jsonObject.getString("success");
                    if(check.equals("false")){
                        return;
                    }


                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    JSONObject jsonGlobal = jsonData.getJSONObject("global");
                    JSONObject jsonVietNam = jsonData.getJSONObject("vietnam");

                    int cases = 0;
                    int deaths = 0;
                    int recovered = 0;
                    int treatment = 0;

                    cases = Integer.parseInt(jsonGlobal.getString("cases"));
                    deaths = Integer.parseInt(jsonGlobal.getString("deaths"));
                    recovered = Integer.parseInt(jsonGlobal.getString("recovered"));
                    treatment = cases - deaths - recovered;

                    Thongke thongke = new Thongke("Thế giới",cases, treatment, recovered,deaths);
                    listTK.add(thongke);

                    cases= Integer.parseInt(jsonVietNam.getString("cases"));
                    deaths = Integer.parseInt(jsonVietNam.getString("deaths"));
                    recovered = Integer.parseInt(jsonVietNam.getString("recovered"));
                    treatment = cases - deaths - recovered;

                    thongke = new Thongke("Việt Nam",cases, treatment, recovered,deaths);
                    listTK.add(thongke);


                    setData(listTK);
                    loadingBar.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingBar.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi load dữ liệu...", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        });
        queue.add(stringRequest);
    }

    private void createChar(Thongke thongke, BarChart barChart){

        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(1f, thongke.getDangDieuTri()));
        visitor.add(new BarEntry(2f, thongke.getKhoi()));
        visitor.add(new BarEntry(3f, thongke.getTuVong()));
        visitor.add(new BarEntry(4f, thongke.getSoCaNhiem()));

        BarDataSet barDataSet = new BarDataSet(visitor, "Visitor");
        int[] color = {Color.rgb(255,152,0), Color.rgb(75,241,169),
                Color.rgb(241,10,10), Color.rgb(154,18,65)};
        barDataSet.setColors(color);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText(thongke.getTen());
        barChart.animateY(2000); // tốc độ hiện biểu đồ
    }

}