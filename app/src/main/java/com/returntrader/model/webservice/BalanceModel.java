package com.returntrader.model.webservice;

import android.util.Log;

import com.returntrader.common.Constants;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.response.BalanceResponse;
import com.returntrader.model.dto.response.DelayPriceResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */

public class BalanceModel extends BaseRetroFitModel<BalanceResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<BalanceResponse> iBaseModelListener;

    public BalanceModel(IBaseModelListener<BalanceResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getBalance(BaseRequest baseRequest) {
        Log.d(TAG,"baseRequest : " + baseRequest.getUserId());
        enQueueTask(Constants.SyncServiceTaskId.SYNC_BALANCE, ApiClient.getClient().create(ApiInterface.class).getBalance(baseRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, BalanceResponse response) {
        Log.d(TAG,"onSuccessfulApi");
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        Log.d(TAG,"onFailureApi" + e.getMessage());
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
