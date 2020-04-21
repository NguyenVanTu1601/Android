package com.example.text_to_speech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btnSpeak, btnStop;
    TextToSpeech textToSpeech;
    int result;
    String text = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

        textToSpeech = new TextToSpeech(MainActivity.this, new OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == textToSpeech.SUCCESS){
                    result = textToSpeech.setLanguage(Locale.UK);
                }else{
                    Toast.makeText(getApplicationContext(),"Not support",Toast.LENGTH_SHORT);
                }
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result == TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(getApplicationContext(),"Not Support!",Toast.LENGTH_SHORT);

                }else{
                    text = editText.getText().toString();
                    textToSpeech.speak(text,TextToSpeech.QUEUE_ADD,null);
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textToSpeech!=null){
                    textToSpeech.stop();
                }

            }
        });
    }
    public void Anhxa(){
        editText = findViewById(R.id.editText);
        btnSpeak = findViewById(R.id.buttonSpeak);
        btnStop = findViewById(R.id.buttonStop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
