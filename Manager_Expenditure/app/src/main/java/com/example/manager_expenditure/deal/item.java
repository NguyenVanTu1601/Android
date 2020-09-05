package com.example.manager_expenditure.deal;

public class item {
    private int color;
    private int icon;
    private String nameItem;
    private String fromCredit;
    private long moneyItem;

    public item(int color, int icon, String nameItem, String fromCredit, long moneyItem) {
        this.color = color;
        this.icon = icon;
        this.nameItem = nameItem;
        this.fromCredit = fromCredit;
        this.moneyItem = moneyItem;
    }

    public int getColor() {
        return color;
    }

    public int getIcon() {
        return icon;
    }

    public String getNameItem() {
        return nameItem;
    }

    public String getFromCredit() {
        return fromCredit;
    }

    public long getMoneyItem() {
        return moneyItem;
    }
}
