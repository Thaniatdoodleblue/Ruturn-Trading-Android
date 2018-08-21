package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.request.WithDrawalRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.dto.response.WithDrawBalanceResponse;
import com.returntrader.model.listener.IBaseModelListener;

public class WithDrawBalanceModel extends BaseRetroFitModel<WithDrawBalanceResponse>{
    private IBaseModelListener<WithDrawBalanceResponse> iBaseModelListener;

    public WithDrawBalanceModel(IBaseModelListener<WithDrawBalanceResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void withDrawBalance(long taskId, BaseRequest request) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).withDrawBalance(request));
    }

    @Override
    public void onSuccessfulApi(long taskId, WithDrawBalanceResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}