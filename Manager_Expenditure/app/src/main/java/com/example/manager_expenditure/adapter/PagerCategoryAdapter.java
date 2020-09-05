package com.example.manager_expenditure.adapter;

import android.media.ThumbnailUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.manager_expenditure.category.Chiphi_Category_Fragment;
import com.example.manager_expenditure.category.Thunhap_Category_Fragment;
import com.example.manager_expenditure.credit.Khoanno_account_Fragment;
import com.example.manager_expenditure.credit.Taichinh_account_Fragment;
import com.example.manager_expenditure.credit.Taikhoan_account_Fragment;

public class PagerCategoryAdapter extends FragmentStateAdapter {

    public PagerCategoryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Chiphi_Category_Fragment();
            case 1 :
                return new Thunhap_Category_Fragment();

            default:
                return new Chiphi_Category_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
