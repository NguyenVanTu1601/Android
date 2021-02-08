package com.example.mydoesapp;

public class KeyDoes {
    public String key;
    public <T extends KeyDoes> T withKey(final String keyDoes){
        this.key = keyDoes;
        return (T) this;
    }
}
