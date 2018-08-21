package com.returntrader.model.common;

/**
 * Created by moorthy on 7/24/2017.
 */

public class FeeDetailItem {

    String title;

    String description;

    public FeeDetailItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
