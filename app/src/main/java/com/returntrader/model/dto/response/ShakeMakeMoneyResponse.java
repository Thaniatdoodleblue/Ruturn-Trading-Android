package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.ShakeMakeMoneyData;

import java.util.List;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ShakeMakeMoneyResponse extends BaseResponse {

    @JsonField(name = "snmGroups")
    private List<ShakeMakeMoneyData> shakeMakeMoneyData;

    public ShakeMakeMoneyResponse(List<ShakeMakeMoneyData> shakeMakeMoneyData) {
        this.shakeMakeMoneyData = shakeMakeMoneyData;
    }

    public ShakeMakeMoneyResponse() {
    }

    public List<ShakeMakeMoneyData> getShakeMakeMoneyData() {
        return shakeMakeMoneyData;
    }

    public void setShakeMakeMoneyData(List<ShakeMakeMoneyData> shakeMakeMoneyData) {
        this.shakeMakeMoneyData = shakeMakeMoneyData;
    }
}
