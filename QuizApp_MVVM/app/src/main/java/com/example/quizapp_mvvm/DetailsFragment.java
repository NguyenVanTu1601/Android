package com.example.quizapp_mvvm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private QuizListViewModel quizListViewModel;
    private NavController navController;

    private int position;
    private String quizId;

    private ImageView detailsImage;
    private TextView detailsTitle;
    private TextView detailsScore;
    private TextView detailsDiff;
    private TextView detailsQuestions;
    private TextView detailsDesc;
    private Button detailsStartBtn;
    private long totalQestions = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        position = DetailsFragmentArgs.fromBundle(getArguments()).getPosition();

        // Initialize UI Elements
        detailsImage = view.findViewById(R.id.detail_imageview);
        detailsTitle = view.findViewById(R.id.detail_title);
        detailsDiff = view.findViewById(R.id.detail_difficuly_text);
        detailsQuestions = view.findViewById(R.id.detail_question_text);
        detailsDesc = view.findViewById(R.id.detail_description);
        detailsScore = view.findViewById(R.id.detail_score_text);

        detailsStartBtn = view.findViewById(R.id.detail_btn);
        detailsStartBtn.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Đọc phần giới thiệu gói câu hỏi về. Nó chứa trong livedata của quizListViewModel hay trên QuizList của FirebaseFireStore
        quizListViewModel = new ViewModelProvider(getActivity()).get(QuizListViewModel.class);
        quizListViewModel.getQuizListModelData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModelList) {
                Glide.with(getContext()).load(quizListModelList.get(position).getImage())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_image)
                        .into(detailsImage);

                detailsTitle.setText(quizListModelList.get(position).getName());
                detailsDesc.setText(quizListModelList.get(position).getDesc());
                detailsDiff.setText(quizListModelList.get(position).getLevel());
                detailsQuestions.setText(quizListModelList.get(position).getQuestions() + "");

                quizId = quizListModelList.get(position).getQuiz_id();
                totalQestions = quizListModelList.get(position).getQuestions();

                loadResult();
            }
        });
    }

    private void loadResult() {
        // Get Result
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore.collection("QuizList")
                .document(quizId).collection("Results")
                .document(firebaseAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot result = task.getResult();
                    if (result != null && result.exists()){
                        long correct = 0;
                        correct = result.getLong("correct").longValue();
                        long wrong = 0;
                        wrong = result.getLong("wrong").longValue();
                        long miss = 0;
                        miss = result.getLong("unanswered").longValue();

                        // calculate progess
                        long total = correct + wrong + miss + 0;
                        long percent = (correct * 100) / total;

                        detailsScore.setText(percent + "%");
                    }

                }else{
                    detailsScore.setText("0%");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_btn:
                // Do cần truyền args để chỉ rõ xem đang xem quiz loại nào nên cần làm như sau
                DetailsFragmentDirections.ActionDetailsFragmentToQuizFragment action = DetailsFragmentDirections.actionDetailsFragmentToQuizFragment();
                action.setTotalQuestion(totalQestions);
                action.setQuizId(quizId);
                navController.navigate(action);
                break;
            default:
                break;
        }
    }
}