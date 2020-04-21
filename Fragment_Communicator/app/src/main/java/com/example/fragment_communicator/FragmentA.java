package com.example.fragment_communicator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {
    Button btnFragA;
    TextView txtFragA;
    EditText edtFragA;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a,container,false);
        btnFragA = view.findViewById(R.id.ButtonClickA);
        txtFragA = view.findViewById(R.id.textFragA);
        edtFragA = view.findViewById(R.id.editTextFragA);
        btnFragA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtFragA.setText(edtFragA.getText().toString());
                // Toast(getActivity(), ......)

                // Tương tác từ fragment A tới Main
                TextView txtMain = getActivity().findViewById(R.id.textViewMain);
                txtMain.setText("Gọi từ Fragment A");
            }
        });

        return view;
    }
}
