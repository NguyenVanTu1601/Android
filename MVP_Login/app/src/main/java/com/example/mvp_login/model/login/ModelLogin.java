package com.example.mvp_login.model.login;

public class ModelLogin {
    ModelResponsePresenter callback;

    public ModelLogin(ModelResponsePresenter callback) {
        this.callback = callback;
    }

    public void handleLogin(String email, String pass){
        if(email.equals("banggia") && pass.equals("160199")){
            callback.onLoginSuccess();
        }else{
            callback.onLoginFailed();
        }
    }
}
