package com.example.androidb05activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidb05activity.databinding.ActivityMainBinding;

import java.util.Calendar;

/*
1. Spinner
2. CheckBox
3. RadioButton
4. DatePicker
5. TimePicker
6. Focus cho editText - slide 4 page 19
7. AutoCompleteTextView và MultiAutoComplete
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (binding.checkbox.isChecked()){
            Toast.makeText(this, "Bạn đã chọn check box",Toast.LENGTH_LONG).show();
        }

        spinerChosen();

        showDatePicker(this);

        showTimePicker(this);

        autoComplete();

        multiAutoComplete();
    }

    private void spinerChosen() {
        String[] subjects = {"Toán","Lý","Hóa","Anh","Văn","Địa"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.spinerSubject.setAdapter(adapter);

        binding.spinerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, subjects[i], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void multiAutoComplete() {
        String[] language = {"Lào", "Việt Nam", "Campuchia", "China","Thái Lan"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, language);
        binding.multiComplete.setAdapter(adapter);
        binding.multiComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    private void autoComplete() {
        String[] language = {"Lào", "Việt Nam", "Campuchia", "China","Thái Lan"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, language);
        binding.autoComplete.setAdapter(adapter);
    }

    private void showTimePicker(Context context) {
        binding.btnTime.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int mHour = calendar.get(Calendar.HOUR);
            int mMinute = calendar.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    binding.textTime.setText(i + " : " + i1);
                }
            }, mHour, mMinute, false);

            dialog.show();
        });
    }

    private void showDatePicker(Context context) {
        binding.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        binding.textPicker.setText(dayOfMonth + " - " + (month + 1) + " - " + year);
                    }
                }, mYear, mMonth, mDay);
                dialog.show();
            }
        });
    }

}