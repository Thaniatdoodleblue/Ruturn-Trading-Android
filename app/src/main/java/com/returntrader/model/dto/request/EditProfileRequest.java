package com.returntrader.model.dto.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.BirthPlace;

/**
 * Created by moorthy on 12/8/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class EditProfileRequest extends BaseRequest implements Parcelable {


    @JsonField(name = "birthPlace")
    private BirthPlace birthPlace;

    @JsonField(name = "income")
    private Income income;

    @JsonField(name = "countryOfResidenceId")
    private String countryOfResidenceId;

    @JsonField(name = "dateOfBirth")
    private String dateOfBirth;

    @JsonField(name = "nationalityCountryId")
    private String nationalityCountryId;

    @JsonField(name = "maritalStatusId")
    private String maritalStatusId;

    @JsonField(name = "province")
    private String province;

    @JsonField(name = "race")
    private String race;

    @JsonField(name = "street")
    private String street;

    @JsonField(name = "taxNumber")
    private String taxNumber;

    @JsonField(name = "complexApartmentNumberOne")
    private String complexApartmentNumberOne;

    @JsonField(name = "complexApartmentNumberTwo")
    private String complexApartmentNumberTwo;

    @JsonField(name = "postalCode")
    private String postalCode;




    public EditProfileRequest() {
    }

    protected EditProfileRequest(Parcel in) {
        birthPlace = in.readParcelable(BirthPlace.class.getClassLoader());
        income = in.readParcelable(Income.class.getClassLoader());
        countryOfResidenceId = in.readString();
        dateOfBirth = in.readString();
        nationalityCountryId = in.readString();
        maritalStatusId = in.readString();
        province = in.readString();
        race = in.readString();
        street = in.readString();
        taxNumber = in.readString();
        complexApartmentNumberOne = in.readString();
        complexApartmentNumberTwo = in.readString();
        postalCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(birthPlace, flags);
        dest.writeParcelable(income, flags);
        dest.writeString(countryOfResidenceId);
        dest.writeString(dateOfBirth);
        dest.writeString(nationalityCountryId);
        dest.writeString(maritalStatusId);
        dest.writeString(province);
        dest.writeString(race);
        dest.writeString(street);
        dest.writeString(taxNumber);
        dest.writeString(complexApartmentNumberOne);
        dest.writeString(complexApartmentNumberTwo);
        dest.writeString(postalCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EditProfileRequest> CREATOR = new Creator<EditProfileRequest>() {
        @Override
        public EditProfileRequest createFromParcel(Parcel in) {
            return new EditProfileRequest(in);
        }

        @Override
        public EditProfileRequest[] newArray(int size) {
            return new EditProfileRequest[size];
        }
    };


    public BirthPlace getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(BirthPlace birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }

    public String getCountryOfResidenceId() {
        return countryOfResidenceId;
    }

    public void setCountryOfResidenceId(String countryOfResidenceId) {
        this.countryOfResidenceId = countryOfResidenceId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalityCountryId() {
        return nationalityCountryId;
    }

    public void setNationalityCountryId(String nationalityCountryId) {
        this.nationalityCountryId = nationalityCountryId;
    }

    public String getMaritalStatusId() {
        return maritalStatusId;
    }

    public void setMaritalStatusId(String maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
