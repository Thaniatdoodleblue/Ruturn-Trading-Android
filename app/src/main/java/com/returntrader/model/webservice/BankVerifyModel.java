package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.request.BuyRequest;
import com.returntrader.model.dto.response.BankVerifyResponse;
import com.returntrader.model.dto.response.BuyResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by nirmal on 4/2/2018.
 */

public class BankVerifyModel extends BaseRetroFitModel<BankVerifyResponse> {


    private IBaseModelListener<BankVerifyResponse> iBaseModelListener;

    public BankVerifyModel(IBaseModelListener<BankVerifyResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void doVerifyBank(long taskId, BaseRequest request) {
//        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).verifyBank(request));
    }

    @Override
    public void onSuccessfulApi(long taskId, BankVerifyResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}