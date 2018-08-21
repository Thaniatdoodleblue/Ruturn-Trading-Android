package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 10/26/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Country {

    @JsonField(name = "countryId")
    private String countryId;

    @JsonField(name = "countryCode")
    private String countryCode;

    @JsonField(name = "countryName")
    private String countryName;

    @JsonField(name = "countryPhoneCode")
    private String countryPhoneCode;


    public Country() {
    }

    public Country(String countryId, String countryCode, String countryName, String countryPhoneCode) {
        this.countryId = countryId;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.countryPhoneCode = countryPhoneCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }
}
