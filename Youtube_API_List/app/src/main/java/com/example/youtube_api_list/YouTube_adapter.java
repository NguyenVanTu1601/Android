package com.example.youtube_api_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class YouTube_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<VideoYouTube> listVideo;

    public YouTube_adapter(Context context, int layout, List<VideoYouTube> listVideo) {
        this.context = context;
        this.layout = layout;
        this.listVideo = listVideo;
    }

    @Override
    public int getCount() {
        return listVideo.size();
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
        ImageView imgThumbnail;
        TextView txtTitle;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.imgThumbnail = convertView.findViewById(R.id.imageViewRowYotube);
            holder.txtTitle = convertView.findViewById(R.id.textViewTitleRow);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        VideoYouTube videoYouTube = listVideo.get(position);
        holder.txtTitle.setText(videoYouTube.getTitle().toString());
        Picasso.with(context)
                .load(videoYouTube.getThumbnail())
                .placeholder(R.drawable.ic_launcher_background) // nếu chờ lâu set 1 ảnh khác
                .error(R.drawable.ic_launcher_foreground) // nếu lỗi set 1 ảnh khác
                .into(holder.imgThumbnail);

        return convertView;
    }
}
