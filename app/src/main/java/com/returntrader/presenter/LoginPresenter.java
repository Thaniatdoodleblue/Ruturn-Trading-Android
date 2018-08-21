package com.returntrader.presenter;

import android.os.Bundle;

import com.returntrader.common.Constants;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.LoginRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.dto.response.LoginResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.LoginModel;
import com.returntrader.presenter.ipresenter.ILoginPresenter;
import com.returntrader.view.iview.ILoginView;

import static com.returntrader.common.Constants.BundleKey.BUNDLE_OTPLOGIN;
import static com.returntrader.common.Constants.RequestCodes.TASKID_LOGIN;

/**
 * Created by azeem on 10-Nov-17
 */

public class LoginPresenter extends BasePresenter implements ILoginPresenter {
    private ILoginView iLoginView;
    private LoginModel mLoginModel;

    public LoginPresenter(ILoginView iView) {
        super(iView);
        this.iLoginView = iView;
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        mLoginModel = new LoginModel(loginResponseIBaseModelListener);
    }

    /***/
    private IBaseModelListener<LoginResponse> loginResponseIBaseModelListener = new IBaseModelListener<LoginResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, LoginResponse response) {
            iLoginView.dismissProgressbar();
            if (response != null) {
                mLoginRequest.setUserId(response.getUserId());
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleKey.BUNDLE_AUTHENTICATION_FROM, Constants.AuthenticationType.FROM_LOGIN);
                bundle.putParcelable(Constants.BundleKey.BUNDLE_LOGIN_REQUEST, mLoginRequest);
                iLoginView.navigateToAuthentication(bundle);
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iLoginView.dismissProgressbar();
            iLoginView.showMessage(e.getException());
        }
    };


    private LoginRequest mLoginRequest;

    @Override
    public void doLogin(LoginRequest loginRequest) {
        if (iLoginView.getCodeSnippet().hasNetworkConnection()) {
            mLoginRequest = loginRequest;
            iLoginView.showProgressbar();
            mLoginModel.checkUserForLogin(TASKID_LOGIN,mLoginRequest);
        } else {
            iLoginView.showNetworkMessage();
        }
    }
}
