package com.returntrader.model.common;


public class Option {

    public String name;

    private int itemType;

    private boolean isChecked;

    public Option(String name, int itemType) {
        this.name = name;
        this.itemType = itemType;
    }

    public Option(String name, int itemType, boolean isChecked) {
        this.name = name;
        this.itemType = itemType;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Option(String name) {
        this.name = name;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
