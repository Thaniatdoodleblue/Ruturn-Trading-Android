package com.returntrader.presenter;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.presenter.ipresenter.IEggPresenter;
import com.returntrader.utils.FingerprintHandler;
import com.returntrader.view.fragment.EggDetailFragment;
import com.returntrader.view.fragment.FingerPrintAuthFragment;
import com.returntrader.view.fragment.PinNumberFragment;
import com.returntrader.view.iview.IEggView;

/**
 * Created by moorthy on 9/15/2017
 */

public class EggPresenter extends BasePresenter implements IEggPresenter {


    private IEggView iEggView;
    private AppConfigurationManager mAppConfigurationManger;
    private PinNumberFragment mPinNumberFragment;
    private EggDetailFragment mEggDetailFragment;
    private FingerprintHandler mFingerprintHandler;

    public EggPresenter(IEggView iView) {
        super(iView);
        this.iEggView = iView;
        this.mAppConfigurationManger = new AppConfigurationManager(iView.getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.mFingerprintHandler = new FingerprintHandler(iView.getActivity(), iFingerPrintResponseListener);
        }
    }

    private void registerFingerPrintAuthentication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mFingerprintHandler != null) {
            if (mFingerprintHandler.isFingerPrintSensorAvailable()) {
                mFingerprintHandler.doAuthenticate();
            }
        }
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        loadCurrentFragment();
    }

    @Override
    public void loadCurrentFragment() {
        Log.d(TAG, "isPinAuthenticationRequired :  " + mAppConfigurationManger.isPinAuthenticationRequired());
        if (mAppConfigurationManger.isPinAuthenticationRequired()) {
            registerFingerPrintAuthentication();
            iEggView.getAuthenticationListener().loadFragment(Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN);
        } else {
            iEggView.getAuthenticationListener().loadFragment(Constants.AuthenticationType.LOAD_AUTHENTICATE_SUCCESS);
        }
    }

    @Override
    public void setPinAuthenticationRequired(boolean isAuthenticationRequired) {
        mAppConfigurationManger.setPinAuthenticationRequired(isAuthenticationRequired);
    }

    @Override
    public PinNumberFragment getPinEnterFragment() {
        if (mPinNumberFragment == null) {
            mPinNumberFragment = new PinNumberFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BundleKey.BUNDLE_ENTRY_TYPE, Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN);
            mPinNumberFragment.setArguments(bundle);
        }
        return mPinNumberFragment;
    }

    @Override
    public EggDetailFragment getEggDetailFragment() {
        if (mEggDetailFragment == null) {
            mEggDetailFragment = new EggDetailFragment();
        }
        return mEggDetailFragment;
    }

    @Override
    public void refreshPinFragment() {
        if (mPinNumberFragment != null) {
            mPinNumberFragment.clearPin();
        }
    }

    @Override
    public void refreshEggFragment() {
        if (mEggDetailFragment != null) {
            mEggDetailFragment.refreshContent(false);
        }
    }

    private FingerprintHandler.IFingerPrintResponseListener iFingerPrintResponseListener = new FingerprintHandler.IFingerPrintResponseListener() {
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            Log.d(TAG, "onAuthenticationSucceeded");
            if (mAppConfigurationManger.isPinAuthenticationRequired() &&
                    mAppConfigurationManger.isFingerPrintEnabled()) {
                setPinAuthenticationRequired(false);
                loadCurrentFragment();
            }
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            Log.d(TAG, "onAuthenticationSucceeded");
            //update(helpString,false);
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Log.d(TAG, "onAuthenticationSucceeded");
        }

        @Override
        public void onAuthenticationFailed() {
            Log.d(TAG, "onAuthenticationFailed");
        }
    };


}
