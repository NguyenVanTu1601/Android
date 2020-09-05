package com.example.manager_expenditure.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manager_expenditure.R;
import com.example.manager_expenditure.adapter.PagerAccountAdapter;
import com.example.manager_expenditure.adapter.PagerCategoryAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditCategoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private boolean isEdit = false;

    public EditCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditCategoryFragment newInstance(Boolean param1) {
        EditCategoryFragment fragment = new EditCategoryFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEdit = getArguments().getBoolean(ARG_PARAM1);
        }
    }


    private ViewPager2 viewPagerCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_category, container, false);


        viewPagerCategory = view.findViewById(R.id.viewPager2_category);

        viewPagerCategory.setAdapter(new PagerCategoryAdapter(getActivity()));
        // Di chuyển tab
        final TabLayout tabLayoutCategory = view.findViewById(R.id.tabLayout_category);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayoutCategory, viewPagerCategory, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Chi phí");
                        break;
                    case 1:
                        tab.setText("Thu nhập");
                        break;

                }
            }
        });
        tabLayoutMediator.attach();

        return view;
    }
}