package com.example.edittext_new;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Để sử dụng dạng edittext có đường kẻ đẹp đẹp thì kích vào Text tải TextInputLayout về
 * Bên xml để set loại cho text VD text nhập số hay chỉ nhập chữ thì dung thuộc tính android:inputType = "'
 * Gioi hạn dòng chữ nhập bên xml: maxLines
 * giới hạn kí tự nhập vào : digits ="01" : chỉ nhập 01
 * Chỉnh màu HeightLight : textColorHeightLight
 * Sự kiện thay đổi addTextChangeListener(new TextWatch)
 * drawableTint để thay đổi màu icon
 * Thay đổi theme cho đường kẻ : style -> thêm các thẻ colorControlNormal/Heightlight/Active..
 */
public class MainActivity extends AppCompatActivity {
    EditText edt;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt = findViewById(R.id.editText);
        txt = findViewById(R.id.textView);

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
