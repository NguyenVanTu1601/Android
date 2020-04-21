package com.example.morse_code;

import java.util.HashMap;

public class Morse_Code {
    public Morse_Code() {
    }

    static String[] ALPHA = {"a","b","c","d","e","f","g","h",
                             "i","j","k","l","m","n","o","p",
                             "q","r","s","t","u","v","w","x",
                             "y", "z","0","1","2","3","4","5",
                             "6", "7","8","9"," "};
    static String[] MORSE = {".-","-...","-.-.","-..",".","..-.","--.","....",
                             "..",".---","-.-",".-..","--","-.","---",".--.",
                             "--.-",".-.","...","-","..-","...-",".--","-..-",
                             "-.--","--..",".---","..---","...--","....-",".....",
                             "-....","--...","---..","----.","----", " "};

    public static HashMap<String, String> ALPHA_TO_MORSE;
    public static HashMap<String, String> MORSE_TO_ALPHA;
    static {
        for (int i = 0; i < MORSE.length; i++){
            ALPHA_TO_MORSE.put(ALPHA[i],MORSE[i]);
            MORSE_TO_ALPHA.put(MORSE[i],ALPHA[i]);
        }
    }

    public static String morseToAlpha(String morseCode){
        String text = morseCode;
        text = text.trim();
        StringBuilder builder = new StringBuilder();
        String[] arrayText = text.split(" ");
        for(int i = 0; i < arrayText.length; i++){
            builder.append(MORSE_TO_ALPHA.get(arrayText[i]));
            builder.append(" ");
        }
        return builder.toString();
    }

    public static String alphaToMorse(String englishCode){
        String text = englishCode;
        text = text.trim();
        text = text.replaceAll("\\s+"," ");
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < text.length(); i++){
            builder.append(ALPHA_TO_MORSE.get(String.valueOf(text.charAt(i))));
        }
        return builder.toString();
    }
}
