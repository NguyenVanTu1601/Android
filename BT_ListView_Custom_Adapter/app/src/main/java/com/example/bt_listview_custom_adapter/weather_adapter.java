package com.example.bt_listview_custom_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class weather_adapter extends BaseAdapter {
    private Context context;
    private int layout; // Layout chứa các item
    private List<weather> weatherList;

    public weather_adapter(Context context, int layout, List<weather> weatherList) {
        this.context = context;
        this.layout = layout;
        this.weatherList = weatherList;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        ImageView imageHinhAnh;
        TextView txtVitri, txtThoiTiet, txtnhietdo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            // Anh xa
            viewHolder = new ViewHolder();
            viewHolder.imageHinhAnh = (ImageView) view.findViewById(R.id.imageViewHinhAnh);
            viewHolder.txtVitri = (TextView) view.findViewById(R.id.textViewVitri);
            viewHolder.txtnhietdo = (TextView) view.findViewById(R.id.textViewNhietDo);
            viewHolder.txtThoiTiet = (TextView) view.findViewById(R.id.textViewThoiTiet);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        // Gan gia tri
        weather weather1 = weatherList.get(i);
        viewHolder.imageHinhAnh.setImageResource(weather1.getHinhanh());
        viewHolder.txtThoiTiet.setText(weather1.getThoitiet());
        viewHolder.txtnhietdo.setText(weather1.getNhietdo()+ "°C");
        viewHolder.txtVitri.setText(weather1.getDiadiem());
        Animation animation_list = AnimationUtils.loadAnimation(context, R.anim.scale_list);
        view.startAnimation(animation_list);
        return view;
    }
}
