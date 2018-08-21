package com.returntrader.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.EmailVerificationRequest;
import com.returntrader.model.dto.response.OtpResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.OtpModel;
import com.returntrader.presenter.ipresenter.IEmailAckActivityPresenter;
import com.returntrader.view.iview.IEmailAckActivityView;

/**
 * Created by moorthy on 8/24/2017.
 */

public class EmailAckPresenter extends BasePresenter implements IEmailAckActivityPresenter {


    private IEmailAckActivityView iEmailAckActivityView;
    private boolean isEmailVerified = false;
    private String mCurrentEmailId;
    private OtpModel mOtpModel;
    private AppConfigurationManager mAppConfigurationManager;

    public EmailAckPresenter(IEmailAckActivityView iView) {
        super(iView);
        this.iEmailAckActivityView = iView;
        this.mOtpModel = new OtpModel(otpResponseIBaseModelListener);
        mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mCurrentEmailId = bundle.getString(Constants.BundleKey.BUNDLE_EMAIL_ID);
            if (!(TextUtils.isEmpty(mCurrentEmailId))) {
                iEmailAckActivityView.setEmailText(mCurrentEmailId);
            }
        }
    }

    @Override
    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    /***/
    private void setEmailVerified() {
        isEmailVerified = true;
        iEmailAckActivityView.displayEmailVerificationDone();
    }

    @Override
    public void requestToVerifyEmail() {
        if (iEmailAckActivityView.getCodeSnippet().hasNetworkConnection()) {
            iEmailAckActivityView.showProgressbar();
            mOtpModel.verifyEmail(new EmailVerificationRequest(mCurrentEmailId));
        } else {
            iEmailAckActivityView.showProgressbar();
        }
    }

    @Override
    public void reSendEmail() {
        if (iEmailAckActivityView.getCodeSnippet().hasNetworkConnection()) {
            UserInfo userInfo = iEmailAckActivityView.getCodeSnippet().getObjectFromJson(mAppConfigurationManager.getPartialUserInfo(), UserInfo.class);
            if (userInfo != null) {
                iEmailAckActivityView.showProgressbar();
                EmailVerificationRequest request = new EmailVerificationRequest();
                request.setEmail(mCurrentEmailId);
                request.setRsaIdentity(userInfo.getRsaIdentificationId());
                if (userInfo.getPhoneNumber() != null) {
                    request.setMobileNumber(userInfo.getPhoneNumber().getCountryCode() + userInfo.getPhoneNumber().getPhoneNumber());
                }
                mOtpModel.submitEmail(request);
            }
        } else {
            iEmailAckActivityView.showProgressbar();
        }
    }

    /***/
    private IBaseModelListener<OtpResponse> otpResponseIBaseModelListener = new IBaseModelListener<OtpResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, OtpResponse response) {
            iEmailAckActivityView.dismissProgressbar();
            switch ((int) taskId) {
                case Constants.OtpModelTaskId.QUEUE_ID_CHECK_EMAIL_VERIFIED:
                    if (response.isSuccess()) {
                        setEmailVerified();
                    }
                    break;
                case Constants.OtpModelTaskId.QUEUE_ID_SUBMIT_EMAIL:
                    if (response.isSuccess()) {
                        iEmailAckActivityView.showMessage(response.getMessage());
                    }
                    break;
            }

        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iEmailAckActivityView.dismissProgressbar();
            if(e!=null) {
                iEmailAckActivityView.showMessage(e.getException());
            }
        }
    };
}
