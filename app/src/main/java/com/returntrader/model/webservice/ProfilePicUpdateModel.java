package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.ProfilePicUpdateRequest;
import com.returntrader.model.dto.response.ProfilePicUpdateResponse;
import com.returntrader.model.listener.IBaseModelListener;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by nirmal on 2/6/2018.
 */

public class ProfilePicUpdateModel extends BaseRetroFitModel<ProfilePicUpdateResponse> {
    private IBaseModelListener<ProfilePicUpdateResponse> iBaseModelListener;

    public ProfilePicUpdateModel(IBaseModelListener<ProfilePicUpdateResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    /***/
    public void updateProfilePic(long taskId, ProfilePicUpdateRequest request) {
        RequestBody userId = getRequestBody(request.getUser_id());
        MultipartBody.Part profile = getMultiFormData(request.getProfile(), "profile");
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).updateProfilePicture(userId, profile));
    }

    @Override
    public void onSuccessfulApi(long taskId, ProfilePicUpdateResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
