package com.example.manager_expenditure.category;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manager_expenditure.R;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private boolean isEdit = false;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(Boolean param1) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEdit = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    private TextView textDate;
    private AlertDialog dialogDateTime;

    private ConstraintLayout layoutDateTime;
    private LinearLayout layoutClassify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_category, container, false);

        initialization(view);



        String date = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(new Date());
        textDate.setText(date);

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogDateTime(view);
            }
        });

        return view;
    }

    private void initialization(View view) {
        textDate = view.findViewById(R.id.datetime_actionbar_category);
        layoutDateTime = view.findViewById(R.id.layout_datetime_category);
    }

    private void showAlertDialogDateTime(View view1) {
        if (dialogDateTime == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_select_datetime,
                    (ViewGroup) view1.findViewById(R.id.alertSelectDateTime), false);
            builder.setView(view);
            dialogDateTime = builder.create();
        }
        dialogDateTime.show();
    }
}