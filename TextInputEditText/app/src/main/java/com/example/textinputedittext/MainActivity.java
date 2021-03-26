package com.example.textinputedittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.textinputedittext.databinding.ActivityMainBinding;
/*
1. setStyle cho textInputLayout
2. startIconDrawer
3. helperText : text helper bên dưới textInputEditText
4. counterEnable, counterMaxLenght : đếm số kí tự tối đa được nhập
    + Nó đặt trong TextInputLayout thì nó sẽ báo đỏ khi nhập quá số lượng kí tự tối đa
    + Nếu đặt trong textInputEditText 1 cái maxLength thì là chỉ nhập tối đa từng đó kí tự
5. endIconMode để chọn, có cái là showPassword trông hay nó tự show luôn k cần code :)) hoặc chọn clear text cũng ngon
    Khi đó inputType của textInputEditText phải là password
    Ngoài ra, khi cho end là custom, thì có thể chèn ảnh khác vào và bắt sự kiện cho nó
    textInputEditText.setEndIconOnClickListener{}

6. prefixText : điền sẵn kí tự vào trước, phù hợp cho điền sđt kiểu +36588...
7. errorIconDrawable và errorEnable
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 10){
                    binding.textInputLayout.setError("No more...!");
                }else{
                    binding.textInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}