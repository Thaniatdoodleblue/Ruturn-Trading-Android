package com.returntrader.model.common;

/**
 * Created by moorthy on 7/21/2017.
 */

public class TransactionFee {

    private String taxName;

    private String tax;

    private String currency = "R";

    private int itemType = 0;


    public TransactionFee(String taxName, int itemType) {
        this.taxName = taxName;
        this.itemType = itemType;
    }

    public TransactionFee(String taxName, String tax, String currency, int itemType) {
        this.taxName = taxName;
        this.tax = tax;
        this.currency = currency;
        this.itemType = itemType;
    }

    public TransactionFee(String taxName, String tax, String currency) {
        this.taxName = taxName;
        this.tax = tax;
        this.currency = currency;
    }

    public TransactionFee(String taxName, String tax) {
        this.taxName = taxName;
        this.tax = tax;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
