package com.example.crawldata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crawldata.ChartPackage.Chart_Fragment;
import com.example.crawldata.SchedulePackage.Schedule_Fragment;
import com.example.crawldata.ScorePackage.Score_Fragment;
import com.example.crawldata.TestSchedule.TestScheduleActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;
    private User u;
    private String user, pass;
    private DrawerLayout drawerLayout;
    private CircleImageView imageDrawer;
    private TextView textMSVDrawer;
    private TextView textNameDrawer;
    private AlertDialog dialogShowInfo, dialogShowFee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        u = (User) getIntent().getSerializableExtra("user");

        user = u.getUserName();
        pass = u.getPassword();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Anh xạ Navigation
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        imageDrawer = header.findViewById(R.id.image_drawer);
        textMSVDrawer = header.findViewById(R.id.name_drawer);
        textNameDrawer = header.findViewById(R.id.status_drawer);

        textMSVDrawer.setText(u.getMaSV());
        textNameDrawer.setText(u.getTenSV());

        loadFragment(Schedule_Fragment.newInstance(user, pass));

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.schedule:
                    fragment = Schedule_Fragment.newInstance(user, pass);
                    loadFragment(fragment);
                    return true;
                case R.id.score:
                    fragment = Score_Fragment.newInstance(user, pass);
                    loadFragment(fragment);
                    return true;
                case R.id.chart:
                    fragment = Chart_Fragment.newInstance(user, pass);
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayoutMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.viewInfo:
                showDialog(u);
                break;
            case R.id.owe:
                Intent intent = new Intent(this, TestScheduleActivity.class);
                intent.putExtra("user", u);
                startActivity(intent);
                break;
            case R.id.fee:
                Toast.makeText(this, "Xem học phí!", Toast.LENGTH_SHORT).show();
                new FeeData().execute(u.getUserName(), u.getPassword());

                break;
            case R.id.logout:
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    // dialog thông tin cá nhân
    private void showDialog(User user){
        if(dialogShowInfo == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_info,
                    (ViewGroup) this.findViewById(R.id.layout_info), false);
            builder.setView(view);
            dialogShowInfo = builder.create();
            if(dialogShowInfo.getWindow() != null){
                dialogShowInfo.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            TextView txtMSV, txtTenSV, txtGioitinh, txtLop, txtNganh,txtKhoa, txtHedaotao,txtCovan,txtKhoahoc;
            txtMSV = view.findViewById(R.id.msv_info);
            txtTenSV = view.findViewById(R.id.tensv_info);
            txtGioitinh = view.findViewById(R.id.gioitinh_info);
            txtLop = view.findViewById(R.id.lop_info);
            txtNganh = view.findViewById(R.id.nganh_info);
            txtKhoa = view.findViewById(R.id.khoa_info);
            txtHedaotao = view.findViewById(R.id.he_info);
            txtKhoahoc = view.findViewById(R.id.khoahoc_info);
            txtCovan = view.findViewById(R.id.covan_info);

            txtMSV.setText(user.getMaSV());
            txtTenSV.setText(user.getTenSV());
            txtGioitinh.setText(user.getGioitinh());
            txtLop.setText(user.getLop());
            txtNganh.setText(user.getNganh());
            txtKhoa.setText(user.getKhoa());
            txtHedaotao.setText(user.getHedaotao());
            txtKhoahoc.setText(user.getKhoahoc());
            txtCovan.setText(user.getCovan());


        }
        dialogShowInfo.show();

    }

    // dialog học phí
    private void showFeeDialog(String[] ss){
        if(dialogShowFee == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_fee,
                    (ViewGroup) this.findViewById(R.id.layout_fee), false);
            builder.setView(view);
            dialogShowFee = builder.create();
            if (dialogShowFee.getWindow() != null) {
                dialogShowFee.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            TextView txt1, txt2, txt3, txt4, txt5;
            txt1 = view.findViewById(R.id.fee_1);
            txt2 = view.findViewById(R.id.fee_2);
            txt3 = view.findViewById(R.id.fee_3);
            txt4 = view.findViewById(R.id.fee_4);
            txt5 = view.findViewById(R.id.fee_5);

            txt1.setText(ss[0]);
            txt2.setText(ss[1]);
            txt3.setText(ss[2]);
            txt4.setText(ss[3]);
            txt5.setText(ss[4]);

        }
        dialogShowFee.show();
    }

    // Lấy thông tin học phí
    public class FeeData extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String result = "No data";

            String user = strings[0];
            String pass = strings[1];
            Socket client;
            try {
                client = new Socket(IP.ip,60000);

                PrintWriter pw = new PrintWriter(client.getOutputStream(),true);
                pw.println("fee" + ";" + user + ";" + pass);


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
            String[] ss = s.split("-");
            showFeeDialog(ss);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}