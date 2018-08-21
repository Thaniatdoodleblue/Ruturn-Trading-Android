package com.returntrader.model.dto.response;

import android.text.TextUtils;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 10/28/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class LoginUserInfo {


    @JsonField(name = "id")
    private String id;

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

    @JsonField(name = "isPreferred")
    private String preferred = "true";

    @JsonField(name = "phoneNoTypeId")
    private int phoneNoTypeId;

    @JsonField(name = "phoneNumberCountryId")
    private String phoneNumberCountryId;

    @JsonField(name = "phoneNumber")
    private String phoneNumber;

    @JsonField(name = "birthCountryId")
    private String birthCountryId;

    @JsonField(name = "cityName")
    private String birthCityName;

    @JsonField(name = "dateOfBirth")
    private String dateOfBirth;

    @JsonField(name = "email")
    private String email;

    @JsonField(name = "username")
    private String username;

   /* @JsonField(name = "password")
    private String password;

    @JsonField(name = "confirmPassword")
    private String confirmPassword;*/

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

    @JsonField(name = "profile_picture")
    private String profilePicture;

    @JsonField(name = "status")
    private String status;

    @JsonField(name = "updated_at")
    private String updatedAt;

    @JsonField(name = "created_at")
    private String createdAt;

    @JsonField(name = "token")
    private String token;

    @JsonField(name = "is_fica")
    private int isFicaVerified;

    @JsonField(name = "trusted_account")
    private int isTrustedAccountCreated;

    @JsonField(name = "is_address")
    int isAddressCompleted;

    @JsonField(name = "rsa")
    private String ficaRsaImageUrl;

    @JsonField(name = "address")
    private String ficaAddressImageUrl;

    @JsonField(name = "green_card_front")
    private String ficaGreenCardFrontImageUrl;

    @JsonField(name = "green_card_back")
    private String ficaGreenCardBackImageUrl;

    // new added fields

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
    private String complexApartmentNumberOne;

    @JsonField(name = "complexApartmentNumberTwo")
    private String complexApartmentNumberTwo;

    @JsonField(name = "postalCode")
    private String pinCode;

    @JsonField(name = "streetNumber")
    private String streetNumber;

    @JsonField(name = "favourites")
    private String favourites;

    @JsonField(name = "maritalStatusId")
    private String maritalStatusId;

    @JsonField(name = "incomeBand")
    private String incomeBand;

    @JsonField(name = "is_bank_details")
    private String isBankDetails;

    @JsonField(name = "bn")
    private boolean notifyBreakingNews;

    @JsonField(name = "jse")
    private boolean notifyJSE;

    @JsonField(name = "cn")
    private boolean notifyCN;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPhoneNumberCountryId() {
        return phoneNumberCountryId;
    }

    public void setPhoneNumberCountryId(String phoneNumberCountryId) {
        this.phoneNumberCountryId = phoneNumberCountryId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthCountryId() {
        return birthCountryId;
    }

    public void setBirthCountryId(String birthCountryId) {
        this.birthCountryId = birthCountryId;
    }

    public String getBirthCityName() {
        return birthCityName;
    }

    public void setBirthCityName(String birthCityName) {
        this.birthCityName = birthCityName;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public int getIsFicaVerified() {
        return isFicaVerified;
    }

    public void setIsFicaVerified(int isFicaVerified) {
        this.isFicaVerified = isFicaVerified;
    }

    public String getFicaRsaImageUrl() {
        return ficaRsaImageUrl;
    }

    public void setFicaRsaImageUrl(String ficaRsaImageUrl) {
        this.ficaRsaImageUrl = ficaRsaImageUrl;
    }

    public String getFicaAddressImageUrl() {
        return ficaAddressImageUrl;
    }

    public void setFicaAddressImageUrl(String ficaAddressImageUrl) {
        this.ficaAddressImageUrl = ficaAddressImageUrl;
    }

    public String getFicaGreenCardFrontImageUrl() {
        return ficaGreenCardFrontImageUrl;
    }

    public void setFicaGreenCardFrontImageUrl(String ficaGreenCardFrontImageUrl) {
        this.ficaGreenCardFrontImageUrl = ficaGreenCardFrontImageUrl;
    }

    public String getFicaGreenCardBackImageUrl() {
        return ficaGreenCardBackImageUrl;
    }

    public void setFicaGreenCardBackImageUrl(String ficaGreenCardBackImageUrl) {
        this.ficaGreenCardBackImageUrl = ficaGreenCardBackImageUrl;
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


    public int getIsTrustedAccountCreated() {
        return isTrustedAccountCreated;
    }

    public void setIsTrustedAccountCreated(int isTrustedAccountCreated) {
        this.isTrustedAccountCreated = isTrustedAccountCreated;
    }

    public int getIsAddressCompleted() {
        return isAddressCompleted;
    }

    public void setIsAddressCompleted(int isAddressCompleted) {
        this.isAddressCompleted = isAddressCompleted;
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


    public String getFavourites() {
        return favourites;
    }

    public void setFavourites(String favourites) {
        this.favourites = favourites;
    }

    public String getMaritalStatusId() {
        return maritalStatusId;
    }

    public void setMaritalStatusId(String maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    public String getIncomeBand() {
        return incomeBand;
    }

    public void setIncomeBand(String incomeBand) {
        this.incomeBand = incomeBand;
    }

    public String getIsBankDetails() {
        return isBankDetails;
    }

    public void setIsBankDetails(String isBankDetails) {
        this.isBankDetails = isBankDetails;
    }

    public boolean isNotifyBreakingNews() {
        return notifyBreakingNews;
    }

    public void setNotifyBreakingNews(boolean notifyBreakingNews) {
        this.notifyBreakingNews = notifyBreakingNews;
    }

    public boolean isNotifyJSE() {
        return notifyJSE;
    }

    public void setNotifyJSE(boolean notifyJSE) {
        this.notifyJSE = notifyJSE;
    }

    public boolean isNotifyCN() {
        return notifyCN;
    }

    public void setNotifyCN(boolean notifyCN) {
        this.notifyCN = notifyCN;
    }

    public boolean isBankDetailsCompleted() {
        return TextUtils.isEmpty(isBankDetails) ? false : isBankDetails.equalsIgnoreCase("0") ? false : true;
    }
}
