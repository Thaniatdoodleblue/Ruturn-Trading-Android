package com.returntrader.model.webservice;

import android.util.Log;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.request.HistoryRequest;
import com.returntrader.model.dto.response.DayHistoryPriceResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */

public class DayHistoryPriceModel extends BaseRetroFitModel<DayHistoryPriceResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<DayHistoryPriceResponse> iBaseModelListener;

    public DayHistoryPriceModel(IBaseModelListener<DayHistoryPriceResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getDayHistoryPrice(HistoryRequest historyRequest) {
        enQueueTask(0, ApiClient.getClient().create(ApiInterface.class).getDayHistoryPrice(historyRequest));
    }

    public void getDayHistoryPriceForMarketTime() {
        enQueueTask(1, ApiClient.getClient().create(ApiInterface.class).getDayHistoryPriceAtMarketTime());
    }

    @Override
    public void onSuccessfulApi(long taskId, DayHistoryPriceResponse response) {
        Log.d(TAG, "onSuccessfulApi");
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        Log.d(TAG, "onFailureApi" + e.getMessage());
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
