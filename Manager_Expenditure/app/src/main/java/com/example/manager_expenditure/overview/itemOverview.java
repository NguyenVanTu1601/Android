package com.example.manager_expenditure.overview;

public class itemOverview {
    private int color;
    private int icon;
    private String nameItem;
    private long moneyItem;

    public itemOverview(int color, int icon, String nameItem, long moneyItem) {
        this.color = color;
        this.icon = icon;
        this.nameItem = nameItem;
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

    public long getMoneyItem() {
        return moneyItem;
    }
}
