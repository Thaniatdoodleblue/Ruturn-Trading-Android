package com.returntrader.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.presenter.ipresenter.IPinNumberPresenter;
import com.returntrader.view.iview.IPinNumberView;

/**
 * Created by moorthy on 9/14/2017.
 */

public class PinNumberPresenter extends BasePresenter implements IPinNumberPresenter {
    private IPinNumberView iPinNumberView;
    private int mEntryType;
    private String mLastKonwnPin;
    private AppConfigurationManager mAppConfigurationManager;
    private int mAttempts = 0;

    public PinNumberPresenter(IPinNumberView iView) {
        super(iView);
        this.iPinNumberView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mEntryType = bundle.getInt(Constants.BundleKey.BUNDLE_ENTRY_TYPE, -1);
            mLastKonwnPin = bundle.getString(Constants.BundleKey.BUNDLE_PIN);
            Log.d(TAG, "mEntryType :" + mEntryType);
            Log.d(TAG, "mLastKonwnPin :" + mLastKonwnPin);
            iPinNumberView.setScreenDetails(mEntryType);
        }
    }

    @Override
    public void doValidation(String pinValue) {
        pinValue = TraderApplication.getInstance().compose(pinValue).replace("\n", "");
        Log.d(TAG, "pinValue :" + pinValue + " mLastKonwnPin :" + mLastKonwnPin + "mEntryType : " + mEntryType);

        switch (mEntryType) {
            case Constants.AuthenticationType.LOAD_CONFIRM_PIN_ENTRY:
                if (!(TextUtils.isEmpty(mLastKonwnPin))) {
                    if (mLastKonwnPin.equalsIgnoreCase(pinValue)) {
                        /// TODO: 9/14/2017 save pin in local storage
                        mAppConfigurationManager.setAuthenticationPin(pinValue);
                        mAppConfigurationManager.setPinAuthenticationRequired(false);
                        iPinNumberView.loadNextPage(Constants.AuthenticationType.LOAD_NEXT_PAGE);
                        hideForgotPassword();
                        mAttempts = 0;
                    } else {
                        iPinNumberView.showMessage("Pin Incorrect!");
                        iPinNumberView.erasePinOnTry();
                    }
                }
                break;
            case Constants.AuthenticationType.LOAD_NEW_PIN_ENTRY:
                iPinNumberView.setEnteredPin(pinValue);
                iPinNumberView.loadNextPage(Constants.AuthenticationType.LOAD_CONFIRM_PIN_ENTRY);
                break;
            case Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN:
                String authPin = mAppConfigurationManager.getAuthenticationPin();
                Log.d(TAG + authPin, "AuthPin :" + pinValue);
                if (TextUtils.isEmpty(authPin)) {
                    iPinNumberView.showMessage("Pin not set");
                } else {
                    if (authPin.equals(pinValue)) {
                        Log.d(TAG, "Pin  correct!");
                        doSuccessNavigation();
                        hideForgotPassword();
                        mAttempts = 0;
                    } else {
                        mAttempts++;
                        iPinNumberView.showMessage("Pin Incorrect!");
                        iPinNumberView.erasePinOnTry();
                        Log.d(TAG, "Pin Incorrect!");
                    }
                }
                showForgotPassword();
                break;

            case Constants.AuthenticationType.LOAD_LOGINAUTH_PIN:
                mAppConfigurationManager.setAuthenticationPin(pinValue);
                iPinNumberView.erasePinOnTry();
                iPinNumberView.loadNextPage(Constants.AuthenticationType.LOAD_NEXT_PAGE);
                mAttempts = 0;
                showForgotPassword();
                break;
        }
    }

    @Override
    public void updateFingerPrintState(boolean canShow) {
        mAppConfigurationManager.setFingerPrintEnabledStatus(canShow);
    }

    private void updatePinAuthState(boolean canShow) {
        mAppConfigurationManager.setPinAuthenticationRequired(canShow);
    }

    @Override
    public boolean canShowFingerPrint() {
        return mAppConfigurationManager.isFingerPrintEnabled();//mAppConfigurationManager.canShowFingerPrint();
    }

    /***/
    private void doSuccessNavigation() {
        updateFingerPrintState(false);
        updatePinAuthState(false);
        iPinNumberView.loadNextPage(Constants.AuthenticationType.LOAD_AUTHENTICATE_SUCCESS);
        iPinNumberView.clearPinNumber();
    }

    /***/
    private void showForgotPassword() {
        if (mAttempts > 3) {
            iPinNumberView.setForgotPasswordEnabled(View.VISIBLE);
        }
    }

    /***/
    private void hideForgotPassword() {
        iPinNumberView.setForgotPasswordEnabled(View.GONE);
    }
}
