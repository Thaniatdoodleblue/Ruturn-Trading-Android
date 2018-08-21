package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.math.BigDecimal;

/**
 * Created by moorthy on 9/15/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BalanceItem {

    @JsonField(name = "trustAccountId")
    private String trustAccountId;

    @JsonField(name = "accountType")
    private String accountType;

    @JsonField(name = "tradingCurrency")
    private String tradingCurrency;

    @JsonField(name = "tradingCurrencyType")
    private String tradingCurrencyType;

    @JsonField(name = "balance")
    private String balance;

    @JsonField(name = "easyMoneyBalance")
    private int easyMoneyBalance;

    @JsonField(name = "eftReference")
    private String eftReference;

    @JsonField(name = "withdrawableAmount")
    private String withDrawableAmount;

    @JsonField(name = "lockedUpAmount")
    private int lockedUpAmount;

    @JsonField(name = "unsettledAmount")
    private int unsettledAmount;


    public String getTrustAccountId() {
        return trustAccountId;
    }

    public void setTrustAccountId(String trustAccountId) {
        this.trustAccountId = trustAccountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getTradingCurrency() {
        return tradingCurrency;
    }

    public void setTradingCurrency(String tradingCurrency) {
        this.tradingCurrency = tradingCurrency;
    }

    public String getTradingCurrencyType() {
        return tradingCurrencyType;
    }

    public void setTradingCurrencyType(String tradingCurrencyType) {
        this.tradingCurrencyType = tradingCurrencyType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getEasyMoneyBalance() {
        return easyMoneyBalance;
    }

    public void setEasyMoneyBalance(int easyMoneyBalance) {
        this.easyMoneyBalance = easyMoneyBalance;
    }

    public String getEftReference() {
        return eftReference;
    }

    public void setEftReference(String eftReference) {
        this.eftReference = eftReference;
    }

    public String getWithDrawableAmount() {
        return withDrawableAmount;
    }

    public void setWithDrawableAmount(String withDrawableAmount) {
        this.withDrawableAmount = withDrawableAmount;
    }

    public int getLockedUpAmount() {
        return lockedUpAmount;
    }

    public void setLockedUpAmount(int lockedUpAmount) {
        this.lockedUpAmount = lockedUpAmount;
    }

    public int getUnsettledAmount() {
        return unsettledAmount;
    }

    public void setUnsettledAmount(int unsettledAmount) {
        this.unsettledAmount = unsettledAmount;
    }
}
