package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BuyRequest;
import com.returntrader.model.dto.response.SellResponse;
import com.returntrader.model.dto.response.ShakeMakeGroupResponse;
import com.returntrader.model.dto.response.ShakeMakeMoneyResponse;
import com.returntrader.model.listener.IBaseModelListener;

public class ShakeMakeGroupModel extends BaseRetroFitModel<ShakeMakeGroupResponse> {

    private IBaseModelListener<ShakeMakeGroupResponse> iBaseModelListener;

    public ShakeMakeGroupModel(IBaseModelListener<ShakeMakeGroupResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getShakeMakeGroupApi(long taskId) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).getShakeMakeGroup());
    }

    @Override
    public void onSuccessfulApi(long taskId, ShakeMakeGroupResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}

