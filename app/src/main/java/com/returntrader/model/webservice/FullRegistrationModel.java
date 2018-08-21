package com.returntrader.model.webservice;

import android.util.Log;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.model.dto.request.UpdatePasscodeRequest;
import com.returntrader.model.dto.response.FullRegisterResponse;
import com.returntrader.model.listener.IBaseModelListener;

import static com.returntrader.common.Constants.RequestCodes.TASKID_DOFULLREGISTRATION;
import static com.returntrader.common.Constants.RequestCodes.TASKID_DOUPDATEPASSCODE;

/**
 * Created by moorthy on 7/21/2017.
 */


public class FullRegistrationModel extends BaseRetroFitModel<FullRegisterResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<FullRegisterResponse> iBaseModelListener;

    public FullRegistrationModel(IBaseModelListener<FullRegisterResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    /***/
    public void doFullRegistration(FullRegistrationRequest fullRegistrationRequest) {
        enQueueTask(TASKID_DOFULLREGISTRATION, ApiClient.getClient().create(ApiInterface.class).doFullRegistration(fullRegistrationRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, FullRegisterResponse response) {
        Log.d(TAG, "onSuccessfulApi");
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        Log.d(TAG, "onFailureApi" + e.getMessage());
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
