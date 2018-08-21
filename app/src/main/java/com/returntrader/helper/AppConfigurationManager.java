package com.returntrader.helper;

import android.content.Context;

import com.returntrader.common.Constants;
import com.returntrader.library.Log;
import com.returntrader.model.common.ShakeMakeGroupData;
import com.returntrader.model.common.ShakeMakeMoneyData;
import com.returntrader.utils.CodeSnippet;
import com.returntrader.utils.SharedPref;

/**
 * Created by guru on 8/19/2017.
 */

public class AppConfigurationManager {

    private Context mContext;
    private SharedPref mSharedPref;
    private CodeSnippet codeSnippet;

    public AppConfigurationManager(Context mContext) {
        this.mContext = mContext;
        this.mSharedPref = new SharedPref();
        codeSnippet = new CodeSnippet(mContext);
    }


    private SharedPref getInstance() {
        if (mSharedPref == null) {
            mSharedPref = new SharedPref();
            return mSharedPref;
        }
        return mSharedPref;
    }

    public boolean getSyncStatus() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_INITIAL_DATA_SYNC_DONE, false);
    }

    public void setSyncStatus(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_INITIAL_DATA_SYNC_DONE, status);
    }

    public void setBankDetailCompletedStatus(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_BANK_DETAILS_COMPLETED, status);
    }

    public boolean isBankDetailCompleted() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_BANK_DETAILS_COMPLETED, false);
    }


    public void setBalanceUpdatedStatus(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_BALANCE_UPDATED, status);
    }

    public boolean isBalanceUpdated() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_BALANCE_UPDATED, false);
    }

    public void setPartialRegisterCompleted(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_PARTIAL_REGISTER_COMPLETED, status);
    }

    public boolean isPartialRegisterCompleted() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_PARTIAL_REGISTER_COMPLETED, false);
    }

    /*trust account verified or not*/
    public void setAccountVerificationStatus(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_ACCOUNT_VERIFIED, status);
    }

    /*trust account verified or not*/
    public boolean isAccountVerified() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_ACCOUNT_VERIFIED, false);
    }

    public void setConfigurationDataUpdated(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_CONFIG_DATA_UPDATED, status);
    }

    public boolean isConfigurationDataUpdated() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_CONFIG_DATA_UPDATED, false);
    }

    public void setFicaDetailCompletedStatus(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_FICA_DETAILS_COMPLETED, status);
    }


    /*whether fica verified by the ee team or not*/
    public boolean isFicaVerified() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_FICA_DOC_VERIFIED, false);
    }

    /*whether fica verified by the ee team or not*/
    public void setFicaVerifiedStatus(boolean status) {
        android.util.Log.e("Running FicaVerStatus", "" + status);
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_FICA_DOC_VERIFIED, status);
    }

    /*whether fica verified by the ee team or not*/
    public boolean isFicaPopUpShown() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_FICA_VERIFIED_POP_UP_SHOWN, false);
    }

    /*whether fica verified by the ee team or not*/
    public void setFicaPopUpShownStatus(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_FICA_VERIFIED_POP_UP_SHOWN, status);
    }


    // address completed status
    public boolean isAddressCompleted() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_ADDRESS_COMPLETED, false);
    }

    // address completed status
    public void setAddressCompletedStatus(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_ADDRESS_COMPLETED, status);
    }


    public boolean isFicaDetailCompleted() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_FICA_DETAILS_COMPLETED, false);
    }


    public void setDepositDetailsStatus(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_DESPOIT_COMPLETED, status);
    }

    public boolean isDepositDetails() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_DESPOIT_COMPLETED, false);
    }


    public void setFingerPrintEnabledStatus(boolean status) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_FINGER_PRINT_ENABLED, status);
    }

    public boolean isFingerPrintEnabled() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_FINGER_PRINT_ENABLED, false);
    }

    public String getUserId() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.USER_ID);
    }

    public void setUserId(String userId) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.USER_ID, userId);
    }

    /* Here its partial user info.  it will not gonna have the more fields as full registration users.
     * Its used for the local user managment and it wont be available on the web server
     */
    public String getPartialUserInfo() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.TEMP_USER_INFO);
    }

    /* Here its partial user info.  it will not gonna have the more fields as full registration users.
     * Its used for the local user managment and it wont be available on the web server
     */
    public void setPartialUserInfo(String userIdInfo) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.TEMP_USER_INFO, userIdInfo);
    }

    /* Here logged in user info it will takes all the fields and data respective to the user */
    public String getUserInfo() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.USER_INFO);
    }

    /* Here its full registered and logged in user info it will takes all the fields and data respective to the user */
    public void setUserInfo(String userIdInfo) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.USER_INFO, userIdInfo);
    }

    /*this is for the save and exit feature user info*//*
    public String getTempUserInfo() {
        return SharedPref.getInstance().getStringValue(mContext, Constants.SharedPrefKey.TEMP_FULL_REGISTER_USER);
    }

    *//*this is for the save and exit feature user info*//*
    public void setTempUserInfo(String userIdInfo) {
        SharedPref.getInstance().setSharedValue(mContext, Constants.SharedPrefKey.TEMP_FULL_REGISTER_USER, userIdInfo);
    }*/

    public String getConfigData() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.CONFIG_DATA);
    }


    /*this will have the data set of uploaded details of the fica including each status
    * */
    public void setFicaDocStatus(String ficaDocStatus) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.FICA_DOC_STATUS, ficaDocStatus);
    }

    /*this will have the data set of uploaded details of the fica including each status
    * */
    public String getFicaDocStatus() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.FICA_DOC_STATUS);
    }


    public void setConfigData(String configData) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.CONFIG_DATA, configData);
    }

    public String getLastKnownBalance() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.LAST_KNOWN_BALANCE, Constants.Common.DEFAULT_PRICE_ZERO);
    }

    public void setLastKnownBalance(String balance) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.LAST_KNOWN_BALANCE, balance);
    }

    public String getTrustAccountType() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.TRUST_ACCOUNT_TYPE, Constants.Common.DEFAULT_CASH_BALANCE_KEY);
    }

    public void setTrustAccountType(String trustAccountType) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.TRUST_ACCOUNT_TYPE, trustAccountType);
    }

    public String getEftReferenceNumber() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.EFT_REFERENCE_NUMBER);
    }

    public void setEftReferenceNumber(String etfReferenceNumber) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.EFT_REFERENCE_NUMBER, etfReferenceNumber);
    }


    public String getLastKnownInvestedAmount() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.LAST_KNOWN_INVESTED_AMOUNT, Constants.Common.DEFAULT_PRICE_ZERO);
    }

    public void setLastKnownInvestedAmount(String investedAmount) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.LAST_KNOWN_INVESTED_AMOUNT, investedAmount);
    }

    public String getLastKnownShare() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.LAST_KNOWN_SHARES, Constants.Common.DEFAULT_PRICE_ZERO);
    }

    public void setLastKnownHoldingAmount(String holdingAmount) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.LAST_KNOWN_HOLDING_AMOUNT, holdingAmount);
    }

    public String getLastKnownHoldingAmount() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.LAST_KNOWN_HOLDING_AMOUNT, Constants.Common.DEFAULT_PRICE_ZERO);
    }

    public void setLastKnownShare(String investedAmount) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.LAST_KNOWN_SHARES, investedAmount);
    }

    public boolean isPinAuthenticationRequired() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_EGG_AUTHENTICATION_REQUIRED, false);
    }

    public void setPinAuthenticationRequired(boolean authenticationRequired) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_EGG_AUTHENTICATION_REQUIRED, authenticationRequired);
    }

    public void clearPreference() {
        getInstance().clearPreference(mContext);
    }

    public void setMarketAvailability(boolean marketAvailability) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_MARKET_AVAILABLE, marketAvailability);
    }

    public boolean isMarketAvailable() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_MARKET_AVAILABLE, false);
    }

    public boolean isFicaVerifyPopUpEnabled() {
        return isFicaVerified() && !(isFicaPopUpShown());
    }

    public void setProfilePicture(String profilePicUrl) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.UPDATED_PROFILEPIC_URL, profilePicUrl);
    }

    public String getProfilePicture() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.UPDATED_PROFILEPIC_URL, "");
    }

    /*public void setShowFingerPrint(boolean canShow) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.CAN_SHOW_FINGERPRINT, canShow);
    }

    public boolean canShowFingerPrint() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.CAN_SHOW_FINGERPRINT, false);
    }*/


    public void setHoldingData(String holdingData) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.HOLDING_DATA, holdingData);
    }

    public String getHoldingData() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.HOLDING_DATA, "");
    }


    public void setCompanyNotifyEnabled(boolean canEnable) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_COMPANY_NOTIFY_ENABLED, canEnable);
    }

    public boolean isCompanyNotifyEnabled() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_COMPANY_NOTIFY_ENABLED, true);
    }

    public void setJseNotifyEnabled(boolean canEnable) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_JSE_NOTIFY_ENABLED, canEnable);
    }

    public boolean isJseNotifyEnabled() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_JSE_NOTIFY_ENABLED, true);
    }

    public void setBreakingNotifyEnabled(boolean canEnable) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_BREAKING_NEWS_NOTIFY_ENABLED, canEnable);
    }

    public boolean isBreakingNotifyEnabled() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_BREAKING_NEWS_NOTIFY_ENABLED, true);
    }


    public void setCompanyNotificationEnabled(boolean marketAvailability) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_COMPANY_NOTIFICATION_ENABLED, marketAvailability);
    }

    public boolean isCompanyNotificationEnabled() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_COMPANY_NOTIFICATION_ENABLED, true);
    }

    public void setJseNotificationEnabled(boolean marketAvailability) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_JSE_NOTIFICATION_ENABLED, marketAvailability);
    }

    public boolean isJseNotificationEnabled() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_JSE_NOTIFICATION_ENABLED, true);
    }

    public void setBreakingNotificationEnabled(boolean marketAvailability) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.IS_BREAKING_NEWS_NOTIFICATION_ENABLED, marketAvailability);
    }

    public boolean isBreakingNotificationEnabled() {
        return getInstance().getBooleanValue(mContext, Constants.SharedPrefKey.IS_BREAKING_NEWS_NOTIFICATION_ENABLED, true);
    }


   /* public void setPasscode(String passcode) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.PASSCODE_DATA, passcode);
    }

    public String getPasscode() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.PASSCODE_DATA, "");
    }*/

    public String getAuthenticationPin() {
        return getInstance().getStringValue(mContext, Constants.SharedPrefKey.PIN);
    }

    public void setAuthenticationPin(String pin) {
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.PIN, pin);
    }

    public void setShakeMakeGroupData(ShakeMakeGroupData shakeMakeGroupData){
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.SHAKE_MAKE_GROUP_DATA, codeSnippet.getJsonStringFromObject(shakeMakeGroupData));
    }

    public ShakeMakeGroupData getShakeMakeGroupData(){
       return codeSnippet.getObjectFromJsonStringRetro( getInstance().getStringValue(mContext, Constants.SharedPrefKey.SHAKE_MAKE_GROUP_DATA), ShakeMakeGroupData.class);
    }

    public void setShakeMakeMoneyData(ShakeMakeMoneyData shakeMakeGroupData){
        getInstance().setSharedValue(mContext, Constants.SharedPrefKey.SHAKE_MAKE_MONEY_DATA, codeSnippet.getJsonStringFromObject(shakeMakeGroupData));
    }

    public ShakeMakeMoneyData getShakeMakeMoneyData(){
        return codeSnippet.getObjectFromJsonStringRetro( getInstance().getStringValue(mContext, Constants.SharedPrefKey.SHAKE_MAKE_MONEY_DATA), ShakeMakeMoneyData.class);
    }
}
