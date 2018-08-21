package com.returntrader.model.common;

public class MoneyInfo  {

    private String title,money;

    public MoneyInfo(String title, String money) {
        this.title = title;
        this.money = money;
    }

    public MoneyInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
