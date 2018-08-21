package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 9/18/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BuyRequest extends BaseRequest {

    @JsonField(name = "orderValue")
    private String orderValue;

    @JsonField(name = "isinCode")
    private String isinCode;

    public BuyRequest(String userId, String orderValue, String isinCode) {
        super(userId);
        this.orderValue = orderValue;
        this.isinCode = isinCode;
    }

    public BuyRequest() {
    }

    public BuyRequest(String orderValue, String isinCode) {
        this.orderValue = orderValue;
        this.isinCode = isinCode;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }
}
