package com.example.manager_expenditure.credit;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.manager_expenditure.R;
import com.example.manager_expenditure.adapter.RecyclerView_TaikhoanAdapter;
import com.example.manager_expenditure.listioner.PublicFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Khoanno_account_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Khoanno_account_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Khoanno_account_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Khoanno_account_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Khoanno_account_Fragment newInstance(String param1, String param2) {
        Khoanno_account_Fragment fragment = new Khoanno_account_Fragment();
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

    private LinearLayout layoutThemKhoanNo; // nút thêm khoản nợ ở dưới cùng của layout
    private RecyclerView recyclerViewNo, recyclerViewChovay, recyclerViewHoanthanh;
    private TextView textSumMoneyNo, textSumMoneyChovay, textSumMoneyHoanthanh;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_khoanno_account_, container, false);

        initialization(view);

        layoutThemKhoanNo = view.findViewById(R.id.layout_them_khoanno);
        layoutThemKhoanNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewCreditActivity.class);
                intent.putExtra("credit","Nợ");
                startActivity(intent);
            }
        });

        createRecyclerViewNo();
        createRecyclerViewChovay();
        createRecyclerViewHoanthanh();

        textSumMoneyNo.setText(new PublicFunction().FormatMoney(0));
        textSumMoneyChovay.setText(new PublicFunction().FormatMoney(0));
        textSumMoneyHoanthanh.setText(new PublicFunction().FormatMoney(0));


        return view;
    }

    private void initialization(View view) {
        recyclerViewNo = view.findViewById(R.id.recyclerViewNo);
        recyclerViewChovay = view.findViewById(R.id.recyclerViewChovay);
        recyclerViewHoanthanh = view.findViewById(R.id.recyclerViewHoanthanh);
        textSumMoneyNo = view.findViewById(R.id.sumMoneyNo);
        textSumMoneyChovay = view.findViewById(R.id.sumMoneyChovay);
        textSumMoneyHoanthanh = view.findViewById(R.id.sumMoneyHoanthanh);
    }

    private void createRecyclerViewNo(){
        List<Credit> listCredit = new ArrayList<>();
        listCredit.add(new Credit(0,0, "Thẻ", 3500000,"Nợ"));
        listCredit.add(new Credit(0,0, "Tiền mặt", 3000000,"Nợ"));

        recyclerViewNo.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView_TaikhoanAdapter adapter = new RecyclerView_TaikhoanAdapter(listCredit, getContext());
        recyclerViewNo.setAdapter(adapter);
    }

    private void createRecyclerViewChovay() {
        List<Credit> listCredit = new ArrayList<>();
        listCredit.add(new Credit(0,0, "Dành mua xe", 4500000, "Cho vay"));
        listCredit.add(new Credit(0,0, "Con đi học", 10000000, "Cho vay"));

        recyclerViewChovay.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView_TaikhoanAdapter adapter = new RecyclerView_TaikhoanAdapter(listCredit, getContext());
        recyclerViewChovay.setAdapter(adapter);
    }

    private void createRecyclerViewHoanthanh() {
        List<Credit> listCredit = new ArrayList<>();
        listCredit.add(new Credit(0,0, "Dành mua xe", 4500000, "Hoàn thành"));
        listCredit.add(new Credit(0,0, "Con đi học", 10000000, " Hoàn thành"));

        recyclerViewHoanthanh.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView_TaikhoanAdapter adapter = new RecyclerView_TaikhoanAdapter(listCredit, getContext());
        recyclerViewHoanthanh.setAdapter(adapter);
    }
}