package com.example.fragment_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Fragment_Dialog extends DialogFragment {
    Delete delete;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        delete = (Delete) getActivity();


        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Dialog");
        dialog.setMessage("Bạn muốn xóa không");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete.GiatriXoa(true);
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete.GiatriXoa(false);
            }
        });
         Dialog hopth = dialog.create();
         return hopth;
    }
}
