package com.returntrader.model.webservice;

import android.util.Log;

import com.returntrader.common.Constants;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.EmailVerificationRequest;
import com.returntrader.model.dto.request.OtpRequest;
import com.returntrader.model.dto.response.OtpResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */


public class OtpModel extends BaseRetroFitModel<OtpResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<OtpResponse> iBaseModelListener;


    public OtpModel(IBaseModelListener<OtpResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void submitPhoneNumber(OtpRequest otpRequest) {
        enQueueTask(Constants.OtpModelTaskId.QUEUE_ID_SUBMIT_PHONE_NUMBER, ApiClient.getClient().create(ApiInterface.class).submitPhoneNumber(otpRequest));
    }

    public void verifyPhoneNumber(OtpRequest otpRequest) {
        enQueueTask(Constants.OtpModelTaskId.QUEUE_ID_VERIFY_PHONE_NUMBER, ApiClient.getClient().create(ApiInterface.class).verifyPhoneNumber(otpRequest));
    }

    public void submitEmail(EmailVerificationRequest emailVerificationRequest) {
        enQueueTask(Constants.OtpModelTaskId.QUEUE_ID_SUBMIT_EMAIL, ApiClient.getClient().create(ApiInterface.class).submitEmail(emailVerificationRequest));
    }


    public void verifyPhoneNumberWithOTP(OtpRequest otpRequest) {
        enQueueTask(Constants.OtpModelTaskId.QUEUE_ID_CHECK_PHONE_VERIFIED, ApiClient.getClient().create(ApiInterface.class).verifyPhoneNumberWithOTP(otpRequest));
    }


    public void verifyEmail(EmailVerificationRequest emailVerificationRequest) {
        enQueueTask(Constants.OtpModelTaskId.QUEUE_ID_CHECK_EMAIL_VERIFIED, ApiClient.getClient().create(ApiInterface.class).verifyEmail(emailVerificationRequest));
    }

    public void verifyRSA(EmailVerificationRequest rsaVerificationRequest) {
        enQueueTask(Constants.OtpModelTaskId.QUEUE_ID_CHECK_RSA_VERIFIED, ApiClient.getClient().create(ApiInterface.class).verifyRSA(rsaVerificationRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, OtpResponse response) {
        Log.d(TAG, "onSuccessfulApi");
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        Log.d(TAG, "onFailureApi" + e.getMessage());
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
