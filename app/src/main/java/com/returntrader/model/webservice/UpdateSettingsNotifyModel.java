package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.UpdateSettingsNotifyRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.listener.IBaseModelListener;


/**
 * Created by nirmal on 3/22/2018.
 */

public class UpdateSettingsNotifyModel extends BaseRetroFitModel<BaseResponse> {
    private IBaseModelListener<BaseResponse> iBaseModelListener;

    public UpdateSettingsNotifyModel(IBaseModelListener<BaseResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    /***/
    public void updateSettingsNotify(long taskId, UpdateSettingsNotifyRequest request) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).updateSettingsNotify(request));
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
