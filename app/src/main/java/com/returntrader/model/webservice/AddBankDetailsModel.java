package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BankDetailRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */


public class AddBankDetailsModel extends BaseRetroFitModel<BaseResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<BaseResponse> iBaseModelListener;


    public AddBankDetailsModel(IBaseModelListener<BaseResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void addBankDetails(BankDetailRequest bankDetailRequest) {
        enQueueTask(0, ApiClient.getClient().create(ApiInterface.class).addBankDetails(bankDetailRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, BaseResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
