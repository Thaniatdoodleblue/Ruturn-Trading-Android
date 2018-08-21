package com.returntrader.model.common;

/**
 * Created by moorthy on 10/9/2017.
 */

public class TopBarData {

    private String lastKnownBalance;

    private String investedAmount;

    private String holdingAmount;

    public String getLastKnownBalance() {
        return lastKnownBalance;
    }

    public void setLastKnownBalance(String lastKnownBalance) {
        this.lastKnownBalance = lastKnownBalance;
    }

    public String getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(String investedAmount) {
        this.investedAmount = investedAmount;
    }

    public String getHoldingAmount() {
        return holdingAmount;
    }

    public void setHoldingAmount(String holdingAmount) {
        this.holdingAmount = holdingAmount;
    }
}
