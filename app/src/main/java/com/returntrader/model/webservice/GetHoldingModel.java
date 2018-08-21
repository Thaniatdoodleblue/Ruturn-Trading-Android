package com.returntrader.model.webservice;

import android.util.Log;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.response.BalanceResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */

public class GetHoldingModel extends BaseRetroFitModel<StockHoldResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<StockHoldResponse> iBaseModelListener;

    public GetHoldingModel(IBaseModelListener<StockHoldResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getHolding(long taskId, BaseRequest baseRequest) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).getHolding(baseRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, StockHoldResponse response) {
        Log.d(TAG, "onSuccessfulApi");
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        Log.d(TAG, "onFailureApi" + e.getMessage());
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
