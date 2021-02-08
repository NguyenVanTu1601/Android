package com.example.quizapp_mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * Hiểu MVVM như này :
 * Đầu tiên, QuizListViewModel là một onFireStore (interface trong class FirebaseRepossitory)do đã implement
 * Tạo 1 FirebaseRepsitory có tham số truyền vào là cái QuizList vừa tạo
 * Khi đó cái QuizListViewModel này = onFireStoreTaskComplete trong FirebaseReositoty
 * Khi gọi cái FirebaseRepossitory gọi .getQuizData() thì tương đương với truyền vào 2 cái override bên dưới dữ liệu lấy được
 *
 */
public class QuizListViewModel extends ViewModel implements FirebaseRepository.OnFireStoreTaskComplete {

    public LiveData<List<QuizListModel>> getQuizListModelData() {
        return quizListModelData;
    }


    private MutableLiveData<List<QuizListModel>> quizListModelData = new MutableLiveData<>();
    private FirebaseRepository firebaseRepository = new FirebaseRepository(this); // this chính là QuizListViewModel

    public QuizListViewModel (){ // hàm này phải public nếu k sẽ lỗi bên ListFragment
        firebaseRepository.getQuizData(); // khởi tạo data lưu vào các interface
    }

    @Override
    public void quizListDataAdded(List<QuizListModel> quizListModelList) {
        quizListModelData.setValue(quizListModelList);
    }

    @Override
    public void onError(Exception e) {

    }
}
