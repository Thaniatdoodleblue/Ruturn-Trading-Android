package com.returntrader.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.dto.response.BaseResponse;

/**
 * Created by moorthy on 10/28/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BirthPlace implements Parcelable {

    @JsonField(name = "countryId")
    private String countryId;

    @JsonField(name = "cityName")
    private String cityName;


    public BirthPlace() {
    }

    protected BirthPlace(Parcel in) {
        countryId = in.readString();
        cityName = in.readString();
    }

    public static final Creator<BirthPlace> CREATOR = new Creator<BirthPlace>() {
        @Override
        public BirthPlace createFromParcel(Parcel in) {
            return new BirthPlace(in);
        }

        @Override
        public BirthPlace[] newArray(int size) {
            return new BirthPlace[size];
        }
    };

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(countryId);
        parcel.writeString(cityName);
    }
}
