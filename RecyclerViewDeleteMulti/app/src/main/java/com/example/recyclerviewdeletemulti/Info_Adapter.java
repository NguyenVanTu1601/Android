package com.example.recyclerviewdeletemulti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Info_Adapter extends RecyclerView.Adapter<Info_Adapter.ViewHolder> {

    ArrayList<Info> listInfo;
    Context context;
    MainActivity mainActivity;
    public Info_Adapter(ArrayList<Info> listInfo, Context context) {
        this.listInfo = listInfo;
        this.context = context;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_info,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textName.setText(listInfo.get(position).getName());

        if (!mainActivity.is_menu_delete){
            holder.checkDelete.setVisibility(View.INVISIBLE);
        }else{
            holder.checkDelete.setVisibility(View.VISIBLE);
            holder.checkDelete.setChecked(false);
        }

        holder.textName.setOnLongClickListener(mainActivity);
        holder.checkDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.prepareSelected(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listInfo.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textName;
        CheckBox checkDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.info_name);
            checkDelete = itemView.findViewById(R.id.info_checkbox);
        }


    }

    public void updateAdapter(ArrayList<Info> list){
        for (Info info : list){
            listInfo.remove(info);
        }
        notifyDataSetChanged();
    }
}
