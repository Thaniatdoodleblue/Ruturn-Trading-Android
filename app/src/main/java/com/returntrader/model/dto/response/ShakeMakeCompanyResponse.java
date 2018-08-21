package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.ShakeMakeCompanyData;


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ShakeMakeCompanyResponse extends BaseResponse {

    @JsonField(name = "snmGroups")
    private ShakeMakeCompanyData shakeMakeGroupData;

    public ShakeMakeCompanyResponse(ShakeMakeCompanyData shakeMakeGroupData) {
        this.shakeMakeGroupData = shakeMakeGroupData;
    }

    public ShakeMakeCompanyResponse() {
    }

    public ShakeMakeCompanyData getShakeMakeGroupData() {
        return shakeMakeGroupData;
    }

    public void setShakeMakeGroupData(ShakeMakeCompanyData shakeMakeGroupData) {
        this.shakeMakeGroupData = shakeMakeGroupData;
    }
}