package com.example.manager_expenditure.credit;

import android.app.AlertDialog;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Taikhoan_account_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Taikhoan_account_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Taikhoan_account_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Taikhoan_account_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Taikhoan_account_Fragment newInstance(String param1, String param2) {
        Taikhoan_account_Fragment fragment = new Taikhoan_account_Fragment();
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

    private LinearLayout layout_them_taikhoan;

    private RecyclerView recyclerViewTaiKhoan;
    private RecyclerView recyclerViewTietkiem;
    private TextView textSumMoneyTaikhoan, textSumMoneyTietkiem;


    private AlertDialog dialogNewCredit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_taikhoan_account_, container, false);

        initialization(view);

        layout_them_taikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogCredit(view);
            }
        });

        createRecyclerViewTaikhoan();
        createRecyclerViewTietkiem();

        // Định dạng tổng tiền trong tài khoản
        textSumMoneyTaikhoan.setText(new PublicFunction().FormatMoney(0));
        textSumMoneyTietkiem.setText(new PublicFunction().FormatMoney(0));

        return view;
    }



    private void initialization(View view){
        layout_them_taikhoan = view.findViewById(R.id.them_tai_khoan);
        recyclerViewTaiKhoan = view.findViewById(R.id.recyclerViewTaikhoan);
        recyclerViewTietkiem = view.findViewById(R.id.recyclerViewTietkiem);
        textSumMoneyTaikhoan = view.findViewById(R.id.sumMoneyTaikhoan);
        textSumMoneyTietkiem = view.findViewById(R.id.sumMoneyTietkiem);
    }



    private void showAlertDialogCredit(View view1){
        if(dialogNewCredit == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_alert_new_credit,
                    (ViewGroup) view1.findViewById(R.id.alertNewCredit), false);
            builder.setView(view);
            dialogNewCredit = builder.create();

            view.findViewById(R.id.radioThuongxuyen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.findViewById(R.id.checkRadio1).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.checkRadio2).setVisibility(View.GONE);
                    view.findViewById(R.id.checkRadio3).setVisibility(View.GONE);
                    Intent intent = new Intent(getContext(), NewCreditActivity.class);
                    intent.putExtra("credit", "Thường xuyên");
                    dialogNewCredit.dismiss();
                    startActivity(intent);
                }
            });

            view.findViewById(R.id.radioNo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.findViewById(R.id.checkRadio1).setVisibility(View.GONE);
                    view.findViewById(R.id.checkRadio2).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.checkRadio3).setVisibility(View.GONE);
                    Intent intent = new Intent(getContext(), NewCreditActivity.class);
                    intent.putExtra("credit", "Nợ");
                    dialogNewCredit.dismiss();
                    startActivity(intent);
                }
            });

            view.findViewById(R.id.radioTietkiem).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.findViewById(R.id.checkRadio1).setVisibility(View.GONE);
                    view.findViewById(R.id.checkRadio2).setVisibility(View.GONE);
                    view.findViewById(R.id.checkRadio3).setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getContext(), NewCreditActivity.class);
                    intent.putExtra("credit", "Tiết kiệm");
                    dialogNewCredit.dismiss();
                    startActivity(intent);
                }
            });
        }


        dialogNewCredit.show();
    }

    private void openBehavior(){
        // Tạo bottomSheetDialog trong fragment
        View dialogView = getLayoutInflater().inflate(R.layout.behavier_credit, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(dialogView);
        dialog.show();
    }

    private void createRecyclerViewTaikhoan(){
        List<Credit> listCredit = new ArrayList<>();
        listCredit.add(new Credit(0,0, "Thẻ", 3500000,"Tài khoản"));
        listCredit.add(new Credit(0,0, "Tiền mặt", 3000000,"Tài khoản"));

        recyclerViewTaiKhoan.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView_TaikhoanAdapter adapter = new RecyclerView_TaikhoanAdapter(listCredit, getContext());
        recyclerViewTaiKhoan.setAdapter(adapter);
    }

    private void createRecyclerViewTietkiem() {
        List<Credit> listCredit = new ArrayList<>();
        listCredit.add(new Credit(0,0, "Dành mua xe", 4500000,"Tiết kiệm"));
        listCredit.add(new Credit(0,0, "Con đi học", 10000000,"Tiết kiệm"));

        recyclerViewTietkiem.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView_TaikhoanAdapter adapter = new RecyclerView_TaikhoanAdapter(listCredit, getContext());
        recyclerViewTietkiem.setAdapter(adapter);
    }

}