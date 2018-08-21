package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by nirmal on 3/29/2018.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class UpdatePasscodeRequest extends BaseRequest {
    private String user_id;
    private String passcode;
    private String passKey;
    private String rsaId;

    public UpdatePasscodeRequest() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getRsaId() {
        return rsaId;
    }

    public void setRsaId(String rsaId) {
        this.rsaId = rsaId;
    }

    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }
}
