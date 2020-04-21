package com.example.fragment_remove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void AddFragmentA(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FrameLayout, new Fragment_A(),"FragA");
        fragmentTransaction.addToBackStack("aaa");                                           // Đưa vào ngăn xếp để xóa (tên quản lý)
        fragmentTransaction.commit();
    }
    public void AddFragmentB(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FrameLayout, new Fragment_B(),"FragB");
        fragmentTransaction.addToBackStack("bbb");
        fragmentTransaction.commit();
    }
    public void AddFragmentC(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FrameLayout, new Fragment_C(),"FragC"); //  tham số thứ 3 là cái Tag
        fragmentTransaction.addToBackStack("ccc");
        fragmentTransaction.commit();
    }


    public void RemoveFragmentA(View view) {
        Fragment_A fragment_A = (Fragment_A) getSupportFragmentManager().findFragmentByTag("FragA");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragment_A != null){
            fragmentTransaction.remove(fragment_A);
            fragmentTransaction.commit();
        }else{
            Toast.makeText(this, "Fragment_A không tồn tại", Toast.LENGTH_SHORT).show();
        }

    }

    public void RemoveFragmentB(View view) {
        Fragment_B fragment_B = (Fragment_B) getSupportFragmentManager().findFragmentByTag("FragB"); // Anh xạ fragB theo tag
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();                // Khai báo fragTransaction
        if(fragment_B != null){                                                                      // Ktra fragB khác null mới xóa
            fragmentTransaction.remove(fragment_B);                                                  // Xóa fragB
            fragmentTransaction.commit();                                                            // Cập nhật lại sau xóa
        }

    }
    public void RemoveFragmentC(View view) {
        Fragment_C fragment_C = (Fragment_C) getSupportFragmentManager().findFragmentByTag("FragC");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragment_C != null){
            fragmentTransaction.remove(fragment_C);
            fragmentTransaction.commit();
        }

    }

    public void Back(View view) { // Quay trở lại màn hình Fragment lần lượt từ cuối lên
        getSupportFragmentManager().popBackStack();
    }
    public void Pop(View view) {// Quay ngay trở lại màn hình A với aaa là tên đặt cho nó
        getSupportFragmentManager().popBackStack("aaa",0);
    }

    // Bavk bằng phím back trên điện thoại
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }

    }
}
