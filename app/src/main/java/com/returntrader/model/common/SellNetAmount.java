package com.returntrader.model.common;

/**
 * Created by moorthy on 9/21/2017.
 */

public class SellNetAmount {

    private String grossAmount;

    private String investmentCost;

    private String netAmount;

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public void setInvestmentCost(String investmentCost) {
        this.investmentCost = investmentCost;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public String getInvestmentCost() {
        return investmentCost;
    }

    public String getNetAmount() {
        return netAmount;
    }
}
