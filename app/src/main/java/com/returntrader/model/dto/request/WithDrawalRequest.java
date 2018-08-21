package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by nirmal on 2/5/2018.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class WithDrawalRequest extends BaseRequest {
    private String amount;
    private String reason;

    public WithDrawalRequest() {

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
