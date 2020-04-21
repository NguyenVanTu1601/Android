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

public class FragmentB extends Fragment {
    Button btnFragB;
    TextView txtFragB;
    EditText edtFragB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b,container,false);
        btnFragB = view.findViewById(R.id.ButtonClickB);
        txtFragB = view.findViewById(R.id.textFragB);
        edtFragB = view.findViewById(R.id.editTextFragB);

        btnFragB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tương tác từ B sang A
                TextView txtA = getActivity().findViewById(R.id.textFragA);
                txtA.setText("Gọi từ fragB");
            }
        });
        return view;
    }
    public void setContent(){
        txtFragB.setText("Gọi thông qua hàm từ Main");
    }
}
