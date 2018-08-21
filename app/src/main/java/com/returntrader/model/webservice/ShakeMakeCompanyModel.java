package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.common.ShakeMakeGroupData;
import com.returntrader.model.dto.response.ShakeMakeCompanyResponse;
import com.returntrader.model.dto.response.ShakeMakeGroupResponse;
import com.returntrader.model.listener.IBaseModelListener;

public class ShakeMakeCompanyModel  extends BaseRetroFitModel<ShakeMakeCompanyResponse> {

    private IBaseModelListener<ShakeMakeCompanyResponse> iBaseModelListener;

    public ShakeMakeCompanyModel(IBaseModelListener<ShakeMakeCompanyResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getShakeMakeCompanyApi(long taskId,ShakeMakeGroupData shakeMakeGroupData) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).getShakeMakeCompany(shakeMakeGroupData));
    }

    @Override
    public void onSuccessfulApi(long taskId, ShakeMakeCompanyResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}