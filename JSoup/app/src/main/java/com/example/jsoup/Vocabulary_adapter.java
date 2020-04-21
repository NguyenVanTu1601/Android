package com.example.jsoup;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class Vocabulary_adapter extends BaseAdapter {
    List<Vocabulary> list;
    int layout;
    Context context;
    public static int result ;
    public static TextToSpeech textToSpeech;
    public Vocabulary_adapter(List<Vocabulary> list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    class ViewHolder{
        TextView txtTu, txtPhienAm, txtTuLoai, txtGiaithich;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.txtGiaithich = convertView.findViewById(R.id.line_giaithich);
            holder.txtPhienAm = convertView.findViewById(R.id.line_phienam);
            holder.txtTu = convertView.findViewById(R.id.line_tu);
            holder.txtTuLoai = convertView.findViewById(R.id.line_tuloai);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        Vocabulary vocabulary = list.get(position);
        holder.txtTu.setText(vocabulary.getTu());
        holder.txtPhienAm.setText(vocabulary.getPhienAm());
        holder.txtTuLoai.setText(vocabulary.getTuLoai());
        holder.txtGiaithich.setText(vocabulary.getGiaithich());

        holder.txtTu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SpeakVocabulary(holder.txtTu.getText().toString());
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
