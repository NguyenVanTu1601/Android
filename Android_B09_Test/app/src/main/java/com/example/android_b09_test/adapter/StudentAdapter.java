package com.example.android_b09_test.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_b09_test.R;
import com.example.android_b09_test.activity.EditStudentActivity;
import com.example.android_b09_test.activity.MainActivity;
import com.example.android_b09_test.database.Database;
import com.example.android_b09_test.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> students;
    private Context context;

    public StudentAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.student_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.txtId.setText("Id : " + student.getId());
        holder.txtName.setText("Name : " + student.getName());
        holder.txtMark.setText("Mark : " + student.getMark());
        if (student.isGender()){
            holder.txtGender.setText("Male");
            holder.txtGender.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_male,0);
        }else{
            holder.txtGender.setText("FeMale");
            holder.txtGender.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_female,0);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(context, "Bạn đang chọn : " + student.getName(), Toast.LENGTH_SHORT).show();
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // bỏ phần khoảng trắng title bên trên dialog
                dialog.setContentView(R.layout.dialog_custom);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //dialog.setCanceledOnTouchOutside(false);

                Button btnSua = dialog.findViewById(R.id.btnSua);
                Button btnXoa = dialog.findViewById(R.id.btnXoa);

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Database stDb = new Database(context);
                        stDb.deleteStudent(student.getId());
                        students.remove(position);
                        ((MainActivity) context).updateList(students);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, EditStudentActivity.class);
                        intent.putExtra("student", student);
                        context.startActivity(intent);
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtMark,txtGender, txtId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId       = itemView.findViewById(R.id.student_item_id);
            txtName     = itemView.findViewById(R.id.student_item_name);
            txtMark     = itemView.findViewById(R.id.student_item_mark);
            txtGender   = itemView.findViewById(R.id.student_item_gender);
        }
    }

}
