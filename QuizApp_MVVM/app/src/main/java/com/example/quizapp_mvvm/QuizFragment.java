package com.example.quizapp_mvvm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
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

    private FirebaseFirestore firebaseFirestore;
    private String currentUID;
    private FirebaseAuth firebaseAuth;
    // ui
    private String quizName;
    private NavController navController;
    private String quizId;
    private TextView quizTitle;
    private Button optionOneBtn, optionTwoBtn, optionThreeBtn;
    private Button nextBtn;
    private TextView questionFeedBack, questionText, questionNumber, questionTime;
    private ProgressBar questionProgess;
    private ImageView Close;

    private CountDownTimer countDownTimer;
    private boolean canAnswer = false;
    private int currentQuestion = 0;

    private int correctAnswer = 0;
    private int wrongAnswer = 0;
    private int notAnswer = 0;
    // firebaseData
    private List<QuestionsModel> allQuestionsList = new ArrayList<>();
    private long totalQuestionsToAnswer = 10;
    private List<QuestionsModel> questionsToAnswer = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // firebase
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            currentUID = firebaseAuth.getCurrentUser().getUid();
        }else{
            // GO BACK HOMEPAGE
        }
        // ui
        navController = Navigation.findNavController(view);
        quizTitle = view.findViewById(R.id.quiz_title);
        optionOneBtn = view.findViewById(R.id.quiz_option_one);
        optionTwoBtn = view.findViewById(R.id.quiz_option_two);
        optionThreeBtn = view.findViewById(R.id.quiz_option_three);
        nextBtn = view.findViewById(R.id.quiz_next_btn);
        questionFeedBack = view.findViewById(R.id.quiz_question_feedback);
        questionText = view.findViewById(R.id.quiz_question);
        questionTime = view.findViewById(R.id.quiz_question_time);
        questionProgess = view.findViewById(R.id.quiz_question_progress);
        questionNumber = view.findViewById(R.id.quiz_question_number);
        Close = view.findViewById(R.id.quiz_close_btn);

        // firebase
        firebaseFirestore = FirebaseFirestore.getInstance();

        // get quizId
        quizId =  QuizFragmentArgs.fromBundle(getArguments()).getQuizId();
        totalQuestionsToAnswer = QuizFragmentArgs.fromBundle(getArguments()).getTotalQuestion();
        quizName = QuizFragmentArgs.fromBundle(getArguments()).getQuizName();
        if (quizName.equals("null")){
            quizName = "Loading data...";
        }
        firebaseFirestore.collection("QuizList")
                .document(quizId).collection("Questions")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            allQuestionsList = task.getResult().toObjects(QuestionsModel.class);
                            Log.d("allQustionListSize", allQuestionsList.size() + "");
                            //pickQuestions
                            pickQuestions();
                            loadUI();
                        }else{
                            // Error getting questions
                            quizTitle.setText("Error Loading Data...");
                        }
                    }
        });

        // onclick Option
        optionOneBtn.setOnClickListener(this);
        optionTwoBtn.setOnClickListener(this);
        optionThreeBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        Close.setOnClickListener(this);
    }

    private void loadUI() {
        quizTitle.setText(quizName);
        questionText.setText("Loading first question");

        // enable option
        enableOption();

        // load first question
        loadQuestions(0);
    }

    private void loadQuestions(int questionNum) {
        // set question number
        questionNumber.setText(questionNum + 1 + "");
        questionText.setText(questionsToAnswer.get(questionNum).getQuestion());

        //load option
        optionOneBtn.setText(questionsToAnswer.get(questionNum).getOption_a());
        optionTwoBtn.setText(questionsToAnswer.get(questionNum).getOption_b());
        optionThreeBtn.setText(questionsToAnswer.get(questionNum).getOption_c());

        canAnswer = true;
        currentQuestion = questionNum;

        // start question number
        startTimmer(questionNum );
        
    }

    private void startTimmer(int questionNumber) {

        //show progess
        questionProgess.setVisibility(View.VISIBLE);

        // setTimerText;
        final Long timeToAnswer = questionsToAnswer.get(questionNumber).getTimer();
        questionTime.setText(timeToAnswer.toString());

        countDownTimer = new CountDownTimer(timeToAnswer*1000,10){

            @Override
            public void onTick(long millisUntilFinished) {
                questionTime.setText(millisUntilFinished/1000 + "");

                //
                Long percent = millisUntilFinished/(timeToAnswer*10);
                questionProgess.setProgress(percent.intValue());
            }

            @Override
            public void onFinish() {
                //Time up. Can not Answer question anymore;
                canAnswer = false;
                questionFeedBack.setText("Time up, No Answer!");
                notAnswer++;
                showNextButton();
            }
        };

        countDownTimer.start();
    }

    private void enableOption() {
        // showAll option button
        optionOneBtn.setVisibility(View.VISIBLE);
        optionTwoBtn.setVisibility(View.VISIBLE);
        optionThreeBtn.setVisibility(View.VISIBLE);

        // enable option button
        optionOneBtn.setEnabled(true);
        optionTwoBtn.setEnabled(true);
        optionThreeBtn.setEnabled(true);

        // hide feedback and next button
        questionFeedBack.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setEnabled(false);
    }

    private void pickQuestions() {
        for (int i = 0; i < totalQuestionsToAnswer; i++){
            int randomNumber = getRandomInteger(allQuestionsList.size(),0);

            QuestionsModel q = allQuestionsList.get(randomNumber);
            Log.d("totalQuestionsToAnswer" , totalQuestionsToAnswer+ "");
            Log.d("randomNumber", q.getQuestion()+"");
            questionsToAnswer.add(allQuestionsList.get(randomNumber));
            allQuestionsList.remove(randomNumber);

        }
    }

    public static int getRandomInteger(int maximum, int minximum){
        return ((int) (Math.random()*(maximum - minximum))) + minximum;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quiz_option_one:
                answerSelected(optionOneBtn);
                break;
            case R.id.quiz_option_two:
                answerSelected(optionTwoBtn);
                break;
            case R.id.quiz_option_three:
                answerSelected(optionThreeBtn);
                break;
            case R.id.quiz_next_btn:
                currentQuestion ++;
                if (currentQuestion >= questionsToAnswer.size()){

                    // load Result
                    submitResults();

                }else{
                    loadQuestions(currentQuestion);
                    resetOptions(); // trả lại màu cho các option sau khi click đúng sai của câu trước
                }
                break;
            case R.id.quiz_close_btn:
                navController.navigate(R.id.action_quizFragment_to_listFragment);
                break;
        }
    }

    private void submitResults() {
        // up result lên fireStore
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("correct", correctAnswer);
        resultMap.put("wrong",wrongAnswer);
        resultMap.put("unanswered", notAnswer);

        firebaseFirestore.collection("QuizList")
                .document(quizId).collection("Results")
                .document(currentUID).set(resultMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    // go to the result page
                    QuizFragmentDirections.ActionQuizFragmentToResultFragment action = QuizFragmentDirections.actionQuizFragmentToResultFragment();
                    action.setQuizId(quizId);
                    navController.navigate(action);
                }else{
                    // show error
                    quizTitle.setText(task.getException().getMessage());
                }
            }
        });
    }

    private void resetOptions() {
        optionOneBtn.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg,null));
        optionTwoBtn.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg,null));
        optionThreeBtn.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg,null));

        questionFeedBack.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setEnabled(false);
    }

    private void answerSelected(Button btn) {

        //btn.setTextColor(getResources().getColor(R.color.colorDark,null)); - api 23
        // check answer
        if (canAnswer){
            questionProgess.setProgressDrawable(getResources().getDrawable(R.drawable.circler_progress,null));
            if (questionsToAnswer.get(currentQuestion).getAnswer().equals(btn.getText())){
                // correct answer
                correctAnswer ++;
                btn.setBackground(getResources().getDrawable(R.drawable.correct_answer_bg_btn,null));
                //set feedback Text
                questionFeedBack.setText("Correct Answer!");
            }else{
                //wrong answer
                wrongAnswer++;
                btn.setBackground(getResources().getDrawable(R.drawable.wrong_answer_bg_btn,null));

                questionFeedBack.setText("Wrong Answer! \n Answer : " + questionsToAnswer.get(currentQuestion).getAnswer());
            }
            // set canAnswer to false
            canAnswer = false;
            countDownTimer.cancel();

            // show Next Button
            showNextButton();
        }
    }

    private void showNextButton() {
        if (currentQuestion + 1 >= questionsToAnswer.size()){
            nextBtn.setText("Submit Result");
        }
        questionFeedBack.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.VISIBLE);
        nextBtn.setEnabled(true);
    }


}