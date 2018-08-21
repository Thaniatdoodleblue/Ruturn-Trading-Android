package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.WithDrawalRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by nirmal on 2/5/2018.
 */

public class WithDrawalModel extends BaseRetroFitModel<BaseResponse>{
    private IBaseModelListener<BaseResponse> iBaseModelListener;

    public WithDrawalModel(IBaseModelListener<BaseResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void withDrawals(long taskId, WithDrawalRequest withDrawalRequest) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).withDrawals(withDrawalRequest));
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
