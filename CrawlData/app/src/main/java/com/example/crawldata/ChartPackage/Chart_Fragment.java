package com.example.crawldata.ChartPackage;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crawldata.ScorePackage.CPA;
import com.example.crawldata.IP;
import com.example.crawldata.R;
import com.example.crawldata.ScorePackage.Score;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Chart_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Chart_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Chart_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chart_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Chart_Fragment newInstance(String param1, String param2) {
        Chart_Fragment fragment = new Chart_Fragment();
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
    private List<Score> scores;
    private List<CPA> cpaList;
    private TextView txt;
    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart_, container, false);

        txt = view.findViewById(R.id.textChart);

        new ScoreData().execute(mParam1,mParam2);

        barChart = view.findViewById(R.id.barChartScore);



        return view;

    }

    public class ScoreData extends AsyncTask<String,Void,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> re = new ArrayList<>();
            String result = "No data";
            String number = "No data";
            String user = strings[0];
            String pass = strings[1];

            try {
                client = new Socket(IP.ip,60000);

                PrintWriter pw = new PrintWriter(client.getOutputStream(),true);
                pw.println("score" + ";" + user + ";" + pass);

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
            super.onPostExecute(strings);
            String s = strings.get(0);
            String cpa = strings.get(1);
            scores = new ArrayList<>();
            cpaList = new ArrayList<>();

            if(s.equals("No data") || cpa.equals("No data")){
                txt.setText(s);
            }else{
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

                // Tách cpa
                StringTokenizer st1 = new StringTokenizer(cpa, ";"); // tách thành từng môn một
                String cpaResult = "";
                while (st1.hasMoreTokens()){
                    String rs = st1.nextToken();
                    StringTokenizer sc = new StringTokenizer(rs, ",");
                    if (sc.countTokens() <= 3) {
                        break;
                    }
                    int i = 0;
                    CPA cp = new CPA();
                    while (sc.hasMoreTokens()){
                        if(i == 0){
                            cp.setSemester(sc.nextToken().replace(":"," : "));
                        }
                        else if(i == 1){
                            cp.setMedium_score(sc.nextToken().replace(":"," : "));
                        }
                        else if(i == 2){
                            cp.setAccumulation_score(sc.nextToken().replace(":"," : "));
                        }
                        else if(i == 3){
                            cp.setSotin(sc.nextToken().replace(":"," : "));
                        }
                        else if(i == 4){
                            cp.setSotin_tichluy(sc.nextToken().replace(":"," : "));
                        }
                        i++;

                    }
                    cpaList.add(cp);
                }

                txt.setText(cpaList.get(cpaList.size() - 1) .toString());

                BarDataSet barDataSet = new BarDataSet(getData(scores), "Score");
                barDataSet.setBarBorderWidth(0.9f);
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                BarData barData = new BarData(barDataSet);
                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                final String[] months = new String[]{"A/A+", "B+", "B", "C+", "C", "D+" , "D", "F"};
                IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
                xAxis.setGranularity(1f);
                xAxis.setValueFormatter(formatter);
                barChart.setData(barData);
                barChart.setFitBars(true);
                barChart.animateXY(3000, 3000);
                barChart.invalidate();
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private ArrayList getData(List<Score> scores){
        ArrayList<BarEntry> entries = new ArrayList<>();
        int soA = 0;
        int soB = 0;
        int soC = 0;
        int soD = 0;
        int soB_ = 0;
        int soC_ = 0;
        int soD_ = 0;
        int soF = 0;
        for (int i = 0; i < scores.size(); i++){
            Score score = scores.get(i);
            String res = score.getDiem_he_4();
            if(res != null){ // check != null vì có 1 số môn kì này chưa có điểm
                if (res.equals("A") || res.equals("A+")) soA += score.getSo_tin();
                if (res.equals("B")) soB += score.getSo_tin();
                if (res.equals("B+")) soB_ += score.getSo_tin();
                if (res.equals("C")) soC += score.getSo_tin();
                if (res.equals("C+")) soC_ += score.getSo_tin();
                if (res.equals("D")) soD += score.getSo_tin();
                if (res.equals("D+")) soD_ += score.getSo_tin();
                if (res.equals("F")) soF += score.getSo_tin();
            }

        }
        entries.add(new BarEntry(0f, soA));
        entries.add(new BarEntry(1f, soB_));
        entries.add(new BarEntry(2f, soB));
        entries.add(new BarEntry(3f, soC_));
        entries.add(new BarEntry(4f, soC));
        entries.add(new BarEntry(5f, soD_));
        entries.add(new BarEntry(6f, soD));
        entries.add(new BarEntry(7f, soF));

        return entries;
    }
}