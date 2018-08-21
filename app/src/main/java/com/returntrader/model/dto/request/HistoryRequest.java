package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 8/7/2017
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class HistoryRequest extends BaseRequest {


    @JsonField(name = "isin_code")
    private String ISINCode;


    public HistoryRequest() {
    }


    public HistoryRequest(String userId, String ISINCode) {
        super(userId);
        this.ISINCode = ISINCode;
    }

    public HistoryRequest(String ISINCode) {
        this.ISINCode = ISINCode;
    }

    public String getISINCode() {
        return ISINCode;
    }

    public void setISINCode(String ISINCode) {
        this.ISINCode = ISINCode;
    }
}
