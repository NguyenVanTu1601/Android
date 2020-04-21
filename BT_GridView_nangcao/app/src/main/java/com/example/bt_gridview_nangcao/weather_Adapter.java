package com.example.bt_gridview_nangcao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class weather_Adapter extends BaseAdapter {
    private Context context;
    private List<Weather> weatherList;
    private int layout;

    public weather_Adapter(Context context, List<Weather> weatherList, int layout) {
        this.context = context;
        this.weatherList = weatherList;
        this.layout = layout;
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
        private ImageView Hinh;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.Hinh = (ImageView) view.findViewById(R.id.image_weather);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Weather weather = weatherList.get(i);
        viewHolder.Hinh.setImageResource(weather.getHinhAnh());
        return view;
    }
}
