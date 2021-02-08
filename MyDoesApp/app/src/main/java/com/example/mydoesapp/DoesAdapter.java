package com.example.mydoesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoesAdapter extends RecyclerView.Adapter<DoesAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyDoes> myDoes;

    public DoesAdapter(Context context, ArrayList<MyDoes> myDoes) {
        this.context = context;
        this.myDoes = myDoes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_does, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final MyDoes does = myDoes.get(position);
        holder.titleDoes.setText(does.getTitledoes());
        holder.descDoes.setText(does.getDescdoes());
        holder.dateDoes.setText(does.getDatedoes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewTaskActivity.class);
                intent.putExtra("state","edit");
                intent.putExtra("title", does.getTitledoes());
                intent.putExtra("desc", does.getDescdoes());
                intent.putExtra("date", does.getDatedoes());
                intent.putExtra("key", does.key);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Thông báo!");
                alert.setMessage("Bạn muốn xóa ghi chú này?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        rootRef.child("MyDoes").child(does.key).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Xóa thành công...", Toast.LENGTH_SHORT).show();
                                myDoes.remove(position);
                                notifyDataSetChanged();
                            }
                        });

                    }
                });
                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDoes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleDoes, descDoes, dateDoes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleDoes = itemView.findViewById(R.id.titleDoes);
            descDoes = itemView.findViewById(R.id.descDoes);
            dateDoes = itemView.findViewById(R.id.dateDoes);
        }
    }
}
