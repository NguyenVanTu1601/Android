package com.example.fragment_bt_xu_ly_giao_dien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

public class Fragment_student_list extends ListFragment {
    ArrayList<SinhVien> arraySinhVien;
    student_Adapter adapter;
    TruyenSinhVien truyenSinhVien;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Anh xạ truyensinhvien
        truyenSinhVien = (TruyenSinhVien) getActivity();

        // Khai bao view
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        arraySinhVien = new ArrayList<>();
        addArraySv();
        adapter = new student_Adapter(getActivity(),R.layout.line_student,arraySinhVien);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Truyền thông tin sinh viên sang Main
        truyenSinhVien.DataStudent(arraySinhVien.get(position));

    }

    private void addArraySv(){
        arraySinhVien.add(new SinhVien("Nguyễn Văn Tú",1999,"Thái Bình","banggia1601@gmail.com"));
        arraySinhVien.add(new SinhVien("Phùng Đình Tùng",1999,"Lào Cai","tungphung1999@gmail.com"));
        arraySinhVien.add(new SinhVien("Trịnh Đình Trung",1999,"Thanh Hóa","dinhtrung1706@gmail.com"));
        arraySinhVien.add(new SinhVien("Nguyễn Văn Tài",2003,"Thái Bình","tainguyen@gmail.com"));
        arraySinhVien.add(new SinhVien("Bùi Quang Cảnh",1999,"Thái Bình","bcanh99@gmail.com"));
        arraySinhVien.add(new SinhVien("Nguyễn Đức Nam",1999,"Thái Bình","ducnam@gmail.com"));
        arraySinhVien.add(new SinhVien("Nguyễn Tiến Khánh",1999,"Thái Bình","khanh@gmail.com"));

    }
}
