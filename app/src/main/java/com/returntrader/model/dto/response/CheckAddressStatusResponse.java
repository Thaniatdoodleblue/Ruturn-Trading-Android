package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 11/30/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class CheckAddressStatusResponse extends BaseResponse {

    @JsonField(name = "is_address")
    private int isAddressCompleted;


    @JsonField(name = "is_fica")
    private int isFicaVerified;


    public int getIsAddressCompleted() {
        return isAddressCompleted;
    }

    public void setIsAddressCompleted(int isAddressCompleted) {
        this.isAddressCompleted = isAddressCompleted;
    }

    public int getIsFicaVerified() {
        return isFicaVerified;
    }

    public void setIsFicaVerified(int isFicaVerified) {
        this.isFicaVerified = isFicaVerified;
    }
}
