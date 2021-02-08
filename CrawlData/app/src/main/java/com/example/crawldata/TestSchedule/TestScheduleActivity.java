package com.example.crawldata.TestSchedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.example.crawldata.IP;
import com.example.crawldata.R;
import com.example.crawldata.User;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class TestScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_schedule);
        Intent intent = getIntent();
        User u = (User) intent.getSerializableExtra("user");
        new TestScheduleData().execute(u.getUserName(),u.getPassword());
    }

    // Async lấy dữ liệu lịch thi từ server
    public class TestScheduleData extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = "No data";

            String user = strings[0];
            String pass = strings[1];
            Socket client;
            try {
                client = new Socket(IP.ip,60000);

                PrintWriter pw = new PrintWriter(client.getOutputStream(),true);
                pw.println("testschedule" + ";" + user + ";" + pass);


                DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                result = dataInputStream.readUTF();

                client.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<TestSchedule> listTests = new ArrayList<>();

            String[] ss = s.split(";");
            for(int i = 0; i < ss.length; i++){
                TestSchedule testSchedule = new TestSchedule();
                String[] ts = ss[i].split(",");
                testSchedule.setStt(ts[0]);
                testSchedule.setMaMH(ts[1]);
                testSchedule.setTenMon(ts[2]);
                testSchedule.setNhomLop(ts[3]);
                testSchedule.setTothi(ts[4]);
                testSchedule.setSoluong(ts[5]);
                testSchedule.setNgaythi(ts[6]);
                testSchedule.setGiothi(ts[7]);
                testSchedule.setThoigian(ts[8]);
                testSchedule.setPhong(ts[9]);
                testSchedule.setHinhthuc(ts[10]);

                listTests.add(testSchedule);

            }

            TestSchduleAdapter adapter = new TestSchduleAdapter(getApplicationContext(), R.layout.item_test,listTests);
            ListView listView = findViewById(R.id.listTestItem);
            listView.setAdapter(adapter);


        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}