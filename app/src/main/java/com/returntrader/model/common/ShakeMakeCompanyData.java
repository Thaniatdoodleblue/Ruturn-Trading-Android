package com.returntrader.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ShakeMakeCompanyData implements Parcelable {

    @JsonField(name = "isin_code")
    private String isinCode;
    @JsonField(name = "logo_url")
    private String logo;
    @JsonField(name = "instrument_name")
    private  String companyName;




    public ShakeMakeCompanyData(String isinCode, String logo, String companyName) {
        this.isinCode = isinCode;
        this.logo = logo;
        this.companyName = companyName;
    }

    public ShakeMakeCompanyData() {
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }



    protected ShakeMakeCompanyData(Parcel in) {
        isinCode = in.readString();
        logo = in.readString();
        companyName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isinCode);
        dest.writeString(logo);
        dest.writeString(companyName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ShakeMakeCompanyData> CREATOR = new Parcelable.Creator<ShakeMakeCompanyData>() {
        @Override
        public ShakeMakeCompanyData createFromParcel(Parcel in) {
            return new ShakeMakeCompanyData(in);
        }

        @Override
        public ShakeMakeCompanyData[] newArray(int size) {
            return new ShakeMakeCompanyData[size];
        }
    };
}