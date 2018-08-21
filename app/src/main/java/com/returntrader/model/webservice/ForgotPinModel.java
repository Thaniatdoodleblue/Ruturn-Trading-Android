package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.ForgotPinRequest;
import com.returntrader.model.dto.response.ForgotPasswordResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */


public class ForgotPinModel extends BaseRetroFitModel<ForgotPasswordResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<ForgotPasswordResponse> iBaseModelListener;


    public ForgotPinModel(IBaseModelListener<ForgotPasswordResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void submitForValidUser(ForgotPinRequest forgotPinRequest) {
        enQueueTask(0, ApiClient.getClient().create(ApiInterface.class).sendForgotPasswordRequestForValidUser(forgotPinRequest));
    }

    public void submitForPartialUser(ForgotPinRequest forgotPinRequest) {
        enQueueTask(1, ApiClient.getClient().create(ApiInterface.class).sendForgotPasswordRequestForPartialUser(forgotPinRequest));
    }

    public void checkForgotPinOtp(ForgotPinRequest forgotPinRequest) {
        enQueueTask(2, ApiClient.getClient().create(ApiInterface.class).checkForgotPinOtp(forgotPinRequest));
    }


    @Override
    public void onSuccessfulApi(long taskId, ForgotPasswordResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
