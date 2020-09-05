package com.example.manager_expenditure.credit;

public class Credit {
    private int icon;
    private int color;
    private String nameCredit;
    private long money;
    private String loại;

    public Credit(int icon, int color, String nameCredit, long money, String loại) {
        this.icon = icon;
        this.color = color;
        this.nameCredit = nameCredit;
        this.money = money;
        this.loại = loại;
    }

    public int getIcon() {
        return icon;
    }

    public int getColor() {
        return color;
    }

    public String getNameCredit() {
        return nameCredit;
    }

    public long getMoney() {
        return money;
    }

    public String getLoại() {
        return loại;
    }
}
