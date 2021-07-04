package com.example.android_b06_test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.android_b06_test.R;
import com.example.android_b06_test.model.Music;

import java.util.List;

public class MusicAdapter extends BaseAdapter {

    private List<Music> musicList;
    private Context context;
    private int layout;

    public MusicAdapter(List<Music> musicList, Context context, int layout) {
        this.musicList = musicList;
        this.context = context;
        this.layout = layout;
    }


    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        ImageView imageMusic;
        TextView txtName, txtDescription;
        Button btnRemove;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();

            // ánh xạ
            holder.imageMusic = view.findViewById(R.id.img_item);
            holder.txtName = view.findViewById(R.id.name_item);
            holder.txtDescription = view.findViewById(R.id.description_item);
            holder.btnRemove = view.findViewById(R.id.buttonRemove);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Music music = musicList.get(position);

        switch(music.getImage()){
            case 1:
                holder.imageMusic.setImageResource(R.drawable.logo);
                break;
            case 2:
                holder.imageMusic.setImageResource(R.drawable.two);
                break;
            case 3:
                holder.imageMusic.setImageResource(R.drawable.three);
                break;
            case 4:
                holder.imageMusic.setImageResource(R.drawable.four);
                break;
            case 5:
                holder.imageMusic.setImageResource(R.drawable.five);
                break;
        }
        holder.txtDescription.setText(music.getDescription());
        holder.txtName.setText(music.getName());
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicList.remove(position);
                notifyDataSetChanged();

            }
        });

        return view;
    }
}
