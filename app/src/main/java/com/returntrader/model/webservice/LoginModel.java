package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.EditProfileRequest;
import com.returntrader.model.dto.request.LoginRequest;
import com.returntrader.model.dto.response.LoginResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */


public class LoginModel extends BaseRetroFitModel<LoginResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<LoginResponse> iBaseModelListener;


    public LoginModel(IBaseModelListener<LoginResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void checkUserForLogin(long taskId,LoginRequest loginRequest) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).checkUserLogin(loginRequest));
    }

    public void checkUserPinForLogin(long taskId,LoginRequest loginRequest) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).checkUserPinLogin(loginRequest));
    }

    public void doLogin(LoginRequest loginRequest) {
        enQueueTask(0, ApiClient.getClient().create(ApiInterface.class).login(loginRequest));
    }

    public void doEditProfile(EditProfileRequest editProfileRequest) {
        enQueueTask(0, ApiClient.getClient().create(ApiInterface.class).editProfile(editProfileRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, LoginResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
