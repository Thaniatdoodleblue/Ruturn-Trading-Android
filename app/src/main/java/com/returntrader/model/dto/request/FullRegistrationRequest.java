package com.returntrader.model.dto.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.BirthPlace;
import com.returntrader.model.common.PhoneNumber;

/**
 * Created by moorthy on 10/28/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class FullRegistrationRequest implements Parcelable {

    @JsonField(name = "titleId")
    private String titleId;

    @JsonField(name = "middleInitials")
    private String middleInitials = "";

    @JsonField(name = "preferredName")
    private String preferredName = "";

    @JsonField(name = "identificationTypeId")
    private String identificationTypeId = "1";

    @JsonField(name = "identificationNumber")
    private String identificationNumber;

    @JsonField(name = "passportIssueCountryCode")
    private String passportIssueCountryCode = "";

    @JsonField(name = "passportExpiryDate")
    private String passportExpiryDate = "";

    @JsonField(name = "phoneNumber")
    private PhoneNumber phoneNumber;

    @JsonField(name = "birthPlace")
    private BirthPlace birthPlace;

    @JsonField(name = "dateOfBirth")
    private String dateOfBirth;

    @JsonField(name = "email")
    private String email;

    @JsonField(name = "username")
    private String username;

    @JsonField(name = "password")
    private String password;

    @JsonField(name = "confirmPassword")
    private String confirmPassword;

    @JsonField(name = "nationalityCountryId")
    private String nationalityCountryId;

    @JsonField(name = "countryOfResidenceId")
    private String countryOfResidenceId;

    @JsonField(name = "genderId")
    private int genderId;

    @JsonField(name = "loyaltyProgramNumber")
    private String loyaltyProgramNumber = "";


    @JsonField(name = "firstName")
    private String firstName;

    @JsonField(name = "lastName")
    private String lastName;

    @JsonField(name = "securityQuestionId")
    private int securityQuestionId;

    @JsonField(name = "securityQuestionAnswer")
    private String securityQuestionAnswer;

    @JsonField(name = "marketingChannelReferral")
    private String marketingChannelReferral = "";

    @JsonField(name = "investmentExperienceLevel")
    private int investmentExperienceLevel = 0;

    @JsonField(name = "referralNumber")
    private String referralNumber = "";


    // Extra fields
    @JsonField(name = "province")
    private String province;

    @JsonField(name = "race")
    private String race;

    @JsonField(name = "taxNumber")
    private String taxNumber;

    @JsonField(name = "street")
    private String street;

    @JsonField(name = "city")
    private String city;

    @JsonField(name = "area")
    private String area;

    @JsonField(name = "location")
    private String location;

    @JsonField(name = "complexApartmentNumberOne")
    private String complexApartmentNumber;

    @JsonField(name = "complexApartmentNumberTwo")
    private String complexApartmentName;

    @JsonField(name = "postalCode")
    private String pinCode;

    @JsonField(name = "streetNumber")
    private String streetNumber;


    @JsonField(name = "maritalStatusId")
    private String maritalStatusId;

    @JsonField(name = "income")
    private Income incomeBand;


    public FullRegistrationRequest() {
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getMiddleInitials() {
        return middleInitials;
    }

    public void setMiddleInitials(String middleInitials) {
        this.middleInitials = middleInitials;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getIdentificationTypeId() {
        return identificationTypeId;
    }

    public void setIdentificationTypeId(String identificationTypeId) {
        this.identificationTypeId = identificationTypeId;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getPassportIssueCountryCode() {
        return passportIssueCountryCode;
    }

    public void setPassportIssueCountryCode(String passportIssueCountryCode) {
        this.passportIssueCountryCode = passportIssueCountryCode;
    }

    public String getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(String passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BirthPlace getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(BirthPlace birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNationalityCountryId() {
        return nationalityCountryId;
    }

    public void setNationalityCountryId(String nationalityCountryId) {
        this.nationalityCountryId = nationalityCountryId;
    }

    public String getCountryOfResidenceId() {
        return countryOfResidenceId;
    }

    public void setCountryOfResidenceId(String countryOfResidenceId) {
        this.countryOfResidenceId = countryOfResidenceId;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getLoyaltyProgramNumber() {
        return loyaltyProgramNumber;
    }

    public void setLoyaltyProgramNumber(String loyaltyProgramNumber) {
        this.loyaltyProgramNumber = loyaltyProgramNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSecurityQuestionId() {
        return securityQuestionId;
    }

    public void setSecurityQuestionId(int securityQuestionId) {
        this.securityQuestionId = securityQuestionId;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public String getMarketingChannelReferral() {
        return marketingChannelReferral;
    }

    public void setMarketingChannelReferral(String marketingChannelReferral) {
        this.marketingChannelReferral = marketingChannelReferral;
    }

    public int getInvestmentExperienceLevel() {
        return investmentExperienceLevel;
    }

    public void setInvestmentExperienceLevel(int investmentExperienceLevel) {
        this.investmentExperienceLevel = investmentExperienceLevel;
    }

    public String getReferralNumber() {
        return referralNumber;
    }

    public void setReferralNumber(String referralNumber) {
        this.referralNumber = referralNumber;
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

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

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

    public String getComplexApartmentNumber() {
        return complexApartmentNumber;
    }

    public void setComplexApartmentNumber(String complexApartmentNumber) {
        this.complexApartmentNumber = complexApartmentNumber;
    }

    public String getComplexApartmentName() {
        return complexApartmentName;
    }

    public void setComplexApartmentName(String complexApartmentName) {
        this.complexApartmentName = complexApartmentName;
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


    public String getMaritalStatusId() {
        return maritalStatusId;
    }

    public void setMaritalStatusId(String maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    public Income getIncomeBand() {
        return incomeBand;
    }

    public void setIncomeBand(Income incomeBand) {
        this.incomeBand = incomeBand;
    }

    protected FullRegistrationRequest(Parcel in) {
        titleId = in.readString();
        middleInitials = in.readString();
        preferredName = in.readString();
        identificationTypeId = in.readString();
        identificationNumber = in.readString();
        passportIssueCountryCode = in.readString();
        passportExpiryDate = in.readString();
        phoneNumber = (PhoneNumber) in.readValue(PhoneNumber.class.getClassLoader());
        birthPlace = (BirthPlace) in.readValue(BirthPlace.class.getClassLoader());
        dateOfBirth = in.readString();
        email = in.readString();
        username = in.readString();
        password = in.readString();
        confirmPassword = in.readString();
        nationalityCountryId = in.readString();
        countryOfResidenceId = in.readString();
        genderId = in.readInt();
        loyaltyProgramNumber = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        securityQuestionId = in.readInt();
        securityQuestionAnswer = in.readString();
        marketingChannelReferral = in.readString();
        investmentExperienceLevel = in.readInt();
        referralNumber = in.readString();
        province = in.readString();
        race = in.readString();
        taxNumber = in.readString();
        street = in.readString();
        city = in.readString();
        area = in.readString();
        location = in.readString();
        complexApartmentNumber = in.readString();
        complexApartmentName = in.readString();
        pinCode = in.readString();
        streetNumber = in.readString();
        maritalStatusId = in.readString();
        incomeBand = (Income) in.readValue(Income.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titleId);
        dest.writeString(middleInitials);
        dest.writeString(preferredName);
        dest.writeString(identificationTypeId);
        dest.writeString(identificationNumber);
        dest.writeString(passportIssueCountryCode);
        dest.writeString(passportExpiryDate);
        dest.writeValue(phoneNumber);
        dest.writeValue(birthPlace);
        dest.writeString(dateOfBirth);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(confirmPassword);
        dest.writeString(nationalityCountryId);
        dest.writeString(countryOfResidenceId);
        dest.writeInt(genderId);
        dest.writeString(loyaltyProgramNumber);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(securityQuestionId);
        dest.writeString(securityQuestionAnswer);
        dest.writeString(marketingChannelReferral);
        dest.writeInt(investmentExperienceLevel);
        dest.writeString(referralNumber);
        dest.writeString(province);
        dest.writeString(race);
        dest.writeString(taxNumber);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(area);
        dest.writeString(location);
        dest.writeString(complexApartmentNumber);
        dest.writeString(complexApartmentName);
        dest.writeString(pinCode);
        dest.writeString(streetNumber);
        dest.writeString(maritalStatusId);
        dest.writeValue(incomeBand);
    }

    @SuppressWarnings("unused")
    public static final Creator<FullRegistrationRequest> CREATOR = new Creator<FullRegistrationRequest>() {
        @Override
        public FullRegistrationRequest createFromParcel(Parcel in) {
            return new FullRegistrationRequest(in);
        }

        @Override
        public FullRegistrationRequest[] newArray(int size) {
            return new FullRegistrationRequest[size];
        }
    };

}
