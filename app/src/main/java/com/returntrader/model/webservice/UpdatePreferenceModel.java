package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.AddressRequest;
import com.returntrader.model.dto.request.ProfileUpdateRequest;
import com.returntrader.model.dto.request.SettingPreferenceRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */


public class UpdatePreferenceModel extends BaseRetroFitModel<BaseResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<BaseResponse> iBaseModelListener;


    public UpdatePreferenceModel(IBaseModelListener<BaseResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void updatePreference(SettingPreferenceRequest settingPreferenceRequest) {
        enQueueTask(0, ApiClient.getClient().create(ApiInterface.class).updatePreference(settingPreferenceRequest));
    }

    public void updateAddress(AddressRequest addressRequest) {
        enQueueTask(1, ApiClient.getClient().create(ApiInterface.class).updateAddress(addressRequest));
    }

    public void sendProfileEditRequest(ProfileUpdateRequest profileUpdateRequest) {
        enQueueTask(1, ApiClient.getClient().create(ApiInterface.class).requestProfileUpdate(profileUpdateRequest));
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
