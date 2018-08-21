package com.returntrader.presenter.ipresenter;

import com.returntrader.model.dto.request.LoginRequest;

/**
 * Created by azeem on 10-Nov-17
 */

public interface ILoginPresenter extends IPresenter {

    void doLogin(LoginRequest loginRequest);
}
