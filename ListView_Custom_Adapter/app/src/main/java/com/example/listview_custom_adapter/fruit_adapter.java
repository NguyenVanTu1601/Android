package com.example.listview_custom_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class fruit_adapter extends BaseAdapter {


    private Context context;
    private int layout ;// File layout trả về kiểu int
    private List<Fruits>fruitsList ;
    public fruit_adapter(Context context, int layout, List<Fruits> fruitsList) {
        this.context = context;
        this.layout = layout;
        this.fruitsList = fruitsList;
    }
    @Override
    public int getCount() {
        // Số dòng hiển thị trên listview
        return fruitsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
        // vi trí item
    }

    @Override
    public long getItemId(int i) {
        // id của dòng listview
        return 0;
    }
    private class viewHolder{
        ImageView imageView;
        TextView txtEng, txtVn;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        if(view == null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            // ánh xạ qua view
            holder = new viewHolder();
            holder.txtEng = (TextView) view.findViewById(R.id.NameEng);
            holder.txtVn = (TextView) view.findViewById(R.id.NameVn);
            holder.imageView = (ImageView) view.findViewById(R.id.image_fruit);
            view.setTag(holder);
        }
        else{
            holder = (viewHolder) view.getTag();
        }

        // gán giá trị
        Fruits fruits = fruitsList.get(i);
        holder.txtEng.setText(fruits.getName());
        holder.txtVn.setText(fruits.getMota());
        holder.imageView.setImageResource(fruits.getHinh());
        return view;
        // trả về mỗi dòng trên item
    }
}
