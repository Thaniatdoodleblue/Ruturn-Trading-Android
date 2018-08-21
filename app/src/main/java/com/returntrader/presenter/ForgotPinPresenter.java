package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.library.Log;
import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.ForgotPinRequest;
import com.returntrader.model.dto.response.ForgotPasswordResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ForgotPinModel;
import com.returntrader.presenter.ipresenter.IForgotPinPresenter;
import com.returntrader.view.iview.IForgotPinView;

import static com.returntrader.common.Constants.AuthenticationType.LOAD_LOGINAUTH_PIN;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_ENTRY_TYPE;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_RSA;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_USER_INFO;

/**
 * Created by moorthy on 11/6/2017.
 */

public class ForgotPinPresenter extends BasePresenter implements IForgotPinPresenter {
    private ForgotPinModel mForgotPinModel;
    private AppConfigurationManager mAppConfigurationManager;
    private IForgotPinView iForgotPinView;
    private String mCurrentEmailId;
    private UserInfo userInfo;
    private int entryType;

    public ForgotPinPresenter(IForgotPinView iView) {
        super(iView);
        this.iForgotPinView = iView;
        this.mForgotPinModel = new ForgotPinModel(forgotPasswordModelListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            entryType = bundle.getInt(BUNDLE_ENTRY_TYPE);
        }
    }

    @Override
    public void onClickSubmit(ForgotPinRequest forgotPinRequest) {
        userInfo = iForgotPinView.getCodeSnippet().getObjectFromJson(mAppConfigurationManager.getPartialUserInfo(), UserInfo.class);
        if (TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
            if (userInfo != null) {
                if (forgotPinRequest.getEmailId().equalsIgnoreCase(userInfo.getEmailId()) &&
                        forgotPinRequest.getRsaId().equalsIgnoreCase(userInfo.getRsaIdentificationId())) {
                    callApi(forgotPinRequest, 1);
                } else {
                    iForgotPinView.showMessage("EmailId Or RSA id incorrect");
                }
            } else {
                if (entryType == LOAD_LOGINAUTH_PIN) {
                    userInfo = new UserInfo();
                    userInfo.setEmailId(forgotPinRequest.getEmailId());
                    userInfo.setRsaIdentificationId(forgotPinRequest.getRsaId());
//                    forgotPinRequest.setEmailId(iForgotPinView.getEmailId());
//                    forgotPinRequest.setRsaId(iForgotPinView.getRSAId());
                    callApi(forgotPinRequest, 0);
                }
            }
        } else {
            forgotPinRequest.setUserId(mAppConfigurationManager.getUserId());
            callApi(forgotPinRequest, 0);
        }
    }

    @Override
    public void disableAuth(boolean canAuth) {
        mAppConfigurationManager.setPinAuthenticationRequired(canAuth);
        mAppConfigurationManager.setFingerPrintEnabledStatus(canAuth);
    }


    /***/
    private void callApi(ForgotPinRequest forgotPinRequest, int type) {
        if (!(iForgotPinView.getCodeSnippet().hasNetworkConnection())) {
            iForgotPinView.showNetworkMessage();
            return;
        }
        iForgotPinView.showProgressbar();
        mCurrentEmailId = forgotPinRequest.getEmailId();
        switch (type) {
            case 0: // For full register user
                mForgotPinModel.submitForValidUser(forgotPinRequest);
                break;
            case 1: // For partial user
                mForgotPinModel.submitForPartialUser(forgotPinRequest);
                break;
        }
    }

    /***/
    private IBaseModelListener<ForgotPasswordResponse> forgotPasswordModelListener = new IBaseModelListener<ForgotPasswordResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, ForgotPasswordResponse response) {
            iForgotPinView.dismissProgressbar();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BundleKey.BUNDLE_EMAIL_ID, mCurrentEmailId);
            bundle.putInt(BUNDLE_ENTRY_TYPE, entryType);
            if (userInfo != null) {
                bundle.putString(BUNDLE_RSA, userInfo.getRsaIdentificationId());
            }
            iForgotPinView.navigateToVerifyForgotPin(bundle);
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iForgotPinView.dismissProgressbar();
            userInfo = null;
            if (e != null) {
                iForgotPinView.showMessage(e.getException());
            }
        }
    };


    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.NAVIGATE_FORGOT_PIN_TO_VERIFY_PIN:
                if (resultCode == Activity.RESULT_OK) {
                    iForgotPinView.getActivity().finish();
                }
                break;
        }
    }
}
