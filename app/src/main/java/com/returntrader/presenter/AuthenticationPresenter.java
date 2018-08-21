package com.returntrader.presenter;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.library.Log;
import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.LoginRequest;
import com.returntrader.model.dto.request.UpdatePasscodeRequest;
import com.returntrader.model.dto.response.LoginResponse;
import com.returntrader.model.dto.response.UpdatePasscodeResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.LoginModel;
import com.returntrader.model.webservice.UpdatePassCodeModel;
import com.returntrader.presenter.ipresenter.IAuthenticationPresenter;
import com.returntrader.utils.FingerprintHandler;
import com.returntrader.view.fragment.PinNumberFragment;
import com.returntrader.view.iview.IAuthenticationView;

import static com.returntrader.common.Constants.AuthenticationType.LOAD_LOGINAUTH_PIN;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_ENTRY_TYPE;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_OTPLOGIN;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_RSA;
import static com.returntrader.common.Constants.RequestCodes.TASKID_LOGIN;
import static com.returntrader.common.Constants.RequestCodes.TASKID_PINVERIFY;

/**
 * Created by moorthy on 9/14/2017
 */

public class AuthenticationPresenter extends BasePresenter implements IAuthenticationPresenter {

    private IAuthenticationView iAuthenticationView;
    private String mFirstEnteredPin;
    private PinNumberFragment mPinNumberFragment;
    private PinNumberFragment mConfirmPinNumberFragment;
    private PinNumberFragment mAuthenticatePinNumberFragment;
    private int mCurrentFragment = -1;
    private int mFromActivity = -1;
    private FingerprintHandler mFingerprintHandler;
    private AppConfigurationManager mAppConfigurationManger;
    private LoginRequest mLoginRequest;
    private LoginModel mLoginModel;
    private UpdatePassCodeModel mUpdatePassCodeModel;
    private String rsaId;

    public AuthenticationPresenter(IAuthenticationView iView) {
        super(iView);
        iAuthenticationView = iView;
        mAppConfigurationManger = new AppConfigurationManager(iView.getActivity());
        mUpdatePassCodeModel = new UpdatePassCodeModel(updatePasscodeResponseIBaseModelListener);
        mLoginModel = new LoginModel(loginResponseIBaseModelListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.mFingerprintHandler = new FingerprintHandler(iView.getActivity(), iFingerPrintResponseListener);
            registerFingerPrintAuthentication();
        }
    }


    /***/
    private IBaseModelListener<LoginResponse> loginResponseIBaseModelListener = new IBaseModelListener<LoginResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, LoginResponse response) {
            iAuthenticationView.dismissProgressbar();
            if (response != null) {
                mAppConfigurationManger.setAuthenticationPin(mAppConfigurationManger.getAuthenticationPin().replace("\n", ""));
                //iAuthenticationView.showMessage(response.getOtp());
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleKey.BUNDLE_OTP_VERIFY_FROM, Constants.OtpVerifyNavigation.FROM_LOGIN);
                bundle.putParcelable(Constants.BundleKey.BUNDLE_LOGIN_REQUEST, mLoginRequest);
                bundle.putString(BUNDLE_OTPLOGIN, response.getOtp());
                iAuthenticationView.navigateToFingerPrintOrOTP(bundle);
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iAuthenticationView.dismissProgressbar();
            iAuthenticationView.showMessage(e.getException());
        }
    };

    /***/
    private void registerFingerPrintAuthentication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mFingerprintHandler != null) {
            if (mFingerprintHandler.isFingerPrintSensorAvailable()) {
                mFingerprintHandler.doAuthenticate();
            }
        }
    }

    @Override
    public void setPinAuthenticationRequired(boolean isAuthenticationRequired) {
        mAppConfigurationManger.setPinAuthenticationRequired(isAuthenticationRequired);
    }

    @Override
    public void verifyPinAndLogin() {
        if (iAuthenticationView.getCodeSnippet().hasNetworkConnection()) {
            LoginRequest request = new LoginRequest();
            iAuthenticationView.showProgressbar();
           request.setUserId(mLoginRequest.getUserId());
            request.setPasscode(mAppConfigurationManger.getAuthenticationPin());
           // request.setPasscode("J7GLKqrFqanWIDv464wJlQ==");
            mLoginModel.checkUserPinForLogin(TASKID_PINVERIFY, request);
        } else {
            iAuthenticationView.showNetworkMessage();
        }
    }

    @Override
    public void updatePasscode() {
        if (iAuthenticationView.getCodeSnippet().hasNetworkConnection()) {
            iAuthenticationView.showProgressbar();
            UpdatePasscodeRequest request = new UpdatePasscodeRequest();
            request.setUser_id(mAppConfigurationManger.getUserId());
            request.setPasscode(mAppConfigurationManger.getAuthenticationPin().replace("\n", ""));
            if (entryType == LOAD_LOGINAUTH_PIN) {
                if (!TextUtils.isEmpty(rsaId)) {
                    request.setRsaId(rsaId);
                }
                request.setPassKey("1");
            } else {
                request.setPassKey("0");
            }
            mUpdatePassCodeModel.dpUpdatePin(request);
        } else {
            iAuthenticationView.showNetworkMessage();
        }
    }


    /***/
    private IBaseModelListener<UpdatePasscodeResponse> updatePasscodeResponseIBaseModelListener = new IBaseModelListener<UpdatePasscodeResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, UpdatePasscodeResponse response) {
            iAuthenticationView.showProgressbar();
            iAuthenticationView.showMessage(iAuthenticationView.getActivity().getString(R.string.txt_successfully_password));
            iAuthenticationView.closeScreen();
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iAuthenticationView.showProgressbar();
            iAuthenticationView.showMessage("Something went wrong. Please try again later.");
        }
    };


    /***/
    private FingerprintHandler.IFingerPrintResponseListener iFingerPrintResponseListener = new FingerprintHandler.IFingerPrintResponseListener() {
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            if (mAppConfigurationManger.isPinAuthenticationRequired() &&
                    mAppConfigurationManger.isFingerPrintEnabled()) {
                mAppConfigurationManger.setFingerPrintEnabledStatus(false);
                ((PinNumberFragment.AuthenticationListener) iAuthenticationView.getActivity()).loadFragment(Constants.AuthenticationType.LOAD_AUTHENTICATE_SUCCESS);
            }

            if (mAppConfigurationManger.isPinAuthenticationRequired()) {
                setPinAuthenticationRequired(false);
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

    Bundle bundle;
    private int entryType;

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            this.bundle = bundle;
            mFromActivity = bundle.getInt(Constants.BundleKey.BUNDLE_AUTHENTICATION_FROM);
            mLoginRequest = bundle.getParcelable(Constants.BundleKey.BUNDLE_LOGIN_REQUEST);
            entryType = bundle.getInt(BUNDLE_ENTRY_TYPE);
            rsaId = bundle.getString(BUNDLE_RSA);

            switch (mFromActivity) {
                case Constants.AuthenticationType.FROM_FORGOT_PIN:
                    iAuthenticationView.setToolBarTitle(iAuthenticationView.getActivity().getString(R.string.txt_title_forgot_pin));
                    loadPinEntryFragment();
                    break;
                case Constants.AuthenticationType.FROM_LOGIN:
//                    loadPinEntryFragment();
                    loadLoginAuthPinEntryFragment();
                    break;
                case Constants.AuthenticationType.FROM_REGISTRATION:
                    loadPinEntryFragment();
                    break;
                case Constants.AuthenticationType.FROM_CHANGE_PIN_VERIFICATION:
                    iAuthenticationView.setToolBarTitle(iAuthenticationView.getActivity().getString(R.string.txt_menu_change_password));
                    loadConfirmPinEntryFragment();
                    break;
                case Constants.AuthenticationType.FROM_CHANGE_REQUEST:
                    loadPinEntryFragment();
                    break;
                case Constants.AuthenticationType.FROM_EDIT_PROFILE:
                    loadConfirmPinEntryFragment();
                    break;
            }
            Log.d(TAG, "mFromActivity: " + mFromActivity);
        }
    }

    /***/
    private void loadPinEntryFragment() {
        ((PinNumberFragment.AuthenticationListener) iAuthenticationView.getActivity()).loadFragment(Constants.AuthenticationType.LOAD_NEW_PIN_ENTRY);
    }

    /***/
    private void loadConfirmPinEntryFragment() {
        ((PinNumberFragment.AuthenticationListener) iAuthenticationView.getActivity()).loadFragment(Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN);
    }

    /***/
    private void loadLoginAuthPinEntryFragment() {
        ((PinNumberFragment.AuthenticationListener) iAuthenticationView.getActivity()).loadFragment(LOAD_LOGINAUTH_PIN);
    }


    @Override
    public PinNumberFragment getNewPinEntryFragment() {
        if (mPinNumberFragment == null) {
            mPinNumberFragment = new PinNumberFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BundleKey.BUNDLE_ENTRY_TYPE, Constants.AuthenticationType.LOAD_NEW_PIN_ENTRY);
            mPinNumberFragment.setArguments(bundle);
        }
        return mPinNumberFragment;
    }


    @Override
    public PinNumberFragment getAuthenticateFragment() {
        if (mAuthenticatePinNumberFragment == null) {
            mAuthenticatePinNumberFragment = new PinNumberFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BundleKey.BUNDLE_ENTRY_TYPE, Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN);
            mAuthenticatePinNumberFragment.setArguments(bundle);
        }
        return mAuthenticatePinNumberFragment;
    }


    @Override
    public PinNumberFragment getLoginAuthFragment() {
        if (mAuthenticatePinNumberFragment == null) {
            mAuthenticatePinNumberFragment = new PinNumberFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BundleKey.BUNDLE_ENTRY_TYPE, LOAD_LOGINAUTH_PIN);
            mAuthenticatePinNumberFragment.setArguments(bundle);
        }
        return mAuthenticatePinNumberFragment;
    }


    @Override
    public PinNumberFragment getConfirmPinEntryFragment() {
        //if (mConfirmPinNumberFragment == null) {
        mConfirmPinNumberFragment = new PinNumberFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BundleKey.BUNDLE_ENTRY_TYPE, Constants.AuthenticationType.LOAD_CONFIRM_PIN_ENTRY);
        bundle.putString(Constants.BundleKey.BUNDLE_PIN, getFirstEnteredPin());
        mConfirmPinNumberFragment.setArguments(bundle);
        //}
        return mConfirmPinNumberFragment;
    }

    @Override
    public void setFirstEnteredPin(String value) {
        this.mFirstEnteredPin = value;
    }

    @Override
    public String getFirstEnteredPin() {
        return mFirstEnteredPin;
    }


    @Override
    public void updateCurrentFragment(int currentFragment) {
        this.mCurrentFragment = currentFragment;
    }

    @Override
    public void clearPin() {
        if (mPinNumberFragment != null) {
            Log.d(TAG, "Clear pin in Pinfragment");
            mPinNumberFragment.clearPin();
        }

        if (mConfirmPinNumberFragment != null) {
            Log.d(TAG, "Clear pin in ConfirmPinfragment");
            mConfirmPinNumberFragment.clearPin();
        }
    }

    @Override
    public int authenticationFrom() {
        return mFromActivity;
    }
}
