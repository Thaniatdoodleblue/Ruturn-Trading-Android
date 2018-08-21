package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BuyRequest;
import com.returntrader.model.dto.response.BuyResponse;
import com.returntrader.model.dto.response.SellResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */

public class SellModel extends BaseRetroFitModel<SellResponse> {

    private IBaseModelListener<SellResponse> iBaseModelListener;

    public SellModel(IBaseModelListener<SellResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void doSellShare(long taskId, BuyRequest buyRequest) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).doSellStock(buyRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, SellResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
