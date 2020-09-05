package com.example.fragment_truyen_nhan_dulieu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Fragment extends androidx.fragment.app.Fragment {
    public static final String ARG_PARAM1 = "argparam1";

    public Fragment() {
    }

    public static Fragment newInstance(String param1) {
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String result;
            result = getArguments().getString(ARG_PARAM1);
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        }
    }

    TextView txtFragment;
    Button btnFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        txtFragment = view.findViewById(R.id.textViewFragment);
        btnFragment = view.findViewById(R.id.buttonFragment);

        if (getArguments() != null) {
            String result;
            result = getArguments().getString(ARG_PARAM1);
            txtFragment.setText(result);
        }

//        Bundle bundle = getArguments();
//        if(bundle != null){
//            txtFragment.setText(bundle.getString("hoten"));
//        }
        return view;
    }
}
