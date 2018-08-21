package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.dto.request.FullRegistrationRequest;

/**
 * Created by moorthy on 10/28/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class FullRegisterResponse extends BaseResponse {

    @JsonField(name = "AppId")
    private String userId;

    @JsonField(name = "userInfo")
    private FullRegistrationRequest userInfo;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FullRegistrationRequest getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(FullRegistrationRequest userInfo) {
        this.userInfo = userInfo;
    }
}
