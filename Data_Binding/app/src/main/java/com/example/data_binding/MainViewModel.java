package com.example.data_binding;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        setUser();
        return user;
    }

    public void setUser() {
        User user = new User();
        user.name = "Nguyễn Văn Tú";
        user.email = "tunguyen160199@gmail.com";

        //we need set multableLiveData user
        this.user.setValue(user);
    }
}
