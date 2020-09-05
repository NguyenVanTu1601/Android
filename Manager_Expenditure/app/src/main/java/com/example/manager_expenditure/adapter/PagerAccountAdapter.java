package com.example.manager_expenditure.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.manager_expenditure.credit.Khoanno_account_Fragment;
import com.example.manager_expenditure.credit.Taichinh_account_Fragment;
import com.example.manager_expenditure.credit.Taikhoan_account_Fragment;

public class PagerAccountAdapter extends FragmentStateAdapter {

    public PagerAccountAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Taikhoan_account_Fragment();
            case 1 :
                return new Khoanno_account_Fragment();
            case 2:
                return new Taichinh_account_Fragment();
            default:
                return new Taikhoan_account_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
