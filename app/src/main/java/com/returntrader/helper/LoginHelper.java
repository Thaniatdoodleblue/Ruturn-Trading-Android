package com.returntrader.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.returntrader.common.Constants;
import com.returntrader.model.common.BirthPlace;
import com.returntrader.model.common.FicaDocumentStatus;
import com.returntrader.model.common.FicaItem;
import com.returntrader.model.common.PhoneNumber;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.model.dto.request.Income;
import com.returntrader.model.dto.response.LoginUserInfo;
import com.returntrader.utils.CodeSnippet;

/**
 * Created by moorthy on 12/9/2017.
 */

public class LoginHelper {

    private Context mContext;
    private AppConfigurationManager mAppConfigurationManager;
    private CodeSnippet mCodeSnippet;


    public LoginHelper(Context context) {
        this.mContext = context;
        this.mAppConfigurationManager = new AppConfigurationManager(context);
        this.mCodeSnippet = new CodeSnippet(context);
    }


    public CodeSnippet getCodeSnippet() {
        return mCodeSnippet;
    }


    public boolean syncUserInfo(LoginUserInfo loginUserInfo, int syncFrom) {
        FullRegistrationRequest fullRegistrationRequest = new FullRegistrationRequest();
        fullRegistrationRequest.setTitleId(loginUserInfo.getTitleId());
        fullRegistrationRequest.setMiddleInitials(loginUserInfo.getMiddleInitials());
        fullRegistrationRequest.setPreferredName(loginUserInfo.getPreferredName());
        fullRegistrationRequest.setIdentificationTypeId(loginUserInfo.getIdentificationTypeId());
        fullRegistrationRequest.setIdentificationNumber(loginUserInfo.getIdentificationNumber());
        fullRegistrationRequest.setPassportIssueCountryCode(loginUserInfo.getPassportIssueCountryCode());
        fullRegistrationRequest.setPassportExpiryDate(loginUserInfo.getPassportExpiryDate());
        fullRegistrationRequest.setDateOfBirth(loginUserInfo.getDateOfBirth());
        fullRegistrationRequest.setEmail(loginUserInfo.getEmail());
        fullRegistrationRequest.setUsername(loginUserInfo.getUsername());
        fullRegistrationRequest.setNationalityCountryId(loginUserInfo.getNationalityCountryId());
        fullRegistrationRequest.setCountryOfResidenceId(loginUserInfo.getCountryOfResidenceId());
        fullRegistrationRequest.setGenderId(loginUserInfo.getGenderId());
        fullRegistrationRequest.setLoyaltyProgramNumber(loginUserInfo.getLoyaltyProgramNumber());
        fullRegistrationRequest.setFirstName(loginUserInfo.getFirstName());
        fullRegistrationRequest.setLastName(loginUserInfo.getLastName());
        fullRegistrationRequest.setSecurityQuestionId(loginUserInfo.getSecurityQuestionId());
        fullRegistrationRequest.setSecurityQuestionAnswer(loginUserInfo.getSecurityQuestionAnswer());
        fullRegistrationRequest.setMarketingChannelReferral(loginUserInfo.getMarketingChannelReferral());
        fullRegistrationRequest.setInvestmentExperienceLevel(loginUserInfo.getInvestmentExperienceLevel());
        fullRegistrationRequest.setReferralNumber(loginUserInfo.getReferralNumber());
        fullRegistrationRequest.setProvince(loginUserInfo.getProvince());
        fullRegistrationRequest.setMaritalStatusId(loginUserInfo.getMaritalStatusId());
        Income income = new Income();
        income.setIncomeBandId(loginUserInfo.getIncomeBand());
        income.setCurrentEarningsStatus(1);
        fullRegistrationRequest.setIncomeBand(income);


        fullRegistrationRequest.setRace(loginUserInfo.getRace());
        fullRegistrationRequest.setTaxNumber(loginUserInfo.getTaxNumber());
        fullRegistrationRequest.setStreet(loginUserInfo.getStreet());
        fullRegistrationRequest.setCity(loginUserInfo.getCity());
        fullRegistrationRequest.setArea(loginUserInfo.getArea());
        fullRegistrationRequest.setLocation(loginUserInfo.getLocation());
        fullRegistrationRequest.setComplexApartmentNumber(loginUserInfo.getComplexApartmentNumberOne());
        fullRegistrationRequest.setComplexApartmentName(loginUserInfo.getComplexApartmentNumberTwo());
        fullRegistrationRequest.setPinCode(loginUserInfo.getPinCode());
        fullRegistrationRequest.setStreetNumber(loginUserInfo.getPhoneNumber());

        // important account status
        mAppConfigurationManager.setAddressCompletedStatus(getCodeSnippet().getStatus(loginUserInfo.getIsAddressCompleted()));

        Log.e("Running LoginHelper",""+loginUserInfo.getIsFicaVerified());

        switch (loginUserInfo.getIsFicaVerified()) {
            case Constants.FicaContentType.FICA_NOT_UPLOAD_COMPLETE:
                mAppConfigurationManager.setFicaDetailCompletedStatus(false);
                mAppConfigurationManager.setFicaVerifiedStatus(false);
                break;
            case Constants.FicaContentType.FICA_UPLOAD_COMPLETE:
                mAppConfigurationManager.setFicaDetailCompletedStatus(true);
                mAppConfigurationManager.setFicaVerifiedStatus(false);
                break;
            case Constants.FicaContentType.FICA_DOC_VERIFIED:
                mAppConfigurationManager.setFicaDetailCompletedStatus(true);
                mAppConfigurationManager.setFicaVerifiedStatus(true);
                break;
        }

        mAppConfigurationManager.setAccountVerificationStatus(getCodeSnippet().getStatus(loginUserInfo.getIsTrustedAccountCreated()));

        // saving phone number
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumber(loginUserInfo.getPhoneNumber());
        phoneNumber.setPreferred(loginUserInfo.getPreferred());
        phoneNumber.setCountryId(loginUserInfo.getPhoneNumberCountryId());
        phoneNumber.setPhoneNoTypeId(loginUserInfo.getPhoneNoTypeId());
        fullRegistrationRequest.setPhoneNumber(phoneNumber);

        // saving birth place
        BirthPlace birthPlace = new BirthPlace();
        birthPlace.setCityName(loginUserInfo.getBirthCityName());
        birthPlace.setCountryId(loginUserInfo.getBirthCountryId());
        fullRegistrationRequest.setBirthPlace(birthPlace);

        switch (syncFrom) {
            case SyncFrom.FROM_EDIT_PROFILE:
                break;
            case SyncFrom.FROM_LOGIN:

                mAppConfigurationManager.setUserId(loginUserInfo.getToken());

                // saving fica docs status
                FicaDocumentStatus ficaDocumentStatus = new FicaDocumentStatus();

                if (!(TextUtils.isEmpty(loginUserInfo.getFicaAddressImageUrl())) ||
                        !(TextUtils.isEmpty(loginUserInfo.getFicaRsaImageUrl()))) {

                    if (!(TextUtils.isEmpty(loginUserInfo.getFicaAddressImageUrl()))) {
                        FicaItem ficaItem = new FicaItem();
                        ficaItem.setUploaded(true);
                        ficaItem.setFrontThumbnail(loginUserInfo.getFicaAddressImageUrl());
                        ficaDocumentStatus.setBankStatement(ficaItem);
                    }

                    if (!(TextUtils.isEmpty(loginUserInfo.getFicaRsaImageUrl()))) { // this is for greenCard is single
                        FicaItem ficaItem = new FicaItem();
                        ficaItem.setUploaded(true);
                        ficaItem.setFrontThumbnail(loginUserInfo.getFicaAddressImageUrl());
                        ficaDocumentStatus.setGreenIdCard(ficaItem);
                    }

                }

                if (!(TextUtils.isEmpty(loginUserInfo.getFicaGreenCardFrontImageUrl())) && !(TextUtils.isEmpty(loginUserInfo.getFicaGreenCardBackImageUrl()))) { // this is for greenCard is single
                    FicaItem ficaItem = new FicaItem();
                    ficaItem.setUploaded(true);
                    ficaItem.setFrontThumbnail(loginUserInfo.getFicaGreenCardFrontImageUrl());
                    ficaItem.setBackThumbnail(loginUserInfo.getFicaGreenCardBackImageUrl());
                    ficaDocumentStatus.setIdCard(ficaItem);
                }

                String ficaDetails = getCodeSnippet()
                        .getJsonStringFromObject(ficaDocumentStatus, FicaDocumentStatus.class);
                mAppConfigurationManager.setFicaDocStatus(ficaDetails);

                // set all topic for push notification
                getCodeSnippet().setAllDefaultTopic();
                break;
        }


        String userInfo = getCodeSnippet()
                .getJsonStringFromObject(fullRegistrationRequest, FullRegistrationRequest.class);
        mAppConfigurationManager.setUserInfo(userInfo);

        return true;
    }

    public interface SyncFrom {
        int FROM_LOGIN = 0;
        int FROM_EDIT_PROFILE = 1;
    }
}
