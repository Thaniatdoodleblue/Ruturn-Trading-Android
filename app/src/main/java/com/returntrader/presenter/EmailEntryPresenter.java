package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
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
import com.returntrader.presenter.ipresenter.IEmailEntryPresenter;
import com.returntrader.view.iview.IEmailEntryView;

import java.util.Calendar;

import static com.returntrader.common.Constants.OtpModelTaskId.QUEUE_ID_CHECK_RSA_VERIFIED;
import static com.returntrader.common.Constants.OtpModelTaskId.QUEUE_ID_SUBMIT_EMAIL;

/**
 * Created by moorthy on 8/24/2017.
 */

public class EmailEntryPresenter extends BasePresenter implements IEmailEntryPresenter {

    private IEmailEntryView iEmailEntryView;
    private UserInfo mUserInfo;
    private OtpModel mOtpModel;
    private AppConfigurationManager mAppConfigurationManager;

    public EmailEntryPresenter(IEmailEntryView iView) {
        super(iView);
        this.iEmailEntryView = iView;
        this.mOtpModel = new OtpModel(otpResponseIBaseModelListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {

    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.NAVIGATE_EMAIL_TO_VERIFY_EMAIL:
                if (resultCode == Activity.RESULT_OK) {
                    iEmailEntryView.getActivity().finish();
                }
                break;
        }
    }

    @Override
    public void onClickSubmit(UserInfo userInfo) {
        this.mUserInfo = userInfo;
        if (iEmailEntryView.getCodeSnippet().hasNetworkConnection()) {
            iEmailEntryView.showProgressbar();
            EmailVerificationRequest mEmailVerificationRequest = new EmailVerificationRequest();
            mEmailVerificationRequest.setRsaId(userInfo.getRsaIdentificationId());
            mEmailVerificationRequest.setEmail(userInfo.getEmailId());
            mOtpModel.verifyRSA(mEmailVerificationRequest);
//            onSubmitEmail();
        }
    }

    /***/
    private void onSubmitEmail() {
        if (iEmailEntryView.getCodeSnippet().hasNetworkConnection()) {
            iEmailEntryView.showProgressbar();
            UserInfo userInfo = iEmailEntryView.getCodeSnippet().getObjectFromJson(mAppConfigurationManager.getPartialUserInfo(), UserInfo.class);
            if (mUserInfo != null) {
                iEmailEntryView.showProgressbar();
                EmailVerificationRequest request = new EmailVerificationRequest();
                request.setEmail(mUserInfo.getEmailId());
                request.setRsaIdentity(mUserInfo.getRsaIdentificationId());
                if (userInfo.getPhoneNumber() != null) {
                    request.setMobileNumber(userInfo.getPhoneNumber().getCountryCode() + userInfo.getPhoneNumber().getPhoneNumber());
                }
                mOtpModel.submitEmail(request);
            }
        }
    }


    @Override
    public boolean isNotAboveTeen(String rsaId) {
        String dateOfBirthFormat = rsaId.substring(0, 7);
        String date = iEmailEntryView.getCodeSnippet().getDateFromRsaId(dateOfBirthFormat);
        Calendar calendar = iEmailEntryView.getCodeSnippet().getCalendarToStandard(date);
        if (calendar == null) {
            return true;
        }
        return !(iEmailEntryView.getCodeSnippet().isAgeInRage(calendar.getTime()));
    }

    /***/
    private IBaseModelListener<OtpResponse> otpResponseIBaseModelListener = new IBaseModelListener<OtpResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, OtpResponse response) {
            iEmailEntryView.dismissProgressbar();
            if (response.isSuccess()) {
                if (taskId == QUEUE_ID_CHECK_RSA_VERIFIED) {
                    onSubmitEmail();
                } else if (taskId == QUEUE_ID_SUBMIT_EMAIL) {
                    if (!(TextUtils.isEmpty(mAppConfigurationManager.getPartialUserInfo()))) {
                        UserInfo userInfo = iEmailEntryView.getCodeSnippet().getObjectFromJson(mAppConfigurationManager.getPartialUserInfo(), UserInfo.class);
                        if (userInfo != null) {
                            userInfo.setRsaIdentificationId(mUserInfo.getRsaIdentificationId());
                            userInfo.setEmailId(mUserInfo.getEmailId());
                            mAppConfigurationManager.setPartialUserInfo(iEmailEntryView.getCodeSnippet().getJsonStringFromObject(userInfo, UserInfo.class));
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.BundleKey.BUNDLE_EMAIL_ID, mUserInfo.getEmailId());
                            iEmailEntryView.navigateToEmailAck(bundle);
                        }
                    }
                }
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iEmailEntryView.dismissProgressbar();
            if (e != null) {
                iEmailEntryView.showMessage(e.getException());
            }
        }
    };
}
