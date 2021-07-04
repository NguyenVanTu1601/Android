package com.example.android_b09_sqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_b09_sqlite.R;
import com.example.android_b09_sqlite.model.Student;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.StudentViewHolder>{
    private List<Student> list;
    public RecyclerViewAdapter() {
        list=new ArrayList<>();
    }
    public void setStudents(List<Student> list){
        this.list=list;
    }
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.student_card,parent,false);
        return new StudentViewHolder(v);
    }

    public void onBindViewHolder(@NonNull StudentViewHolder holder,int position) {
        Student s=list.get(position);
        holder.tvIdName.setText(s.getName()+" - "+s.getId());
        if(s.isGender())
            holder.imgGender.setImageResource(R.drawable.ic_baseline_male_24);
        else
            holder.imgGender.setImageResource(R.drawable.ic_baseline_female_24);
        holder.tvMark.setText(" Mark:"+s.getMark());
    }


    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        else
            return 0;
    }
    class StudentViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIdName;
        private ImageView imgGender;
        private TextView tvMark;
        public StudentViewHolder(@NonNull View v) {
            super(v);
            tvIdName=v.findViewById(R.id.idName);
            imgGender=v.findViewById(R.id.img);
            tvMark=v.findViewById(R.id.mark);
        }
    }}
