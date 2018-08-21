package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.request.BuyRequest;
import com.returntrader.model.dto.response.BuyResponse;
import com.returntrader.model.dto.response.DelayPriceResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */

public class BuyModel extends BaseRetroFitModel<BuyResponse> {


    private IBaseModelListener<BuyResponse> iBaseModelListener;

    public BuyModel(IBaseModelListener<BuyResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void doBuyShare(long taskId, BuyRequest buyRequest) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).doBuyStock(buyRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, BuyResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
