package com.returntrader.presenter.ipresenter;

import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.ForgotPinRequest;

/**
 * Created by moorthy on 8/24/2017.
 */

public interface IForgotPinPresenter extends IPresenter {

    void onClickSubmit(ForgotPinRequest forgotPinRequest);

    void disableAuth(boolean canAuth);
}
