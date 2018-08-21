package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.WithDrawableBalanceData;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class WithDrawBalanceResponse extends BaseResponse {

   private WithDrawableBalanceData data;

    public WithDrawBalanceResponse() {
    }

    public WithDrawBalanceResponse(WithDrawableBalanceData data) {
        this.data = data;
    }

    public WithDrawableBalanceData getData() {
        return data;
    }

    public void setData(WithDrawableBalanceData data) {
        this.data = data;
    }
}
