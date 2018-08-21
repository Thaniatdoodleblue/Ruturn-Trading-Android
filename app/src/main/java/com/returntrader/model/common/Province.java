package com.returntrader.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 10/26/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Province implements Parcelable {

    @JsonField(name = "provinceId")
    private String provinceId;

    @JsonField(name = "provinceName")
    private String provinceName;

    @JsonField(name = "countryId")
    private String countryId;

    public Province() {
    }


    public Province(String provinceId, String provinceName, String countryId) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.countryId = countryId;
    }

    protected Province(Parcel in) {
        provinceId = in.readString();
        provinceName = in.readString();
        countryId = in.readString();
    }

    public static final Creator<Province> CREATOR = new Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel in) {
            return new Province(in);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(provinceId);
        parcel.writeString(provinceName);
        parcel.writeString(countryId);
    }
}
