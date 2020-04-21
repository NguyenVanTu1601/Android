package com.example.innet_explicit_truyen_nhan_doituong;

import androidx.annotation.NonNull;

import java.io.Serializable;


public class Sinhvien implements Serializable {
        private String MaSv;
        private String Tensv;
        private float Mark;

        public Sinhvien(String maSv, String tensv, float mark) {
            MaSv = maSv;
            Tensv = tensv;
            Mark = mark;
        }

        @NonNull
        @Override
        public String toString() {
            return MaSv + "\n" + Tensv + "\n" + Mark;
        }
    }

