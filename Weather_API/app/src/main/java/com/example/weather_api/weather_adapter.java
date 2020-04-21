package com.example.weather_api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class weather_adapter extends BaseAdapter {
    Context context;
    int layout;
    List<weather> weatherList;

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
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgHinh;
        TextView txtTime, txtTemp, txtThoiTiet;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.imgHinh = convertView.findViewById(R.id.lineImageWeather);
            viewHolder.txtTemp = convertView.findViewById(R.id.line_temp);
            viewHolder.txtThoiTiet = convertView.findViewById(R.id.line_thoitiet);
            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.line_Time);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        weather w = weatherList.get(position);
        viewHolder.txtTime.setText(w.getTime());
        viewHolder.txtThoiTiet.setText(w.getDay_weather());
        viewHolder.txtTemp.setText( "min : "+w.getMinTemp()+ "°C" + "\n" + "max : "+ w.getMaxTemp() + "°C");
        Picasso.with(context)
                .load("http://openweathermap.org/img/wn/" + w.getIcon() + ".png")
                .into(viewHolder.imgHinh);
        return convertView;
    }
}
