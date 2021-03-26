package com.example.quizzgame.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizzgame.R;
import com.example.quizzgame.databinding.FragmentWalletBinding;
import com.example.quizzgame.model.User;
import com.example.quizzgame.model.WithdrawRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WalletFragment extends Fragment {

    public WalletFragment() {

    }


    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    FragmentWalletBinding binding;
    FirebaseFirestore database;
    User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWalletBinding.inflate(inflater, container, false);
        database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        binding.currentCoins.setText(user.getCoins() + "");
                    }
                });

        binding.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getCoins() >= 50000){

                    String uid = FirebaseAuth.getInstance().getUid();
                    String payPay = binding.edtPaypal.getText().toString();
                    WithdrawRequest request = new WithdrawRequest(uid, payPay, user.getName());

                    database.collection("withdraw")
                            .document(uid)
                            .set(request)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext(), "Request send successfully!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    Toast.makeText(getContext(), "You need more coins to get withdraw...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }
}