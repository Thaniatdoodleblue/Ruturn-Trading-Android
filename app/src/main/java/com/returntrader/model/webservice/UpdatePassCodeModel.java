package com.returntrader.model.webservice;

import android.util.Log;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.model.dto.request.UpdatePasscodeRequest;
import com.returntrader.model.dto.response.FullRegisterResponse;
import com.returntrader.model.dto.response.UpdatePasscodeResponse;
import com.returntrader.model.listener.IBaseModelListener;

import static com.returntrader.common.Constants.RequestCodes.TASKID_DOFULLREGISTRATION;
import static com.returntrader.common.Constants.RequestCodes.TASKID_DOUPDATEPASSCODE;

/**
 * Created by nirmal on 3/30/2018.
 */

public class UpdatePassCodeModel extends BaseRetroFitModel<UpdatePasscodeResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<UpdatePasscodeResponse> iBaseModelListener;

    public UpdatePassCodeModel(IBaseModelListener<UpdatePasscodeResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    /***/
    public void dpUpdatePin(UpdatePasscodeRequest request) {
        enQueueTask(TASKID_DOUPDATEPASSCODE, ApiClient.getClient().create(ApiInterface.class).updatePasscode(request));
    }

    @Override
    public void onSuccessfulApi(long taskId, UpdatePasscodeResponse response) {
        Log.d(TAG, "onSuccessfulApi");
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        Log.d(TAG, "onFailureApi" + e.getMessage());
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
