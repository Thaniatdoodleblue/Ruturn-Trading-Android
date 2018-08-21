package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.common.TraderApplication;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class WithDrawableBalanceData {
    private String availableCash;
    private String unsettledCash;
    private String netSettledCash;
    private int lockedUpCash;
    private String withdrawableCash;

    public WithDrawableBalanceData(String availableCash, String unsettledCash, String netSettledCash, int lockedUpCash, String withdrawableCash) {
        this.availableCash = availableCash;
        this.unsettledCash = unsettledCash;
        this.netSettledCash = netSettledCash;
        this.lockedUpCash = lockedUpCash;
        this.withdrawableCash = withdrawableCash;
    }

    public WithDrawableBalanceData() {
    }

    public String getAvailableCash() {
        return TraderApplication.getInstance().convertPriceCentToRand(availableCash);
    }

    public void setAvailableCash(String availableCash) {
        this.availableCash = availableCash;
    }

    public String getUnsettledCash() {
        return TraderApplication.getInstance().convertPriceCentToRand(unsettledCash);
    }

    public void setUnsettledCash(String unsettledCash) {
        this.unsettledCash = unsettledCash;
    }

    public String getNetSettledCash() {
        return TraderApplication.getInstance().convertPriceCentToRand(netSettledCash);
    }

    public void setNetSettledCash(String netSettledCash) {
        this.netSettledCash = netSettledCash;
    }

    public int getLockedUpCash() {
        return lockedUpCash;
    }

    public void setLockedUpCash(int lockedUpCash) {
        this.lockedUpCash = lockedUpCash;
    }

    public String getWithdrawableCash() {
        return TraderApplication.getInstance().convertPriceCentToRand(withdrawableCash);
    }

    public void setWithdrawableCash(String withdrawableCash) {
        this.withdrawableCash = withdrawableCash;
    }
}
