package com.returntrader.model.common;

/**
 * Created by moorthy on 10/3/2017.
 */

public class FilterCategory {

    private String categoryName;

    private int type;

    private boolean isSelected;

    public String getCategoryName() {
        return categoryName;
    }


    public FilterCategory() {
    }

    public FilterCategory(String categoryName, int type) {
        this.categoryName = categoryName;
        this.type = type;
    }

    public FilterCategory(String categoryName, int type, boolean isSelected) {
        this.categoryName = categoryName;
        this.type = type;
        this.isSelected = isSelected;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
