package com.returntrader.library;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.dto.response.BaseResponse;

/**
 * Created by Guru Karthi on 05/12/2016.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class CustomException extends Exception {

    private int code;

    private String exception;

    public CustomException(int code, String exception) {
        this.code = code;
        this.exception = exception;
    }

    public CustomException(int code, Throwable throwable) {
        this.code = code;
        this.exception = throwable.getMessage();
    }

    public CustomException(int code, BaseResponse response) {
        this.code = code;
        this.exception = response.getMessage();
    }

    public CustomException() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
