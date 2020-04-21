package com.example.dictionary;

import java.util.Comparator;

public class sortVocabulary implements Comparator<English> {
    @Override
    public int compare(English o1, English o2) {
        return o1.getVocabulary().compareTo(o2.getVocabulary());
    }
}
