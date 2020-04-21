package com.example.mvp_login.view.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mvp_login.R;
import com.example.mvp_login.presenter.login.PresenterLogin;

public class LoginRegisterFragment extends Fragment implements View.OnClickListener, ViewLoginListener {
    EditText edtEmail, edtPassword;
    TextView txtLogin, txtRegister;
    Button btnLogin;

    // Tầng presenter
    PresenterLogin presenterLogin;

    public LoginRegisterFragment(){
        // requiment empty contructor
    }

    public static LoginRegisterFragment newInstance(){
        LoginRegisterFragment loginRegisterFragment = new LoginRegisterFragment();
        return loginRegisterFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_register,container,false);

        edtEmail = view.findViewById(R.id.editTextEmail);
        edtPassword = view.findViewById(R.id.editTextPassword);
        txtLogin = view.findViewById(R.id.textViewLogin);
        txtRegister = view.findViewById(R.id.textViewRegister);
        btnLogin = view.findViewById(R.id.buttonLogin);

        presenterLogin = new PresenterLogin(this); // truyền this do đã implement rồi

        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewLogin:
                Toast.makeText(getActivity(), "Login...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textViewRegister:
                Toast.makeText(getActivity(), "Register...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonLogin:
                String em = edtEmail.getText().toString();
                String pa = edtPassword.getText().toString();
                presenterLogin.receivedHanderLogin(em,pa);
                break;
        }
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getActivity(), "Success...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFaile() {
        Toast.makeText(getActivity(), "Faile...", Toast.LENGTH_SHORT).show();
    }
}
