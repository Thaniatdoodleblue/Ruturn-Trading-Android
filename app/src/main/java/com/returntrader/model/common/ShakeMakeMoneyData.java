package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ShakeMakeMoneyData {

    private String snmAmountId;
    private String amount;
    private String isTopPick;
    private String status;
    private String createdDate;
    private boolean selected;

    public ShakeMakeMoneyData(String snmAmountId, String amount, String isTopPick, String status, String createdDate, boolean selected) {
        this.snmAmountId = snmAmountId;
        this.amount = amount;
        this.isTopPick = isTopPick;
        this.status = status;
        this.createdDate = createdDate;
        this.selected = selected;
    }

    public ShakeMakeMoneyData() {
    }

    public String getSnmAmountId() {
        return snmAmountId;
    }

    public void setSnmAmountId(String snmAmountId) {
        this.snmAmountId = snmAmountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIsTopPick() {
        return isTopPick;
    }

    public void setIsTopPick(String isTopPick) {
        this.isTopPick = isTopPick;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
