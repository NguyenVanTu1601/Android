package com.example.fragment_truyen_nhan_dulieu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Fragment extends androidx.fragment.app.Fragment {
    TextView txtFragment;
    Button btnFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        txtFragment = view.findViewById(R.id.textViewFragment);
        btnFragment = view.findViewById(R.id.buttonFragment);

        Bundle bundle = getArguments();
        if(bundle != null){
            txtFragment.setText(bundle.getString("hoten"));
        }
        return view;
    }
}
