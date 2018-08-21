package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.response.ShakeMakeGroupResponse;
import com.returntrader.model.dto.response.ShakeMakeMoneyResponse;
import com.returntrader.model.listener.IBaseModelListener;

public class ShakeMakeMoneyModel extends BaseRetroFitModel<ShakeMakeMoneyResponse> {

    private IBaseModelListener<ShakeMakeMoneyResponse> iBaseModelListener;

    public ShakeMakeMoneyModel(IBaseModelListener<ShakeMakeMoneyResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getShakeMakeMoneyApi(long taskId) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).getShakeMakeMoney());
    }

    @Override
    public void onSuccessfulApi(long taskId, ShakeMakeMoneyResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
