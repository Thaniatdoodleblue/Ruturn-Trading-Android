package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 9/18/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BuyResponse extends BaseResponse{

    @JsonField(name = "id")
    private String id;

    @JsonField(name = "trustAccountId")
    private String trustAccountId;

    @JsonField(name = "isinCode")
    private String isinCode;

    @JsonField(name = "shareCode")
    private String shareCode;

    @JsonField(name = "shareName")
    private String shareName;

    @JsonField(name = "dateCreated")
    private String dateCreated;

    @JsonField(name = "amount")
    private String amount;

    @JsonField(name = "tradeType")
    private String tradeType;

    @JsonField(name = "isProcessed")
    private boolean processed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
