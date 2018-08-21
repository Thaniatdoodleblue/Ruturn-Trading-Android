package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 9/6/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class DelayPrice {

    @JsonField(name = "id")
    private String id;

    @JsonField(name = "isinCode")
    private String isinCode;

    @JsonField(name = "close")
    private Float closePrice = 0F;

    @JsonField(name = "bid")
    private Float bid = 0F;

    @JsonField(name = "offer")
    private Float offer = 0F;

    @JsonField(name = "last")
    private Float lastPrice = 0F;

    @JsonField(name = "status")
    private String companyItemStatus;

    @JsonField(name = "updated_at")
    private String updatedAt;

    @JsonField(name = "created_at")
    private String createdAt;

    @JsonField(name = "maxTradeAmount")
    private String maxTradeAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public Float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Float closePrice) {
        this.closePrice = closePrice;
    }

    public Float getBid() {
        return bid;
    }

    public void setBid(Float bid) {
        this.bid = bid;
    }

    public Float getOffer() {
        return offer;
    }

    public void setOffer(Float offer) {
        this.offer = offer;
    }

    public Float getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Float lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getCompanyItemStatus() {
        return companyItemStatus;
    }//companyItemStatus

    public void setCompanyItemStatus(String companyItemStatus) {
        this.companyItemStatus = companyItemStatus;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMaxTradeAmount() {
        return maxTradeAmount;
    }

    public void setMaxTradeAmount(String maxTradeAmount) {
        this.maxTradeAmount = maxTradeAmount;
    }
}
