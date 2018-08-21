package com.returntrader.presenter.ipresenter;

/**
 * Created by Dell on 19-05-2017.
 */

public interface IOtpCodeVerifyPresenter extends IPresenter {

    void onClickVerifyOtp(String otp);


    void onClickResendOtp();
}
