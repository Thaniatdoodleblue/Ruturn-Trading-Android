package com.returntrader.presenter;

import android.os.Bundle;

import com.returntrader.common.Constants;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.ForgotPinRequest;
import com.returntrader.model.dto.response.ForgotPasswordResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ForgotPinModel;
import com.returntrader.presenter.ipresenter.IForgotPinVerifyPresenter;
import com.returntrader.view.iview.IForgotPinVerifyView;

import static com.returntrader.common.Constants.BundleKey.BUNDLE_ENTRY_TYPE;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_RSA;

/**
 * Created by moorthy on 11/6/2017.
 */

public class ForgotPinVerifyPresenter extends BasePresenter implements IForgotPinVerifyPresenter {


    private IForgotPinVerifyView iForgotPinVerifyView;
    private String mCurrentEmailId;
    private ForgotPinModel mForgotPinModel;
    private int entryType;
    private String rsaId;

    public ForgotPinVerifyPresenter(IForgotPinVerifyView iView) {
        super(iView);
        this.iForgotPinVerifyView = iView;
        this.mForgotPinModel = new ForgotPinModel(forgotPinVerifyResponseModelListener);
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mCurrentEmailId = bundle.getString(Constants.BundleKey.BUNDLE_EMAIL_ID);
            entryType = bundle.getInt(BUNDLE_ENTRY_TYPE);
            rsaId = bundle.getString(BUNDLE_RSA);
            iForgotPinVerifyView.setEmailText(mCurrentEmailId);
        }
    }

    @Override
    public void submitOtp(String otp) {
        if (iForgotPinVerifyView.getCodeSnippet().hasNetworkConnection()) {
            iForgotPinVerifyView.enableOrDisableNext(false);
            ForgotPinRequest forgotPinRequest = new ForgotPinRequest();
            forgotPinRequest.setEmailId(mCurrentEmailId);
            forgotPinRequest.setOtp(otp);
            mForgotPinModel.checkForgotPinOtp(forgotPinRequest);
        } else {
            iForgotPinVerifyView.showNetworkMessage();
        }
    }

    /***/
    private IBaseModelListener<ForgotPasswordResponse> forgotPinVerifyResponseModelListener = new IBaseModelListener<ForgotPasswordResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, ForgotPasswordResponse response) {
            iForgotPinVerifyView.dismissProgressbar();
            iForgotPinVerifyView.enableOrDisableNext(true);
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BundleKey.BUNDLE_AUTHENTICATION_FROM, Constants.AuthenticationType.FROM_FORGOT_PIN);
            bundle.putInt(BUNDLE_ENTRY_TYPE, entryType);
            bundle.putString(BUNDLE_RSA, rsaId);
            iForgotPinVerifyView.navigateToResetPassword(bundle);
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iForgotPinVerifyView.dismissProgressbar();
            iForgotPinVerifyView.enableOrDisableNext(true);
            iForgotPinVerifyView.showMessage(e.getException());
        }
    };


}
