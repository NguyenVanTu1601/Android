package com.example.ncovi_2020.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ncovi_2020.R;
import com.example.ncovi_2020.World.Sort_by_deaths;
import com.example.ncovi_2020.World.nCov_World;
import com.example.ncovi_2020.World.nCov_World_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Thegioi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Thegioi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Thegioi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Thegioi.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Thegioi newInstance(String param1, String param2) {
        Fragment_Thegioi fragment = new Fragment_Thegioi();
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

    private ArrayList<nCov_World> list_nCov;
    private ListView listView;
    private ImageButton imgSearch;
    private EditText edtNameCountry;
    private nCov_World_Adapter adapter;
    private ProgressDialog loadingBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment__thegioi, container, false);
        Anhxa(view);

        String url = "https://corona-api.com/countries?fbclid=IwAR3K-IKHwfwz7AUfJNkpopzpxYnEkeNr1WJRVvvgtyhqmWVwPNtawsSGaBk";


        getData(url);

        adapter = new nCov_World_Adapter(getContext(),R.layout.line_covid_world,list_nCov);
        listView.setAdapter(adapter);
        searchData();

        return view;
    }

    private void Anhxa(View view){
        list_nCov = new ArrayList<>();
        listView = view.findViewById(R.id.listViewCovid);
        imgSearch = view.findViewById(R.id.imageButtonSearch);
        edtNameCountry = view.findViewById(R.id.editTextNameCountry);

        loadingBar = new ProgressDialog(getContext());
        loadingBar.setTitle("Load dữ liệu... ");
        loadingBar.setMessage("Đang cập nhật dữ liệu...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    private void getData(String url){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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

                        nCov_World CoVid19 = new nCov_World(nameCountry,deaths,confirmed,recovered);
                        list_nCov.add(CoVid19);
                    }
                    Collections.sort(list_nCov, new Sort_by_deaths());
                    adapter.notifyDataSetChanged();
                    loadingBar.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingBar.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Load dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
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
                    adapter = new nCov_World_Adapter(getContext(), R.layout.line_covid_world,list_nCov);
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
                    ArrayList<nCov_World> newList = new ArrayList<>();
                    for(int i = 0; i < list_nCov.size(); i++){
                        matcher = pattern.matcher(list_nCov.get(i).getNameCountry());
                        if(matcher.find()){
                            newList.add(list_nCov.get(i));
                        }
                    }
                    nCov_World_Adapter adapterNew = new nCov_World_Adapter(getActivity(),R.layout.line_covid_world,newList);
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