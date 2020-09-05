package com.example.ncovi_2020.World;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ncovi_2020.R;
import com.example.ncovi_2020.World.nCov_World;

import java.util.List;

public class nCov_World_Adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<nCov_World> nCovidList;

    public nCov_World_Adapter(Context context, int layout, List<nCov_World> nCovidList) {
        this.context = context;
        this.layout = layout;
        this.nCovidList = nCovidList;
    }

    @Override
    public int getCount() {
        return nCovidList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView txtCountry, txtSoCaNhiem, txtCaNhiem, txtDie, txtKhoiBenh;

        public ViewHolder() {
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewHolder.txtCountry = convertView.findViewById(R.id.line_nameCountry);
            viewHolder.txtSoCaNhiem = convertView.findViewById(R.id.line_Socanhiem);
            viewHolder.txtCaNhiem = convertView.findViewById(R.id.line_canhiem2);
            viewHolder.txtDie = convertView.findViewById(R.id.line_die);
            viewHolder.txtKhoiBenh = convertView.findViewById(R.id.line_khoi);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        nCov_World covid = nCovidList.get(position);
        viewHolder.txtCountry.setText(covid.getNameCountry() + "");
        viewHolder.txtSoCaNhiem.setText("Số ca nhiễm : " + covid.getConfirmed());
        viewHolder.txtCaNhiem.setText(covid.getConfirmed()+ "");
        viewHolder.txtDie.setText(covid.getDeaths() + "");
        viewHolder.txtKhoiBenh.setText(covid.getRecovered()+ "");

        return convertView;
    }
}
