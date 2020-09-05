package com.example.manager_expenditure.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.manager_expenditure.category.CategoryFragment;
import com.example.manager_expenditure.credit.AccountFragment;
import com.example.manager_expenditure.deal.DealFragment;
import com.example.manager_expenditure.overview.OverViewFragment;

public class PagerAdapter extends FragmentStateAdapter {


    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AccountFragment();
            case 1 :
                return new CategoryFragment();
            case 2:
                return new DealFragment();
            case 3:
                return new OverViewFragment();
            default:
                return new AccountFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
