package com.returntrader.model.common;

import android.net.Uri;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 8/10/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ContactItem {


    @JsonField(name = "number")
    private String phoneNumber;

    @JsonField(name = "isInviteEnabled")
    private int isAppUser;

    private String displayName;

    private Uri displayUri;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Uri getDisplayUri() {
        return displayUri;
    }

    public void setDisplayUri(Uri displayUri) {
        this.displayUri = displayUri;
    }

    public int getIsAppUser() {
        return isAppUser;
    }

    public void setIsAppUser(int isAppUser) {
        this.isAppUser = isAppUser;
    }
}
