package com.returntrader.presenter;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;

import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.presenter.ipresenter.IAskAuthenticationPresenter;
import com.returntrader.utils.FingerprintHandler;
import com.returntrader.view.fragment.PinNumberFragment;
import com.returntrader.view.iview.IAskAuthenticationView;

/**
 * Created by moorthy on 9/14/2017
 */

public class AskAuthenticationPresenter extends BasePresenter implements IAskAuthenticationPresenter {

    private IAskAuthenticationView iAskAuthenticationView;
    private AppConfigurationManager mAppConfigurationManager;
    private FingerprintHandler mFingerprintHandler;


    public AskAuthenticationPresenter(IAskAuthenticationView iView) {
        super(iView);
        this.iAskAuthenticationView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        setAuthPinRequired(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.mFingerprintHandler = new FingerprintHandler(iView.getActivity(), iFingerPrintResponseListener);
            registerFingerPrintAuthentication();
        }
    }

    /***/
    private void registerFingerPrintAuthentication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mFingerprintHandler != null) {
            if (mFingerprintHandler.isFingerPrintSensorAvailable()) {
                mFingerprintHandler.doAuthenticate();
            }
        }
    }


    @Override
    public void setAuthPinRequired(boolean isRequired) {
        mAppConfigurationManager.setPinAuthenticationRequired(isRequired);
        mAppConfigurationManager.setFingerPrintEnabledStatus(isRequired);
    }

    /***/
    private FingerprintHandler.IFingerPrintResponseListener iFingerPrintResponseListener = new FingerprintHandler.IFingerPrintResponseListener() {
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            if (mAppConfigurationManager.isPinAuthenticationRequired() &&
                    mAppConfigurationManager.isFingerPrintEnabled()) {
                setAuthPinRequired(false);
                ((PinNumberFragment.AuthenticationListener) iAskAuthenticationView.getActivity()).loadFragment(Constants.AuthenticationType.LOAD_AUTHENTICATE_SUCCESS);
            }
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            android.util.Log.d(TAG, "onAuthenticationHelp");
            //update(helpString,false);
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            android.util.Log.d(TAG, "onAuthenticationError");
        }

        @Override
        public void onAuthenticationFailed() {
            android.util.Log.d(TAG, "onAuthenticationFailed");
        }
    };

    @Override
    public void onCreatePresenter(Bundle bundle) {
        ((PinNumberFragment.AuthenticationListener) iAskAuthenticationView.getActivity()).loadFragment(Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN);
    }


    private String getCurrentActivePin() {
        return mAppConfigurationManager.getAuthenticationPin();
    }

    @Override
    public PinNumberFragment getAuthPinEntryFragment() {
        PinNumberFragment pinNumberFragment = new PinNumberFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BundleKey.BUNDLE_ENTRY_TYPE, Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN);
        bundle.putString(Constants.BundleKey.BUNDLE_PIN, getCurrentActivePin());
        pinNumberFragment.setArguments(bundle);
        return pinNumberFragment;
    }

}
