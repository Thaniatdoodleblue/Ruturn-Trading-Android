package com.returntrader.model.webservice;

import com.returntrader.common.Constants;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.request.LoginRequest;
import com.returntrader.model.dto.response.CheckAddressStatusResponse;
import com.returntrader.model.dto.response.LoginResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */


public class CheckAddressStatusModel extends BaseRetroFitModel<CheckAddressStatusResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<CheckAddressStatusResponse> iBaseModelListener;


    public CheckAddressStatusModel(IBaseModelListener<CheckAddressStatusResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void checkAddressStatus(BaseRequest baseRequest) {
        enQueueTask(Constants.SyncServiceTaskId.SYNC_CHECK_ADDRESS_STATUS, ApiClient.getClient().create(ApiInterface.class).checkAddressStatus(baseRequest));
    }

    public void checkFicaStatus(BaseRequest baseRequest) {
        enQueueTask(Constants.SyncServiceTaskId.SYNC_CHECK_FICA_STATUS, ApiClient.getClient().create(ApiInterface.class).checkFicaStatus(baseRequest));
    }

    public void checkTrustedAccountStatus(BaseRequest baseRequest) {
        enQueueTask(Constants.SyncServiceTaskId.SYNC_CHECK_TRUSTED_ACCOUNT_STATUS, ApiClient.getClient().create(ApiInterface.class).checkTrustedAccountStatus(baseRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, CheckAddressStatusResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
