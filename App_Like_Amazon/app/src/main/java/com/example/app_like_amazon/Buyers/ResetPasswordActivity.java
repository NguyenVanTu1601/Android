package com.example.app_like_amazon.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_like_amazon.Prevalent.Prevalent;
import com.example.app_like_amazon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/** Chia làm 2 phần
 * Nếu người dùng tới đây từ phần cài đặt -> Cho phép ng dùng cập nhật 2 câu trả lời để giúp lấy pass khi cần
 * Nếu người dùng tới đây từ phần forgetPassword thì cho ng dùng nhập sđt, 2 câu trả lời đã lưu để lấy mật khẩu
 */
public class ResetPasswordActivity extends AppCompatActivity {
    private String check = "";
    private TextView pageTitle, titleQuestion;
    private EditText phoneNumber, question1, question2;
    private Button verifyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check = getIntent().getStringExtra("check");
        
        initialization();

    }

    private void initialization() {
        pageTitle = findViewById(R.id.reset_password);
        titleQuestion = findViewById(R.id.question);
        phoneNumber = findViewById(R.id.find_phone_number);
        question1 = findViewById(R.id.question_1);
        question2 = findViewById(R.id.question_2);
        verifyBtn = findViewById(R.id.verify_btn);

    }

    @Override
    protected void onStart() {
        super.onStart();
        phoneNumber.setVisibility(View.GONE);



        // Kiểm tra xem đăng nhập vào từ đâu
        if(check.equals("settings")){

            // Đọc các câu trả lời nếu đã có để ng dùng sửa hoặc ko
            DisplayPreveousAnswer();


            pageTitle.setText("Set Question");
            titleQuestion.setText("Please set answwer the question");

            verifyBtn.setText("Set");

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswer();

                }
            });

        }else if(check.equals("login")){
            phoneNumber.setVisibility(View.VISIBLE);

            // Lấy mật khẩu
            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();
                }
            });
        }
    }

    private void verifyUser() {

        final String phone = phoneNumber.getText().toString().trim();


        if(question1.equals("") && question2.equals("")){
            Toast.makeText(ResetPasswordActivity.this, "Please Answer the question", Toast.LENGTH_SHORT).show();

        }else if(phone.equals("")){
            Toast.makeText(this, "Please write phone number...", Toast.LENGTH_SHORT).show();
        }
        else{
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String mPhone = dataSnapshot.child("phone").getValue().toString();

                        if(dataSnapshot.hasChild("Security Questions")){
                            String answer1 = question1.getText().toString().toLowerCase();
                            String answer2 = question2.getText().toString().toLowerCase();

                            String ans1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String ans2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();

                            if(!ans1.equals(answer1)){
                                Toast.makeText(ResetPasswordActivity.this, "Answer1 not true", Toast.LENGTH_SHORT).show();
                            }else if(!ans2.equals(answer2)){
                                Toast.makeText(ResetPasswordActivity.this, "Answer2 not true", Toast.LENGTH_SHORT).show();
                            }else{

                                // Hiện dialog thay đổi password
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("Your Password !!");
                                final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("Please write new password >.<");
                                builder.setView(newPassword);

                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!newPassword.getText().toString().equals("")){
                                            ref.child("password").setValue(newPassword.getText().toString().trim())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(ResetPasswordActivity.this, "Change password success!", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                                startActivity(intent);
                                                                finish();

                                                            }

                                                        }
                                                    });
                                        }
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.show();
                            }
                        }
                        else{
                            Toast.makeText(ResetPasswordActivity.this, "You not set answer Security Question!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ResetPasswordActivity.this, "Phone number is not exits", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void setAnswer(){

        // Lấy thông tin các câu trả lời và cập nhật vào nhánh users
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();
        if(question1.equals("") && question2.equals("")){
            Toast.makeText(ResetPasswordActivity.this, "Please Answer the question", Toast.LENGTH_SHORT).show();

        }else{
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(Prevalent.currentOnlineUser.getPhone());
            Map<String,Object> userMap = new HashMap<>();
            userMap.put("answer1",answer1);
            userMap.put("answer2",answer2);
            ref.child("Security Questions").updateChildren(userMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this, "Set security question success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }

    private void DisplayPreveousAnswer(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(Prevalent.currentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String ans1 = dataSnapshot.child("answer1").getValue().toString();
                    String ans2 = dataSnapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

