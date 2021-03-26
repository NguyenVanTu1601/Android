package com.example.android_b04_exercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.android_b04_exercise.MainActivity;
import com.example.android_b04_exercise.R;
import com.example.android_b04_exercise.model.Staff;

import java.util.ArrayList;

public class StaffAdapter extends BaseAdapter {

    private ArrayList<Staff> staffs;
    private int layout;
    private MainActivity mainActivity;

    public StaffAdapter(ArrayList<Staff> staffs, int layout, MainActivity mainActivity) {
        this.staffs = staffs;
        this.layout = layout;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return staffs.size();
    }

    @Override
    public Object getItem(int i) {
        return staffs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder{
        ImageView img_icon;
        TextView txtID, txtName;
        CheckBox checkBox;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null){

            LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(mainActivity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.img_icon = view.findViewById(R.id.img_item);
            viewHolder.txtID    = view.findViewById(R.id.manv_item);
            viewHolder.txtName  = view.findViewById(R.id.tennv_item);
            viewHolder.checkBox = view.findViewById(R.id.check_item);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Staff staff = staffs.get(i);
        if (staff.getSex() == 1){
            viewHolder.img_icon.setImageResource(R.drawable.female);
        }else{
            viewHolder.img_icon.setImageResource(R.drawable.male);
        }
        viewHolder.txtID.setText(staff.getMaNV());
        viewHolder.txtName.setText(staff.getTenNV());

        if (!mainActivity.isClick){
            viewHolder.checkBox.setVisibility(View.INVISIBLE);
            viewHolder.checkBox.setChecked(true);
        }else{
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setChecked(false);
        }

        view.setOnLongClickListener(mainActivity);

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
