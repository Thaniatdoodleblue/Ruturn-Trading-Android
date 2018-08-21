package com.returntrader.model.webservice;

import android.util.Log;

import com.returntrader.library.CustomException;
import com.returntrader.model.common.ConfigData;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */

public class GetConfigurationDataModel extends BaseRetroFitModel<ConfigData> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<ConfigData> iBaseModelListener;

    public GetConfigurationDataModel(IBaseModelListener<ConfigData> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getConfigData() {
        enQueueTask(5, ApiClient.getClient().create(ApiInterface.class).getConfigData());
    }

    @Override
    public void onSuccessfulApi(long taskId, ConfigData response) {
        Log.d(TAG, "onSuccessfulApi");
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        Log.d(TAG, "onFailureApi" + e.getMessage());
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
