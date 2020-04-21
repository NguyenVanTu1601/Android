package com.example.dictionary;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class English_Adapter extends BaseAdapter {
    MainActivity context;
    int layout;
    List<English> englishList;
    int result;
    TextToSpeech textToSpeech;

    public English_Adapter(MainActivity context, int layout, List<English> englishList) {
        this.context = context;
        this.layout = layout;
        this.englishList = englishList;
    }

    @Override
    public int getCount() {
        return englishList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder {
        TextView txtVocabulary;
        TextView txtMean;
        ImageButton imgVolumn;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder.txtMean = convertView.findViewById(R.id.line_mean);
            viewHolder.txtVocabulary = convertView.findViewById(R.id.line_vocabulary);
            viewHolder.imgVolumn = convertView.findViewById(R.id.line_volumn);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final English e = englishList.get(position);
        viewHolder.txtVocabulary.setText(e.getVocabulary());
        viewHolder.txtMean.setText(e.getWordMeaning());
        viewHolder.imgVolumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeakVocabulary(e.getVocabulary());
            }
        });

        viewHolder.txtVocabulary.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context.Delete(e.getVocabulary());
                return true;
            }
        });
        return convertView;
    }

    public void SpeakVocabulary(final String text){

        textToSpeech = new TextToSpeech(context, new OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == textToSpeech.SUCCESS){
                    result = textToSpeech.setLanguage(Locale.UK);

                }else{
                    Toast.makeText(context.getApplicationContext(),"Not support",Toast.LENGTH_SHORT);
                }

                if(result == TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(context.getApplicationContext(),"Not Support!",Toast.LENGTH_SHORT);

                }else{

                    textToSpeech.speak(text,TextToSpeech.QUEUE_ADD,null);

                }
            }
        });

    }


}
