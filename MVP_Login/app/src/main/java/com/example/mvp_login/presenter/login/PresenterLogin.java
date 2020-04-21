package com.example.mvp_login.presenter.login;

import com.example.mvp_login.model.login.ModelLogin;
import com.example.mvp_login.model.login.ModelResponsePresenter;
import com.example.mvp_login.view.login.ViewLoginListener;

public class PresenterLogin implements ModelResponsePresenter {
    private ModelLogin modelLogin;
    private ViewLoginListener callback;

    public PresenterLogin(ViewLoginListener callback) {
        this.callback = callback;
    }

    public void receivedHanderLogin(String email, String pass){
        // Tại đây thông báo cho model biết để xử lý logic ng dung login
        modelLogin = new ModelLogin(this);
        modelLogin.handleLogin(email,pass);
    }

    @Override
    public void onLoginSuccess() {
        callback.onLoginSuccess();
    }

    @Override
    public void onLoginFailed() {
        callback.onLoginFaile();
    }
}
