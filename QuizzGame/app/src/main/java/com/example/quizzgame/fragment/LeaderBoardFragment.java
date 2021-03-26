package com.example.quizzgame.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizzgame.R;
import com.example.quizzgame.adapter.LeaderBoardAdapter;
import com.example.quizzgame.databinding.FragmentLeaderBoardBinding;
import com.example.quizzgame.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LeaderBoardFragment extends Fragment {


    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LeaderBoardFragment newInstance() {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentLeaderBoardBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeaderBoardBinding.inflate(inflater,container,false);

        ArrayList<User> users = new ArrayList<>();
        LeaderBoardAdapter adapter = new LeaderBoardAdapter(users,getContext());
        binding.recyclerViewRank.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewRank.setAdapter(adapter);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .orderBy("coins", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                            User user = snapshot.toObject(User.class);
                            users.add(user);

                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        return binding.getRoot();
    }
}