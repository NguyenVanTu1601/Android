package com.example.manager_expenditure.overview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manager_expenditure.R;
import com.example.manager_expenditure.adapter.RecyclerView_OverviewAdapter;
import com.example.manager_expenditure.adapter.RecyclerView_TaikhoanAdapter;
import com.example.manager_expenditure.credit.Credit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OverViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OverViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OverViewFragment newInstance(String param1, String param2) {
        OverViewFragment fragment = new OverViewFragment();
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

    private TextView textDate;
    private RecyclerView recyclerViewItemOverview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_over_view, container, false);

        initialization(view);

        String date = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(new Date());
        textDate.setText(date);


        createRecyclerViewItemOverview();

        return view;
    }

    private void initialization(View view){
        textDate = view.findViewById(R.id.datetime_actionbar_overview);
        recyclerViewItemOverview = view.findViewById(R.id.recyclerViewOverview);
    }

    private void createRecyclerViewItemOverview() {
        List<itemOverview> listItemOverview = new ArrayList<>();
        listItemOverview.add(new itemOverview(0,0, "Sức khỏe", 4500000));
        listItemOverview.add(new itemOverview(0,0, "Mua sắm", 10000000));

        recyclerViewItemOverview.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView_OverviewAdapter adapter = new RecyclerView_OverviewAdapter(listItemOverview, getContext());
        recyclerViewItemOverview.setAdapter(adapter);
    }
}