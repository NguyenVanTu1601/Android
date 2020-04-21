package com.example.fragment_bt_xu_ly_giao_dien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_student_info extends Fragment {
    TextView txtTen, txtNamSinh, txtDiaChi, txtEmail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_info,container,false);
        txtTen = view.findViewById(R.id.textViewName);
        txtNamSinh = view.findViewById(R.id.textViewDate);
        txtDiaChi = view.findViewById(R.id.textViewDiaChi);
        txtEmail = view.findViewById(R.id.textViewMail);
        return view;
    }

    public void setInfo(SinhVien sv){
        txtTen.setText(sv.getHoTen());
        txtNamSinh.setText(sv.getNamSinh() + "");
        txtDiaChi.setText("Địa chỉ : " + sv.getDiaChi());
        txtEmail.setText(sv.getEmail());
    }
}
