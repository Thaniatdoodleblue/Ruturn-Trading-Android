package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.response.DelayPriceResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */

public class GetDelayPriceModel extends BaseRetroFitModel<DelayPriceResponse> {


    private IBaseModelListener<DelayPriceResponse> iBaseModelListener;

    public GetDelayPriceModel(IBaseModelListener<DelayPriceResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getDelayPriceList(long taskId, BaseRequest baseRequest) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).getDelayPriceList(baseRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, DelayPriceResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
