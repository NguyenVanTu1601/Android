package com.example.crawldata.TestSchedule;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crawldata.R;

import java.util.ArrayList;

public class TestSchduleAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<TestSchedule> listTests;

    public TestSchduleAdapter(Context context, int layout, ArrayList<TestSchedule> listTests) {
        this.context = context;
        this.layout = layout;
        this.listTests = listTests;
    }

    @Override
    public int getCount() {
        return listTests.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenMon, txtNgaythi, txtGiothi, txtThoigian, txtHinhthuc, txtPhongthi;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            // ánh xạ qua view
            holder = new ViewHolder();
            holder.txtTenMon = convertView.findViewById(R.id.item_test_tenmon);
            holder.txtNgaythi = convertView.findViewById(R.id.item_test_ngaythi);
            holder.txtGiothi = convertView.findViewById(R.id.item_test_giothi);
            holder.txtPhongthi = convertView.findViewById(R.id.item_test_phongthi);
            holder.txtHinhthuc = convertView.findViewById(R.id.item_test_hinhthuc);
            holder.txtThoigian = convertView.findViewById(R.id.item_test_thoigian);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        // gán giá trị
        TestSchedule ts = listTests.get(position);
        holder.txtTenMon.setText(ts.getTenMon());
        holder.txtNgaythi.setText("Ngày thi : " + ts.getNgaythi());
        holder.txtGiothi.setText("Giờ thi : " + ts.getGiothi());
        holder.txtPhongthi.setText("Phòng : " + ts.getPhong());
        holder.txtThoigian.setText("Thời gian làm bài : " + ts.getThoigian() + " phút");
        holder.txtHinhthuc.setText("Hình thức : " + ts.getHinhthuc());
        return convertView;
    }
}
