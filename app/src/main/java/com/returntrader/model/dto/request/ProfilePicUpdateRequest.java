package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.File;

/**
 * Created by nirmal on 2/6/2018.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ProfilePicUpdateRequest {
    private String user_id;
    private File profile;

    public ProfilePicUpdateRequest() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public File getProfile() {
        return profile;
    }

    public void setProfile(File profile) {
        this.profile = profile;
    }
}
