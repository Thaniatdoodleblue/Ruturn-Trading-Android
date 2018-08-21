package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 10/26/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class EmailVerificationRequest {

    @JsonField(name = "email")
    private String email;

    @JsonField(name = "rsa_id")
    private String rsaId;

    @JsonField(name = "rsaId")
    private String rsaIdentity;

    @JsonField(name = "mobileNumber")
    private String mobileNumber;


    public EmailVerificationRequest() {
    }

    public EmailVerificationRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRsaId() {
        return rsaId;
    }

    public void setRsaId(String rsaId) {
        this.rsaId = rsaId;
    }

    public String getRsaIdentity() {
        return rsaIdentity;
    }

    public void setRsaIdentity(String rsaIdentity) {
        this.rsaIdentity = rsaIdentity;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
