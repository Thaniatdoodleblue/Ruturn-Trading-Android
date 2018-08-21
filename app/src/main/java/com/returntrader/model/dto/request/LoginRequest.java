package com.returntrader.model.dto.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 11/13/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class LoginRequest implements Parcelable {

    @JsonField(name = "email")
    private String emailId;

    @JsonField(name = "mobile_number")
    private String phoneNumber;

    @JsonField(name = "rsa_id")
    private String rsaId;

    @JsonField(name = "otp")
    private String otp;

    @JsonField(name = "passcode")
    private String passcode;

    @JsonField(name = "user_id")
    private String userId;

    public LoginRequest() {
    }

    protected LoginRequest(Parcel in) {
        emailId = in.readString();
        phoneNumber = in.readString();
        rsaId = in.readString();
        passcode = in.readString();
        userId = in.readString();
    }

    public static final Creator<LoginRequest> CREATOR = new Creator<LoginRequest>() {
        @Override
        public LoginRequest createFromParcel(Parcel in) {
            return new LoginRequest(in);
        }

        @Override
        public LoginRequest[] newArray(int size) {
            return new LoginRequest[size];
        }
    };

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRsaId() {
        return rsaId;
    }

    public void setRsaId(String rsaId) {
        this.rsaId = rsaId;
    }


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(emailId);
        parcel.writeString(phoneNumber);
        parcel.writeString(rsaId);
        parcel.writeString(passcode);
        parcel.writeString(userId);
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
