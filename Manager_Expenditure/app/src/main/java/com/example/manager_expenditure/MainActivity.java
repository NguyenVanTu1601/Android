package com.example.manager_expenditure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.manager_expenditure.adapter.PagerAdapter;
import com.example.manager_expenditure.category.CategoryFragment;
import com.example.manager_expenditure.category.EditCategoryFragment;
import com.example.manager_expenditure.credit.AccountFragment;
import com.example.manager_expenditure.credit.NewCreditActivity;
import com.example.manager_expenditure.deal.DealFragment;
import com.example.manager_expenditure.overview.OverViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private ImageView imgIcon; // icon dấu + trên actionBar
    private static int tabPosition = 0; // xem đang ở tab Tài khoản, danh mục hay gì
    private ImageView imgMenu; // icon menu trên actionBar
    private AlertDialog dialogNewCredit; // dialogThemtaikhoan

    private TextView textDescriptionCategory; // text hiện ra khi click vào icon sửa của tab category
    private LinearLayout descriptionCategory; // Đoạn text mặc định của tab category
    public  boolean isEditCategory = false;

    private DrawerLayout layoutMain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgIcon = findViewById(R.id.imgIcon);
        imgMenu = findViewById(R.id.menu);
        layoutMain = findViewById(R.id.layout_main);

        textDescriptionCategory = findViewById(R.id.text_description_category);
        descriptionCategory = findViewById(R.id.description_Category);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new AccountFragment());

        // Anh xa drawer, va toggle điều khiển đóng mở
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layoutMain, null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        layoutMain.addDrawerListener(toggle);
        toggle.syncState();

        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabPosition == 0){
                    showAlertDialogCredit();
                }
                else if(tabPosition == 1){
                    descriptionCategory.setVisibility(View.GONE);
                    textDescriptionCategory.setVisibility(View.VISIBLE);
                    imgMenu.setImageResource(R.drawable.ic__backspace);
                    imgIcon.setVisibility(View.INVISIBLE);
                    isEditCategory = true;

                    // Truyền tham số sang Fragment
                    loadFragment(EditCategoryFragment.newInstance(isEditCategory));



                }else if(tabPosition == 2){

                }else if(tabPosition == 3){

                }else{

                }
            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabPosition == 1 && isEditCategory == true){

                    // Back khỏi giao diện chỉnh sửa
                    descriptionCategory.setVisibility(View.VISIBLE);
                    textDescriptionCategory.setVisibility(View.GONE);
                    imgMenu.setImageResource(R.drawable.ic_menu);
                    imgIcon.setVisibility(View.VISIBLE);
                    isEditCategory = false;
                    // Truyền tham số sang Fragment
                    CategoryFragment fragment = new CategoryFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutMain,fragment).commit();

                }else{
                    layoutMain.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.main_credit:
                    fragment = new AccountFragment();
                    loadFragment(fragment);

                    imgIcon.setImageResource(R.drawable.ic_add);
                    imgIcon.setVisibility(View.VISIBLE);
                    imgMenu.setImageResource(R.drawable.ic_menu);
                    textDescriptionCategory.setVisibility(View.GONE);
                    descriptionCategory.setVisibility(View.VISIBLE);
                    tabPosition = 0;

                    return true;
                case R.id.main_category:
                    fragment = new CategoryFragment();
                    loadFragment(fragment);

                    imgIcon.setImageResource(R.drawable.ic_create);
                    imgMenu.setImageResource(R.drawable.ic_menu);
                    tabPosition = 1;

                    return true;
                case R.id.main_deal:
                    fragment = new DealFragment();
                    loadFragment(fragment);

                    imgIcon.setImageResource(R.drawable.ic_search);
                    imgIcon.setVisibility(View.VISIBLE);
                    imgMenu.setImageResource(R.drawable.ic_menu);
                    textDescriptionCategory.setVisibility(View.GONE);
                    descriptionCategory.setVisibility(View.VISIBLE);
                    tabPosition = 2;

                    return true;
                case R.id.main_overview:
                    fragment = new OverViewFragment();
                    loadFragment(fragment);

                    imgIcon.setImageResource(R.drawable.ic_calendar);
                    imgIcon.setVisibility(View.VISIBLE);
                    imgMenu.setImageResource(R.drawable.ic_menu);
                    textDescriptionCategory.setVisibility(View.GONE);
                    descriptionCategory.setVisibility(View.VISIBLE);
                    tabPosition = 3;

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

    private void showAlertDialogCredit(){
        if(dialogNewCredit == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View view = LayoutInflater.from(this).inflate(R.layout.layout_alert_new_credit,
                    (ViewGroup) findViewById(R.id.alertNewCredit), false);
            builder.setView(view);
            dialogNewCredit = builder.create();

//            dialogNewCredit.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            WindowManager.LayoutParams wmlp = dialogNewCredit.getWindow().getAttributes();
//            wmlp.gravity = Gravity.TOP | Gravity.RIGHT;

            view.findViewById(R.id.radioThuongxuyen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.findViewById(R.id.checkRadio1).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.checkRadio2).setVisibility(View.GONE);
                    view.findViewById(R.id.checkRadio3).setVisibility(View.GONE);
                    Intent intent = new Intent(MainActivity.this, NewCreditActivity.class);
                    intent.putExtra("credit", "Thường xuyên");
                    startActivity(intent);
                    dialogNewCredit.dismiss();
                }
            });

            view.findViewById(R.id.radioNo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.findViewById(R.id.checkRadio1).setVisibility(View.GONE);
                    view.findViewById(R.id.checkRadio2).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.checkRadio3).setVisibility(View.GONE);
                    Intent intent = new Intent(MainActivity.this, NewCreditActivity.class);
                    intent.putExtra("credit", "Nợ");
                    startActivity(intent);
                    dialogNewCredit.dismiss();
                }
            });

            view.findViewById(R.id.radioTietkiem).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.findViewById(R.id.checkRadio1).setVisibility(View.GONE);
                    view.findViewById(R.id.checkRadio2).setVisibility(View.GONE);
                    view.findViewById(R.id.checkRadio3).setVisibility(View.VISIBLE);
                    Intent intent = new Intent(MainActivity.this, NewCreditActivity.class);
                    intent.putExtra("credit", "Tiết kiệm");
                    startActivity(intent);
                    dialogNewCredit.dismiss();
                }
            });
        }


        dialogNewCredit.show();
    }

    @Override
    public void onBackPressed() {

        if(layoutMain.isDrawerOpen(GravityCompat.START)){
            layoutMain.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}