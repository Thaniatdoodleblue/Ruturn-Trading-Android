package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.ShakeMakeGroupData;

import java.util.List;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ShakeMakeGroupResponse extends BaseResponse {

    @JsonField(name = "snmGroups")
    private List<ShakeMakeGroupData> shakeMakeGroupData;

    public ShakeMakeGroupResponse(List<ShakeMakeGroupData> shakeMakeGroupData) {
        this.shakeMakeGroupData = shakeMakeGroupData;
    }

    public ShakeMakeGroupResponse() {
    }

    public List<ShakeMakeGroupData> getShakeMakeGroupData() {
        return shakeMakeGroupData;
    }

    public void setShakeMakeGroupData(List<ShakeMakeGroupData> shakeMakeGroupData) {
        this.shakeMakeGroupData = shakeMakeGroupData;
    }
}
