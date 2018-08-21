package com.returntrader.presenter.ipresenter;

import com.returntrader.model.dto.request.ForgotPinRequest;

/**
 * Created by moorthy on 11/6/2017.
 */

public interface IForgotPinVerifyPresenter extends IPresenter {

    void submitOtp(String otp);
}
