package com.example.manager_expenditure.listioner;

import java.text.NumberFormat;
import java.util.Locale;

public class PublicFunction {
    public String FormatMoney(long x){
        // Định dạng tiền tệ
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(x);
    }
}
