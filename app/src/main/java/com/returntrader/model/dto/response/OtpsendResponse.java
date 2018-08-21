package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class OtpsendResponse extends BaseResponse {

    private String msg;
    @JsonField(name = "status")
    private boolean status_bool;
    @JsonField(name = "msg")
    private String message;
    private String OTP;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus_bool() {
        return status_bool;
    }

    public void setStatus_bool(boolean status_bool) {
        this.status_bool = status_bool;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getOTP() {
        return OTP;
    }

    public void setOTP(String oTP) {
        this.OTP = oTP;
    }

}