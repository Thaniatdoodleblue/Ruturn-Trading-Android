package com.returntrader.model.dto.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 12/1/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class AddressRequest extends BaseRequest implements Parcelable {

    @JsonField(name = "street")
    private String street;

    @JsonField(name = "city")
    private String city;

    @JsonField(name = "area")
    private String area;

    @JsonField(name = "location")
    private String location;

    @JsonField(name = "complexApartmentNumberOne")
    private String complexApartmentNumberOne;

    @JsonField(name = "complexApartmentNumberTwo")
    private String complexApartmentNumberTwo;

    @JsonField(name = "postalCode")
    private String pinCode;

    @JsonField(name = "streetNumber")
    private String streetNumber;

    private String countryOfResidenceId;

    private String province;

    public AddressRequest() {
    }

    protected AddressRequest(Parcel in) {
        street = in.readString();
        city = in.readString();
        area = in.readString();
        location = in.readString();
        complexApartmentNumberOne = in.readString();
        complexApartmentNumberTwo = in.readString();
        pinCode = in.readString();
        streetNumber = in.readString();
        countryOfResidenceId = in.readString();
        province = in.readString();
    }

    public static final Creator<AddressRequest> CREATOR = new Creator<AddressRequest>() {
        @Override
        public AddressRequest createFromParcel(Parcel in) {
            return new AddressRequest(in);
        }

        @Override
        public AddressRequest[] newArray(int size) {
            return new AddressRequest[size];
        }
    };

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComplexApartmentNumberOne() {
        return complexApartmentNumberOne;
    }

    public void setComplexApartmentNumberOne(String complexApartmentNumberOne) {
        this.complexApartmentNumberOne = complexApartmentNumberOne;
    }

    public String getComplexApartmentNumberTwo() {
        return complexApartmentNumberTwo;
    }

    public void setComplexApartmentNumberTwo(String complexApartmentNumberTwo) {
        this.complexApartmentNumberTwo = complexApartmentNumberTwo;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }


    public String getCountryOfResidenceId() {
        return countryOfResidenceId;
    }

    public void setCountryOfResidenceId(String countryOfResidenceId) {
        this.countryOfResidenceId = countryOfResidenceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(street);
        parcel.writeString(city);
        parcel.writeString(area);
        parcel.writeString(location);
        parcel.writeString(complexApartmentNumberOne);
        parcel.writeString(complexApartmentNumberTwo);
        parcel.writeString(pinCode);
        parcel.writeString(streetNumber);
        parcel.writeString(countryOfResidenceId);
        parcel.writeString(province);
    }
}
