package com.example.manager_expenditure.deal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manager_expenditure.R;
import com.example.manager_expenditure.adapter.RecyclerView_DealAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DealFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DealFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DealFragment newInstance(String param1, String param2) {
        DealFragment fragment = new DealFragment();
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
    private RecyclerView recyclerViewDeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deal, container, false);

        initialization(view);

        String date = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(new Date());
        textDate.setText(date);

        createRecyclerDeal();
        return view;
    }

    private void createRecyclerDeal() {
        List<Deal> listDeal = new ArrayList<>();
        List<item> listItem = new ArrayList<>();

        listItem.add(new item(0,0,"Tiền lương","Tiền mặt",50000));
        listItem.add(new item(0,0,"Tiền lương","Thẻ",500000));
        listItem.add(new item(0,0,"Tiền thưởng","Tiền mặt",100000));
        listItem.add(new item(0,0,"Tiền lương","Thẻ",20000));

        listDeal.add(new Deal(3500000,"31", "Thứ hai","Tháng 8 2020", listItem ));
        listDeal.add(new Deal(3000000,"01", "Thứ ba","Tháng 9 2020", listItem ));

        RecyclerView_DealAdapter adapter = new RecyclerView_DealAdapter(listDeal,getContext());
        recyclerViewDeal.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDeal.setAdapter(adapter);

    }

    private void initialization(View view){
        textDate = view.findViewById(R.id.datetime_actionbar_deal);
        recyclerViewDeal = view.findViewById(R.id.recyclerViewDeal);

    }
}