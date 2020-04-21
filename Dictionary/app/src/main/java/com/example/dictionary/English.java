package com.example.dictionary;

public class English {
    private String vocabulary;
    private String wordMeaning;

    public English(String vocabulary, String wordMeaning) {
        this.vocabulary = vocabulary;
        this.wordMeaning = wordMeaning;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public String getWordMeaning() {
        return wordMeaning;
    }
}
