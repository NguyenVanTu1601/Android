package com.example.manager_expenditure.deal;

import java.util.List;

public class Deal {
    private long moneyDeal;
    private String day;
    private String dayOfWeek;
    private String monthYear;
    private List<item> listItem;

    public long getMoneyDeal() {
        return moneyDeal;
    }

    public String getDay() {
        return day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public List<item> getListItem() {
        return listItem;
    }

    public Deal(long moneyDeal, String day, String dayOfWeek, String monthYear, List<item> listItem) {
        this.moneyDeal = moneyDeal;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.monthYear = monthYear;
        this.listItem = listItem;
    }
}
