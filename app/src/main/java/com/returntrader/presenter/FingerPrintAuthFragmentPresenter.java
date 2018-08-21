package com.returntrader.presenter;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.presenter.ipresenter.IFingerPrintAuthFragmentPresenter;
import com.returntrader.utils.FingerprintHandler;
import com.returntrader.view.iview.IFingerPrintAuthFragmentView;

/**
 * Created by nirmal on 2/8/2018.
 */

public class FingerPrintAuthFragmentPresenter extends BasePresenter implements IFingerPrintAuthFragmentPresenter {
    private IFingerPrintAuthFragmentView iFingerPrintAuthFragmentView;
    private AppConfigurationManager mAppConfigurationManger;
    private FingerprintHandler mFingerprintHandler;

    public FingerPrintAuthFragmentPresenter(IFingerPrintAuthFragmentView iView) {
        super(iView);
        iFingerPrintAuthFragmentView = iView;
        this.mAppConfigurationManger = new AppConfigurationManager(iView.getActivity());
    }

    /***/
    private void setPinAuthenticationRequired(boolean isAuthenticationRequired) {
        mAppConfigurationManger.setPinAuthenticationRequired(isAuthenticationRequired);
    }


    /***/
    private FingerprintHandler.IFingerPrintResponseListener iFingerPrintResponseListener = new FingerprintHandler.IFingerPrintResponseListener() {
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            Log.d("FingerPrint", "onAuthenticationSucceeded");
            if (mAppConfigurationManger.isPinAuthenticationRequired() &&
                    mAppConfigurationManger.isFingerPrintEnabled()) {
                setPinAuthenticationRequired(false);
                iFingerPrintAuthFragmentView.showMessage("Success");
//                loadCurrentFragment();
            }
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            Log.d(TAG, "onAuthenticationSucceeded");
            //update(helpString,false);
            iFingerPrintAuthFragmentView.showMessage("Help");
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Log.d(TAG, "onAuthenticationSucceeded");
            iFingerPrintAuthFragmentView.showMessage("Error");
        }

        @Override
        public void onAuthenticationFailed() {
            Log.d(TAG, "onAuthenticationFailed");
            iFingerPrintAuthFragmentView.showMessage("Failed");
        }
    };

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.mFingerprintHandler = new FingerprintHandler(iFingerPrintAuthFragmentView.getActivity(), iFingerPrintResponseListener);
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
}
