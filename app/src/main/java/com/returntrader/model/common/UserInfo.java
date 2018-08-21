package com.returntrader.model.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by moorthy on 10/26/2017.
 */

public class UserInfo implements Parcelable {
    private Title title;

    private String firstName;

    private String surname;

    private PhoneNumber phoneNumber;

    private String emailId;

    private String rsaIdentificationId;


    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        title = (Title) in.readValue(Title.class.getClassLoader());
        firstName = in.readString();
        surname = in.readString();
        phoneNumber = (PhoneNumber) in.readValue(PhoneNumber.class.getClassLoader());
        emailId = in.readString();
        rsaIdentificationId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeString(firstName);
        dest.writeString(surname);
        dest.writeValue(phoneNumber);
        dest.writeString(emailId);
        dest.writeString(rsaIdentificationId);
    }

    @SuppressWarnings("unused")
    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRsaIdentificationId() {
        return rsaIdentificationId;
    }

    public void setRsaIdentificationId(String rsaIdentificationId) {
        this.rsaIdentificationId = rsaIdentificationId;
    }


    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public static Creator<UserInfo> getCREATOR() {
        return CREATOR;
    }
}
