package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.FcmTokenRequest;
import com.returntrader.model.dto.request.LoginRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.dto.response.LoginResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */


public class FcmTokenModel extends BaseRetroFitModel<BaseResponse> {

    private String TAG = getClass().getSimpleName();

    public void registerFcmToken(FcmTokenRequest fcmTokenRequest) {
        enQueueTask(0, ApiClient.getClient().create(ApiInterface.class).updateFcmToken(fcmTokenRequest));
    }


    @Override
    public void onSuccessfulApi(long taskId, BaseResponse response) {
        //iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        //iBaseModelListener.onFailureApi(taskId, e);
    }
}
