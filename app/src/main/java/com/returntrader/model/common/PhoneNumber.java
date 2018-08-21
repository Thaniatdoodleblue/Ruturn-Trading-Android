package com.returntrader.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 10/26/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class PhoneNumber implements Parcelable {


    @JsonField(name = "countryCode")
    private String countryCode;

    @JsonField(name = "isPreferred")
    private String preferred = "true";

    @JsonField(name = "phoneNoTypeId")
    private int phoneNoTypeId;

    @JsonField(name = "countryId")
    private String countryId;

    @JsonField(name = "number")
    private String phoneNumber;

    public PhoneNumber() {
    }


    protected PhoneNumber(Parcel in) {
        countryCode = in.readString();
        preferred = in.readString();
        phoneNoTypeId = in.readInt();
        countryId = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<PhoneNumber> CREATOR = new Creator<PhoneNumber>() {
        @Override
        public PhoneNumber createFromParcel(Parcel in) {
            return new PhoneNumber(in);
        }

        @Override
        public PhoneNumber[] newArray(int size) {
            return new PhoneNumber[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(countryCode);
        parcel.writeString(preferred);
        parcel.writeInt(phoneNoTypeId);
        parcel.writeString(countryId);
        parcel.writeString(phoneNumber);
    }


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPreferred() {
        return preferred;
    }

    public void setPreferred(String preferred) {
        this.preferred = preferred;
    }

    public int getPhoneNoTypeId() {
        return phoneNoTypeId;
    }

    public void setPhoneNoTypeId(int phoneNoTypeId) {
        this.phoneNoTypeId = phoneNoTypeId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static Creator<PhoneNumber> getCREATOR() {
        return CREATOR;
    }
}

