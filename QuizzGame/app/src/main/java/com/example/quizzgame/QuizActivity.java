package com.example.quizzgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizzgame.databinding.ActivityQuizBinding;
import com.example.quizzgame.model.Question;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private ActivityQuizBinding binding;
    private ArrayList<Question> questions; // mảng các questions
    private Question question; // question hiện tại đang hiển thị
    private int index = 0; // vị trí tương ứng với question lấy từ questions
    private CountDownTimer timer;
    private FirebaseFirestore database;
    private int correctAnswer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        questions = new ArrayList<>();

        String catId = getIntent().getStringExtra("catId");
        Random random = new Random();
        int rand = random.nextInt(8);

        database = FirebaseFirestore.getInstance();
        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(5)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() < 5){
                            database.collection("categories")
                                    .document(catId)
                                    .collection("questions")
                                    .whereLessThanOrEqualTo("index", rand)
                                    .orderBy("index")
                                    .limit(5)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if (queryDocumentSnapshots.getDocuments().size() < 5){

                                            }else{
                                                for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                                                    Question question = snapshot.toObject(Question.class);
                                                    questions.add(question);
                                                }
                                                setNextQuestion();
                                            }
                                        }
                                    });
                        }else{
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                            setNextQuestion();
                        }
                    }
                });

        if (questions.isEmpty()){
            questions.add(new Question("What is earth?",
                    "Planet","Sun","Human","Car","Planet"));
            questions.add(new Question("What is firstname of Tu?",
                    "Nguyễn","Trần","Trịnh","Hoàng","Nguyễn"));
        }

        resetTimer();

    }

    // chuyển sang câu hỏi khác
    public void setNextQuestion(){
        if (timer != null){
            timer.cancel();
        }
        timer.start();

        if (index + 1 < questions.size()){
            binding.questionCounter.setText(String.format("%d/%d", (index + 1), questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());

        }
    }

    // onclick
    public void onClick(View view){
        TextView selected = (TextView) view;
        switch (view.getId()){
            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:
                if (timer != null){ // nếu nhấn nút thì dừng đếm lại
                    timer.cancel();
                }
                checkAnswer(selected);
                break;

            case R.id.nextBtn:
                removeSelected();
                if (index + 1  < questions.size()){
                    index++;
                    setNextQuestion();
                }else{
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("correct", correctAnswer);
                    intent.putExtra("total",questions.size());

                    startActivity(intent);
                    finish();
                }


                break;
            case R.id.quizBtn:
                break;
        }
    }

    public void resetTimer(){
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    // gọi khi ng chơi đã chọn đáp án, hàm này show ra đáp án đúng
    public void showAnswer(){
        if (question.getAnswer().equals(binding.option1.getText().toString())){
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        if (question.getAnswer().equals(binding.option2.getText().toString())){
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        if (question.getAnswer().equals(binding.option3.getText().toString())){
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        if (question.getAnswer().equals(binding.option4.getText().toString())){
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
        }

    }

    // kiểm tra đáp án của ng chơi
    public void checkAnswer(TextView textView){
        String as = textView.getText().toString();
        if (as.equals(questions.get(index).getAnswer())){
            correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else{
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    // xóa hết background đáp án đã chọn khi chuyển sang câu hỏi mới
    public void removeSelected(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));

    }
}