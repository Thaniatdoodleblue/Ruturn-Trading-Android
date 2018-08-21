package com.returntrader.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.returntrader.common.Constants;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.helper.LoginHelper;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.LoginRequest;
import com.returntrader.model.dto.request.OtpRequest;
import com.returntrader.model.dto.response.LoginResponse;
import com.returntrader.model.dto.response.LoginUserInfo;
import com.returntrader.model.dto.response.OtpResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.LoginModel;
import com.returntrader.model.webservice.OtpModel;
import com.returntrader.presenter.ipresenter.IOtpCodeVerifyPresenter;
import com.returntrader.view.iview.IOtpCodeVerifyView;

public class OtpCodeVerifyPresenter extends BasePresenter implements IOtpCodeVerifyPresenter {


    private IOtpCodeVerifyView iOtpCodeVerifyView;
    private UserInfo mUserInfo;
    private OtpModel mOtpModel;
    private AppConfigurationManager mAppConfigurationManager;
    private LoginRequest mLoginRequest;
    private int entryPoint;
    private LoginModel mLoginModel;
    private DatabaseManager mDatabaseManager;
    private LoginHelper mLoginHelper;


    public OtpCodeVerifyPresenter(IOtpCodeVerifyView iOtpCodeVerifyView) {
        super(iOtpCodeVerifyView);
        this.iOtpCodeVerifyView = iOtpCodeVerifyView;
        this.mOtpModel = new OtpModel(otpResponseIBaseModelListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iOtpCodeVerifyView.getActivity());
        this.mLoginModel = new LoginModel(loginResponseIBaseModelListener);
        this.mDatabaseManager = new DatabaseManager(iOtpCodeVerifyView.getActivity());
        this.mLoginHelper = new LoginHelper(iOtpCodeVerifyView.getActivity());
    }


    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            int from = bundle.getInt(Constants.BundleKey.BUNDLE_OTP_VERIFY_FROM);
            setEntryPoint(from);
            switch (from) {
                case Constants.OtpVerifyNavigation.FROM_REGISTRATION:
                    mUserInfo = bundle.getParcelable(Constants.BundleKey.BUNDLE_USER_INFO);
                    if (mUserInfo != null && mUserInfo.getPhoneNumber() != null) {
                        iOtpCodeVerifyView.setPhoneNumberText(mUserInfo.getPhoneNumber().getPhoneNumber());
                    }
                    break;
                case Constants.OtpVerifyNavigation.FROM_LOGIN:
                    String otp = bundle.getString(Constants.BundleKey.BUNDLE_OTPLOGIN);
                    iOtpCodeVerifyView.showMessage(otp);
                    mLoginRequest = bundle.getParcelable(Constants.BundleKey.BUNDLE_LOGIN_REQUEST);
                    if (mLoginRequest != null) {
                        iOtpCodeVerifyView.setPhoneNumberText(mLoginRequest.getPhoneNumber());
                    }
                    break;
            }
        }
    }

    @Override
    public void onClickVerifyOtp(String otp) {
        switch (getEntryPoint()) {
            case Constants.OtpVerifyNavigation.FROM_LOGIN:
                mLoginRequest.setOtp(otp);
                checkOtpForLogin(mLoginRequest);
                break;
            case Constants.OtpVerifyNavigation.FROM_REGISTRATION:
                OtpRequest otpRequest = new OtpRequest();
                otpRequest.setOtp(otp);
                checkOtpForRegistration(otpRequest);
                break;
        }
    }


    /***/
    private void reSendOtp(OtpRequest otpRequest) {
        if (iOtpCodeVerifyView.getCodeSnippet().hasNetworkConnection()) {
            iOtpCodeVerifyView.showProgressbar();
            mOtpModel.submitPhoneNumber(otpRequest);
        } else {
            iOtpCodeVerifyView.showNetworkMessage();
        }
    }


    private void updateFavList(String favourites) {
        mDatabaseManager.setFavouriteList(favourites);
    }


    @Override
    public void onClickResendOtp() {
        OtpRequest otpRequest = new OtpRequest();
        switch (getEntryPoint()) {
            case Constants.OtpVerifyNavigation.FROM_REGISTRATION:
                otpRequest.setPhoneNumber(mUserInfo.getPhoneNumber().getPhoneNumber());
                reSendOtp(otpRequest);
                break;
            case Constants.OtpVerifyNavigation.FROM_LOGIN:
                otpRequest.setPhoneNumber(mLoginRequest.getPhoneNumber());
                reSendOtp(otpRequest);
                break;
        }
    }

    /***/
    private void checkOtpForLogin(LoginRequest loginRequest) {
        if (iOtpCodeVerifyView.getCodeSnippet().hasNetworkConnection()) {
            iOtpCodeVerifyView.showProgressbar();
            mLoginModel.doLogin(loginRequest);
        } else {
            iOtpCodeVerifyView.showNetworkMessage();
        }
    }

    /***/
    private void checkOtpForRegistration(OtpRequest otpRequest) {
        if (iOtpCodeVerifyView.getCodeSnippet().hasNetworkConnection()) {
            iOtpCodeVerifyView.showProgressbar();
            otpRequest.setPhoneNumber(mUserInfo.getPhoneNumber().getPhoneNumber());
            mOtpModel.verifyPhoneNumberWithOTP(otpRequest);
        } else {
            iOtpCodeVerifyView.showNetworkMessage();
        }
    }

    /***/
    private IBaseModelListener<OtpResponse> otpResponseIBaseModelListener = new IBaseModelListener<OtpResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, OtpResponse response) {
            iOtpCodeVerifyView.dismissProgressbar();
            switch ((int) taskId) {
                case Constants.OtpModelTaskId.QUEUE_ID_SUBMIT_PHONE_NUMBER:
                    if (response != null) {
                        if (response.isSuccess()) {
                            iOtpCodeVerifyView.showMessage(response.getMessage() + " OTP is " + response.getOtp());
                        }
                    }
                    break;
                case Constants.OtpModelTaskId.QUEUE_ID_CHECK_PHONE_VERIFIED:
                    if (response != null) {
                        if (response.isSuccess()) {
                            iOtpCodeVerifyView.showMessage(response.getMessage());
                            iOtpCodeVerifyView.navigateToEmailEntry(null);
                        }
                    }
                    break;
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iOtpCodeVerifyView.dismissProgressbar();
            iOtpCodeVerifyView.resetPin();
            iOtpCodeVerifyView.showMessage(e.getException());
        }
    };


    /***/
    private IBaseModelListener<LoginResponse> loginResponseIBaseModelListener = new IBaseModelListener<LoginResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, LoginResponse response) {

            if (response == null) {
                iOtpCodeVerifyView.dismissProgressbar();
                return;
            }

            if (response.getLoginUserInfo() == null) {
                iOtpCodeVerifyView.dismissProgressbar();
                return;
            }


            LoginUserInfo loginUserInfo = response.getLoginUserInfo();
            // updating favourites list into database
            updateFavList(loginUserInfo.getFavourites());
            boolean isSyncDone = mLoginHelper.syncUserInfo(loginUserInfo, LoginHelper.SyncFrom.FROM_LOGIN);
            if (isSyncDone) {

//                if (mAppConfigurationManager.isFicaVerified()) {
                iOtpCodeVerifyView.syncAccount(Constants.AccountSync.SYNC_BALANCE);
//                }

                if (!TextUtils.isEmpty(loginUserInfo.getProfilePicture())) {
                    mAppConfigurationManager.setProfilePicture(loginUserInfo.getProfilePicture());
                }
                mAppConfigurationManager.setBankDetailCompletedStatus(loginUserInfo.isBankDetailsCompleted());
                mAppConfigurationManager.setJseNotifyEnabled(loginUserInfo.isNotifyJSE());
                mAppConfigurationManager.setCompanyNotifyEnabled(loginUserInfo.isNotifyCN());
                mAppConfigurationManager.setBreakingNotifyEnabled(loginUserInfo.isNotifyBreakingNews());

                iOtpCodeVerifyView.dismissProgressbar();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleKey.BUNDLE_AUTHENTICATION_FROM, Constants.AuthenticationType.FROM_LOGIN);
                iOtpCodeVerifyView.navigateToHome(bundle);
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iOtpCodeVerifyView.dismissProgressbar();
            iOtpCodeVerifyView.resetPin();
            iOtpCodeVerifyView.showMessage(e.getException());
        }
    };


    private int getEntryPoint() {
        return entryPoint;
    }

    private void setEntryPoint(int entryPoint) {
        this.entryPoint = entryPoint;
    }
}
