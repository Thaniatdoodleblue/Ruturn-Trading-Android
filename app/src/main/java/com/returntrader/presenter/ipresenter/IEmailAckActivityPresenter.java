package com.returntrader.presenter.ipresenter;

/**
 * Created by Dell on 19-05-2017.
 */

public interface IEmailAckActivityPresenter extends IPresenter {

    boolean isEmailVerified();

    void requestToVerifyEmail();

    void reSendEmail();
}
