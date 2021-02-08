package com.example.crawldata.ScorePackage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crawldata.IP;
import com.example.crawldata.R;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Score_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Score_Fragment() {
        // Required empty public constructor
    }


    public static Score_Fragment newInstance(String param1, String param2) {
        Score_Fragment fragment = new Score_Fragment();
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

    private Socket client;
    private RecyclerView recyclerView;
    private ProgressDialog loadingBar;
    private List<Score> scores;
    private EditText edtSearch;
    private ImageView imgSearch;
    private ScoreAdapter adapter;
    private TextView txtReload;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score_, container, false);
        recyclerView = view.findViewById(R.id.recyclerScore);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        edtSearch = view.findViewById(R.id.edtSearch);
        imgSearch = view.findViewById(R.id.imgSearch);
        txtReload = view.findViewById(R.id.reloadScore);

        loadingBar = new ProgressDialog(getContext());
        loadingBar.setTitle("Đang lấy dữ liệu...");
        loadingBar.setMessage("Chờ trong giây lát..");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        new ScoreData().execute(mParam1,mParam2,"1");

        // click reload điểm
        txtReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar = new ProgressDialog(getContext());
                loadingBar.setTitle("Đang lấy dữ liệu...");
                loadingBar.setMessage("Chờ trong giây lát..");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();
                scores = new ArrayList<>();
                adapter = new ScoreAdapter(scores, getContext());
                recyclerView.setAdapter(adapter);

                new ScoreData().execute(mParam1,mParam2,"2");
            }
        });

        return view;
    }

    // gửi yêu cầu và lấy điểm
    public class ScoreData extends AsyncTask<String,Void,ArrayList<String>> {


        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> re = new ArrayList<>();
            String result = "No data";
            String number = "";
            String user = strings[0];
            String pass = strings[1];
            String type = strings[2];
            try {
                client = new Socket(IP.ip,60000);

                PrintWriter pw = new PrintWriter(client.getOutputStream(),true);
                if (type.equals("1")){
                    pw.println("score" + ";" + user + ";" + pass);
                }else{
                    pw.println("reload" + ";" + user + ";" + pass);
                }

                DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                result = dataInputStream.readUTF();
                number = dataInputStream.readUTF();

                client.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            re.add(result);
            re.add(number);
            return re;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            // Gắn liền và là UI Main Thread luôn
            super.onPostExecute(strings);
            String s = strings.get(0);
            String num = strings.get(1);
            scores = new ArrayList<>();

            // tách điểm
            StringTokenizer st = new StringTokenizer(s, ","); // tách thành từng môn một

            while (st.hasMoreTokens()){
                String rs = st.nextToken();
                StringTokenizer sc = new StringTokenizer(rs, ":");
                int i = 0; // kiểm tra xem đang lấy vị trí nào của token
                Score score = new Score();
                while (sc.hasMoreTokens()){
                    if(i == 0){
                        score.setTen_mon(sc.nextToken());
                    }
                    else if(i == 1){
                        score.setSo_tin(Integer.parseInt(sc.nextToken()));
                    }
                    else if(i == 2){
                        score.setDiem_he_10(Double.parseDouble(sc.nextToken()));
                    }
                    else if(i == 3){
                        score.setDiem_he_4(sc.nextToken());
                    }
                    i++;
                }
                scores.add(score);
            }

            loadingBar.dismiss();

            adapter = new ScoreAdapter(scores, getContext());
            recyclerView.setAdapter(adapter);

            searchScore();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    // Tìm kiếm điểm môn học theo tên môn
    public void searchSubject(String str){
        String regex = ".*" + str.toLowerCase(); // tìm từ bắt đầu bằng str
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        ArrayList<Score> newList = new ArrayList<>();
        for(int i = 0; i < scores.size(); i++){
            matcher = pattern.matcher(scores.get(i).getTen_mon().toLowerCase());
            if(matcher.find()){
                newList.add(scores.get(i));
            }
        }

        adapter = new ScoreAdapter(newList, getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void searchScore(){
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")){
                    adapter = new ScoreAdapter(scores, getContext());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }else{
                    searchSubject(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}