package com.returntrader.model.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 9/15/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class StockHold implements Parcelable {

    @JsonField(name = "trustAccountId")
    private String trustAccountId;

    @JsonField(name = "isinCode")
    private String isinCode;

    @JsonField(name = "shareCode")
    private String shareCode;

    @JsonField(name = "name")
    private String companyName;

    @JsonField(name = "shares")
    private String shares;

    @JsonField(name = "baseCost")
    private String baseCostStr;
//    private float baseCost = 0f;

    @JsonField(name = "valuation")
    private String valuation;

    @JsonField(name = "closingPrice")
    private Float closingPrice = 0f;

    private String companyLogo;

    private Float lastUpdatedPrice = 0f;

    private String currentHolding;

    private String currentInvestment;

    private String maxTradeAmount;

    private String companyAvailabilityStatus;

    public String getTrustAccountId() {
        return trustAccountId;
    }

    public void setTrustAccountId(String trustAccountId) {
        this.trustAccountId = trustAccountId;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public float getBaseCost() {
        return (Float.valueOf(TextUtils.isEmpty(baseCostStr) ? "0.0" : baseCostStr.replace(",", ".")) / 100);
    }

    /*public void setBaseCost(float baseCost) {
        this.baseCost = baseCost;
    }*/

    public String getValuation() {
        return valuation;
    }

    public void setValuation(String valuation) {
        this.valuation = valuation;
    }

    public Float getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(Float closingPrice) {
        this.closingPrice = closingPrice;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public Float getLastUpdatedPrice() {
        return lastUpdatedPrice;
    }

    public void setLastUpdatedPrice(Float lastUpdatedPrice) {
        this.lastUpdatedPrice = lastUpdatedPrice;
    }

    public String getCurrentHolding() {
        return currentHolding;
    }

    public void setCurrentHolding(String currentHolding) {
        this.currentHolding = currentHolding;
    }

    public String getCurrentInvestment() {
        return currentInvestment;
    }

    public void setCurrentInvestment(String currentInvestment) {
        this.currentInvestment = currentInvestment;
    }


    public StockHold() {
    }

    protected StockHold(Parcel in) {
        trustAccountId = in.readString();
        isinCode = in.readString();
        shareCode = in.readString();
        companyName = in.readString();
        shares = in.readString();
//        baseCost = in.readFloat();
        baseCostStr = in.readString();
        valuation = in.readString();
        closingPrice = in.readByte() == 0x00 ? null : in.readFloat();
        companyLogo = in.readString();
        lastUpdatedPrice = in.readFloat();
        currentHolding = in.readString();
        currentInvestment = in.readString();
        maxTradeAmount = in.readString();
        companyAvailabilityStatus = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trustAccountId);
        dest.writeString(isinCode);
        dest.writeString(shareCode);
        dest.writeString(companyName);
        dest.writeString(shares);
//        dest.writeFloat(baseCost);
        dest.writeString(baseCostStr);
        dest.writeString(valuation);
        if (closingPrice == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(closingPrice);
        }
        dest.writeString(companyLogo);
        dest.writeFloat(lastUpdatedPrice);
        dest.writeString(currentHolding);
        dest.writeString(currentInvestment);
        dest.writeString(maxTradeAmount);
        dest.writeString(companyAvailabilityStatus);
    }

    @SuppressWarnings("unused")
    public static final Creator<StockHold> CREATOR = new Creator<StockHold>() {
        @Override
        public StockHold createFromParcel(Parcel in) {
            return new StockHold(in);
        }

        @Override
        public StockHold[] newArray(int size) {
            return new StockHold[size];
        }
    };

    public String getBaseCostStr() {
        return baseCostStr;
    }

    public void setBaseCostStr(String baseCostStr) {
        this.baseCostStr = baseCostStr;
    }

    public String getMaxTradeAmount() {
        return maxTradeAmount;
    }

    public void setMaxTradeAmount(String maxTradeAmount) {
        this.maxTradeAmount = maxTradeAmount;
    }

    public String getCompanyAvailabilityStatus() {
        return companyAvailabilityStatus;
    }

    public void setCompanyAvailabilityStatus(String companyAvailabilityStatus) {
        this.companyAvailabilityStatus = companyAvailabilityStatus;
    }
}
